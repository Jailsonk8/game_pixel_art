package com.jsgame.entites;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jsgame.main.Game;
import com.jsgame.world.Camera;
import com.jsgame.world.World;

public class Enemy extends Entity{

	private double speed = 0.8;
	
	private int maskX = 8, maskY = 8, maskW = 7, maskH = 7;
	
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	private int frames = 0, maxFrames = 6, index = 0, maxIndex = 1;
	private boolean moved = false;
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;

	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		rightEnemy = new BufferedImage[2];
		leftEnemy  = new BufferedImage[2];
		
		rightEnemy[0] = Game.spritesheet.getSprite(144, 48, 16, 16);
		rightEnemy[1] = Game.spritesheet.getSprite(144, 64, 16, 16);
		
		leftEnemy[0] = Game.spritesheet.getSprite(144, 16, 16, 16);
		leftEnemy[1] = Game.spritesheet.getSprite(144, 32, 16, 16);
		
	}
	
	public void tick() {
		
		moved = false;
		
		if(Game.rand.nextInt(100) < 50) { // aleatoriedade como outros inimigos
			
			if (isColiddinWithPlayer() == false) { // Colisão com o player
				
				if((int)x < Game.player.getX() && World.isFree((int)(x + speed), this.getY())
						&& !isColidding((int)(x + speed), this.getY())) {
						
						moved = true;
						dir = right_dir;
						x += speed;
					
				}else if ((int)x > Game.player.getX() && World.isFree((int)(x - speed), this.getY())
						&& !isColidding((int)(x - speed), this.getY())) {
						
						moved = true;
						dir = left_dir;
						x -= speed;
					
				}else if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y + speed))
						&& !isColidding(this.getX(), (int)(y + speed))) {
						
						moved = true;
						dir = right_dir;
						y += speed;
					
				}else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y - speed))
						&& !isColidding(this.getX(), (int)(y - speed))) {
						
						moved = true;
						dir = left_dir;
						y -= speed;
					
				}
			}else {
				//Estamos colidindo com o player!
				if(Game.rand.nextInt(100) < 10) {
					
					Game.player.life -= Game.rand.nextInt(5);
					if(Game.player.life <= 0) {
						// Game Over1
						System.exit(1);
					}
					
					//System.out.println("Vida: "+ Game.player.life);
				}
				
//				if(Game.player.life == 0) {
//					System.exit(1);
//				}
				
			}
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
		
	}
	
	public boolean isColiddinWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskX, this.getY() + maskY, maskW, maskH);
		
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColidding(int xNext, int yNext) {
		
		Rectangle enemyCurrent = new Rectangle(xNext + maskX, yNext + maskY, maskW, maskH);
		
		for (int i = 0; i < Game.enemies.size(); i++) {
			
			Enemy e = Game.enemies.get(i);
			
			if(e == this)
				continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskW, maskH);
			
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		
		if(dir == right_dir) {
			g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == left_dir) {
			g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		//Para Ver a Mascara de colisão 
		//super.render(g);
//		g.setColor(Color.blue);
//		g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, maskW, maskH);
		
	}
}
