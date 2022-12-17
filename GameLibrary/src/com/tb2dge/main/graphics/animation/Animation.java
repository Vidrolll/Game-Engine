package com.tb2dge.main.graphics.animation;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.graphics.Textures;
import com.tb2dge.main.util.json.JSONArray;
import com.tb2dge.main.util.json.JSONFile;
import com.tb2dge.main.util.json.JSONObject;

public class Animation {
	BufferedImage[] animationParts;
	int[][] xValues; int[][] yValues;
	int[][] widths; int[][] heights;
	double[][] rotation; int[][] pivotX; int[][] pivotY;
	double[][] transparency;
	int frames;
	int fps;
	int currentFrame = 0;
	int currentUpdate = 0;
	BufferedImage animImage;
	JSONFile file;
	int width, height;
	boolean looping;
	
	public Animation(JSONFile file) {
		this.file = file;
		animationParts = new BufferedImage[file.getObjects().length-2];
		JSONObject animData = file.getObjects()[0];
		frames = animData.getInt("frames");
		fps = animData.getInt("fps");
		if(Textures.getTexture(animData.get("image_file"))!=null)
			animImage = Textures.getTexture(animData.get("image_file"));
		else {
			try {
				animImage = ImageIO.read(new File(animData.get("image_file")));
			} catch(Exception e) {}
		}
		width = animData.getInt("width");
		height = animData.getInt("height");
		xValues = new int[frames][animationParts.length];
		yValues = new int[frames][animationParts.length];
		widths = new int[frames][animationParts.length];
		heights = new int[frames][animationParts.length];
		rotation = new double[frames][animationParts.length];
		pivotX = new int[frames][animationParts.length];
		pivotY = new int[frames][animationParts.length];
		transparency = new double[frames][animationParts.length];
		looping = animData.getBool("looping");
		setupParts();
	}
	
	public void setupParts() {
		JSONObject[] animParts = file.getObjects();
		for(int i = 0; i < animationParts.length; i++) {
			JSONObject animPart = animParts[i+1];
			JSONArray subImage = animPart.getArray("image");
			animationParts[animPart.getInt("id")] = animImage.getSubimage(
					subImage.getInt(0),subImage.getInt(1), 
					subImage.getInt(2)-subImage.getInt(0), 
					subImage.getInt(3)-subImage.getInt(1));
			xValues[0][animPart.getInt("id")] = animPart.getInt("x");
			yValues[0][animPart.getInt("id")] = animPart.getInt("y");
			widths[0][animPart.getInt("id")] = animPart.getInt("width");
			heights[0][animPart.getInt("id")] = animPart.getInt("height");
			setupAnimData(animPart.getInt("id"),animPart);
		}
	}
	
	public void setupAnimData(int id, JSONObject obj) {
		for(int i = 0; i < frames; i++) {
			if(obj.getArray(""+i)!=null) {
				JSONArray frame = obj.getArray(""+i);
				String dataType = frame.get(0);
				String animType = frame.get(1);
				int beginningFrame = i-1;
				if(beginningFrame<0) beginningFrame = 0;
				int endingFrame = frame.getInt(2);
				if(endingFrame > frames-1) endingFrame = frames-1;
				for(int j = beginningFrame; j < endingFrame; j++) {
					if(dataType.equals("position")) {
						int beginX = xValues[beginningFrame][id];
						int beginY = yValues[beginningFrame][id];
						int endX = frame.getInt(3);
						int endY = frame.getInt(4);
						if(animType.equals("linear")) {
							xValues[j][id] = linearValue(beginX,endX,beginningFrame-endingFrame,j);
							yValues[j][id] = linearValue(beginY,endY,beginningFrame-endingFrame,j);
						}
						if(animType.equals("quadratic")) {
							xValues[j][id] = quadraticValue(beginX,endX,beginningFrame-endingFrame,j);
							yValues[j][id] = quadraticValue(beginY,endY,beginningFrame-endingFrame,j);
						}
					}
					if(dataType.equals("size")) {
						int beginX = xValues[beginningFrame][id];
						int beginY = yValues[beginningFrame][id];
						int endX = frame.getInt(3);
						int endY = frame.getInt(4);
						if(animType.equals("linear")) {
							widths[j][id] = linearValue(beginX,endX,beginningFrame-endingFrame,j);
							heights[j][id] = linearValue(beginY,endY,beginningFrame-endingFrame,j);
						}
						if(animType.equals("quadratic")) {
							widths[j][id] = quadraticValue(beginX,endX,beginningFrame-endingFrame,j);
							heights[j][id] = quadraticValue(beginY,endY,beginningFrame-endingFrame,j);
						}
					}
					if(dataType.equals("transparency")) {
						double beginValue = transparency[beginningFrame][id];
						double endValue = frame.getDouble(3);
						if(animType.equals("linear")) 
							transparency[j][id] = linearValue(beginValue,endValue,beginningFrame-endingFrame,j);
						if(animType.equals("quadratic")) 
							transparency[j][id] = quadraticValue(beginValue,endValue,beginningFrame-endingFrame,j);
					}
					if(dataType.equals("rotation")) {
						pivotX[j][id] = frame.getInt(3);
						pivotY[j][id] = frame.getInt(4);
						double beginValue = rotation[beginningFrame][id];
						double endValue = frame.getDouble(5);
						if(animType.equals("linear")) 
							rotation[j][id] = linearValue(beginValue,endValue,beginningFrame-endingFrame,j);
						if(animType.equals("quadratic")) 
							rotation[j][id] = quadraticValue(beginValue,endValue,beginningFrame-endingFrame,j);
					}
				}
			}
		}
	}
	
	public int linearValue(int beginningValue, int endingValue, int frames, int currentFrame) {
		return ((endingValue-beginningValue)/frames)*currentFrame;
	}
	public double linearValue(double beginningValue, double endingValue, int frames, int currentFrame) {
		return ((endingValue-beginningValue)/frames)*currentFrame;
	}
	public int quadraticValue(int beginningValue, int endingValue, int frames, int currentFrame) {
		int b = frames;
		int c = beginningValue - endingValue;
		int a = c / (b*b);
		return (int)((-a * Math.pow(currentFrame-b,2))+c);
	}
	public double quadraticValue(double beginningValue, double endingValue, int frames, int currentFrame) {
		double b = frames;
		double c = beginningValue - endingValue;
		double a = c / (b*b);
		return ((-a * Math.pow(currentFrame-b,2))+c);
	}
	
	public void updateFrames() {
		currentUpdate++;
		if(currentUpdate == EngineSettings.UPDATE_RATE / fps) {
			currentUpdate = 0;
			currentFrame++;
			if(currentFrame == frames) {
				if(looping) currentFrame = 0;
			}
		}
	}
	
	public void drawAnimation(Graphics2D g) {
		for(int i = 0; i < animationParts.length; i++) {
			AlphaComposite old = (AlphaComposite)g.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER,(float)transparency[currentFrame][i]);
			g.setComposite(ac);
			g.rotate(Math.toRadians(rotation[currentFrame][i]),
					pivotX[currentFrame][i],pivotY[currentFrame][i]);
			g.drawImage(animationParts[i],xValues[currentFrame][i],yValues[currentFrame][i],
					widths[currentFrame][i],heights[currentFrame][i],null);
			g.rotate(-Math.toRadians(rotation[currentFrame][i]),
					pivotX[currentFrame][i],pivotY[currentFrame][i]);
			g.setComposite(old);
		}
	}
}