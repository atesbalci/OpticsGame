import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
//a panel extending CorePanel which is the engine of editor mode
public class EditorPanel extends CorePanel {
	EditorField editorField;
	JButton addObs, addTarg, addRay, save;
	JTextField nameField;
	
	public EditorPanel() {
		remove(inventory);
		remove(fieldPanel);
		inventory = new EditorInventory();
		fieldPanel = new EditorField();
		editorField = (EditorField)fieldPanel;
		field = fieldPanel.getField();
		editorField.addMouseListener(new FieldMouse());
		editorField.addTrashAction(new EditorTrashAction());
		inventory.addMouseListener(new InventoryMouse());
		inventory.addMouseMotionListener(new InventoryMouse());
		add("East", inventory);
		add("Center", editorField);
		
		addRay = new JButton("Add Ray");
		addRay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorField.field.addRay(400, 300, 0);
				editorField.repaint();
			}
		});
		addObs = new JButton("Add Obstacle");
		addObs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorField.field.addOpticObject(new Obstacle(400, 300));
				editorField.repaint();
			}
		});
		addTarg = new JButton("Add Target");
		addTarg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorField.field.addOpticObject(new Target(400, 300));
				editorField.repaint();
			}
		});
		nameField = new JTextField(10);		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().length() > 0)
					name = nameField.getText();
				save();
			}
		});
		menu.add(addObs);
		menu.add(addTarg);
		menu.add(addRay);
		menu.add(nameField);
		menu.add(save);
	}
	
	public class EditorTrashAction extends TrashAction {
		public void actionPerformed(ActionEvent e) {
			editorField.check.setVisible(false);
			super.actionPerformed(e);
		}
	}
}
