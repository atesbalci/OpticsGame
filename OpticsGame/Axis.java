import javax.swing.*;
import java.awt.*;
import java.io.*;
//a helping class to define an axis used by lenses, flat mirror and principal axis classes
class Axis implements Serializable{
	int x, y, xBound1, yBound1, xBound2, yBound2;
	int angle;
	int length;
	
	public Axis(int xOrigin, int yOrigin, int angle, int length) {
		x = xOrigin;
		y = yOrigin;
		this.angle = angle;
		this.length = length;
		recalculate();
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
		recalculate();
	}
	
	public void setX(int xOrigin) {
		x = xOrigin;
		recalculate();
	}
	
	public void setY(int yOrigin) {
		y = yOrigin;
		recalculate();
	}
	
	public void recalculate() {
		xBound1 = (int)(x + (Math.cos(Math.toRadians(angle)) * length/2));
		xBound2 = (int)(x - (Math.cos(Math.toRadians(angle)) * length/2));
		yBound1 = (int)(y - (Math.sin(Math.toRadians(angle)) * length/2));
		yBound2 = (int)(y + (Math.sin(Math.toRadians(angle)) * length/2));
	}
	
	public void draw(Graphics g) {		
		g.drawLine(xBound1, yBound1, xBound2, yBound2);
	}
}
