import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.text.DecimalFormat;
//optic object extending curved mirror
public class ConvexMirror extends CurvedMirror {
	
	DecimalFormat df = new DecimalFormat("#.##");
	
	public ConvexMirror(int x, int y, int focus) {
       	super(x, y, focus, false);
	}
	
	public Ray action(Ray rayBeforeConvexLens) {
		
		double focus =  this.focus;
		double dC=10000, dG=10000;
		double dCx=0, dCy=0, dGx=0, dGy=0;
		double a=0, b=0;
		double xBound = rayBeforeConvexLens.xBound;
		double yBound = rayBeforeConvexLens.yBound;
		double rayAngle = rayBeforeConvexLens.angle;
		double newAngle = rayAngle;
		double radius = focus*2;
		double Ox, Oy;
		double centerRay=60;
		
		double angleBetween = (rayAngle - angle);
		double sourceIntercept = yBound+Math.tan(Math.toRadians(rayAngle))*xBound;
		double centerIntercept = y+Math.tan(Math.toRadians(rayAngle))*x;
		double verticalDistance = Math.hypot(xBound-x,yBound-y);
		
				Ox = x + radius*Math.cos(Math.toRadians(angle));
				Oy = y - radius*Math.sin(Math.toRadians(angle));
				centerRay = -Math.toDegrees(atan((yBound-Oy),(xBound-Ox)));
				
				if ( (angleBetween > 90 && angleBetween <= 270) || (angleBetween >= -270 && angleBetween <= -90) ) {
					return null;
				}	
		


		newAngle = 180+ (2*centerRay - rayAngle);
		
//		System.out.println("dC\tdG\ta\tb\txBound\tyBound\trayAngle\tangle\tangleBetween\tsourceIntercept\tcenterIntercept\tverticalDistance\tnewAngle");
//		System.out.println(dC +"\t"+dG+"\t"+a+"\t"+b+"\t"+xBound+"\t"+yBound+"\t"+rayAngle+"\t"+angle+"\t"+angleBetween+"\t"+sourceIntercept+"\t"+centerIntercept+"\t"+verticalDistance+"\t"+newAngle);
		System.out.println("dC\tdG\ta\tb\txBound\tyBound\trayAngle");
		System.out.println(df.format(dC) +"\t"+df.format(dG)+"\t"+df.format(a)+"\t"+df.format(b)+"\t"+df.format(xBound)+"\t"+df.format(yBound)+"\t"+df.format(rayAngle));
		System.out.println("angle\tangleBetween\tsourceInt\tcenterInt\tverticalDis\tnewAngl");
		System.out.println(df.format(angle) +"\t"+df.format(angleBetween)+"\t"+"\t"+df.format(sourceIntercept)+"\t"+"\t"+df.format(centerIntercept)+"\t"+"\t"+df.format(verticalDistance)+"\t"+"\t"+df.format(newAngle));
//		System.out.println ( df.format(dCx) + "\t" + df.format(dCy) + "\t" + df.format(dGx) + "\t" + df.format(dGy) +"\n");
//		return new Ray((int)Ox,(int)Oy,(int)centerRay,this);
//		return new Ray((int)dGx,(int)dGy,(int)newAngle,this);
		return new Ray((int)xBound,(int)yBound,(int)arg(newAngle),this);
	}
}
