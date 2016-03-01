import java.awt.*;
import java.util.ArrayList;
//a helper class for inventory class
public class Slot {
	String type;
	int x, y;
	ArrayList<OpticObject> objects;
	OpticObject typeObject;
	boolean fixedType;
	
	public Slot(int x, int y) {
		objects = new ArrayList();
		this.x = x;
		this.y = y;
		fixedType = false;
		type = "";
	}
	
	public void setFixedType(boolean b) {
		fixedType = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String type() {
		return type;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public void setTypeObject(OpticObject o, String s) {
		type = s;
		typeObject = o;
	}
	
	public void add(OpticObject o) {
		objects.add(o);
		refresh();
	}
	
	public void remove(OpticObject o) {
		objects.remove(o);
	}
	
	public void refresh() {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).setX(x);
			objects.get(i).setY(y);
		}
	}
	
	public int size() {
		return objects.size();
	}
	
	public OpticObject get(int i) {
		return objects.get(i);
	}
	
	public void draw(Graphics g) {
		if(typeObject != null)
			typeObject.draw(g);
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).draw(g);
		}
	}
	
	public void clear() {
		objects.clear();
		if(!fixedType)
			type = "";
	}
}
