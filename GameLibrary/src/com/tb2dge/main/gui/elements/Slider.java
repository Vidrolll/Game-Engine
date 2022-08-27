package com.tb2dge.main.gui.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class Slider extends GUIElement implements Inputable {
	Color backgroundColor;
	Color borderColor;
	Color sliderColor;
	int minValue, maxValue;
	double valueIncrements;
	double incrementation = 1.0;
	int selectedValue;
	int roundness;
	float thickness;
	double sliderScale;
	boolean selected;
	
	public Slider(int x, int y, int width, int height, GUIHandler gHandler, int minValue, int maxValue, int starterValue) {
		super(x+(height/2), y, Math.max(width-height,1), height, gHandler);
		if(width-height<1)width=height+1;
		maxValue--;
		minValue--; 
		this.minValue = minValue;
		this.maxValue = maxValue - minValue;
		this.valueIncrements = (((double)width-height) / ((double)this.maxValue))*1.0;
		this.selectedValue = starterValue - minValue - 1;
		this.roundness = height;
		this.thickness = 1;
		this.sliderScale = 0;
		backgroundColor = new Color(35,35,35);
		borderColor = new Color(255,255,255,0);
		sliderColor = new Color(54,54,54);
		selected = false;
	}
	public Slider(int x, int y, int width, int height, GUIHandler gHandler, int minValue, int maxValue, int starterValue, double incrementation) {
		this(x,y,width,height,gHandler,minValue,maxValue,starterValue);
		this.incrementation = incrementation;
	}

	public double getValue() {
		return (selectedValue + minValue + 1)*incrementation;
	}
	public void setIncrementation(double incrementation) {
		this.incrementation = incrementation;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	public void setSliderColor(Color sliderColor) {
		this.sliderColor = sliderColor;
	}
	public void setRoundness(int roundness) {
		this.roundness = roundness;
	}
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	public void setSliderScale(double sliderScale) {
		this.sliderScale = sliderScale;
	}

	@Override
	public void input(KeyEvent e, KeyType kt) {
		if(kt != KeyType.KeyPressed) return;
		if(selected) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) selectedValue--;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) selectedValue++;
			if(selectedValue > maxValue) selectedValue = maxValue;
			if(selectedValue < 0) selectedValue = 0;
		}
	}
	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		if(mt != MouseType.MouseDragged && mt != MouseType.MouseClicked) return;
		if(mouseX > x+xOffset-(height/2) && mouseX < x+xOffset-(height/2) + width+height && mouseY > y+yOffset && mouseY < y+yOffset + height) {
			selected = true;
			int clickX = (mouseX - (int)x+xOffset);
			double segment = (clickX * ((double)maxValue / (double)(width)))*1.0;
			selectedValue = (int)Math.round(segment);
			if(selectedValue > maxValue) selectedValue = maxValue;
			if(selectedValue < 0) selectedValue = 0;
		} else {
			selected = false;
		}
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
	@Override
	public void update() {
		
	}
	@Override
	public void render(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		BasicStroke newStroke = new BasicStroke(thickness);
		g.setColor(backgroundColor);
		g.fillRoundRect(xOffset+(int)x-(height/2), yOffset+(int)y, width+height, height, roundness, roundness);
		g.setColor(sliderColor);
		g.fillRoundRect((int)(xOffset+(int)x + (selectedValue * valueIncrements)-(height*(sliderScale/2))-(height/2)), (int)(yOffset+y-(height*(sliderScale/2)))
				, (int)(height*(sliderScale+1)), (int)(height*(sliderScale+1)), 
				(int)(roundness*(sliderScale+1)), (int)(roundness*(sliderScale+1)));
		g.setColor(borderColor);
		g.setStroke(newStroke);
		g.drawRoundRect(xOffset+(int)x-(height/2), yOffset+(int)y, width+height, height, roundness, roundness);
		g.setStroke(oldStroke);
	}
}
