import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//extends coreinventory to add the ability to add/remove objects
public class EditorInventory extends CoreInventory {
	ArrayList<JButton> addButtons, removeButtons;

	public EditorInventory() {
		super();

		addButtons = new ArrayList();
		for(int i = 0; i < 6; i++) {
			addButtons.add(new JButton("+"));
			addButtons.get(i).setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
			addButtons.get(i).setSize(15, 15);
			addButtons.get(i).setLocation(slots.get(i).getX() - 35, slots.get(i).getY() - 30);
			addButtons.get(i).addActionListener(new InventoryAdd());
			addButtons.get(i).setBorder(null);
			add(addButtons.get(i));
		}

		removeButtons = new ArrayList();
		for(int i = 0; i < 6; i++) {
			removeButtons.add(new JButton("-"));
			removeButtons.get(i).setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
			removeButtons.get(i).setSize(15, 15);
			removeButtons.get(i).setLocation(slots.get(i).getX() - 35, slots.get(i).getY() + 15);
			removeButtons.get(i).addActionListener(new InventoryRemove());
			removeButtons.get(i).setBorder(null);
			add(removeButtons.get(i));
		}

		slots.get(0).setType("ConvexLens");
		slots.get(0).setFixedType(true);
		slots.get(1).setType("ConcaveLens");
		slots.get(1).setFixedType(true);
		slots.get(2).setType("ConvexMirror");
		slots.get(2).setFixedType(true);
		slots.get(3).setType("ConcaveMirror");
		slots.get(3).setFixedType(true);
		slots.get(4).setType("FlatMirror");
		slots.get(4).setFixedType(true);
		slots.get(5).setType("Prism");
		slots.get(5).setFixedType(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		OpticObject o1 = (new ConvexLens(slots.get(0).getX(), slots.get(0).getY(), 25));
		o1.setIcon(true);
		o1.draw(g);
		OpticObject o2 = (new ConcaveLens(slots.get(1).getX(), slots.get(1).getY(), 25));
		o2.setIcon(true);
		o2.draw(g);
		OpticObject o3 = (new ConvexMirror(slots.get(2).getX(), slots.get(2).getY(), 25));
		o3.setIcon(true);
		o3.draw(g);
		OpticObject o4 = (new ConcaveMirror(slots.get(3).getX(), slots.get(3).getY(), 25));
		o4.setIcon(true);
		o4.draw(g);
		OpticObject o5 = (new FlatMirror(slots.get(4).getX(), slots.get(4).getY()));
		o5.setIcon(true);
		o5.draw(g);
		OpticObject o6 = (new Prism(slots.get(5).getX(), slots.get(5).getY(), 4));
		o6.setIcon(true);
		o6.draw(g);
		for(int i = 0; i < 6; i++) {
			if(slots.get(i).size() < 2)
				g.drawString( "x" + slots.get(i).size(), slots.get(i).getX() + 20, slots.get(i).getY() + 30 );
		}
	}

	public class InventoryAdd implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addButtons.get(0))
				addObject(new ConvexLens(0, 0, 25));
			if(e.getSource() == addButtons.get(1))
				addObject(new ConcaveLens(0, 0, 25));
			if(e.getSource() == addButtons.get(2))
				addObject(new ConvexMirror(0, 0, 25));
			if(e.getSource() == addButtons.get(3))
				addObject(new ConcaveMirror(0, 0, 25));
			if(e.getSource() == addButtons.get(4))
				addObject(new FlatMirror(0, 0));
			if(e.getSource() == addButtons.get(5))
				addObject(new Prism(0, 0, 1.5));
		}
	}

	public class InventoryRemove implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == removeButtons.get(0) && slots.get(0).size() > 0)
				removeObject(slots.get(0).get(0));
			if(e.getSource() == removeButtons.get(1) && slots.get(1).size() > 0)
				removeObject(slots.get(1).get(0));
			if(e.getSource() == removeButtons.get(2) && slots.get(2).size() > 0)
				removeObject(slots.get(2).get(0));
			if(e.getSource() == removeButtons.get(3) && slots.get(3).size() > 0)
				removeObject(slots.get(3).get(0));
			if(e.getSource() == removeButtons.get(4) && slots.get(4).size() > 0)
				removeObject(slots.get(4).get(0));
			if(e.getSource() == removeButtons.get(5) && slots.get(5).size() > 0)
				removeObject(slots.get(5).get(0));
		}
	}

	public static void main(String[] args) {
		EditorInventory i = new EditorInventory();
		JFrame f = new JFrame("test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(i);
		f.pack();
		f.setVisible(true);
	}
}
