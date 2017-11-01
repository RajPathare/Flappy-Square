package flappyBird; 

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class flappyBird implements ActionListener,MouseListener,KeyListener
{
	public static flappyBird flappyBird;
	public final int WIDTH = 800 , HEIGHT = 750;
	public Renderer renderer;
	public Rectangle bird;
	public int ticks , ymotion, score;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public boolean gameOver , started;
	
	public flappyBird()
	{
		JFrame jframe = new JFrame();
		
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		Timer timer = new Timer(20, this);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setTitle("Flappy Bird");
		
		jframe.setVisible(true);
		
		
		bird = new Rectangle(WIDTH /2 -10,HEIGHT / 2 -10,20,20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
	
		timer.start();

	}
	
	public void addColumn(boolean start)
	{
		int space=300;
		int width=100;
		int height = 50 + rand.nextInt(300);
		
		if(start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300 , HEIGHT - height - 120 , width,height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() -1)*300, 0 ,width,HEIGHT - height - space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, HEIGHT - height - 120 , width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0 ,width,HEIGHT - height - space));
		}
		
	
	}
	
	public void paintColumn(Graphics g , Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	
	public void jump()
	{
		if(gameOver)
		{
			bird = new Rectangle(WIDTH /2 -10,HEIGHT / 2 -10,20,20);
			columns.clear();
			ymotion = 0;
			score = 0;
			
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
		}
		if(!started)
		{
			started = true;
		}
		else if(!gameOver)
		{
				if(ymotion > 0)
				{
					ymotion = 0;	
				}
				ymotion -=10;
				
		}
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int speed = 10;
		ticks++;
		
		if(started)
		{
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column = columns.get(i);
			column.x -= speed;
		}
		
		if(ticks %2 ==0 && ymotion < 15)
		{
			ymotion += 2;
		}
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column = columns.get(i);
			if(column.x + column.width < 0 )
			{
				columns.remove(column);
				
				if(column.y == 0)
				{
				addColumn(false);
				}
			}
		}
		
		
		bird.y += ymotion;
		
		for(Rectangle column: columns)
		{
			if(column.y ==0 && bird.x + bird.width / 2 > column.x + column.width / 2 -5 && bird.x + bird.width / 2 < column.x + column.width / 2 + 5)
			{
				score++;
			}
			if(column.intersects(bird))
			{
				gameOver = true;
				
				if(bird.x <= column.x){
				
				bird.x = column.x - bird.width;
				
				}
				else
				{
					if(column.y != 0)
					{
						bird.y = column.y - bird.height;
						
					}
					else if(bird.y < column.height)
					{
						bird.y = column.height;
					}
				}
			}
		}
		if(bird.y > HEIGHT -120 || bird.y <0)
		{
			gameOver = true;
		}
		
		if(bird.y + ymotion >= HEIGHT - 120)
		{
			
			bird.y = HEIGHT -120 - bird.height;
		}
	}
		renderer.repaint();
}
	
	
	public void repaint(Graphics g)
	{
		
		int alpha = 127; // 50% transparent
		Color myColour = new Color(0, 255, 0, alpha);
		Color myColour2 = new Color(238, 130, 238, alpha);
		
		
		
		g.setColor(Color.black);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(myColour2);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.white);
		g.fillOval(600, 100, 100, 100);
		
		g.setColor(Color.white);
		g.drawOval(100, 100, 7, 7);
		g.fillOval(200, 200, 7, 7);
		g.fillOval(300, 300, 7, 7);
		g.fillOval(150, 290, 7, 7);
		g.fillOval(350, 100, 7, 7);
		g.fillOval(100, 100, 7, 7);
		g.fillOval(100, 100, 7, 7);
		g.fillOval(500, 100, 7, 7);
		g.fillOval(600, 470, 7, 7);
		g.fillOval(700, 300, 7, 7);
		g.fillOval(100, 20, 7, 7);
		g.fillOval(100, 320, 7, 7);
		g.fillOval(300, 10, 7, 7);
		g.fillOval(450, 210, 7, 7);
		g.fillOval(670, 20, 7, 7);
		g.fillOval(540, 300, 7, 7);
		g.fillOval(50, 200, 7, 7);
		g.fillOval(470, 400, 7, 7);
		
		
		
		g.fill3DRect(100, 400, 200, 250, true);
		g.drawLine(100, 400, 80, 420);
		g.drawLine(80, 420, 80, 700);
		
		g.fill3DRect(400, 500, 100, 200, true);
		g.drawLine(400, 500, 380, 520);
		g.drawLine(380, 520, 380, 700);
		
		g.setColor(myColour);
		
		//1st grass//
		g.fillOval(100, 600, 30, 30);
		g.fillOval(110, 590, 30, 30);
		g.fillOval(120, 580, 30, 30);
		g.fillOval(130, 590, 30, 30);
		g.fillOval(140, 600, 30, 30);
		g.fillOval(120, 610, 30, 30);
		
		//2nd grass//
		g.fillOval(300, 600, 30, 30);
		g.fillOval(310, 590, 30, 30);
		g.fillOval(320, 580, 30, 30);
		g.fillOval(330, 590, 30, 30);
		g.fillOval(340, 600, 30, 30);
		g.fillOval(320, 610, 30, 30);
		
		g.fillOval(600, 600, 30, 30);
		g.fillOval(610, 590, 30, 30);
		g.fillOval(620, 580, 30, 30);
		g.fillOval(630, 590, 30, 30);
		g.fillOval(640, 600, 30, 30);
		g.fillOval(620, 610, 30, 30);
		
		g.fillOval(55, 610, 20, 20);
		g.fillOval(60, 605, 20, 20);
		g.fillOval(70, 593, 20, 20);
		g.fillOval(80, 605, 20, 20);
		g.fillOval(85, 610, 20, 20);
		g.fillOval(70, 620, 20, 20);
		
		g.fillOval(155, 610, 20, 20);
		g.fillOval(160, 605, 20, 20);
		g.fillOval(170, 593, 20, 20);
		g.fillOval(180, 605, 20, 20);
		g.fillOval(185, 610, 20, 20);
		g.fillOval(170, 620, 20, 20);
		
		g.fillOval(355, 610, 20, 20);
		g.fillOval(360, 605, 20, 20);
		g.fillOval(370, 593, 20, 20);
		g.fillOval(380, 605, 20, 20);
		g.fillOval(385, 610, 20, 20);
		g.fillOval(370, 620, 20, 20);
		
		g.fillOval(655, 610, 20, 20);
		g.fillOval(660, 605, 20, 20);
		g.fillOval(670, 593, 20, 20);
		g.fillOval(680, 605, 20, 20);
		g.fillOval(685, 610, 20, 20);
		g.fillOval(670, 620, 20, 20);
		
		g.fillOval(455, 610, 20, 20);
		g.fillOval(460, 605, 20, 20);
		g.fillOval(470, 593, 20, 20);
		g.fillOval(480, 605, 20, 20);
		g.fillOval(485, 610, 20, 20);
		g.fillOval(470, 620, 20, 20);
		
		
		
		
		g.setColor(Color.orange);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WIDTH, 120);
		
		g.setColor(Color.green);
		g.fillRect(0,HEIGHT-120,WIDTH,20);
		
		for(Rectangle column : columns)
		{
			paintColumn(g , column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		
		
		if(!started)
		{
			g.drawString("Click to start!", 100, HEIGHT / 2 - 50);	
		}

		if(gameOver)
		{  
			g.drawString("GAME OVER!", 100, HEIGHT / 2 - 50);
		}
		
		if(!gameOver && started)
		{
		    g.drawString(String.valueOf(score),WIDTH / 2 - 25,100);
		}
	}
	
public static void main(String args[])
	{
		flappyBird = new flappyBird();
		
		
	}

@Override
public void mouseClicked(MouseEvent arg0) 
{
	jump();
}

@Override
public void mouseEntered(MouseEvent arg0) 
{
	
	
}

@Override
public void mouseExited(MouseEvent arg0) 
{
	
	
}

@Override
public void mousePressed(MouseEvent arg0) 
{

	
}

@Override
public void mouseReleased(MouseEvent arg0) 
{
	
	
}

@Override
public void keyPressed(KeyEvent arg0) {
	
	
}

@Override
public void keyReleased(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	{
		jump();
		
	}
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	
	
}


}
