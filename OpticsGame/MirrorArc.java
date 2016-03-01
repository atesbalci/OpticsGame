import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.io.*;
//class to geometrically express a curved mirror
public class MirrorArc implements Serializable {
	int x, y, angle, focus;
	Arc2D.Double arc;
	boolean isConcave;
	
	public MirrorArc(int x, int y, int focus, boolean isConcave) {
		this.x = x;
		this.y = y;
		this.focus = focus;
		this.isConcave = isConcave;
		if(isConcave)
			angle = 180;
		else
			angle = 0;
		arc = new Arc2D.Double((x - 2 * focus) + 2 * focus * Math.cos(Math.toRadians(angle)), (y - 2 * focus) - 2 * focus * Math.sin(Math.toRadians(angle)), focus * 4, focus * 4, 120 + angle, 120, Arc2D.OPEN);
	}
	
	public void setAngle(int angle) {
		if(isConcave) {
			this.angle = angle + 180;
			recalculate();
		}
		else {
			this.angle = angle;
			recalculate();
		}
	}
	
	public void setX(int x) {
		this.x = x;
		recalculate();
	}
	
	public void setY(int y) {
		this.y = y;
		recalculate();
	}
	
	public void setFocus(int f) {
		focus = f;
		recalculate();
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
//		g2.drawArc((int)arc.getX(), (int)arc.getY(), focus * 4, focus * 4, 120 + angle, 120);
		g2.draw(arc);
		g2.setStroke(new BasicStroke(5));
//		g2.setColor(Color.cyan); 
		g2.setColor(new Color(143, 143, 143, 125));
		if(!isConcave)
			g2.drawArc((int)arc.getX() - 3, (int)arc.getY() - 3, focus * 4 + 6, focus * 4 + 6, 120 + angle, 120);
		else
			g2.drawArc((int)arc.getX() + 3, (int)arc.getY() + 3, focus * 4 - 6, focus * 4 - 6, 120 + angle, 120);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(1));
	}
	
	public void recalculate() {
		arc.setArc((x - 2 * focus) + 2 * focus * Math.cos(Math.toRadians(angle)), (y - 2 * focus) - 2 * focus * Math.sin(Math.toRadians(angle)), focus * 4, focus * 4, 120 + angle, 120, Arc2D.OPEN);
	}
	
	public Arc2D.Double getArc() {
		return arc;
	}
	
	public Point getArcCenter() {
		return new Point( (int)arc.getX() + 2 * focus, (int)arc.getY() + 2 * focus );
	}
	
	public static void main(String[] args) {
		final MirrorArc a = new MirrorArc(400, 300, 50, false);
		final JFrame f = new JFrame("Test") {
			public void paint(Graphics g) {
				super.paint(g);
				a.draw(g);
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
