package com.jsgame.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jsgame.main.Game;
import com.jsgame.world.Camera;

public class Entity {
	
	public static BufferedImage  LIFEPACK_EN = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage  WEAPON_EN = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage  BULLET_EN = Game.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage  ENEMY_EN = Game.spritesheet.getSprite(144, 16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
