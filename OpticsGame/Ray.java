import java.awt.*;
import java.awt.geom.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
//ray class with intersection methods
class Ray implements Serializable {
	int x, y, angle, xBound, yBound;
	OpticObject source;
	boolean isSource;
	transient BufferedImage bufferedImage;
	transient Image image;
	boolean isInside ;

	public Ray(int x, int y, int angle, OpticObject source) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.source = source;
		isSource = false ;
		isInside = false ;
		recalculate();
		reloadImages();
	}

	public void reloadImages() {
		try {
          	bufferedImage = ImageIO.read(new File("handle.png"));
       	} catch (IOException ex) {}
       	image = ImageUtils.rotateImage(bufferedImage, -angle);
	}

	public void setAngle(int a) {
		angle = a;
		recalculate();
		image = ImageUtils.rotateImage(bufferedImage, -angle);
	}

	public void setX(int x) {
		this.x = x;
		recalculate();
	}

	public void setY(int y) {
		this.y = y;
		recalculate();
	}

	public void setIsInside(boolean b)
	{
		isInside = b ;
	}

	public boolean isClicked(Point p) {
		if(p.x < x + 25 && p.x > x - 25 && p.y < y + 25 && p.y > y - 25)
			return true;
		return false;
	}

	//source stter to show the source image when drawing
	public void setIsSource(boolean b) {
		isSource = b;
	}

	public Point getOrigin() {
		return new Point(x, y);
	}

	public void recalculate() {
		xBound = (int)(x + (Math.cos(Math.toRadians(angle)) * 1600));
		yBound = (int)(y - (Math.sin(Math.toRadians(angle)) * 1600));
	}

	public void obstruct(int xb, int yb) {
		xBound = xb;
		yBound = yb;
	}

	public OpticObject getSource() {
		return source;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f));
        g2.setColor(Color.yellow);
        g2.drawLine(x, y, xBound, yBound);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.white);
        g2.drawLine(x, y, xBound, yBound);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.black);
        if(isSource) {
			BufferedImage bufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_INDEXED);
			Image image;

			try {
          		bufferedImage = ImageIO.read(new File("handle.png"));
       		} catch (IOException ex) {}
       		image = ImageUtils.rotateImage(bufferedImage, -angle);
       		g2.drawImage(image, x - image.getWidth(null)/2, y - image.getHeight(null)/2, null);
		}
	}

	//determines if the ray intersects the specified optic object, returns null if it doesn't
	public Point intersection(OpticObject o) {
		if(source == o)
			return null;
		LineEquations r = new LineEquations(x, y, xBound, yBound);
		Axis ax = o.getObstruction();
		Line2D.Double line = new Line2D.Double(x, y, xBound, yBound);

		if(ax == null) {
			if(o instanceof CurvedMirror) {
				CurvedMirror c = (CurvedMirror)o;
				Arc2D.Double arc = c.getArc();
				List<Point> points = getCircleLineIntersectionPoint(new Point(x, y), new Point(xBound, yBound), c.getArcCenter(), 2 * c.getFocus());
				if(points.size() > 1) {
					Point p1 = points.get(0);
					Point p2 = points.get(1);
					if(arc.intersects(p1.x - 2, p1.y - 2, 4, 4) && (Math.abs(p1.x - x) > 4 || Math.abs(p1.y - y) > 4) && line.intersects(p1.x - 2, p1.y - 2, 4, 4))
						return p1;
					if(arc.intersects(p2.x - 2, p2.y - 2, 4, 4) && (Math.abs(p2.x - x) > 4 || Math.abs(p2.y - y) > 4) && line.intersects(p2.x - 2, p2.y - 2, 4, 4))
						return p2;
				}
			}
			if(o instanceof Prism) {
				return prismIntersection(o);
			}
			if(o instanceof Obstacle) {
				Obstacle obs = (Obstacle)o;
				ArrayList<Point> ints = new ArrayList();
				if(obs.getRectangle().intersectsLine(x, y, xBound, yBound)) {
					return obstacleIntersection(obs.getX(), obs.getY(), obs.width, obs.height);
				}
			}
			if(o instanceof Target) {
				Target t = (Target)o;
				List<Point> points2 = getCircleLineIntersectionPoint(new Point(x, y), new Point(xBound, yBound), t.getCenter(), t.radius);
				if(points2.size() > 1) {
					Point p1 = points2.get(0);
					Point p2 = points2.get(1);
					if(Point2D.distance(p1.x, p1.y, x, y) < Point2D.distance(p2.x, p2.y, x, y) && line.intersects(p1.x - 2, p1.y - 2, 4, 4))
						return p1;
					if(Point2D.distance(p1.x, p1.y, x, y) > Point2D.distance(p2.x, p2.y, x, y) && line.intersects(p2.x - 2, p2.y - 2, 4, 4))
						return p2;
				}
			}
			return null ;
		}
		else {
			LineEquations a = new LineEquations(ax.xBound1, ax.yBound1, ax.xBound2, ax.yBound2);
			int intX = (int)a.getIntersectionX(r);
			int intY = (int)a.getIntersectionY(r);
			if(Line2D.linesIntersect((double)x, (double)y, (double) xBound, (double) yBound, (double) ax.xBound1, (double) ax.yBound1, (double) ax.xBound2, (double) ax.yBound2))
				return ( new Point(intX, intY) );
			return null;
		}

	}

	int intSide ;

	public Point prismIntersection(OpticObject o) {
		Line2D.Double line = new Line2D.Double(x, y, xBound, yBound);
		LineEquations r = new LineEquations(x, y, xBound, yBound);
		Line2D.Double[] sides = ((Prism)o).getSides();
//		Line2D.Double l2 = (((Prism)o).getSides())[1];
//		Line2D.Double l3 = (((Prism)o).getSides())[2];
		LineEquations side;
		int distance = 10000, closest = -1, closestDistance = 10000;
		Point[] points = new Point[3];

		for(int i = 0; i < 3; i++) {
			if(sides[i].intersectsLine(line)) {
				side = new LineEquations(sides[i].x1, sides[i].y1, sides[i].x2, sides[i].y2);
				points[i] = new Point((int)side.getIntersectionX(r), (int)side.getIntersectionY(r));
				distance = (int)Point2D.distance(points[i].x, points[i].y, x, y);
				if(distance < closestDistance) {
					closest = i ;
					closestDistance = distance;
					intSide = i ;
				}

			}
		}
		if(closest != -1 && closestDistance > 3 ){

			return points[closest];
		}
		else{

			return null;
		}
	}

	public int intSide (OpticObject o)
	{
		return intSide ;
	}

	public Point obstacleIntersection ( int coX, int coY, int width, int height)
	{
		LineEquations ray = new LineEquations(x,y,xBound,yBound) ;
		Point[] intersections = new Point[4] ;
		Point closestPoint ;
		if(ray.findY(coX)>=coY && ray.findY(coX) <= (coY+height))
		{
			intersections[0] = new Point(coX,ray.findY(coX)) ;
		}
		if(ray.findY(coX+width) >= coY && ray.findY(coX+width) <= (coY+height))
		{
			intersections[1] = new Point(coX+width,ray.findY(coX+width)) ;
		}
		if(ray.findX(coY) >= coX && ray.findX(coY) <= (coX+width))
		{
			intersections[2] = new Point(ray.findX(coY),coY) ;
		}
		if(ray.findX(coY+height) >= coX && ray.findX(coY+height) <= (coX+width))
		{
			intersections[3] = new Point( ray.findX(coY+height) , coY+height ) ;
		}
		for(int i = 1 ; i < 3 ; i++ )
		{
			if(intersections[0] != null){
				Point temp1 = intersections [0] ;
				if(intersections[i] != null){
					Point temp2 = intersections[i] ;
					double hypot1 = Math.hypot(Math.abs(temp1.getX()-x),Math.abs(temp1.getY()-y)) ;
					double hypot2 = Math.hypot(Math.abs(temp2.getX()-x),Math.abs(temp2.getY()-y)) ;
					if(hypot1>hypot2)
					{
						intersections[0] = intersections[i] ;
						intersections[i] = null ;
					}
				}
			}
			else
			{
				if(intersections[i] != null){
					intersections[0] = intersections[i] ;
				}
				intersections[i] = null ;
			}
		}

		return intersections[0] ;
	}

	//taken from http://stackoverflow.com/questions/13053061/circle-line-intersection-points
    public static List<Point> getCircleLineIntersectionPoint(Point pointA,
            Point pointB, Point center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.x - pointA.x;
        double caY = center.y - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point p1 = new Point((int)(pointA.x - baX * abScalingFactor1), (int)(pointA.y
                - baY * abScalingFactor1));
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Point p2 = new Point((int)(pointA.x - baX * abScalingFactor2), (int)(pointA.y
                - baY * abScalingFactor2));
        return Arrays.asList(p1, p2);
    }
}
