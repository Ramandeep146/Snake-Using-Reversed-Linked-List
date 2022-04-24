package zProject_SnakeUsingLinkedList;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

public class MyPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int screenW = 600;
	static final int screenH = 600;
	
	int delay = 200;
	
	private int score;
	
	char direction = 'R';
	
	Apple apple;
	SnakeList snakeBody;
	
	boolean gameOver;
	
	Timer myTimer;
	
	MyPanel(){
		initializePanel();
		initializeGame();
		start();
	}

	private void initializePanel() {
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setFocusable(true);
		this.setBackground(new Color(50,50,50));
		this.addKeyListener(new myKeyAdapter());
	}
	
	private void initializeGame() {
		
		apple = new Apple();
		apple.setCord();
		
		snakeBody = new SnakeList();
		snakeBody.addFront(new BodyPart(5,5));
		snakeBody.addFront(new BodyPart(6,5));
		snakeBody.addFront(new BodyPart(7,5));
		snakeBody.addFront(new BodyPart(8,5));
		snakeBody.addLast(new BodyPart(4,5));
		snakeBody.addLast(new BodyPart(3,5));
	}
	
	private void start() {
		myTimer = new Timer(delay,this);
		gameOver = false;
		myTimer.start();
	}
	
	private void checkApple(){
		int appleX = apple.getX();
		int appleY = apple.getY();
		int snakeHeadX = snakeBody.getHead().bodypart.getX();
		int snakeHeadY = snakeBody.getHead().bodypart.getY();
		
		if(appleX == snakeHeadX && appleY == snakeHeadY) {
			apple.setCord();
			addBodyPart();
			score++;
		}
		
	}
	
	private void addBodyPart() {
		
		// A complicated way to add body parts but this eliminates most of the bugs
		// A body part will be added by checking the tails direction and movement 
		
		int snakeTailX = snakeBody.getTail().bodypart.getX();
		int snakeTailY = snakeBody.getTail().bodypart.getY();
		
		int XDir = snakeBody.getTail().bodypart.getXDir();
		int YDir = snakeBody.getTail().bodypart.getYDir();
		
		if(XDir==1 && YDir==0) {
			snakeBody.addLast(new BodyPart(snakeTailX-XDir,snakeTailY-YDir));	
		}
		if(XDir==-1 && YDir==0) {
			snakeBody.addLast(new BodyPart(snakeTailX-XDir,snakeTailY-YDir));
		}
		if(XDir==0 && YDir==1) {
			snakeBody.addLast(new BodyPart(snakeTailX-XDir,snakeTailY-YDir));
		}
		if(XDir==0 && YDir==-1){
			snakeBody.addLast(new BodyPart(snakeTailX-XDir,snakeTailY-YDir));
		}
		
		// The code below will fix the bug when snake is reversed at the same time when body-part is added.
		
		snakeBody.getTail().bodypart.setXDir(snakeTailX);
		snakeBody.getTail().bodypart.setYDir(snakeTailY);

	}

	private void checkCollisions() {
		gameOver = snakeBody.checkCol();
	}

	private void reverse() {
		//First we will change the directions so that we can use 
		//Tail node to get directions and change directions accordingly
		reverseDirections();
		
		//Secondly, reverse the snake's whole body
		snakeBody.reverseList();
	}

	private void reverseDirections() {
		
		int x = snakeBody.getTail().bodypart.getXDir();
		int y = snakeBody.getTail().bodypart.getYDir();
		
		if(x==1 && y==0) {
			direction = 'L';
			return;
		}
		if(x==-1 && y==0) {
			direction = 'R';
			return;
		}
		if(x==0 && y==1) {
			direction = 'U';
			return;
		}
		if(x==0 && y==-1){
			direction = 'D';
			return;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		apple.draw(g);
		snakeBody.draw(g);
		
		g.setFont(new Font("Timer Roman", Font.ITALIC, 25));
		g.drawString("Score: " + score, screenW/2-30, 25);
	}
	
	public void update() {
		snakeBody.move(direction);
		checkCollisions();
		checkApple();
		
		/*
		 * First body will be moved
		 * Second collisions will be checked
		 * Third apple collision will be checked
		 * 
		 * After apple collision is checked, new body-part overlaps tail(body-part),
		 * so, snake should be moved first before checking the collisions
		 * This will move the tail by one unit and it will not overlap anymore
		 * This will stop the game. */
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!gameOver) {
			update();
			repaint();
			return;
		}
		myTimer.stop();
	}	
	
	
	
	
	public class myKeyAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_SPACE:
				reverse();
				break;	
			}	
		}

	}
	
}
