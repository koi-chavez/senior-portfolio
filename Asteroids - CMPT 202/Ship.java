import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Ship extends Polygon implements KeyListener{

	public boolean forward = false;
	public boolean left = false;
	public boolean right = false;
	public boolean space = false;
	public boolean isFired = false;

	public Bullet bullet;
	ArrayList<Bullet> arrlist = new ArrayList <Bullet>();

	public Ship(Point[] sshape, Point sposition, double srotation) {
		super(sshape, sposition, srotation);

	}

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
		brush.fillPolygon(xPoints, yPoints, points.length);		
	}

	@Override
	public void move() {

		//position.x += Math.cos(Math.toRadians(rotation));
		//position.y += Math.sin(Math.toRadians(rotation));

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

		if (forward == true) {
			position.x += 3 * Math.cos(Math.toRadians(rotation));
			position.y += 3 * Math.sin(Math.toRadians(rotation));
		}
		
		if (left == true) {
			rotate(-2);
					}

		if(right == true) {
			rotate(2);
			
		}	
		
		if(space == true && isFired == false) {
		bullet = new Bullet (getCenter(), rotation);
		arrlist.add(bullet);
		isFired = true;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		return;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}	
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			space = true;
			
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			space = false;
			isFired = false;
		}

	}

	public ArrayList<Bullet> getBullets(){
		return arrlist;
	}
	
	public Point getCenter() {
		Point[] points = getPoints();
		return points[1];
	}

}
