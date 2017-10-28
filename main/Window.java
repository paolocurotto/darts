package main;
import java.awt.EventQueue;
import javax.swing.JFrame;

import Neural.Brain;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public Window() {
		this.initUI();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Window w = new Window();
                w.setVisible(true);
            }
        });

		
	}
	
    private void initUI() {

    	Surface surface = new Surface();
    	add(surface);
    	
        setTitle("Points");
        setSize(740, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
