package Platform;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

// class to display & update the screen
public class Board extends JPanel implements ActionListener {
    public static final Color BLACK = new Color(0, 0, 0);
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip spaceShip;

    public Board() {
        initBoard();
    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        spaceShip = new SpaceShip();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Spaceship getter
    public SpaceShip getSpaceShip() {
        return this.spaceShip;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    public void step() {
        spaceShip.move();

        repaint(spaceShip.getX()-2, spaceShip.getY()-2, spaceShip.getWidth()+4, spaceShip.getHeight()+4);
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
