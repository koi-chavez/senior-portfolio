import java.awt.Color;
import java.awt.Graphics;

public class Asteroid extends Polygon {

	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);		
	}	

	/**
	 * The paint() method will have to get the points of the Polygon, and then use them to draw 
	 * the Polygon using the drawPolygon() method. Your brush object is a Java Graphics object, 
	 * where the Graphics class has a drawPolygon() method
	 */
	@Override
	public void paint(Graphics brush, Color color) {

		Point[] points = getPoints();

		int [] xPoints = new int[points.length];
		int [] yPoints = new int[points.length];


		for (int i = 0; i < points.length; i++) {			
			xPoints[i] = (int)points[i].x;
			yPoints[i]= (int)points[i].y;

		}	
		brush.drawPolygon(xPoints, yPoints, points.length);		
		
	}
	/**
	 * The move() method is used to move the Asteroid across the canvas. 
	 * This can be done by adjusting the offset in the Polygon class 
	 */
	@Override
	public void move() {	
		
		position.x += Math.cos(Math.toRadians(rotation));
		position.y += Math.sin(Math.toRadians(rotation));
		
		if (position.x > 800) {
			position.x = 0;
			
		}
		if (position.x < 0) {
			position.x = 800;
			
		}
		
		if (position.y > 600) {
			position.y = 0;
			
		}
		if (position.y < 0) {
			position.y = 600;
			
		}

	}

}
