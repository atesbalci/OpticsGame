import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//proper inventory class with distinction of rotation locked and unlocked objets
public class CoreInventory extends Inventory {
	public void load(ArrayList<OpticObject> o) {
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).clear();
		}
		objects.clear();
		for(int i = 0; i < o.size(); i++) {
			o.get(i).reloadImages();
			addObject(o.get(i));
		}
	}
		
	public void addObject(OpticObject o) {
		o.setIcon(true);
		if(!o.locked())
			o.setAngle(0);
		objects.add(o);
		addToSlot(o);
	}
	
	public void addToSlot(OpticObject o) {
		if(o.locked()) {
			for(int i = 6; i < slots.size(); i++) {
				if(slots.get(i).size() < 1) {
					slots.get(i).add(o);
					break;
				}
			}
			refresh();
		}
		else
			super.addToSlot(o);
	}
	
	public static void main(String[] args) {
		CoreInventory ep;
		JFrame f;
		OpticObject o = new ConvexLens(0, 0, 25);
		o.setAngle(60);
		o.setLocked(true);
		
		ep = new CoreInventory();
		ep.addObject(new ConvexLens(0, 0, 25));
		ep.addObject(new ConvexLens(0, 0, 25));
		ep.addObject(new ConvexLens(0, 0, 25));
		ep.addObject(o);
		ep.addObject(new ConcaveLens(0, 0, 25));
		ep.addObject(new ConcaveLens(0, 0, 25));
		ep.addObject(new ConcaveMirror(0, 0, 25));
		ep.addObject(new ConcaveMirror(0, 0, 25));
		ep.addObject(new ConvexMirror(0, 0, 25));
		ep.addObject(new ConvexMirror(0, 0, 25));
		ep.addObject(new FlatMirror(0, 0));
		ep.addObject(new FlatMirror(0, 0));
		f = new JFrame("test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(ep);
		f.pack();
		f.setVisible(true);
	}
}
