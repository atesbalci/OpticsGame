import java.awt.*;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
//Target class which is the goal of the game
public class Target extends OpticObject {
	int radius;
	boolean hit;
	
	public Target(int x, int y) {
		this.x = x;
		this.y = y;
		radius = 25;
		hit = false;
	}
	
	public boolean isClicked(Point p) {
		if( (int)Math.hypot( p.x - x, p.y - y ) <= radius ) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g) {
		if(hit)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.red);
		g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
		g.setColor(Color.black);
	}
	
	public Point getCenter() {
		return new Point(x, y);
	}
	
	public Axis getObstruction() {
		return null;
	}
	
	public void setHit(boolean b) {
		hit = b;
	}
	
	public Ray action(Ray r) {
		setHit(true);
		return null;
	}
}
