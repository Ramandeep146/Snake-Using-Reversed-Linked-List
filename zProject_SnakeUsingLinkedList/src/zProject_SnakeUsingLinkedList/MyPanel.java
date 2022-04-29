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
	
	private final int screenW = 800;
	private final int screenH = 600;
	
	private final int unitSize = 25;
	private final int totalRows = screenW/unitSize;
	private final int totalCols = screenH/unitSize;
	
	private int delay = 100;
	
	private int score;
	
	private char direction;
	
	private Apple apple;
	private SnakeList snakeBody;
	
	private boolean gameOver;
	
	private boolean changeDirection; // This variable will allow us to avoid multiple key inputs
	
	private Timer myTimer;
	
	MyPanel(){
		initializePanel();
		reset();
	}
	
	private void initializePanel() {
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setFocusable(true);
		this.setBackground(new Color(50,50,50));
		this.addKeyListener(new myKeyAdapter());
	}
	
	private void reset(){
		initializeGame();
		start();
	}
	
	private void initializeGame() {
		
		apple = new Apple();
		apple.setCord(totalRows,totalCols);
		
		snakeBody = new SnakeList();
		snakeBody.addFront(new BodyPart(5,5));
		snakeBody.addFront(new BodyPart(6,5));
//		snakeBody.addFront(new BodyPart(7,5));
//		snakeBody.addFront(new BodyPart(8,5));
//		snakeBody.addLast(new BodyPart(4,5));
//		snakeBody.addLast(new BodyPart(3,5));
		
		changeDirection = true;
		direction = 'R';
		score = 0;
	}
	
	private void start() {
		myTimer = new Timer(delay,this);
		gameOver = false;
		myTimer.start();
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
	
	private void update() {
		snakeBody.moveSnakeList(direction,totalRows,totalCols);
		checkCollisions();
		checkApple();
		
		/*
		 * First body will be moved
		 * Second collisions will be checked
		 * Third apple collision will be checked
		 */
		
		// Changing this variable to true so that we can change the direction again
		changeDirection = true;
	}
	
	private void checkCollisions() {
		gameOver = snakeBody.checkCol();
	}
	
	private void checkApple(){
		int appleX = apple.getX();
		int appleY = apple.getY();
		int snakeHeadX = snakeBody.getHead().bodypart.getX();
		int snakeHeadY = snakeBody.getHead().bodypart.getY();
		
		if(appleX == snakeHeadX && appleY == snakeHeadY) {
			apple.setCord(totalRows,totalCols);
			addBodyPart();
			score+=5;
		}
		
	}
	
	private void addBodyPart() {
		
		// A complicated way to add body parts but this eliminates most of the bugs
		// A body part will be added behind the tail by checking the tails direction and movement 
		
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
		
		//**The code below will fix the bug when snake is reversed at the same time when body-part is added.**//
		snakeBody.getTail().bodypart.setXDir(XDir);
		snakeBody.getTail().bodypart.setYDir(YDir);

	}
		
 
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		apple.draw(g);
		snakeBody.draw(g);
		drawScore(g);
		
		if(gameOver) {
			drawGameOver(g);
		}
	}
	
	
	private void drawScore(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Timer Roman", Font.ITALIC, 25));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + score, screenW/2 - metrics.stringWidth("Score: " + score )/2, 25);
	}

	private void drawGameOver(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Timer Roman", Font.ITALIC, 50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GameOver", 
				screenW/2 - metrics.stringWidth(" GameOver")/2, screenH/2-50);
		g.drawString("Your Score: " + score, 
				screenW/2 - metrics.stringWidth("Your Score: " + score)/2, screenH/2);
		g.drawString("Press 'R' to restart", 
				screenW/2 - metrics.stringWidth("Press 'R' to restart")/2, screenH/2 + 50);
		
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

	public class myKeyAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			
			if(!changeDirection) {
				return;
			}
			
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
			case KeyEvent.VK_R:
				if(gameOver) {
					reset();
				}
				break;
			}
			
			// After first key pressed, it will be set to false until the snake moves by one unit
			if(!gameOver) {
				changeDirection = false;
			}
		}

	}
	
}
