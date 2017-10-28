package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Surface extends JPanel implements ActionListener {
	
	private final int DELAY = 0;
	private Timer timer;
	public int iter;
	public double avg=0;
	public int newavg=0;
	public ADN adn;
	Graphics2D g; //740 x 500
    
	//Number of darts
	public int ngreen_darts = 20;	
	public int nmag_darts = 30;
	
	GreenDart[] greenDarts = new GreenDart[ngreen_darts];
	MagDart[] magDarts = new MagDart[nmag_darts];
	
	/*Rectangle[] rectangles = {new Rectangle(-20, -20, 720, 5),
    						  new Rectangle(-20, -20, 3, 720),
    						  new Rectangle(720, -20, 740, 550),
    						  new Rectangle(-20, 458, 720, 520)};*/
	Rectangle[] rectangles = {new Rectangle (0,0,0,0)};
    				
    int h;
    int w;

    public Surface() {
    	timer = new Timer(DELAY, this);
	    timer.start();
	    iter = 1;
	    h = getHeight();
	    w = getWidth();
        
	    for (int i = 0; i < nmag_darts; i++) {
        	//magDarts[i] = new MagDart(705, 200, 180);
	    	magDarts[i] = new MagDart(470, 200, 180);
	    }
	    
	    for (int i = 0; i < ngreen_darts; i++) {
        	greenDarts[i] = new GreenDart(15, 200, 0);
        	((GreenDart) greenDarts[i]).setEnemies(magDarts);
        	greenDarts[i].id = i;
        }
     
	}

	private void doDrawing(Graphics g) {

	    Graphics2D g2d = (Graphics2D) g;
	    this.g = g2d;
	    int w = getWidth();
	    int h = getHeight();
	    
	    for (Dart dart : greenDarts) {
        	dart.draw(h, w, g2d);
        }
        
	    for (Dart dart : magDarts) {
        	dart.draw(h, w, g2d);
        }
       
        for (Rectangle rectangle : rectangles) {
        	rectangle.draw(h, w, g2d, greenDarts, magDarts);
        }
        
        
        
        /*for (Dart dart : greenDarts) {
        	dart.checkIfHitAny(magDarts);
        }*/
        
        for (Dart dart : magDarts) {
        	dart.checkIfHitAny(greenDarts);
        }
         
        if (allGreenCollided() == true) {
        	adn = new ADN(greenDarts);
        	greenDarts = adn.new_generation_darts();
        	restartDarts();
        	calcAvg();
        	System.out.println(avg+" Iter n° " + iter);
        	iter++;

        } 
       
        g2d.dispose();
	}
	
	public boolean allGreenCollided() {
		for (Dart greenDart : greenDarts) {
			if (greenDart.collided == false) {
				return false;
			}
		}
		return true;
	}
	
	public void restartDarts() {
		for (Dart greenDart : greenDarts) {
			greenDart.reset();
		}
		
		for (Dart magDart : magDarts) {
			magDart.reset();
		}
	}
	
	public void calcAvg() {
		newavg = adn.averg;
		/*System.out.println("iter = "+ iter);
		System.out.println("avg = "+ avg);
		System.out.println("newavg = "+ newavg);
		
		System.out.println("avg * (iter-1/iter) = "+ (avg*((iter-1)/iter)));
		System.out.println("newavg / iter) = "+ (newavg/iter));*/


		avg = ((avg  * (iter-1)/iter  ) + (newavg/iter));
	}
	
	public Timer getTimer() {
			return timer;
	}
		
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    doDrawing(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    repaint();
	}
}