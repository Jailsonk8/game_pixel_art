package com.jsgame.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jsgame.entites.*;
import com.jsgame.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		
		try {
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			
			WIDTH = map.getHeight();
			HEIGHT = map.getHeight();
			
			tiles = new Tile[map.getWidth() * map.getHeight()];

			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			//System.out.println("Cor Vermelho ");
			for (int x = 0; x < map.getWidth(); x++) {
				
				for (int y = 0; y < map.getHeight(); y++) {

					int pixelAtual = pixels[x + y * map.getWidth()];
					tiles[x + (y * WIDTH)] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
					
					if (pixelAtual == 0xFF000000) {
						//Floor / Chão
						tiles[x + (y * WIDTH)] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
						
					}else if(pixelAtual == 0xFFFFFFFF) {
						//Wall / Parede
						tiles[x + (y * WIDTH)] = new WallTile(x * 16, y * 16, Tile.TILE_WALL);
						
					}else if(pixelAtual == 0xFF2137d6) {
						// Player
						Game.player.setX(x * 16);
						Game.player.setY(y * 16);
						
					}else if(pixelAtual == 0xFFb90f0b){
						//Enemy
						Enemy en = new Enemy(x * 16, y * 16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					}else if(pixelAtual == 0xFF4c200a){
						//Weapon
						Game.entities.add(new Weapon(x * 16, y * 16, 16, 16, Entity.WEAPON_EN));
						
					}else if(pixelAtual == 0xFFd614e2){
						//Life
						Game.entities.add(new Lifepack(x * 16, y * 16, 16, 16, Entity.LIFEPACK_EN));
						
					}else if(pixelAtual == 0xFF218612){
						//Bullet
						Game.entities.add(new Bullet(x * 16, y * 16, 16, 16, Entity.BULLET_EN));
					}
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) || 
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		
		int xStart = (Camera.x >> 4); //posição da camera
		int yStart = (Camera.y >> 4); //posição da camera
		
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int x = xStart; x <= xFinal; x++) {
			
			for(int y = yStart; y <= yFinal; y++) {
				
				if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
					continue;
				Tile tile = tiles[x + (y*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	
	
}
