import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.text.DecimalFormat;
//optic object extending lens
class ConcaveLens extends Lens {
	
	DecimalFormat df = new DecimalFormat("#.##");
	
	public ConcaveLens(int x, int y, int focus) {
		super(x, y, focus, "concaveLens.png");
	}
	
	public Ray action(Ray rayBeforeConvexLens){
		double angle = this.angle;
//		while(angle >= 180) {
//			angle -= 180;
//		}
		
		double focus =  -this.focus;
		double dC=10000, dG=10000;
		double dCx=0, dCy=0, dGx=0, dGy=0;
		double a=0, b=0;
		double xBound = rayBeforeConvexLens.xBound;
		double yBound = rayBeforeConvexLens.yBound;
		double rayAngle = rayBeforeConvexLens.angle;
		double newAngle = rayAngle;
		
		double angleBetween = (rayAngle - angle);
		double sourceIntercept = yBound+Math.tan(Math.toRadians(rayAngle))*xBound;
		double centerIntercept = y+Math.tan(Math.toRadians(rayAngle))*x;
		double verticalDistance = Math.hypot(xBound-x,yBound-y);
		
//1		
		if( (rayAngle >= 0 && rayAngle <= 90) || (rayAngle >= -360 && rayAngle <= -270) )
		{
			if ( (angleBetween >= 0 && angleBetween <= 90) || (angleBetween >= -360 && angleBetween <= -270) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case11");
	
					dG = 1 / ( (1 / ( focus)) + (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
						
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}	
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case12");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
//					if(newAngle>0 && 90>newAngle){
//						newAngle = newAngle-180;
//					}
					
						
				}
			}
			
			else if ( (angleBetween >= 90 && angleBetween <= 180) || (angleBetween >= -270 && angleBetween <= -180) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case21");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case22");
	
					dG = 1 / ( (1 / focus) + (1 / -dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
					
				}
			
			}
			else if ( (angleBetween >= 180 && angleBetween <= 270) || (angleBetween >= -180 && angleBetween <= -90) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case31");
							
					dG = 1 / ( (1 / ( focus)) + (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case32");
	
					
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
//					if(newAngle>0 && 90>newAngle){
//						newAngle = newAngle-180;
//					}
					
				
				}
			}
			else if ( (angleBetween >= 270 && angleBetween <= 360) || (angleBetween >= -90 && angleBetween <= 0) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case41");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x - Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
					
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case42");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
						
				}
			
			}
		}
		
//2
		else if( (rayAngle >= 90 && rayAngle <= 180) || (rayAngle >= 270 && rayAngle <= -180) )
		{
			if ( (angleBetween >= 0 && angleBetween <= 90) || (angleBetween >= -360 && angleBetween <= -270) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case11");
	
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case12");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
						
				}
			}
			
			else if ( (angleBetween >= 90 && angleBetween <= 180) || (angleBetween >= -270 && angleBetween <= -180) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case21");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case22");
	
					dG = 1 / ( (1 / focus) + (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
				}
			
			}
			else if ( (angleBetween >= 180 && angleBetween <= 270) || (angleBetween >= -180 && angleBetween <= -90) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case31");
							
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case32");
	
					
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
				
				}
			}
			else if ( (angleBetween >= 270 && angleBetween <= 360) || (angleBetween >= -90 && angleBetween <= 0) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case41");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case42");
	
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
						
				}
			
			}
		}
//3			
		else if( (rayAngle >= 180 && rayAngle <= 270) || (rayAngle >= -180 && rayAngle <= -90) )
		{
			if ( (angleBetween >= 0 && angleBetween <= 90) || (angleBetween >= -360 && angleBetween <= -270) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case11");
	
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case12");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
						
				}
			}
			
			else if ( (angleBetween >= 90 && angleBetween <= 180) || (angleBetween >= -270 && angleBetween <= -180) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case21");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case22");
	
					dG = 1 / ( (1 / focus) + (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
				}
			
			}
			else if ( (angleBetween >= 180 && angleBetween <= 270) || (angleBetween >= -180 && angleBetween <= -90) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case31");
							
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case32");
	
					
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
					
				
				}
			}
			else if ( (angleBetween >= 270 && angleBetween <= 360) || (angleBetween >= -90 && angleBetween <= 0) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case41");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case42");
	
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					if ( newAngle > -90 && newAngle < 0){
						newAngle = newAngle + 180;
					}
						
				}
			
			}
		}
