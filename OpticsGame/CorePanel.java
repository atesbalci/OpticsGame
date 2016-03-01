import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
//core panel class which is extendd by either gamepanel or editorpanel depending on game mode
class CorePanel extends JPanel {
	FieldPanel fieldPanel;
	Field field;
	CoreInventory inventory;
	boolean clicked, transfer;
	OpticObject active;
	String name;
	String filePath;
	JPanel menu;
	JButton back;
	
	public CorePanel() {
		name = "Untitled Level";
		fieldPanel = new FieldPanel();
		field = fieldPanel.getField();
		inventory = new CoreInventory();
		clicked = false;
		transfer = false;
		setLayout(new BorderLayout());
		fieldPanel.addMouseListener(new FieldMouse());
		inventory.addMouseListener(new InventoryMouse());
		inventory.addMouseMotionListener(new InventoryMouse());
		fieldPanel.addTrashAction(new TrashAction());
		add("West", fieldPanel);
		add("East", inventory);
		menu = new JPanel();
		menu.setPreferredSize(new Dimension(980, 40));
		back = new JButton("Back");
		menu.add(back);
		add("South", menu);
	}
	
	public void disableMouse() {
		fieldPanel.removeMouseListener(fieldPanel.mouse);
		fieldPanel.removeMouseMotionListener(fieldPanel.mouse);
		inventory.removeMouseListener(inventory.mouse);
		inventory.removeMouseListener(inventory.mouse);
		fieldPanel.deselect();
	}
	
	public void enableMouse() {
		fieldPanel.addMouseListener(fieldPanel.mouse);
		fieldPanel.addMouseMotionListener(fieldPanel.mouse);
		inventory.addMouseListener(inventory.mouse);
		inventory.addMouseListener(inventory.mouse);
	}
	
	public void addBackAction(ActionListener al) {
		back.addActionListener(al);
	}
	
	public void save() {
		if(filePath == null)
			filePath = "custom/" + name + ".sav";
		
		try {
			FileOutputStream saveFile = new FileOutputStream(filePath);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.flush();
			save.writeObject(name);
			save.writeObject(fieldPanel.getField().objects);
			save.writeObject(fieldPanel.getField().sourceRays);
			save.writeObject(inventory.objects);
			save.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load(String fileName) {
		ArrayList<OpticObject> temp = null, temp3 = null;
		ArrayList<Ray> temp2 = null;
		filePath = fileName;
		
		try {
			FileInputStream saveFile = new FileInputStream(fileName);
			ObjectInputStream load = new ObjectInputStream(saveFile);
			name = (String)load.readObject();
			temp = (ArrayList<OpticObject>)load.readObject();
			temp2 = (ArrayList<Ray>)load.readObject();
			temp3 = (ArrayList<OpticObject>)load.readObject();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(temp != null && temp2 != null && temp3 != null) {
			fieldPanel.getField().loadObjects(temp);
			fieldPanel.getField().loadRays(temp2);
			fieldPanel.repaint();
			inventory.load(temp3);
		}
		else
			System.out.println("Null!");
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public class TrashAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OpticObject o = fieldPanel.active;
			inventory.addObject(o);
			field.removeOpticObject(o);
			fieldPanel.slider.setVisible(false);
			fieldPanel.trash.setVisible(false);
			fieldPanel.repaint();
		}
	}
	
	protected class InventoryMouse extends MouseAdapter {		
		public void mouseDragged(MouseEvent e) {
			if(inventory.getActive() != null) {
				transfer = true;
				active = inventory.getActive();
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			transfer = false;
		}
	}
	
	protected class FieldMouse extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			if(transfer) {
				field.addOpticObject(active);
				active.setX(e.getX());
				active.setY(e.getY());
				fieldPanel.repaint();
				fieldPanel.clicked = true;
				fieldPanel.active = active;
				inventory.removeObject(active);
			}
		}
	}
}
