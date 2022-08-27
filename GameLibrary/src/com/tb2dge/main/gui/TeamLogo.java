package com.tb2dge.main.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import com.tb2dge.main.Game;

public class TeamLogo {
	boolean animating = true;
	Image teamLogo1;
	Image teamLogo2;
	Image teamLogo3;
	Game game;
	
	float alpha = 0.0f;
	int timer = 0;
	
	public TeamLogo(Game game) {
		this.game = game;
		try {
			teamLogo1 = ImageIO.read(TeamLogo.class.getResourceAsStream("/team_logo1.png"));
			teamLogo2 = ImageIO.read(TeamLogo.class.getResourceAsStream("/team_logo2.png"));
			teamLogo3 = ImageIO.read(TeamLogo.class.getResourceAsStream("/team_logo3.png"));
		} catch(Exception e) {}
	}
	
	public boolean isDone() {
		return !animating;
	}
	
	public void skip() {
		animating = false;
	}
	
	public void update() {
		if(!animating) return;
		alpha+=0.02f;
		timer++;
		if(timer>=230) {
			alpha-=0.04f;
		}
		if(timer==330) {
			animating = false;
		}
		if (alpha > 1.0)
			alpha = (float) 1.0;
		if (alpha < 0.0)
			alpha = (float) 0.0;
	}
	public void render(Graphics2D g) {
		if(!animating) return;
		g.fillRect(0, 0, (int)game.resX, (int)game.resY);
		AlphaComposite old = (AlphaComposite)g.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(ac);
		g.drawImage(teamLogo3,0,0,(int)game.resX,(int)game.resY,null);
		g.drawImage(teamLogo1,(int)game.resX/4,(int)game.resY/4,(int)game.resX/2,(int)game.resY/2,null);
		g.drawImage(teamLogo2,(int)game.resX/4,(int)game.resY/4,(int)game.resX/2,(int)game.resY/2,null);
		g.setComposite(old);
	}
}
