package SnakeGame;

import javax.swing.JFrame;

import Animation.MyPanel;

public class GameFrame extends JFrame{	
	GameFrame()
	{
		GamePanel panel = new GamePanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.setTitle("Snake");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
