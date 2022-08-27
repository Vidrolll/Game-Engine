package com.tb2dge.main.gui.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.gui.GUIHandler;

public class Label extends GUIElement {
	boolean loopingImageBackground = false;
	
	Color textColor = Color.BLACK;
	String text;
	Font font;
	
	public Label(int x, int y, int width, int height, GUIHandler gHandler) {
		super(x, y, width, height, gHandler);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	public void setFont(String font, int style) {
		this.font = new Font(font,style,height);
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Font getFont() {
		return font;
	}
	public void setImageLooping(boolean bool) {
		this.loopingImageBackground = bool;
	}
	public Font scaleFontToFit(String text, int width, Graphics2D g, Font pFont) {
	    float fontSize = pFont.getSize();
	    float fWidth = g.getFontMetrics(pFont).stringWidth(text);
	    if(fWidth <= width) {
		    float fHeight = getFontHeight(g,pFont);
		    if(fHeight <= height)
		    	return pFont;
		    fontSize = (int)(((float)height / fHeight) * fontSize * 1.5);
		    return pFont.deriveFont(fontSize);
	    }
	    fontSize = ((float)width / fWidth) * fontSize;
	    return pFont.deriveFont(fontSize);
	}
	public int getFontHeight(Graphics2D g, Font font) {
		FontMetrics fm = g.getFontMetrics(font);
		return fm.getAscent() - fm.getDescent();
	}
	public int getFontWidth(Graphics2D g, Font font, String text) {
		return g.getFontMetrics(font).stringWidth(text);
	}

	@Override
	public void update() {
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
	}
	
	@Override
	public void render(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		if(font!=null) {
			font = scaleFontToFit(text,width - 5,g,font);
			g.setFont(font);
		}
		g.setColor(backgroundColor);
		if(!noBackground)g.fillRect(xOffset+(int)x, yOffset+(int)y, width, height);
		g.setColor(textColor);
		if(text!=null)g.drawString(text,xOffset+(int)x + 5,((int)(yOffset+y + (height / 2))) + (getFontHeight(g,font) / 2));
		g.setStroke(new BasicStroke(borderThickness));
		g.setColor(borderColor);
		if(!noBorder)g.drawRect(xOffset+(int)x, yOffset+(int)y, width, height);
		g.setStroke(oldStroke);
	}
}
