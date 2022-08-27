package com.tb2dge.main.graphics.animation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

import com.tb2dge.main.graphics.effects.Grayscale;
import com.tb2dge.main.graphics.effects.Tint;
import com.tb2dge.main.math.Vector2;

public class AnimationPart {
	LinkedList<String[]> animData = new LinkedList<String[]>();
	HashMap<Integer,Object[]> animFrames = new HashMap<Integer,Object[]>();
	public BufferedImage part;
	Vector2 pos;
	Vector2 size;
	
	int x1,y1,x2,y2;
	
	Animation parentAnim;
	
	Grayscale gScale = new Grayscale();
	Tint tint = new Tint();
	
	Vector2 offset;
	Vector2 sizeOffset;
	Vector2 pivotPoint;
	int transparency=0;
	int rotation;
	Color color;
	
	public AnimationPart(BufferedImage part, Vector2 pos, Vector2 size, int x1, int y1, int x2, int y2, Animation parentAnim) {
		this.part = part;
		this.pos = pos;
		this.size = size;
		this.parentAnim = parentAnim;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void setup() {
		init();
		for(int i = 0; i < animData.size(); i++) {
			String[] data = animData.get(i);
			String animType = data[1];
			changeData(animType,data);
		}
	}
	
	public void init() {
		for(int i = 0; i <= parentAnim.frames; i++) {
			Object[] info = new Object[6];
			info[0] = null; info[1] = null; info[2] = null; info[3] = null; info[4] = null; info[5] = null;
			animFrames.put(i,info);
		}
	}
	
	public int changeData(String animType, String[] data) {
		if(animType.equals(AnimationType.MOVE)) move(data);
		if(animType.equals(AnimationType.SIZE)) size(data);
		if(animType.equals(AnimationType.TRANSPARENCY)) transparency(data);
		if(animType.equals(AnimationType.ROTATE)) rotate(data);
		if(animType.equals(AnimationType.COLOR)) color(data);
		return 0;
	}
	
	public void render(Graphics2D g) {
		AlphaComposite old = (AlphaComposite)g.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(transparency/100.0f));
		if(sizeOffset != null) {
			g.scale((sizeOffset.getX()/10.0), (sizeOffset.getY()/10.0));
		}
		if(offset!=null) {
			g.translate(offset.getX(),offset.getY());
		}
		if(color != null) {
			part = parentAnim.crop(x1, y1, x2, y2);
			//part = gScale.grayScale(part);
			part = tint.tint(part, color);
		}
		if(rotation != 0) {
			g.translate(parentAnim.pos.getX()+pos.getX()+offset.getX()+pivotPoint.getX(), parentAnim.pos.getY()+pos.getY()+offset.getY()+pivotPoint.getY());
			g.rotate(Math.toRadians(rotation));
			g.translate(-(parentAnim.pos.getX()+pos.getX()+offset.getX()+pivotPoint.getX()), -(parentAnim.pos.getY()+pos.getY()+offset.getY()+pivotPoint.getY()));
		}
		g.setComposite(ac);
		g.drawImage(part, (int)(parentAnim.pos.getX()+pos.getX()), (int)(parentAnim.pos.getY()+pos.getY()), 
				(int)(size.getX()), (int)(size.getY()), null);
		g.setComposite(old);
		if(rotation != 0) {
			g.translate(pos.getX()+offset.getX()+pivotPoint.getX(), pos.getY()+offset.getY()+pivotPoint.getY());
			g.rotate(Math.toRadians(-rotation));
			g.translate(-(pos.getX()+offset.getX()+pivotPoint.getX()), -(pos.getY()+offset.getY()+pivotPoint.getY()));
		}
		if(offset!=null) {
			g.translate(-(offset.getX()),-(offset.getY()));
		}
		if(sizeOffset != null) {
			g.scale(-(sizeOffset.getX()/10.0), -(sizeOffset.getY()/10.0));
		}
	}
	
