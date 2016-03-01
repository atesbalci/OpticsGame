import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.io.*;
//a resizable optic object which only obstructs the incoming ray
class Obstacle extends OpticObject {
	Rectangle2D.Double obs;
	int width, height;
	
	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
		width = 100;
		height = 100;
		recalculate();
	}
	
	//rectangle getter
	public Rectangle2D.Double getRectangle() {
		return obs;
	}
	
	public void recalculate() {
		obs = new Rectangle2D.Double(x, y, width, height);
	}
	
	public void setX(int x) {
		this.x = x;
		recalculate();
	}
	
	public void setY(int y) {
		this.y = y;
		recalculate();
	}
	
	public void setWidth(int w) {
		width = w;
		recalculate();
	}
	
	public void setHeight(int h) {
		height = h;
		recalculate();
	}
	
	public boolean isClicked(Point e) {
		if(e.x > x && e.x < x + width && e.y > y && e.y < y + height)
			return true;
		return false;
	}
	
	public void draw(Graphics g) {
//		g.fillRect(x, y, width, height);
		Graphics2D g2 = (Graphics2D)g;
		g2.fill(obs);
	}
	
	//angle is not settable
	public void setAngle(int angle) {
	}
	
	//no action, only obstruction
	public Ray action(Ray r) {
		return null;
	}
	
	//null since it returns a rectangle
	public Axis getObstruction() {
		return null;
	}
}
