import java.awt.*;
//a helper class to draw the principal axis of lenses and curved mirrors
class PrincipalAxis extends Axis {
	int focus, focus1x, focus1y, focus2x, focus2y, center1x, center1y, center2x, center2y;
	double ratio;
	
	public PrincipalAxis(int x, int y, int angle, int focus) {
		super(x, y, angle, 4 * focus);
		this.focus = focus;
		recalculate();
	}
	
	public void recalculate() {
		super.recalculate();
		focus1x = (int)(x + focus*Math.cos(Math.toRadians(-angle)));
		focus1y = (int)(y + focus*Math.sin(Math.toRadians(-angle)));
		focus2x = (int)(x - focus*Math.cos(Math.toRadians(-angle)));
		focus2y = (int)(y - focus*Math.sin(Math.toRadians(-angle)));
		center1x = (int)(x + 2*focus*Math.cos(Math.toRadians(-angle)));
		center1y = (int)(y + 2*focus*Math.sin(Math.toRadians(-angle)));
		center2x = (int)(x - 2*focus*Math.cos(Math.toRadians(-angle)));
		center2y = (int)(y - 2*focus*Math.sin(Math.toRadians(-angle)));
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.fillOval( focus1x-3, focus1y-3, 6, 6);
		g.drawString("F", focus1x-3, focus1y+11);
		g.fillOval( focus2x-3, focus2y-3, 6, 6);
		g.drawString("F", focus2x-3, focus2y+11);
		g.fillOval( center1x-3, center1y-3, 6, 6);
		g.drawString("C", center1x-3, center1y+11);
		g.fillOval( center2x-3, center2y-3, 6, 6);
		g.drawString("C", center2x-3, center2y+11);
	}
}
