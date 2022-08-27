package com.tb2dge.main.graphics.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import java.util.Scanner;

import com.tb2dge.main.graphics.Textures;
import com.tb2dge.main.math.Vector2;

public class Animation {
	public HashMap<String,AnimationPart> parts = new HashMap<String,AnimationPart>();
	public HashMap<String,Runnable> commands = new HashMap<String,Runnable>();
	HashMap<Integer,Object[]> animFrames = new HashMap<Integer,Object[]>();
	public InputStream animationStream;
	public File animationFile;
	public BufferedImage animImage;
	public Vector2 pos;
	public Vector2 size;
	public int updatesPerFrame;
	public int update = 0;
	public int frames;
	public int frame;
	int loops;
	int currentLoop = 0;
	
	int zoomX,zoomY,zoomAmount=1;
	int clipX,clipY,clipSizeX,clipSizeY;
	
	String changeType;
	
	public String[] data;
	
	public Animation(InputStream stream) {
		animationStream = stream;
		setupCommands();
		setupFile();
	}
	public Animation(File file) {
		animationFile = file;
		setupCommands();
		setupFile();
	}
	
	public void setZoom(int zoomAmount, int zoomX, int zoomY) {
		this.zoomAmount = zoomAmount;
		this.zoomX = zoomX;
		this.zoomY = zoomY;
	}
	
	public void setClip(int x, int y, int sizeX, int sizeY) {
		this.clipX = x;
		this.clipY = y;
		this.clipSizeX = sizeX;
		this.clipSizeY = sizeY;
	}
	
	public void setLoops(int loops) {
		this.loops = loops;
	}
	
	public void play() {
		currentLoop = 0;
		frame = 0;
	}
	
	public void stop() {
		frame = frames+1;
		currentLoop = loops;
	}
	
	public void init() {
		for(int i = 0; i <= frames; i++) {
			Object[] info = new Object[5];
			info[0] = null; info[1] = null; info[2] = null; info[3] = null; info[4] = null;
			animFrames.put(i,info);
		}
	}
	
	public void setupCommands() {
		commands.put("setImage", () -> setImage());
		commands.put("addPart", () -> addPart());
		commands.put("animData", () -> animData());
		commands.put("info", () -> info());
		
		commands.put("move", () -> move());
		commands.put("size", () -> size());
		commands.put("transparency", () -> transparency());
		commands.put("rotate", () -> rotate());
	}
	
	public void setupFile() {
		Scanner fileReader;
		try {
			if(animationFile != null)fileReader = new Scanner(animationFile);
			else fileReader = new Scanner(animationStream);
			while(fileReader.hasNext()) {
				String line = fileReader.nextLine();
				if(line.equals("")) continue;
				if(line.contains("//")) continue;
				if(line.contains("(")) {
					String cmd = line.substring(0,line.indexOf("("));
					data = line.substring(line.indexOf("(")+1,line.indexOf(")")).split(",");
					commands.get(cmd).run();;
				}
			}
			fileReader.close();
			for(Entry<String,AnimationPart> part : parts.entrySet()) {
				AnimationPart animPart = part.getValue();
				animPart.setup();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		BufferStrategy bs = Animations.game.getBufferStrategy();
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		if(clipSizeX!=0&&clipSizeY!=0)g.clipRect(clipX,clipY,clipSizeX,clipSizeY);
		g.translate(zoomX,zoomY);
		g.scale(zoomAmount,zoomAmount);
		g.translate(-zoomX,-zoomY);
		for(Entry<String,AnimationPart> part : parts.entrySet()) {
			AnimationPart animPart = part.getValue();
			animPart.render(g);
		}
		g.setClip(null);
		g.dispose();
	}
	
	public void update() {
		if(currentLoop > loops && loops != -1) return;
		update++;
		if(update % updatesPerFrame == 0) {
			frame++;
			if(loops != -1) currentLoop++;
			if(frame > frames) {
				frame = 0;
			}
			for(Entry<String,AnimationPart> part : parts.entrySet()) {
				AnimationPart animPart = part.getValue();
				animPart.update();
			}
			for(Entry<String,AnimationPart> part : parts.entrySet()) {
				AnimationPart animPart = part.getValue();
				Object[] data = animFrames.get(frame);
				if(data[0] != null) animPart.offset = (Vector2)data[0];
				if(data[1] != null) animPart.sizeOffset = (Vector2)data[1];
				if(data[2] != null) animPart.transparency = (int)data[2];
				if(data[3] != null) animPart.rotation = (int)data[3];
				if(data[4] != null) animPart.pivotPoint = (Vector2)data[4];
			}
		}
	}
	
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public void setImage() {
		if(Textures.getTexture(data[0])!=null) animImage = Textures.getTexture(data[0]);
		else {
			try {
				animImage=ImageIO.read(new File(data[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void addPart() {
		int x1 = Integer.parseInt(data[1]);
		int y1 = Integer.parseInt(data[2]);
		int x2 = Integer.parseInt(data[3]);
		int y2 = Integer.parseInt(data[4]);
		int posX = Integer.parseInt(data[5]);
		int posY = Integer.parseInt(data[6]);
		int sizeX = Integer.parseInt(data[7]);
		int sizeY = Integer.parseInt(data[8]);
		AnimationPart animPart = new AnimationPart(crop(x1,y1,x2,y2),
				new Vector2(posX,posY),new Vector2(sizeX,sizeY),x1,y1,x2,y2,this);
		parts.put(data[0], animPart);
	}
	public void animData() {
		String part = data[0];
		if(!part.equals("all")) {
			parts.get(part).animData.add(data);
		} else {
			String animType = data[1];
			String changeType = data[2];
			this.changeType = changeType;
			commands.get(animType).run();
		}
	}
	public void info() {
		updatesPerFrame = 60 / Integer.parseInt(data[0]);
		frames = Integer.parseInt(data[1]);
		pos = new Vector2(Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		init();
	}
	public BufferedImage crop(int x1, int y1, int x2, int y2) {
		return animImage.getSubimage(x1, y1, x2-x1, y2-y1);
	}
	
	public void move() {
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
	public void transparency() {
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
	public void rotate() {
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
	public void size() {
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
