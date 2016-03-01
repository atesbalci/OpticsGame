import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
//abstract lens class which is extended by concave and convex lens classes
abstract class Lens extends OpticObject {
	Axis lensAxis;
	int focus;
	String imagePath;
	transient Image image, imageIcon;
	transient BufferedImage bufferedImage, bufferedIconImage;
	
	public Lens(int x, int y, int focus, String imagePath) {
       	this.x = x;
       	this.y = y;      
       	this.focus = focus;
       	this.imagePath = imagePath;
       	angle = 0;
       	lensAxis = new Axis(x, y, 90, 100);
       	reloadImages();
	}
	
	public void reloadImages() {
		bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_INDEXED);
       	bufferedIconImage = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_INDEXED);
       	try {   
          	bufferedImage = ImageIO.read(new File(imagePath));
       	} catch (IOException ex) {}
       	image = ImageUtils.rotateImage(bufferedImage, -angle);
       	try {   
          	bufferedIconImage = ImageIO.read(new File("icon_" + imagePath));
       	} catch (IOException ex) {}
       	imageIcon = ImageUtils.rotateImage(bufferedIconImage, -angle);
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
		lensAxis.setAngle(angle - 90);
		image = ImageUtils.rotateImage(bufferedImage, -angle);
		imageIcon = ImageUtils.rotateImage(bufferedIconImage, -angle);
	}
	
	public void setX(int xNew) {
		super.setX(xNew);
		lensAxis.setX(xNew);
	}
	
	public void setY(int yNew) {
		super.setY(yNew);
		lensAxis.setY(yNew);
	}
	
	public void draw(Graphics g) {	
		PrincipalAxis principalAxis;
		if(!isIcon) {
			g.drawImage(image, x - image.getWidth(null)/2, y - image.getHeight(null)/2, null);
			principalAxis = new PrincipalAxis(x, y, angle, focus);
		}
		else {
			g.drawImage(imageIcon, x - imageIcon.getWidth(null)/2, y - imageIcon.getHeight(null)/2, null);
			principalAxis = new PrincipalAxis(x, y, angle, focus*3/4);
		}
		principalAxis.draw(g);
	}
	
	public Ray action(Ray rayBeforeConvexLens) {
		return null;
	}
	
	public Axis getObstruction() {
		return lensAxis;
	}
}
