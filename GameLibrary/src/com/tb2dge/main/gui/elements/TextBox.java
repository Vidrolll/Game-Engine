package com.tb2dge.main.gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class TextBox extends GUIElement implements Inputable {
	boolean loopingImageBackground = false;
	public boolean barShowing = true;
	public boolean selected = false;
	public boolean typing = false;
	int inputIndex = 0;
	int storedNum = 0;
	int inputNum = 0;
	int scrollLimit;
	int maxLimit = -1;
	int showCycle = 0;

	Color textColor = Color.BLACK;
	String storedText = "";
	Label textInput;
	
	Runnable typeEvent;
	
	public TextBox(int x, int y, int width, int height, GUIHandler gHandler) {
		super(x, y, width, height, gHandler);
		textInput = new Label(x,y,width,height,gHandler);
		scrollLimit = width - (width / 3);
		textInput.setText("");
		updateLabel();
	}
	
	@Override
	public void setBorderThickness(int borderThickness) {
		super.setBorderThickness(borderThickness);
		textInput.setBorderThickness(borderThickness);
	}
	public void setMax(int max) {
		this.maxLimit = max;
	}
	public void setScroll(int scrollLimit) {
		this.scrollLimit = scrollLimit;
	}
	public void setText(String text) {
		textInput.setText(text);
		storedText = text;
	}
	public void setTextColor(Color color) {
		this.textColor = color;
	}
	public Label getLabel() {
		return textInput;
	}
	
	@Override
	public void input(KeyEvent e, KeyType kt) {
		if(selected) {
			showCycle = 0;
			typing = true;
			if(kt == KeyType.KeyReleased) keyPressed(e);
			if(kt == KeyType.KeyTyped) keyTyped(e);
			try {
				typeEvent.run();
			} catch(Exception error) {}
			Graphics2D g = new BufferedImage(width, height, BufferedImage.TRANSLUCENT).createGraphics();
			int xIndex = (int)x+xOffset + 5;
			for(int i = 0; i < textInput.getText().length(); i++) {
				String character = textInput.getText().substring(i, i+1);
				int charLength = textInput.getFontWidth(g,textInput.scaleFontToFit(textInput.getText(), width, g, textInput.getFont()), character);
				if(i == inputNum) {
					inputIndex = xIndex;
					return;
				}
				xIndex+=(charLength);
			}
			inputIndex = xIndex;
		}
	}
	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		if(!(mt == MouseType.MouseClicked)) return;
		if(new Rectangle((int)x+xOffset,(int)y+yOffset,width,height).contains(new Point(mouseX,mouseY)) && e.getButton() == 1) {
			selected = true;
			Graphics2D g = new BufferedImage(width, height, BufferedImage.TRANSLUCENT).createGraphics();
			int xIndex = (int)x+xOffset + 5;
			if((textInput.getText().equals(storedText)))storedNum = 0;
			else storedNum-=inputNum;
			inputNum = 0;
			showCycle = 0;
			for(int i = 0; i < textInput.getText().length(); i++) {
				String character = textInput.getText().substring(i, i+1);
				int charLength = textInput.getFontWidth(g,textInput.scaleFontToFit(textInput.getText(), width, g, textInput.getFont()), character);
				xIndex+=(charLength/2);
				if(xIndex > e.getX()+xOffset) {
					inputIndex = xIndex-(charLength/2);
					return;
				}
				xIndex+=(charLength/2);
				inputNum++;
				storedNum++;
			}
			inputIndex = xIndex;
		} else if(e.getButton() == 1) {
			selected = false;
		}
	}
	
	public void typeEvent(Runnable typeEvent) {
		this.typeEvent = typeEvent;
	}
	
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()!='') {
			if(textInput.getText().length()==maxLimit&&maxLimit>0) return;
			textInput.setText(textInput.getText().substring(0,inputNum) + e.getKeyChar() + textInput.getText().substring(inputNum));
			storedText = (storedText.substring(0,storedNum) + e.getKeyChar() + storedText.substring(storedNum));
			storedNum++;
			inputNum++;
			Graphics2D g = new BufferedImage(width, height, BufferedImage.TRANSLUCENT).createGraphics();
			if(textInput.getFontWidth(g,textInput.scaleFontToFit(textInput.getText(), width, g, textInput.getFont()), textInput.getText()) > scrollLimit
					&& inputNum == textInput.getText().length()) {
				textInput.setText(textInput.getText().substring(1));
				inputNum--;
			} else if(textInput.getFontWidth(g,textInput.scaleFontToFit(textInput.getText(), width, g, textInput.getFont()), textInput.getText()) > scrollLimit){
				int beginIndex = storedNum - inputNum;
				int endIndex = (storedNum - inputNum) + textInput.getText().length() - 1;
				textInput.setText(storedText.substring(beginIndex,endIndex));
			}
		} else if(inputNum!=0&&(textInput.getText().equals(storedText))){ 
			textInput.setText(textInput.getText().substring(0,inputNum-1) + textInput.getText().substring(inputNum));
			storedText = (storedText.substring(0,storedNum-1) + storedText.substring(storedNum));
			storedNum--;
			inputNum--;
		} else if(inputNum!=0) {
			int beginIndex = storedNum - (inputNum + 1);
			int endIndex = (storedNum + (textInput.getText().length() - inputNum))-1;
			if(beginIndex < 0) {
				beginIndex = 0;
				endIndex = (storedNum + (textInput.getText().length() - inputNum));
				inputNum--;
			}
			if(endIndex > storedText.length()-1) endIndex = storedText.length()-1;
			if(endIndex < 0) endIndex = 0;
			storedText = (storedText.substring(0,storedNum-1) + storedText.substring(storedNum));
			textInput.setText(storedText.substring(beginIndex,endIndex));
			storedNum--;
		}
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT && inputNum!=0) {
				inputNum--;
				storedNum--;
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT && inputNum==0 && storedNum!=0) {
				textInput.setText(storedText.substring(storedNum-1,storedNum + textInput.getText().length()-1));
				storedNum--;
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && inputNum!=textInput.getText().length()) {
				inputNum++;
				storedNum++;
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && inputNum==textInput.getText().length() 
					&& storedNum!=storedText.length()) {
				if(storedNum + textInput.getText().length() < storedText.length()) {
					textInput.setText(storedText.substring(storedNum,storedNum + textInput.getText().length()));
					inputNum = 0;
				} else {
					int diff = storedText.length() - storedNum;
					textInput.setText(storedText.substring(storedText.length() - textInput.getText().length()));
					inputNum = textInput.getText().length() - diff;
				}
			}
			Graphics2D g = new BufferedImage(width, height, BufferedImage.TRANSLUCENT).createGraphics();
			int xIndex = (int)x + 5;
			for(int i = 0; i < textInput.getText().length(); i++) {
				String character = textInput.getText().substring(i, i+1);
				int charLength = textInput.getFontWidth(g,textInput.scaleFontToFit(textInput.getText(), width, g, textInput.getFont()), character);
				if(i == inputNum) {
					inputIndex = xIndex;
					return;
				}
				xIndex+=(charLength);
			}
			inputIndex = xIndex;
			return;
		}
	}
	public void updateLabel() {
		textInput.setX((int)x);
		textInput.setY((int)y);
		textInput.setOffsetX(xOffset);
		textInput.setOffsetY(yOffset);
		textInput.setBackground(!noBackground);
		textInput.setBorder(!noBorder);
		textInput.setBackgroundColor(backgroundColor);
		textInput.setBorderColor(borderColor);
		textInput.setImageBackground(imageBackground);
		textInput.setImageLooping(loopingImageBackground);
		textInput.setTextColor(textColor);
		textInput.setFont(Font.DIALOG_INPUT, Font.PLAIN);
	}
	@Override
	public void update() {
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
		updateLabel();
		textInput.update();
		if(showCycle == 5) return;
		if(System.currentTimeMillis() % 1000.0 == 500 && selected && barShowing == true && !typing) {
			barShowing = false;
		} else if(System.currentTimeMillis() % 1000.0 == 0 && selected && barShowing == false || typing) {
			barShowing = true;
			showCycle++;
			typing = false;
		}
	}
	@Override
	public void render(Graphics2D g) {
		textInput.render(g);
		g.setColor(Color.BLACK);
		if(selected && barShowing) g.fillRect(inputIndex + 1, yOffset + (int)y + (height / 2) - (textInput.getFontHeight(g,textInput.getFont()) / 2), 
				1, textInput.getFontHeight(g,textInput.getFont()));
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
}
