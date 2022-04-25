package zProject_SnakeUsingLinkedList;

import java.awt.*;
import java.util.Random;

public class Apple {
	
	private int unitSize;
	
	private int x,y;
	
	private Random random;
	
	Apple(){
		unitSize = 25;
		random = new Random();
	}
	
	public void setCord(int totalrows, int totalcols) {
		x = random.nextInt(totalrows-1);
		y = random.nextInt(totalcols-1);
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(200,0,0));
		g.fillOval(x*unitSize,y*unitSize,unitSize,unitSize);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
