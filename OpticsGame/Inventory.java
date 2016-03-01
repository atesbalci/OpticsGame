import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
//basic inventory class which is extended by  core inventory to distinguish rotation locked and not rotation locked objects
public class Inventory extends JPanel {
	ArrayList<OpticObject> objects;
	ArrayList<Slot> slots;
	boolean clicked, exited;
	int active;
	BufferedImage back;
	MouseAdapter mouse;
	
	public Inventory() {
		setPreferredSize(new Dimension(180, 600));
		setBackground(Color.green);
		setLayout(null);
		clicked = false;
		exited = false;
		active = -1;
		objects = new ArrayList();
		slots = new ArrayList();
		
		int y = 60;
		int x = 45;
		for(int i = 0; i < 12; i++) {
			slots.add(new Slot(x, y));
			if(x == 45)
				x = 135;
			else {
				x = 45;
				y += 100;
			}
		}
		
		mouse = new InventoryMouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		back = new BufferedImage(180, 600, BufferedImage.TYPE_BYTE_INDEXED);
		try {   
          	back = ImageIO.read(new File("backInventory.png"));
       	} catch (IOException ex) {}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0, null);
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).draw(g);
			if(slots.get(i).size() > 1) {
				g.drawString( "x" + slots.get(i).size(), slots.get(i).getX() + 20, slots.get(i).getY() + 30 );
			}
		}
	}
	
	public OpticObject getActive() {
		if(active > -1 && active < objects.size())
			return objects.get(active);
		return null;
	}
	
	public void refresh() {
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).refresh();
		}
		repaint();
	}
	
	public void addObject(OpticObject o) {
		o.setIcon(true);
		o.setAngle(0);
		objects.add(o);
		addToSlot(o);
	}
	
	public void addToSlot(OpticObject o) {
		boolean exists = false;
		int i;
		String type = "";
		
		if(o instanceof ConvexLens)
			type = "ConvexLens";			
		if(o instanceof ConcaveLens)
			type = "ConcaveLens";			
		if(o instanceof ConvexMirror)
			type = "ConvexMirror";			
		if(o instanceof ConcaveMirror)
			type = "ConcaveMirror";			
		if(o instanceof FlatMirror)
			type = "FlatMirror";			
		if(o instanceof Prism)
			type = "Prism";
			
		for(i = 0; i < slots.size(); i++) {
			if(slots.get(i).type().equals(type)) {
				exists = true;
				slots.get(i).add(o);
			}
		}
		
		if(!exists) {
			for(i = 0; i < slots.size(); i++) {
				if(slots.get(i).type().equals("")) {
					slots.get(i).add(o);
					slots.get(i).setType(type);
					break;
				}
			}
		}
		
		refresh();
	}
	
	public void removeObject(int i) {
		objects.get(i).setIcon(false);
		objects.remove(i);
		for(int k = 0; k < slots.size(); k++) {
			slots.get(k).clear();
		}
		
		for(int k = 0; k < objects.size(); k++) {
			addToSlot(objects.get(k));
		}
		refresh();
	}
	
	public void removeObject(OpticObject o) {
		removeObject(objects.indexOf(o));
	}
	
	private class InventoryMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			for(int i = 0; i < objects.size(); i++) {
				if(objects.get(i).isClicked(e.getPoint())) {
					clicked = true;
					active = i;
					break;
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			clicked = false;
			active = -1;
			refresh();
		}
		
		public void mouseExited(MouseEvent e) {
			exited = true;
		}
		
		public void mouseEntered(MouseEvent e) {
			exited = false;
		}
		
		public void mouseDragged(MouseEvent e) {
			if(clicked && !exited) {
				objects.get(active).setX(e.getX());
				objects.get(active).setY(e.getY());
				repaint();
			}
		}
	}
}
