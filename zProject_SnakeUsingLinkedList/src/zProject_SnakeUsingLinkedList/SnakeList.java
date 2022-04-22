package zProject_SnakeUsingLinkedList;

import java.awt.Graphics;

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
	
//	public void display() {
//		
//		Node temp = tail;
//		
//		while(temp!=null) {
//			System.out.print("X " + tail.bodypart.xDir);
//			temp = temp.next;
//		}
//		System.out.println();
//		
//		temp = tail;
//		while(temp!=null) {
//			System.out.print("Y " + tail.bodypart.yDir);
//			temp = temp.next;
//		}
//		System.out.println();
//		
//	}

	public void draw(Graphics g) {
		
		Node temp = head;
		
		while(temp!=null) {
			temp.bodypart.draw(g);
			temp = temp.next;
		}
	}
	
	public void move(char direction) {
		
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
		
		temp = temp.next;
		
		while(temp!=null) {
			tempx2 = temp.bodypart.getX();
			tempy2 = temp.bodypart.getY();
			
			temp.bodypart.setXDir(tempx1);
			temp.bodypart.setYDir(tempy1);
			temp.bodypart.setX(tempx1);
			temp.bodypart.setY(tempy1);
			
			tempx1 = tempx2;
			tempy1 = tempy2;
			
			temp = temp.next;
		}
		//display();
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
	
	public Node getTail() {
		return tail;
	}
	
	
	
}
