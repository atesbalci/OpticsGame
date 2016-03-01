import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.io.*;
//Parent abstarct class of all optic objects
abstract class OpticObject implements Serializable {
	protected int x, y, angle;
	protected boolean isIcon = false;
	protected boolean rotationLocked = false;
	
	//location setters (Most child classes override these)
	public void setX(int x) {
		this.x = x;
	}	
	public void setY(int y) {
		this.y = y;
	}
	
	//location getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	//angle getter
	public int getAngle() {
		return angle;
	}
	
	//angle setter (Most child classes override this)
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	//image reloading class for classes with images for loading from file (not abstract because some classes does not have images)
	public void reloadImages() {
	}
	
	//determines if the object is clicked
	abstract boolean isClicked(Point e);
	
	//draw method to draw the object on field
	abstract void draw(Graphics g);
	
	//generates the new ray according to input ray
	abstract Ray action(Ray r);
	
	//rotation lock setter
	public void setLocked(boolean b) {
		rotationLocked = b;
	}
	
	//rotation lock getter
	public boolean locked() {
		return rotationLocked;
	}
	
	//icon setter to modify the inventory appearance of the optic object
	public void setIcon(boolean b) {
		isIcon = b;
	}
	
	//some helping methods for calculations below	
	public double atan(double rise, double run, double dG){
		double result = Math.atan(rise/run);
		if(rise>=0 && run >=0){
			return result;
		}
		else if(rise<=0 && run >=0){
			result += 2*Math.PI;
		}
		else if(rise>=0 && run <=0){
			result += Math.PI;
		}
		else if(rise<=0 && run <=0){
			result += Math.PI;
		}
		if (dG<0){
			result = result+Math.PI;
		}
		return result;
	}
	
	public double atan(double rise, double run){
		double result = Math.atan(rise/run);
		if(rise>=0 && run >=0){
			return result;
		}
		else if(rise<=0 && run >=0){
			result += 2*Math.PI;
		}
		else if(rise>=0 && run <=0){
			result += Math.PI;
		}
		else if(rise<=0 && run <=0){
			result += Math.PI;
		}
		return result;
	}
	
	public double arg(double degrees){
		while(degrees>360){
			degrees = degrees - 360 ;
		}
		
		while(degrees < 0){
			degrees = degrees + 360 ;
		}
		
		return degrees;
	}
	
	//returns the obstructing axis if exists
	public abstract Axis getObstruction();
}
