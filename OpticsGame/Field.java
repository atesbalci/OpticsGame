import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;
//Basically the engine of the program. It checks the path of rays, obstructs them with the closest intersecting optic object and
//calls that optic object's action class. Recursively egenerates new rays until the cycle is finished.
public class Field {
	ArrayList<OpticObject> objects;
	ArrayList<Ray> rays, sourceRays;
	ArrayList<Target> targets;
	GameMenu gameMenu;
	
	public Field() {
		objects = new ArrayList();
		rays = new ArrayList();
		sourceRays = new ArrayList();
		targets = new ArrayList();
	}
	
	public int getNumOfTargets() {
		return targets.size();
	}
	
	public void addSourceRay(Ray r) {
		r.setIsSource(true);
		sourceRays.add(r);
		refreshRays();
	}
	
	public void addRay(int x, int y, int angle) {
		Ray r = new Ray(x, y, angle, null);
		r.setIsSource(true);
		sourceRays.add(r);
		refreshRays();
	}
	
	//recursive method, checking and generating new rays
	private void addRay(Ray r) {
		rays.add(r);
		int closestIndex = -1, closestDistance = 100000, distance = 100000;
			
		for(int i = 0; i < objects.size(); i++) {
			if( r.intersection(objects.get(i)) != null ) {
				distance = (int)Math.hypot( r.x - r.intersection(objects.get(i)).getX(), r.y - r.intersection(objects.get(i)).getY() );
				if( distance < closestDistance ) {
					closestDistance = distance;
					closestIndex = i;
				}
			}
		}
		
		if(closestIndex > -1) {
			Point p = r.intersection(objects.get(closestIndex));
			r.obstruct( p.x, p.y );
			if(objects.get(closestIndex).action(r) != null)
				addRay( objects.get(closestIndex).action(r) );
		}
	}
	
	public void refreshRays() {
		rays = new ArrayList();
		for(int i = 0; i < targets.size(); i++) {
			targets.get(i).setHit(false);
		}
		for(int i = 0; i < sourceRays.size(); i++) {
			sourceRays.get(i).recalculate();
			addRay( sourceRays.get(i) );
		}
		int n = 0;
		for(int i = 0; i < targets.size(); i++) {
			if(targets.get(i).hit)
				n++;
		}
		if(n >= targets.size()) {
			if(gameMenu != null) {
				gameMenu.gameOver();
			}
		}
	}
	
	public void addOpticObject(OpticObject o) {
		objects.add(o);
		if(o instanceof Target)
			targets.add( (Target)o );
		refreshRays();
	}
	
	public void removeOpticObject(OpticObject o) {
		objects.remove(o);
		if(o instanceof Target)
			targets.remove( (Target)o );
		refreshRays();
	}
	
	public void move(int x, int y, int i) {
		objects.get(i).setX(x);
		objects.get(i).setY(y);
		refreshRays();
	}
	
	public void move(int x, int y, OpticObject o) {
		o.setX(x);
		o.setY(y);
		refreshRays();
	}
	
	public void rotate(int angle, int i) {
		objects.get(i).setAngle(angle);
		refreshRays();
	}
	
	public void rotate(int angle, OpticObject o) {
		o.setAngle(angle);
		refreshRays();
	}
	
	public void moveRay(int x, int y, Ray r) {
		r.setX(x);
		r.setY(y);
		refreshRays();
	}
	
	public void rotateRay(int angle, Ray r) {
		r.setAngle(angle);
		refreshRays();
	}
	
	public void removeRay(Ray r) {
		sourceRays.remove(r);
		refreshRays();
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < rays.size(); i++)
			rays.get(i).draw(g);
		for(int i = 0; i < objects.size(); i++)
			objects.get(i).draw(g);
	}
	
	public void loadObjects(ArrayList<OpticObject> objs) {
		objects.clear();
		targets.clear();
		
		for(int i = 0; i < objs.size(); i++) {
			objs.get(i).reloadImages();
			addOpticObject(objs.get(i));
		}
	}
	
	public void loadRays(ArrayList<Ray> rs) {
		rays.clear();
		sourceRays.clear();
		
		for(int i = 0; i < rs.size(); i++) {
			rs.get(i).reloadImages();
			addSourceRay(rs.get(i));
		}
	}
}
