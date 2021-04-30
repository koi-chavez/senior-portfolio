import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle{

	private static final int RADIUS = 5;
	private double rotation;

	public Bullet(Point center, double rotation) {
		super(center, RADIUS); // define RADIUS in Bullet class
		this.rotation = rotation;
	}


	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, radius*2, radius);	
	}

	@Override
	public void move() {
		center.x += 2 * Math.cos(Math.toRadians(rotation));
		center.y += 2 * Math.sin(Math.toRadians(rotation));
	}

	public boolean outOfBounds() {

		if(center.x > 800) {
			return true;
		}
		if (center.x < 0) {
			return true;
		}
		if(center.y > 600) {
			return true;
		}
		if (center.y < 0) {
			return true;
		}

		return false;
	}

	public Point getCenter() {
		
	return center;	
	}
}