	public void update() {
		if(parentAnim.frame == 0) reset();
		Object[] data = animFrames.get(parentAnim.frame);
		if(data[0] != null) offset = (Vector2)data[0];
		if(data[1] != null) sizeOffset = (Vector2)data[1];
		if(data[2] != null) transparency = (int)data[2];
		if(data[3] != null) rotation = (int)data[3];
		if(data[4] != null) pivotPoint = (Vector2)data[4];
		if(data[5] != null) color = (Color)data[5];
	}
	
	public void reset() {
		offset = null;
		sizeOffset = null;
		transparency = 0;
		rotation = 0;
		color = null;
		//part = parentAnim.crop(x1, y1, x2, y2);
	}
	
	public void move(String[] data) {
		String changeType = data[2];
		int startingFrame = Integer.parseInt(data[3]);
		int endingFrame = (!changeType.equals(AnimationType.CUSTOM)) ? Integer.parseInt(data[4]) : 0;
		int x1 = (!changeType.equals(AnimationType.CUSTOM)) ? Integer.parseInt(data[5])-(int)pos.getX() : Integer.parseInt(data[4])-(int)pos.getX();
		int y1 = (!changeType.equals(AnimationType.CUSTOM)) ? Integer.parseInt(data[6])-(int)pos.getY() : Integer.parseInt(data[5])-(int)pos.getY();
		int x2 = (!changeType.equals(AnimationType.CUSTOM)) ? Integer.parseInt(data[7])-(int)pos.getX() : 0;
		int y2 = (!changeType.equals(AnimationType.CUSTOM)) ? Integer.parseInt(data[8])-(int)pos.getY() : 0;
		switch(changeType) {
			case AnimationType.LINEAR:
				for(int i = 0; i < endingFrame-startingFrame; i++) {
					int frame = startingFrame+i;
					animFrames.get(frame)[0] = linearPos(new Vector2(x1,y1),new Vector2(x2,y2),endingFrame-startingFrame,i);
				}
				break;
			case AnimationType.QUADRATIC:
				for(int i = 0; i < endingFrame-startingFrame; i++) {
					int frame = startingFrame+i;
					animFrames.get(frame)[0] = quadraticPos(new Vector2(x1,y1),new Vector2(x2,y2),endingFrame-startingFrame,i);
				}
				break;
			case AnimationType.CUSTOM:
				animFrames.get(startingFrame)[0] = new Vector2(x1,y1);
				break;
		}
	}
	public void transparency(String[] data) {
		String changeType = data[2];
		int startingFrame = Integer.parseInt(data[3]);
		int endingFrame = Integer.parseInt(data[4]);
		int val1 = Integer.parseInt(data[5]);
		int val2 = Integer.parseInt(data[6]);
		switch(changeType) {
			case AnimationType.LINEAR:
				for(int i = 0; i < endingFrame - startingFrame; i++) {
					animFrames.get(startingFrame+i)[2] = (int)linearPos(new Vector2(val1,val1),new Vector2(val2,val2),endingFrame-startingFrame,i).getX();
				}
				break;
			case AnimationType.QUADRATIC:
				for(int i = 0; i < endingFrame - startingFrame; i++) {
					animFrames.get(startingFrame+i)[2] = (int)quadraticPos(new Vector2(val1,val1),new Vector2(val2,val2),endingFrame-startingFrame,i).getX();
				}
				break;
			case AnimationType.CUSTOM:
				animFrames.get(startingFrame)[2] = val2;
				break;
		}
	}
	public void rotate(String[] data) {
		String changeType = data[2];
		int startingFrame = Integer.parseInt(data[3]);
		int endingFrame = Integer.parseInt(data[4]);
		int pivotX = Integer.parseInt(data[5]);
		int pivotY = Integer.parseInt(data[6]);
		int val1 = Integer.parseInt(data[7]);
		int val2 = Integer.parseInt(data[8]);
		switch(changeType) {
			case AnimationType.LINEAR:
				for(int i = 0; i < endingFrame - startingFrame; i++) {
					animFrames.get(startingFrame+i)[3] = (int)linearPos(new Vector2(val1,val1),new Vector2(val2,val2),endingFrame-startingFrame,i).getX();
					animFrames.get(startingFrame+i)[4] = new Vector2(pivotX,pivotY);
				}
				break;
			case AnimationType.QUADRATIC:
				for(int i = 0; i < endingFrame - startingFrame; i++) {
					animFrames.get(startingFrame+i)[3] = (int)quadraticPos(new Vector2(val1,val1),new Vector2(val2,val2),endingFrame-startingFrame,i).getX();
					animFrames.get(startingFrame+i)[4] = new Vector2(pivotX,pivotY);
				}
				break;
			case AnimationType.CUSTOM:
				animFrames.get(startingFrame)[3] = val2;
				animFrames.get(startingFrame)[4] = new Vector2(pivotX,pivotY);
				break;
		}
	}
	public void size(String[] data) {
		String changeType = data[2];
		int startingFrame = Integer.parseInt(data[3]);
		int endingFrame = Integer.parseInt(data[4]);
		int x1 = Integer.parseInt(data[5]);
		int y1 = Integer.parseInt(data[6]);
		int x2 = Integer.parseInt(data[7]);
		int y2 = Integer.parseInt(data[8]);
		switch(changeType) {
			case AnimationType.LINEAR:
				for(int i = 0; i < endingFrame-startingFrame; i++) {
					int frame = startingFrame+i;
					animFrames.get(frame)[1] = linearPos(new Vector2(x1,y1),new Vector2(x2,y2),endingFrame-startingFrame,i);
				}
				break;
			case AnimationType.QUADRATIC:
				for(int i = 0; i < endingFrame-startingFrame; i++) {
					int frame = startingFrame+i;
					animFrames.get(frame)[1] = quadraticPos(new Vector2(x1,y1),new Vector2(x2,y2),endingFrame-startingFrame,i);
				}
				break;
			case AnimationType.CUSTOM:
				animFrames.get(startingFrame)[1] = new Vector2(x1,y1);
				break;
		}	
	}
	public void color(String[] data) {
		int frame = Integer.parseInt(data[2]);
		int r = Integer.parseInt(data[3]);
		int g = Integer.parseInt(data[4]);
		int b = Integer.parseInt(data[5]);
		int a = Integer.parseInt(data[6]);
		animFrames.get(frame)[5] = new Color(r,g,b,a);
	}
	
	public Vector2 quadraticPos(Vector2 startingPos, Vector2 endingPos, int frames, int currentFrame) {
		Vector2 pos = new Vector2(0,0);
		double bx = endingPos.getX() - startingPos.getX();
		double ax = (bx)/(-(Math.pow(frames,2)))*1.0;
		double by = endingPos.getY() - startingPos.getY();
		double ay = (by)/(-(Math.pow(frames,2)))*1.0;
		pos.setX(startingPos.getX() + (((ax*Math.pow(((currentFrame-frames)),2))+bx)));
		pos.setY(startingPos.getY() + (((ay*Math.pow(((currentFrame-frames)),2))+by)));
		return pos;
	}
	public Vector2 linearPos(Vector2 startingPos, Vector2 endingPos, int frames, int frame) {
		Vector2 pos = new Vector2(0,0);
		double rateOfChangeX = (double)(endingPos.getX()-startingPos.getX())/(double)(frames);
		double rateOfChangeY = (double)(endingPos.getY()-startingPos.getY())/(double)(frames);
		pos.setX(startingPos.getX()+(rateOfChangeX*frame));
		pos.setY(startingPos.getY()+(rateOfChangeY*frame));
		return pos;
	}
}