//4 
		else if((rayAngle >= 270 && rayAngle <= 360) || (rayAngle >= -90 && rayAngle <= 0) )
		{
			if ( (angleBetween >= 0 && angleBetween <= 90) || (angleBetween >= -360 && angleBetween <= -270) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case11");
	
					dG = 1 / ( (1 / ( focus)) + (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
						
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case12");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
					
						
				}
			}
			
			else if ( (angleBetween >= 90 && angleBetween <= 180) || (angleBetween >= -270 && angleBetween <= -180) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case21");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case22");
	
					dG = 1 / ( (1 / focus) + (1 / -dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
					
				}
			
			}
			else if ( (angleBetween >= 180 && angleBetween <= 270) || (angleBetween >= -180 && angleBetween <= -90) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(angleBetween));
				
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case31");
							
					dG = 1 / ( (1 / ( focus)) + (1 / -dC) );
					dGx = (x + Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle-180))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case32");
	
					
					dG = 1 / ( (1 / ( focus)) + (1 / dC) );
					dGx = (x - Math.cos(Math.toRadians(angle))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
					
				
				}
			}
			else if ( (angleBetween >= 270 && angleBetween <= 360) || (angleBetween >= -90 && angleBetween <= 0) ) {
				
				dCx = (Math.tan(Math.toRadians(rayAngle))*xBound-Math.tan(Math.toRadians(angle))*x+y-yBound)/(Math.tan(Math.toRadians(rayAngle))-Math.tan(Math.toRadians(angle)));
				dCy = Math.tan(Math.toRadians(rayAngle))*(dCx-xBound)+yBound;
				dC = verticalDistance/Math.tan(Math.toRadians(-angleBetween));
			
				if ( Math.abs(centerIntercept-sourceIntercept) < 3 ){
				}
				
				else if( centerIntercept>sourceIntercept ){
	
	System.out.println("case41");
	
					dG = 1 / ( (1 / ( focus)) - (1 / -dC) );
					dGx = (x - Math.cos(Math.toRadians(angle-180))*dG);
					dGy = (y + Math.sin(Math.toRadians(angle-180))*dG);
					newAngle =  -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
				}
				
				else if( centerIntercept<sourceIntercept ){
	
	System.out.println("case42");
	
					dG = 1 / ( (1 / ( focus)) - (1 / dC) );
					dGx = (x + Math.cos(Math.toRadians(angle))*dG);
					dGy = (y - Math.sin(Math.toRadians(angle))*dG);
					newAngle = -( Math.toDegrees(atan((dGy-yBound),(dGx-xBound),dG) )); 
					
						
				}
			
			}
		}
		

		
//		System.out.println("dC\tdG\ta\tb\txBound\tyBound\trayAngle\tangle\tangleBetween\tsourceIntercept\tcenterIntercept\tverticalDistance\tnewAngle");
//		System.out.println(dC +"\t"+dG+"\t"+a+"\t"+b+"\t"+xBound+"\t"+yBound+"\t"+rayAngle+"\t"+angle+"\t"+angleBetween+"\t"+sourceIntercept+"\t"+centerIntercept+"\t"+verticalDistance+"\t"+newAngle);
		System.out.println("dC\tdG\ta\tb\txBound\tyBound\trayAngle");
		System.out.println(df.format(dC) +"\t"+df.format(dG)+"\t"+df.format(a)+"\t"+df.format(b)+"\t"+df.format(xBound)+"\t"+df.format(yBound)+"\t"+df.format(rayAngle));
		System.out.println("angle\tangleBetween\tsourceInt\tcenterInt\tverticalDis\tnewAngl");
		System.out.println(df.format(angle) +"\t"+df.format(angleBetween)+"\t"+"\t"+df.format(sourceIntercept)+"\t"+"\t"+df.format(centerIntercept)+"\t"+"\t"+df.format(verticalDistance)+"\t"+"\t"+df.format(newAngle));
		System.out.println ( df.format(dCx) + "\t" + df.format(dCy) + "\t" + df.format(dGx) + "\t" + df.format(dGy) +"\n");
//		return new Ray((int)dGx,(int)dGy,(int)newAngle,this);
		return new Ray((int)xBound,(int)yBound,(int)arg(newAngle),this);

	}
}
