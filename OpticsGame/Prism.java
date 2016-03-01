import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
//Prism class
class Prism extends OpticObject {
	double refractionIndex ;
	int limitAngle ;
	PrismShape prism;
	transient BufferedImage bufferedImage, bufferedImageIcon;
	transient Image image, imageIcon;

	public Prism(int x, int y, double refractionIndex) {
       	this.x = x;
       	this.y = y;
       	this.refractionIndex = refractionIndex;
       	limitAngle = (int)arg(Math.toDegrees(Math.asin(1/refractionIndex))) ;
       	prism = new PrismShape(x, y, 140);
       	angle = 0;
       	reloadImages();
	}

	public void reloadImages() {
		try {
          	bufferedImage = ImageIO.read(new File("prism.png"));
       	} catch (IOException ex) {}
       	image = ImageUtils.rotateImage(bufferedImage, -angle);

       	try {
          	bufferedImageIcon = ImageIO.read(new File("prismIcon.png"));
       	} catch (IOException ex) {}
       	imageIcon = ImageUtils.rotateImage(bufferedImageIcon, -angle);
	}

	public boolean isClicked(Point e) {
		if((e.getY() < y + 50) && (e.getY() > y - 50) && (e.getX() < x + 50) && (e.getX() > x - 50)) {
			return true;
		}
		return false;
	}

	public void setAngle(int angle) {
		this.angle = angle;
		prism.setAngle(angle);
		image = ImageUtils.rotateImage(bufferedImage, -angle);
		imageIcon = ImageUtils.rotateImage(bufferedImageIcon, -angle);
	}

	public void setX(int x) {
		this.x = x;
		prism.setX(x);
	}

	public void setY(int y) {
		this.y = y;
		prism.setY(y);
	}

	public void draw(Graphics g) {
		if(isIcon) {
//			PrismShape p = new PrismShape(x, y, 40);
//			p.setAngle(angle);
//			p.draw(g);
			g.drawImage(imageIcon, x - imageIcon.getWidth(null)/2, y - imageIcon.getHeight(null)/2, null);
		}
		else {
//			prism.draw(g);
			g.drawImage(image, x - image.getWidth(null)/2, y - image.getHeight(null)/2, null);
		}
	}

	//since it has sides instead of an axis, returns null
	public Axis getObstruction() {
		return null;
	}

	//gets the sides in an array
	public Line2D.Double[] getSides() {
		return prism.getSides();
	}

	public double arg90 (double angle)
	{
		angle = arg(angle) ;
		while(angle > 90)
		{
			angle = angle - 90 ;
		}
		return angle ;
	}

	public Ray action(Ray rayBeforeFraction) {
		Ray nRay ;
		int intX = rayBeforeFraction.xBound ;
		int intY = rayBeforeFraction.yBound ;
		System.out.println(rayBeforeFraction.isInside) ;

		if(!rayBeforeFraction.isInside){

			if(rayBeforeFraction.intSide(this) == 2){

				nRay = new Ray( intX,
								intY,
								(int)arg(angle + 90 - Math.toDegrees(Math.asin(Math.sin(Math.toRadians(angle + 90 - rayBeforeFraction.angle))/refractionIndex))),
								null) ;
				nRay.setIsInside(true) ;
			}
			else if(rayBeforeFraction.intSide(this) == 1){

				nRay = new Ray( intX,
								intY,
								(int)arg(angle + Math.toDegrees(Math.asin(Math.sin(Math.toRadians(rayBeforeFraction.angle - angle))/refractionIndex))),
								null) ;
				nRay.setIsInside(true) ;
			}
			else{

				nRay = new Ray( intX,
								intY,
								(int) arg(angle + 225 - Math.toDegrees(Math.asin(Math.sin(Math.toRadians(angle - rayBeforeFraction.angle + 225))/refractionIndex))),
								null) ;
				nRay.setIsInside(true) ;
			}

		}
		else
		{

			int normalAngleInside = (int)arg90(-45 - angle + rayBeforeFraction.angle) ;
			System.out.println("normal:" + normalAngleInside + "\nintSide:" + rayBeforeFraction.intSide) ;

			if(rayBeforeFraction.intSide == 0){

					nRay = new Ray (intX , intY ,
									(int)arg(45 + angle - Math.toDegrees(Math.asin(Math.toRadians(45 + angle - rayBeforeFraction.angle))*refractionIndex))
									, null) ;
					if(normalAngleInside < limitAngle)
						nRay.setIsInside(false) ;
					else
						nRay.setIsInside(true) ;


				System.out.println("nRayAngle:"+nRay.angle) ;
			}
		//	else
			//	return null ;
			//}
//			else if (rayBeforeFraction.intSide == 1)
//			{
//				normalAngleInside = (int)arg(180-rayBeforeFraction.angle + angle) ;
////				if (normalAngleInside > limitAngle )
////				{
////					nRay = new Ray (intX , intY ,
////									(int)arg(2*angle + rayBeforeFraction.angle - 180) , null) ;
////				}
////				else{
//					nRay = new Ray (intX , intY ,
//									(int)arg(+180+ angle - Math.toDegrees(Math.asin(Math.toRadians(normalAngleInside))*refractionIndex)),
//									null) ;
//					nRay.setIsInside(false) ;
//					if(normalAngleInside > limitAngle)
//					{
//						nRay.setIsInside(true) ;
//					}
//			}
			else
				nRay = null ;
			}

	//	}
			return nRay ;
	}
}
