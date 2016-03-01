import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
//flat mirror class, most basic optic object, nothing fancy
public class FlatMirror extends OpticObject {
	Axis mirror;
	
	public FlatMirror(int x, int y) {
       	this.x = x;
       	this.y = y;	
       	angle = 0;
       	mirror = new Axis(x, y, 0, 100);
	}
	
	public boolean isClicked(Point e) {
		if(e.getY() < y + 50 && e.getY() > y - 50 && e.getX() < x + 50 && e.getX() > x - 50) {
			return true;
		}
		return false;
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
		mirror.setAngle(angle);
	}
	
	public void setX(int x) {
		super.setX(x);
		mirror.setX(x);
	}
	
	public void setY(int y) {
		super.setY(y);
		mirror.setY(y);
	}
	
	public void draw(Graphics g) {
		Axis back;
		Axis mirrorDraw;
		if(isIcon) {
			mirrorDraw = new Axis(x, y, angle, 75);
			back = new Axis((int)(x + 4 * Math.sin(Math.toRadians(angle))), (int)(y + 4 * Math.cos(Math.toRadians(angle))), angle, 75);
		}
		else {
			mirrorDraw = new Axis(x, y, angle, 100);
			back = new Axis((int)(x + 4 * Math.sin(Math.toRadians(angle))), (int)(y + 4 * Math.cos(Math.toRadians(angle))), angle, 100);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));
		mirrorDraw.draw(g);
		g2.setStroke(new BasicStroke(6));
		g2.setColor(new Color(143, 143, 143, 125)); 
		back.draw(g);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.black);
	}
	
	public Ray action(Ray rayBeforeFlatMirror) {
		
		int rayAngle = rayBeforeFlatMirror.angle;
		int newAngle = rayAngle;
		
		int angleBetween = (rayAngle - angle);
		
		if ( (angleBetween >= 0 && angleBetween <= 180) || (angleBetween >= -360 && angleBetween <= -180) ) 
		{
			newAngle = 2*angle - rayAngle;
			return new Ray(rayBeforeFlatMirror.xBound,rayBeforeFlatMirror.yBound,(int)arg(newAngle),this);
		}	
		else{
			return null;
		}			
	}
	
	public Axis getObstruction() {
		return mirror;
	}
}
