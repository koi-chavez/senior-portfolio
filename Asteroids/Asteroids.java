
/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.*;

//import javax.sound.sampled.Clip;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;



public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	private static final int COLLISION_PERIOD = 100;
	private static int collisionTime = COLLISION_PERIOD;
	
	//private  Clip fireClip;    
    //private Clip smallAsteroidBang;   



	private int numLives = 5;

	static int counter = 0;
	boolean collision = false;
	boolean stars = false;
	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	private Star[] randomStars;
	private Ship ship;

	

	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(10,60,30);
		// create a number of random star objects
		randomStars = createStars(400,4);

		ship = createShip();
		this.addKeyListener(ship);

	}

	// private helper method to create the Ship
	private Ship createShip() {
		// Look of ship
		Point[] shipShape = {
				new Point(400,275),
				new Point (450,300),
				new Point (400, 325),
				new Point (410, 300)     		
		};

		Point inPosition = new Point(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		double inRotation = 0;
		return new Ship(shipShape, inPosition, inRotation);
	}

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}
	// Create a certain number of stars with a given max radius
	public Star[] createStars(int numberOfStars, int maxRadius) {
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; ++i) {
			Point center = new Point
					(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);

			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1) {
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}


		return stars;
	}

	public void paint(Graphics brush) {

		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);	
		brush.drawString("Num Lives: " + numLives, 10, 30);


		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			
			if(!collision) {
                collision = asteroid.collision(ship);
                
                if(collision == true) {
					numLives--;
				}
            }

		}

		if(collision) {
			   brush.setColor(Color.CYAN);
	           ship.paint(brush, Color.CYAN);
	           collisionTime -= 1;
	           if(collisionTime <= 0) {
	               collision = false;
	               collisionTime = COLLISION_PERIOD;
	           }
	       } else {
	    	   brush.setColor(Color.RED);
	           ship.paint(brush, Color.RED);
	       }

			ship.move();
		for(Star star : randomStars) {
			star.paint(brush, Color.white);
			if(stars == false) {
				star.radius+=1;
				if(star.radius >= 6) {
					stars = true;
				}
			}
			if (stars == true) {
				star.radius-=1;
				if(star.radius <= 4) {
					stars = false;
				}
			}					
		}

		//brush.setColor(Color.RED);		
		//ship.paint(brush, Color.RED);
		

		/**if (counter > 0 && counter < 100) {
			brush.setColor(Color.CYAN);		
			ship.paint(brush, Color.CYAN);
			//collision = true;
		}
		if (counter <= 0) {
			brush.setColor(Color.RED);		
			ship.paint(brush, Color.RED);
			//collision = false;
		}*/

		// Code that creates the bullet objects and removes them once they have
		// Disappeared off-screen
		ArrayList <Bullet> remove = new ArrayList<Bullet>();
		ArrayList<Asteroid> collidedAst = new ArrayList<Asteroid>();
		if(ship.getBullets().isEmpty() != true) {
			for (int i = 0; i < ship.getBullets().size(); i++) {
				brush.setColor(Color.red);
				ship.getBullets().get(i).paint(brush, Color.red);
				ship.getBullets().get(i).move();

				if (ship.getBullets().get(i).outOfBounds() == true) {
					remove.add(ship.getBullets().get(i));
				}

				for (Asteroid asteroid : randomAsteroids) {
					if (asteroid.contains(ship.getBullets().get(i).getCenter()) == true){
						remove.add(ship.getBullets().get(i));
						collidedAst.add(asteroid);						
					}
				}
			}

			for (int j = 0; j < remove.size(); j++) {

				ship.getBullets().remove(remove.get(j));
			}
			remove.clear();

			for (int k = 0; k < collidedAst.size(); k++) {

				randomAsteroids.remove(collidedAst.get(k));
			}
			collidedAst.clear();
		}

		if (randomAsteroids.isEmpty() == true) {
			on = false;
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("You have won the game!!", 325, 300);
		}

		if (numLives <= 0) {
			on = false;
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("You have lost the game!!", 325, 300);
		}

	}

	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}