import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
//abstarct parent of convex and concave mirrors
abstract class CurvedMirror extends OpticObject {
	int focus;
	MirrorArc arc;
	boolean isConcave;
	
	public CurvedMirror(int x, int y, int focus, boolean isConcave) {
       	this.x = x;
       	this.y = y;      
       	this.focus = focus; 	
       	this.isConcave = isConcave;
       	angle = 0;
       	arc = new MirrorArc(x, y, focus, isConcave);
	}
	
	public boolean isClicked(Point e) {
		if(!isIcon) {
			if(e.getY() < y + 50 && e.getY() > y - 50 && e.getX() < x + 50 && e.getX() > x - 50) {
				return true;
			}
		}
		else {
			if(e.getY() < y + 50 && e.getY() > y - 50 && e.getX() < x + 50 && e.getX() > x - 50) {
				return true;
			}
		}
		return false;
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
		arc.setAngle(angle);
	}
	
	public void setX(int x) {
		super.setX(x);
		arc.setX(x);
	}
	
	public void setY(int y) {
		super.setY(y);
		arc.setY(y);
	}
	
	public void draw(Graphics g) {
		PrincipalAxis principalAxis;
		MirrorArc arcDraw;
		
		if(!isIcon) {
			arcDraw = new MirrorArc(x, y, focus, isConcave);
			arcDraw.setAngle(angle);
			principalAxis = new PrincipalAxis(x, y, angle, focus);
		}
		else {
			arcDraw = new MirrorArc(x, y, focus*3/4, isConcave);
			arcDraw.setAngle(angle);
			principalAxis = new PrincipalAxis(x, y, angle, focus*3/4);
		}
		principalAxis.draw(g);
		arcDraw.draw(g);
	}
	
	public Ray action(Ray rayBeforeConvexLens) {
		return null;
	}
	
	public Axis getObstruction() {
		return null;
	}
	
	public Arc2D.Double getArc() {
		return arc.getArc();
	}
	
	public Point getArcCenter() {
		return arc.getArcCenter();
	}
	
	public int getFocus() {
		return focus;
	}
	
	public boolean contains(Point p) {
		return getArc().contains(p);
	}
}
