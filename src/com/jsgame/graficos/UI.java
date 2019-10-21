package com.jsgame.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jsgame.entites.Player;

public class UI {
	
	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(8, 4, 60, 9);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Player.life / Player.maxlife) * 60), 9);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 7));
		g.drawString((int) Player.life + "  /  "+ (int)Player.maxlife , 18, 11);
	}
}
