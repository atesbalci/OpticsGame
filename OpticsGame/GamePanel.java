import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends CorePanel {
	int levelNo;
	
	public GamePanel() {
		levelNo = 0;
	}
	
	public boolean hasNextLevel() {
		return (levelNo > 0 && levelNo < 10);
	}
	
	public void loadNextLevel() {
		if(levelNo > 0 && levelNo < 10) {
			levelNo++;
			load("preMade/" + levelNo + ".sav");
			enableMouse();
			fieldPanel.repaint();
		}
	}
	
	public void setMenu(GameMenu gm) {
		fieldPanel.field.gameMenu = gm;
	}
	
	public static void main(String[] args) {
		GamePanel gp;
		JFrame f;
		OpticObject o = new ConvexLens(0, 0, 25);
		o.setAngle(60);
		o.setLocked(true);
		
		gp = new GamePanel();
//		gp.fieldPanel.getField().addRay(800, 300, 150);
		gp.fieldPanel.getField().addRay(0, 0, 330);
		gp.getInventory().addObject(new ConvexLens(0, 0, 25));
		gp.getInventory().addObject(new ConvexLens(0, 0, 25));
		gp.getInventory().addObject(new ConvexLens(0, 0, 25));
		gp.getInventory().addObject(o);
		gp.getInventory().addObject(new ConcaveLens(0, 0, 25));
		gp.getInventory().addObject(new ConcaveLens(0, 0, 25));
		gp.getInventory().addObject(new ConcaveMirror(0, 0, 25));
		gp.getInventory().addObject(new ConcaveMirror(0, 0, 25));
		gp.getInventory().addObject(new ConvexMirror(0, 0, 25));
		gp.getInventory().addObject(new ConvexMirror(0, 0, 25));
		gp.getInventory().addObject(new FlatMirror(0, 0));
		gp.getInventory().addObject(new FlatMirror(0, 0));
		f = new JFrame("test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(gp);
		f.pack();
		f.setVisible(true);
	}
}
