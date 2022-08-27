package com.tb2dge.main.gui.elements;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import com.tb2dge.main.graphics.Colors;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.gui.util.DataTypes;
import com.tb2dge.main.gui.windows.GUIPanel;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class FileExplorer extends GUIElement implements Inputable {
	GUIPanel panel;
	GUIPanel files;
	TextBox searchBar;
	Dropbox fileTypesBox;
	
	Label selectedFileLabel;
	
	Button backButton;
	Button homeButton;
	Button documentsButton;
	
	Button selectButton;
	Button cancelButton;
	
	int yScroll,maxYScroll;
	
	String[] fileTypes;
	File selectedFile;
	
	Runnable selectionFunction;
	
	public FileExplorer(GUIHandler gHandler, Runnable selectionFunction, String...fileTypes) {
		super((1920/2)-(900/2),(1080/2)-(700/2),900,700,gHandler);
		this.fileTypes = fileTypes;
		panel = new GUIPanel((int)x,(int)y,width,height,gHandler);
		panel.setBackgroundColor(Colors.getColor(Colors.GREY,-4));
		panel.setBorderColor(Colors.getColor(Colors.GREY,-6));
		panel.setBorderThickness(15);
		files = new GUIPanel((int)x + 10, (int)y + 70, width - 20, (int)(height * (2.0 / 3.0)), gHandler);
		files.setBackgroundColor(Colors.getColor(Colors.GREY, -2));
		panel.addPanel(files);
		selectedFileLabel = new Label(15,height-140,width-300,50,gHandler);
		selectedFileLabel.setFont(Font.DIALOG_INPUT,10);
		selectedFileLabel.setText("");
		cancelButton = new Button(width-330,height-75,150,50,gHandler);
		cancelButton.setBackground(false);
		cancelButton.setBorder(false);
		selectButton = new Button(width-175,height-75,150,50,gHandler);
		selectButton.setBackground(false);
		selectButton.setBorder(false);
		backButton = new Button(15,15,150,50,gHandler);
		backButton.setBackground(false);
		backButton.setBorder(false);
		Label cancelLabel = new Label(width-330,height-75,150,50,gHandler);
		cancelLabel.setBackgroundColor(Colors.getColor(Colors.GREY, -2));
		cancelLabel.setFont(Font.DIALOG_INPUT,10);
		cancelLabel.setText("Cancel");
		Label selectLabel = new Label(width-175,height-75,150,50,gHandler);
		selectLabel.setBackgroundColor(Colors.getColor(Colors.GREY, -2));
		selectLabel.setFont(Font.DIALOG_INPUT,10);
		selectLabel.setText("Select");
		Label backLabel = new Label(15,15,150,50,gHandler);
		backLabel.setBackgroundColor(Colors.getColor(Colors.GREY, -2));
		backLabel.setFont(Font.DIALOG_INPUT,10);
		backLabel.setText("Back");
		try {
			homeButton = new Button(170,15,50,50,gHandler);
			homeButton.setBackground(false);
			homeButton.setBorder(false);
			homeButton.setFunction(() -> {
				loadDir(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
			});
			documentsButton = new Button(225,15,50,50,gHandler);
			documentsButton.setBackground(false);
			documentsButton.setBorder(false);
			documentsButton.setFunction(() -> {
				loadDir(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath());
			});
			panel.addElement(new Image(170,15,50,50,ImageIO.read(FileExplorer.class.getResourceAsStream("/house.png")),gHandler));
			panel.addElement(new Image(225,15,50,50,ImageIO.read(FileExplorer.class.getResourceAsStream("/document.png")),gHandler));
		}catch(Exception e) {}
		selectButton.setFunction(() -> {
			selectionFunction.run();
			gHandler.removeElement(this);
			gHandler.removePanel(panel);
		});
		cancelButton.setFunction(() -> {
			gHandler.removeElement(this);
			gHandler.removePanel(panel);
		});
		gHandler.addElement(this);
		gHandler.addPanel(panel);
		panel.addElement(backButton);
		panel.addElement(homeButton);
		panel.addElement(documentsButton);
		panel.addElement(cancelButton);
		panel.addElement(selectButton);
		panel.addElement(backLabel);
		panel.addElement(selectLabel);
		panel.addElement(cancelLabel);
		panel.addElement(selectedFileLabel);
		loadDir(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath());
	}
	
	public boolean acceptedFile(File dir, String name) {
		File file = new File(dir.getAbsolutePath()+"/"+name);
		for(String extension : fileTypes) {
			if(file.getName()=="AppData")return true;
			if(file.isHidden()) return false;
			if(!file.exists()) return false;
			if(file.isDirectory()) return true;
			if(name.toLowerCase().endsWith(extension)) return true;
		}
		return false;
	}
	
	public void loadDir(String dir) {
		files.elements.clear();
		File dirFile = new File(dir);
		File[] dirFiles = dirFile.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return acceptedFile(dir,name);
		    }
		});
		int yLevel = 0;
		yScroll = 0;
		try {
			for(File file : dirFiles) {
				Label name = new Label(0,yLevel,width-20,50,gHandler);
				Button button = new Button(0,yLevel,width-20,50,gHandler);
				button.setBackground(false);
				button.setBorder(false);
				button.setFunction(() -> {
					if(file.isDirectory()) {
						loadDir(file.getAbsolutePath());
					} else {
						selectedFile = file;
						selectedFileLabel.setText(file.getName());
					}
				});
				Image fileTypeImage = new Image(width-65,yLevel+18,25,25,
						ImageIO.read(FileExplorer.class.getResourceAsStream("/folder.png")),gHandler);
				if(file.isFile()) fileTypeImage.setImage(getImage());
				name.setFont(Font.DIALOG_INPUT,10);
				name.setText(file.getName());
				files.addElement(name);
				files.addElement(fileTypeImage);
				files.addElement(button);
				yLevel+=50;
			}
			maxYScroll = yLevel-200;
		} catch(Exception e) {}
		backButton.setFunction(() -> {
			if(dirFile.getParent()==null)return;
			loadDir(dirFile.getParent());
		});
	}
	
	public BufferedImage getImage() {
		try {
			if(fileTypes == DataTypes.DOCUMENT) return ImageIO.read(
					FileExplorer.class.getResourceAsStream("/document.png"));
			if(fileTypes == DataTypes.IMAGE) return ImageIO.read(
					FileExplorer.class.getResourceAsStream("/image.png"));
			if(fileTypes == DataTypes.AUDIO) return ImageIO.read(
					FileExplorer.class.getResourceAsStream("/audio.png"));
			if(fileTypes == DataTypes.VIDEO) return ImageIO.read(
					FileExplorer.class.getResourceAsStream("/video.png"));
			return ImageIO.read(FileExplorer.class.getResourceAsStream("/document.png"));
		} catch(Exception e) {
			return null;
		}
	}
	
	public File getSelectedFile() {
		return selectedFile;
	}

	@Override
	public void update() {
		
	}
	@Override
	public void render(Graphics2D g) {
		
	}

	@Override
	public void input(KeyEvent e, KeyType kt) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN && kt == KeyType.KeyPressed) {
			if(yScroll<maxYScroll) {
				yScroll+=25;
				files.setOffset((int)x+10,(int)(y+70)-yScroll);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && kt == KeyType.KeyPressed) {
			if(yScroll>0) {
				yScroll-=25;
				files.setOffset((int)x+10,(int)(y+70)-yScroll);
			} else yScroll = 0;
		}
	}

	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		
	}

	@Override
	public boolean isSelected() {
		return false;
	}
}
