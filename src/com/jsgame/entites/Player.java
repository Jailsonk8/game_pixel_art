package com.jsgame.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jsgame.main.Game;
import com.jsgame.world.Camera;
import com.jsgame.world.World;

public class Player extends Entity{

	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.1; //nunca abaixo de zero;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	public static double life = 100, maxlife = 100;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer 	= new BufferedImage[4];
		upPlayer 	= new BufferedImage[4];
		downPlayer 	= new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(64 + (i * 16), 0, 16, 16);			
		}
		
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(64 + (i * 16), 16, 16, 16);			
		}
		
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(48, 16 * i, 16, 16);			
		}
		
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32, 16 * i, 16, 16);			
		}
		
//		upPlayer[0] = Game.spritesheet.getSprite(48, 0, 16, 16);	
//		downPlayer[0] = Game.spritesheet.getSprite(48, 16, 16, 16);
		
	}

	public void tick () {
		
		moved = false;
		
		if(right && World.isFree((int)(this.getX() + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			this.setX(this.getX() + speed);
		}else if(left && World.isFree((int)(this.getX() - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			this.setX(this.getX() - speed);
		}
		
		if(up && World.isFree(this.getX(), (int)(this.getY() - speed))) {
			moved = true;
			dir = up_dir;
			this.setY(this.getY() - speed);
		}else if(down && World.isFree(this.getX(), (int)(this.getY() + speed))) {
			moved = true;
			dir = down_dir;
			this.setY(getY() + speed);
		}
		
		if(moved) {
			
			frames++;
			
			if(frames == maxFrames) {
				frames = 0;
				index++;
				
				if(index > maxIndex)
					index = 0;
				
			}
			
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, (World.HEIGHT * 16) - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}else if (dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
	
	
}
