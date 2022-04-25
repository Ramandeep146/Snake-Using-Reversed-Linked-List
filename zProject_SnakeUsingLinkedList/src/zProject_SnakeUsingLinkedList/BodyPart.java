package zProject_SnakeUsingLinkedList;

import java.awt.*;

public class BodyPart{
	
	private int unitSize;
	
	private int x,y;
	
	private int xDir,yDir;
	
	BodyPart(int x, int y){
		unitSize = 25;
		this.x = x;
		this.y = y;
	}
	
	public void drawBody(Graphics g) {
		
		g.setColor(Color.GREEN);
		g.drawRect(x*unitSize,y*unitSize,unitSize,unitSize);
	}
	
	public void drawHead(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x*unitSize,y*unitSize,unitSize,unitSize);
	}
	
	public void moveX(int xdir) {
		this.xDir = xdir;
		this.x += xDir;
	}
	
	public void moveY(int ydir) {
		this.yDir = ydir;
		this.y += yDir;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setXDir(int xdir) {
		this.xDir = xdir;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setYDir(int ydir) {
		this.yDir = ydir;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getXDir() {
		return xDir;
	}
	
	public int getYDir() {
		return yDir;
	}
	
	public int getUnitSize() {
		return unitSize;
	}

}
