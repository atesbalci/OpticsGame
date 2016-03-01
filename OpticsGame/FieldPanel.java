import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
//a panel to draw the field class & ability to move and rotate objects inside it
public class FieldPanel extends JPanel {
	Field field;
	ArrayList<OpticObject> objects;
	OpticObject active;
	boolean clicked, entered;
	int xDiff, yDiff;
	JSlider slider;
	UIDefaults defaults;
	JButton trash;
	BufferedImage back;
	MouseAdapter mouse;
	
	public FieldPanel() {
		super();
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.white);
		field = new Field();
		slider = new JSlider(SwingConstants.VERTICAL, 0, 360, 0);
		add(slider);
		slider.setVisible(false);
		slider.setOpaque(false);
		slider.setSize(20, 100);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				field.rotate(slider.getValue(), active);
				repaint();
			}
		});
		trash = new JButton("i");
		trash.setSize(15, 15);
		trash.setOpaque(false);
		trash.setVisible(false);
		trash.setBorder(null);
		add(trash);
		setLayout(null);
		mouse = new FieldMouseAdapter();
		addMouseListener(mouse);	
		addMouseMotionListener(mouse);	
		objects = field.objects;
		clicked = false;
		active = null;
		defaults = UIManager.getDefaults();
		//defaults.put("Slider.horizontalThumbIcon", icon);
		back = new BufferedImage(800, 600, BufferedImage.TYPE_BYTE_INDEXED);
		try {   
          	back = ImageIO.read(new File("back.png"));
       	} catch (IOException ex) {}
	}
	
	public void deselect() {
		active = null;
		trash.setVisible(false);
		slider.setVisible(false);
	}
	
	public void addTrashAction(ActionListener a) {
		trash.addActionListener(a);
	}
	
	public Field getField() {
		return field;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	g.drawImage(back, 0, 0, null);
		field.draw(g);
	}
	
	protected class FieldMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			for(int i = 0; i < objects.size() && !clicked; i++) {
				if(objects.get(i).isClicked(e.getPoint()) && !(objects.get(i) instanceof Obstacle) && !(objects.get(i) instanceof Target)) {
					active = objects.get(i);
					slider.setVisible(true);
					slider.setVisible(!active.locked());
					slider.setLocation(active.getX() + 60, active.getY() - 60);
					slider.setValue(active.getAngle());
					trash.setVisible(true);
					trash.setLocation(active.getX() + 45, active.getY() + 30);
					clicked = true;
					xDiff = active.getX() - e.getX();
					yDiff = active.getY() - e.getY();
				}
			}
			if(!clicked) {
				slider.setVisible(false);
				trash.setVisible(false);
				active = null;
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			clicked = false;
		}
		
		public void mouseDragged(MouseEvent e) {
			if(clicked) {
				slider.setLocation(active.getX() + 60, active.getY() - 60);
				slider.setVisible(!active.locked());
				slider.setValue(active.getAngle());
				trash.setVisible(true);
				trash.setLocation(active.getX() + 45, active.getY() + 30);
				field.move(e.getX() + xDiff, e.getY() + yDiff, active);
				repaint();
			}
		}
	}
}
