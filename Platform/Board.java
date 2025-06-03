package Platform;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.JPanel;

// class to display & update the screen
public class Board extends JPanel implements ActionListener {
    public static final Color BLACK = new Color(0, 0, 0);
    private final int DELAY = 17;
    private Timer timer;
    private SpaceShip spaceShip;
    private ArrayList<Laser> lasers;

    public Board() {
        initBoard();
    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        spaceShip = new SpaceShip(this);
        lasers = new ArrayList<>();

        timer = new Timer(DELAY, this);
        timer.start();

    }

    // Getter methods
    public SpaceShip getSpaceShip() {
        return this.spaceShip;
    }

    public ArrayList<Laser> getBoardLasers() {
        return this.lasers;
    }


    // calls the function to draw the spaceship
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
        //debugging
        for (Laser laser : lasers) {
            g2d.drawImage(Laser.image, laser.getX(), laser.getY(), this);
        }
    }

    // calls the action
    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    public void step() {
        spaceShip.move();

            // Updates the pixels of the spaceship and a small box surrounding it that is determined based on the velocity
            //repaint(Math.abs(spaceShip.getX()-spaceShip.getWidth()), 
                    //Math.abs(spaceShip.getY()-spaceShip.getHeight()), 
                    //Math.abs(spaceShip.getX()+spaceShip.getWidth()), 
                    //Math.abs(spaceShip.getY()+2*spaceShip.getHeight()));
            
            // Update the pixels of the lasers and a small box surrounding it that is determined based on the velocity of the laser
        
        // The system above is getting replaced by simply repainting the whole screen
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }

}
