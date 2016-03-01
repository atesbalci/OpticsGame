import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
//a class extending FieldPanel which adds the ability to move/rotate rays, move/resize obstacles, add optic objects, lock/unlock optic objects
public class EditorField extends FieldPanel {
	JCheckBox check;
	JButton trashObs, trashTarg, trashRay;
	boolean obsClicked, targClicked, rayClicked;
	JSlider wSlider, hSlider, rayRotater;
	Obstacle activeObs;
	Target activeTarg;
	Ray activeRay;
	ArrayList<Ray> rays;
	
	public EditorField() {
		super();
		rays = field.sourceRays;
		obsClicked = false;
		targClicked = false;
		rayClicked = false;
		check = new JCheckBox();
		check.setSize(20, 20);
		check.setOpaque(false);
		check.setVisible(false);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active.setLocked(!active.locked());
				slider.setVisible(!active.locked());
			}
		});
		trashObs = new JButton("x");
		trashObs.setSize(20, 20);
		trashObs.setOpaque(false);
		trashObs.setVisible(false);
		trashObs.setBorder(null);
		trashObs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.removeOpticObject(activeObs);
				repaint();
				wSlider.setVisible(false);
				hSlider.setVisible(false);
				trashObs.setVisible(false);
			}
		});
		trashTarg = new JButton("x");
		trashTarg.setSize(20, 20);
		trashTarg.setOpaque(false);
		trashTarg.setVisible(false);
		trashTarg.setBorder(null);
		trashTarg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.removeOpticObject(activeTarg);
				repaint();
				trashTarg.setVisible(false);
			}
		});
		trashRay = new JButton("x");
		trashRay.setSize(20, 20);
		trashRay.setOpaque(false);
		trashRay.setVisible(false);
		trashRay.setBorder(null);
		trashRay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.removeRay(activeRay);
				repaint();
				rayRotater.setVisible(false);
				trashRay.setVisible(false);
			}
		});
		addMouseListener(new EditorMouse());
		addMouseMotionListener(new EditorMouse());
		add(check);
		add(trashObs);
		add(trashTarg);
		add(trashRay);
		wSlider = new JSlider(SwingConstants.HORIZONTAL, 20, 400, 20);
		add(wSlider);
		wSlider.setVisible(false);
		wSlider.setOpaque(false);
		wSlider.setSize(100, 20);
		wSlider.addChangeListener(new ObstacleResize());
		hSlider = new JSlider(SwingConstants.VERTICAL, 20, 400, 20);
		hSlider.setInverted(true);
		add(hSlider);
		hSlider.setVisible(false);
		hSlider.setOpaque(false);
		hSlider.setSize(20, 100);
		hSlider.addChangeListener(new ObstacleResize());
		rayRotater = new JSlider(SwingConstants.VERTICAL, 0, 360, 0);
		add(rayRotater);
		rayRotater.setVisible(false);
		rayRotater.setOpaque(false);
		rayRotater.setSize(20, 75);
		rayRotater.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				field.rotateRay(rayRotater.getValue(), activeRay);
				repaint();
			}
		});		
	}
	
	private class ObstacleResize implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if(e.getSource() == wSlider) {
				activeObs.setWidth(wSlider.getValue());
				field.refreshRays();
				repaint();
			}
			if(e.getSource() == hSlider) {
				activeObs.setHeight(hSlider.getValue());
				field.refreshRays();
				repaint();
			}
		}
	}
	
	private class EditorMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(clicked) {
				check.setVisible(true);
				check.setLocation(active.getX() + 40, active.getY() - 50);
				check.setSelected(active.locked());
			}
			else {
				check.setVisible(false);
			}
			
			for(int i = 0; i < objects.size() && !obsClicked && !clicked && !rayClicked && !targClicked; i++) {
				if(objects.get(i).isClicked(e.getPoint()) && (objects.get(i) instanceof Obstacle)) {
					activeObs = (Obstacle)objects.get(i);
					wSlider.setVisible(true);
					wSlider.setLocation(activeObs.getX(), activeObs.getY() - 20);
					wSlider.setValue(activeObs.width);
					hSlider.setVisible(true);
					hSlider.setLocation(activeObs.getX() - 20, activeObs.getY());
					hSlider.setValue(activeObs.height);
					trashObs.setVisible(true);
					trashObs.setLocation(activeObs.getX() - 20, activeObs.getY() - 20);
					obsClicked = true;
					xDiff = activeObs.getX() - e.getX();
					yDiff = activeObs.getY() - e.getY();
				}
			}
			if(!obsClicked) {
				wSlider.setVisible(false);
				hSlider.setVisible(false);
				trashObs.setVisible(false);
				activeObs = null;
			}
			
			for(int i = 0; i < objects.size() && !obsClicked && !clicked && !targClicked && !rayClicked; i++) {
				if(objects.get(i).isClicked(e.getPoint()) && (objects.get(i) instanceof Target)) {
					activeTarg = (Target)objects.get(i);
					targClicked = true;
					xDiff = activeTarg.getX() - e.getX();
					yDiff = activeTarg.getY() - e.getY();
					trashTarg.setVisible(true);
					trashTarg.setLocation(activeTarg.getX() - activeTarg.radius - 15, activeTarg.getY() - activeTarg.radius - 15);
				}
			}
			if(!targClicked) {
				activeTarg = null;
				trashTarg.setVisible(false);
			}
			
			for(int i = 0; i < rays.size() && !obsClicked && !clicked && !targClicked && !rayClicked; i++) {
				if(rays.get(i).isClicked(e.getPoint())) {
					activeRay = rays.get(i);
					rayClicked = true;
					xDiff = activeRay.x - e.getX();
					yDiff = activeRay.y - e.getY();
					rayRotater.setVisible(true);
					rayRotater.setLocation(activeRay.x + 25, activeRay.y - 25);
					rayRotater.setValue(activeRay.angle);
					trashRay.setVisible(true);
					trashRay.setLocation(activeRay.x - 25, activeRay.y - 25);
				}
			}
			if(!rayClicked) {
				activeRay = null;
				rayRotater.setVisible(false);
				trashRay.setVisible(false);
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			obsClicked = false;
			targClicked = false;
			rayClicked = false;
		}
		
		public void mouseDragged(MouseEvent e) {
			if(clicked) {
				check.setVisible(true);
				check.setLocation(active.getX() + 40, active.getY() - 50);
				check.setSelected(active.locked());
			}
			else if(obsClicked) {
				wSlider.setLocation(activeObs.getX(), activeObs.getY() - 20);
				wSlider.setValue(activeObs.width);
				wSlider.setVisible(true);
				hSlider.setLocation(activeObs.getX() - 20, activeObs.getY());
				hSlider.setValue(activeObs.height);
				hSlider.setVisible(true);
				trashObs.setVisible(true);
				trashObs.setLocation(activeObs.getX() - 20, activeObs.getY() - 20);
				field.move(e.getX() + xDiff, e.getY() + yDiff, activeObs);
				repaint();
			}
			else if(targClicked) {
				field.move(e.getX() + xDiff, e.getY() + yDiff, activeTarg);
				repaint();
				trashTarg.setVisible(true);
				trashTarg.setLocation(activeTarg.getX() - activeTarg.radius - 15, activeTarg.getY() - activeTarg.radius - 15);
			}
			else if(rayClicked) {
				field.moveRay(e.getX() + xDiff, e.getY() + yDiff, activeRay);
				repaint();
				rayRotater.setVisible(true);
				rayRotater.setLocation(activeRay.x + 25, activeRay.y - 25);
				trashRay.setVisible(true);
				trashRay.setLocation(activeRay.x - 25, activeRay.y - 25);
			}
		}
	}
}