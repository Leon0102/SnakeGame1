package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int Screen_Width=600;
	static final int Screen_Height=600;
	static final int UnitSize=25;
	static final int GameUnit=(Screen_Width*Screen_Height)/UnitSize;
	static int Delay=75;
	final int x[] = new int[GameUnit];
	final int y[] = new int[GameUnit];
	int bodyParts=6;
	int AppleEaten;
	int AppleX;
	int AppleY;
	char direction ='R';
	boolean running=false;
	Timer timer;
	Random random;
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame()
	{
		newApple();
		running=true;
		timer =new Timer(Delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(running)
		{
		for(int i =0;i<Screen_Height/UnitSize;i++)
		{
			g.drawLine(i*UnitSize,0, i*UnitSize,Screen_Height);
			g.drawLine(0,i*UnitSize, Screen_Width,i*UnitSize);
		}
		//Apple
		g.setColor(Color.red);
		g.fillOval(AppleX, AppleY, UnitSize, UnitSize);
		//Snake
		for(int i = 0;i<bodyParts;i++)
		{
			if(i==0)
			{
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UnitSize,UnitSize);
			}
			else {
				//g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fillRect(x[i], y[i], UnitSize,UnitSize);
			}
		}
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,25));
		g.drawString("Score: "+AppleEaten, 490, 20);
		}
		else {
			gameOver(g);
		}
	}
	public void newApple()
	{
		AppleX=random.nextInt(Screen_Width/UnitSize)*UnitSize;
		AppleY=random.nextInt(Screen_Height/UnitSize)*UnitSize;
	}
	public void move()
	{
		for(int i=bodyParts;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction)
		{
		case 'U':
			y[0] =y[0]-UnitSize;
			break;
		case 'D':
			y[0] =y[0]+UnitSize;
			break;
		case 'L':
			x[0] =x[0]-UnitSize;
			break;
		case 'R':
			x[0] =x[0]+UnitSize;
			break;
			
		}
	}
	public void checkApple()
	{
		if((x[0]==AppleX) && (y[0]==AppleY))
				{
					bodyParts++;
					AppleEaten++;
					newApple();
					Delay=Delay-15;
				}
	}
	public void checkCollisions()
	{
		//check if head collies with body
		for(int i=bodyParts;i>0;i--)
		{
			if((x[0]==x[i]) && (y[0]==y[i]))
			{
				running = false;
			}
		}
		//check if head collies touches left bodies
		if(x[0]<0 || x[0]>Screen_Width)
		{
			running=false;
		}
		if(y[0]<0 || y[0]>Screen_Height)
		{
			running=false;
		}
		if(!running)
		{
			timer.stop();
		}
	}
	public void gameOver(Graphics g)
	{
		//Score
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,25));
		g.drawString("Score: "+AppleEaten, 250, 350);
		//Game Over
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		g.drawString("Game Over", 100, 300);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT: 
				if(direction !='R')
				{
					direction='L';
				}
			break;
			case KeyEvent.VK_RIGHT: 
				if(direction !='L')
				{
					direction='R';
				}
			break;
			case KeyEvent.VK_UP: 
				if(direction !='D')
				{
					direction='U';
				}
			break;
			case KeyEvent.VK_DOWN: 
				if(direction !='U')
				{
					direction='D';
				}
			break;
			}
		}
	}
}
