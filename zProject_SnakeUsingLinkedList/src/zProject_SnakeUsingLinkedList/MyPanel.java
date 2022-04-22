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
	
	static final int unitSize = 25;
	
	static final int totalRows = screenW/unitSize;
	static final int totalCols = screenH/unitSize;
	
	char direction = 'R';
	
	SnakeList snakeBody;
	
	Timer myTimer;
	
	MyPanel(){
		initializePanel();
		initializeBody();
		start();
	}

	private void initializePanel() {
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setFocusable(true);
		this.setBackground(new Color(50,50,50));
		
		this.addKeyListener(new myKeyAdapter());
		
	}
	
	private void initializeBody() {
		snakeBody = new SnakeList();
		snakeBody.addFront(new BodyPart(5,5));
		snakeBody.addFront(new BodyPart(6,5));
		snakeBody.addFront(new BodyPart(7,5));
		snakeBody.addFront(new BodyPart(8,5));
		snakeBody.addLast(new BodyPart(4,5));
		snakeBody.addLast(new BodyPart(3,5));
	}
	
	private void start() {
		myTimer = new Timer(200,this);
		myTimer.start();
	}
	
	public void moveBody() {
		snakeBody.move(direction);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		snakeBody.draw(g2);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		moveBody();
		repaint();
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
				//First we will change the directions so that we can use Tail node to get directions and change directions accordingly
				reverse();
				snakeBody.reverseList();
				break;	
			}
		
		}

		private void reverse() {
			
			int x = snakeBody.getTail().bodypart.getXDir();
			int y = snakeBody.getTail().bodypart.getYDir();
			
			if(x==1 && y==0) {
				direction = 'L';
			}
			else if(x==-1 && y==0) {
				direction = 'R';
			}
			else if(x==0 && y==1) {
				direction = 'U';
			}
			else if(x==0 && y==-1){
				direction = 'D';
			}
			
		}
	}

	
}
