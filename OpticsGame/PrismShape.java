import java.awt.geom.*;
import java.awt.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.event.*;
//a helper class to geometrically express prism class
public class PrismShape implements Serializable {
	Line2D.Double side1, side2, side3;
	int x, y, angle, size;
	
	public PrismShape(int x, int y, int size) {
		this.x = x;
		this.y = y;
		angle = 0;
		this.size = size;
		side1 = new Line2D.Double();
		side2 = new Line2D.Double();
		side3 = new Line2D.Double();
		recalculate();
	}
	
	public void recalculate() {
		Point p1 = new Point((int)(x + size / 2 * Math.cos(-Math.toRadians(angle+135))), (int)(y + size / 2 * Math.sin(-Math.toRadians(angle+135))));
		Point p2 = new Point((int)(x + size / 2 * Math.cos(-Math.toRadians(angle-45))), (int)(y + size / 2 * Math.sin(-Math.toRadians(angle-45))));
		Point p3 = new Point((int)(x + size / 2 * Math.cos(-Math.toRadians(angle-135))), (int)(y + size / 2 * Math.sin(-Math.toRadians(angle-135))));
		
		side1.setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		side2.setLine(p1.getX(), p1.getY(), p3.getX(), p3.getY());
		side3.setLine(p3.getX(), p3.getY(), p2.getX(), p2.getY());
	}
	
	public void recalculateOld() {
		side1.setLine(x - size / 2, y + size / 2, x - size / 2 + size * Math.cos(Math.toRadians(angle)), y + size / 2 + size * Math.sin(Math.toRadians(-angle)));
		side2.setLine(x - size / 2, y + size / 2, x - size / 2 + size * Math.cos(Math.toRadians(angle + 90)), y + size / 2 + size * Math.sin(Math.toRadians(-(angle + 90))));
		side3.setLine(x - size / 2 + size * Math.cos(Math.toRadians(angle + 90)), y + size / 2 + size * Math.sin(Math.toRadians(-(angle + 90))), x - size / 2 + size * Math.cos(Math.toRadians(angle)), y + size / 2 + size * Math.sin(Math.toRadians(-angle)));
	}
	
	public Line2D.Double[] getSides() {
		Line2D.Double[] array = new Line2D.Double[3];
		array[0] = side1;
		array[1] = side2;
		array[2] = side3;
		return array;
	}
	
	public void setX(int x) {
		this.x = x;
		recalculate();
	}
	
	public void setY(int y) {
		this.y = y;
		recalculate();
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
		recalculate();
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.draw(side1);
		g2.draw(side2);
		g2.draw(side3);
		g2.setStroke(new BasicStroke(1));
	}
	
	public static void main(String[] args) {
		final PrismShape a = new PrismShape(400, 300, 50);
		final JFrame f = new JFrame("Test") {
			public void paint(Graphics g) {
				super.paint(g);
				a.draw(g);
//				g.drawOval(a.getShapeCenter().x - 4, a.getShapeCenter().y - 4, 8, 8);
//				g.drawOval(a.getTop().x - 4, a.getTop().y - 4, 8, 8);
			}
		};
		final JSlider s = new JSlider(0, 360, 0);
		s.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				a.setAngle(s.getValue());
				f.repaint();
			}
		});
		f.add("South" , s);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(new Dimension(800, 600));
		f.setVisible(true);
	}
}
