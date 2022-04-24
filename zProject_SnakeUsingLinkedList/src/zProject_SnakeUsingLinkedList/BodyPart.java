package zProject_SnakeUsingLinkedList;

import java.awt.*;

public class BodyPart{
	
	private int unitSize = 25;
	
	private int x,y;
	
	private int xDir,yDir;
	
	BodyPart(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(x*unitSize,y*unitSize,unitSize,unitSize);
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
	
	public void setXDir(int x) {
		this.xDir = x-this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setYDir(int y) {
		this.yDir = y-this.y;
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

}
