package zProject_SnakeUsingLinkedList;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SnakeList {
	
	private Node head;
	private Node tail;
	
	private int bodySize;
	
	SnakeList(){
		bodySize = 0;
	}
	
	public void addFront(BodyPart bodypart) {
		
		Node node = new Node(bodypart);
		
		node.next = head;
		head = node;
		
		if(bodySize == 0) {
			tail = head;
		}
		
		bodySize++;
	}
	
	public void addLast(BodyPart bodypart) {
		Node node = new Node(bodypart);
		
		if(bodySize == 0) {
			addFront(bodypart);
			return;
		}
		
		tail.next = node;
		tail = node;
		
		bodySize++;
		
	}

//  Checking if tail's coordinates were correct or not	
//	public void display() {
//		
//		Node temp = tail;
//		
//		System.out.print("X: ");
//		
//		while(temp!=null) {
//			System.out.print(tail.bodypart.getX() + " ");
//			temp = temp.next;
//		}
//		System.out.println();
//		
//		System.out.print("Y: ");
//		temp = tail;
//		while(temp!=null) {
//			System.out.print(tail.bodypart.getY() + " ");
//			temp = temp.next;
//		}
//		System.out.println();
//		
//	}

	public void draw(Graphics g) {
		
		Node temp = head;
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		
		head.bodypart.drawHead(g);
		
		while(temp!=null) {
			temp.bodypart.drawBody(g2);
			temp = temp.next;
		}
	}
	
	public void moveSnakeList(char direction, int totalrows, int totalcols) {
		
		/* A simple way to move the snake
		 * 
		 * First head is moved and then its direction is set
		 *     - If the head wraps around, it is moved to a wrapped around coordinate
		 *     - The "wrapAroundHead()" method is used
		 *     
		 * Second, using the head's previous coordinates, each body-part is moved and their directions are updated
		 *     - ****IMPORTANT****
		 *       If the head wraps around, the tail follows as well and it also wraps around but it causes 
		 *       problem in its direction. If I change the direction of the snake right when the tail gets 
		 *       wrapped around the game board, game glitches      
		 * 	   - To reset the tails directions, "wrapTailDirection()" method is used
		 * */
		
		Node temp = head;
		
		int tempx1 = temp.bodypart.getX();
		int tempy1 = temp.bodypart.getY();
		int tempx2;
		int tempy2;
		
		switch (direction) {
		case 'R': temp.bodypart.moveX(1);
				  temp.bodypart.moveY(0);
		break;
		case 'L': temp.bodypart.moveX(-1);
				  temp.bodypart.moveY(0);
		break;
		case 'U': temp.bodypart.moveY(-1);
				  temp.bodypart.moveX(0);
		break;
		case 'D': temp.bodypart.moveY(1);
				  temp.bodypart.moveX(0);
		break;		
		}
		
		wrapAroundHead(totalrows,totalcols);
		
		temp = temp.next;
		
		while(temp!=null) {
			
			tempx2 = temp.bodypart.getX();
			tempy2 = temp.bodypart.getY();
			
			temp.bodypart.setX(tempx1);
			temp.bodypart.setY(tempy1);
			
			temp.bodypart.setXDir(tempx1-tempx2);
			temp.bodypart.setYDir(tempy1-tempy2);
			
			tempx1 = tempx2;
			tempy1 = tempy2;
			
			temp = temp.next;
		}
	
		wrapTailDirections(totalrows,totalcols);
		
		//display();
	}

	private void wrapAroundHead(int totalrows, int totalcols) {
		if(head.bodypart.getX() < 0) {
			head.bodypart.setX(totalrows - 1);
			return;
		}
		if(head.bodypart.getX() > totalrows - 1) {
			head.bodypart.setX(0);
			return;
		}
		if(head.bodypart.getY() < 0) {
			head.bodypart.setY(totalcols - 1);
			return;
		}
		if(head.bodypart.getY() > totalcols - 1) {
			head.bodypart.setY(0);
			return;
		}
	}
	
	private void wrapTailDirections(int totalrows, int totalcols) {
		if(tail.bodypart.getXDir()<-1) {
			tail.bodypart.setXDir(+1);
			return;
		}
		if(tail.bodypart.getXDir()>1) {
			tail.bodypart.setXDir(-1);
			return;
		}
		if(tail.bodypart.getYDir()<-1) {
			tail.bodypart.setYDir(+1);
			return;
		}
		if(tail.bodypart.getYDir()>1) {
			tail.bodypart.setYDir(-1);
			return;
		}
	}

	public void reverseList() {
		
		Node temp = head;
		Node prevN = null;
		Node nextN;
		
		boolean tailReversed = false;
		
		while(temp!=null) {
			 nextN = temp.next;
			 temp.next = prevN;
			 if(!tailReversed) {
				tail = temp;
				tailReversed = true;
			 }
			 prevN = temp;
			 temp = nextN;
		}
		
		temp = prevN;
		
		head = temp;
	}
	
	public boolean checkCol() {
		
		Node temp = head;
		
		int headX = head.bodypart.getX();
		int headY = head.bodypart.getY();
		
		temp = temp.next;
		
		while(temp!=null) {
			if(temp.bodypart.getX() == headX && temp.bodypart.getY()==headY) {
				return true;
			}
			temp = temp.next;
		}
		
		return false;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public Node getHead() {
		return head;
	}
	
	
	
}
