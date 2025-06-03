package Platform;

// dependencies
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

// class to contain spaceship objects
public class SpaceShip {
    private int dx;
    private int dy;
    private int x = 300;
    private int y = 450/2;
    private int health;
    private int energy;
    private int imgWidth;
    private int imgHeight;
    private Image image;
    public Boolean[] isKeyPressed;
    private Timer timer;
    private TimerTask task;
    private Board board;
    // constants
    public final String HOSTNAME = "localhost";
    public final int PORT = 9090;
    public final String NAME = "player0";
    public final int MAX_SPEED = 4;
    public final long DRAG_INTERVAL = 500;
    // Sends output to the socket
    private OutputStream output;
    private PrintWriter writer;
    private InputStream input;
    private BufferedReader reader;
    private Socket socket;

    // main constructor
    public SpaceShip(Board board) {
        this.board = board;
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                //System.out.println("drag applied: " + LocalTime.now());
                // applies a drag force that slows down the space ship over time
                dx -= dx>0? 1:dx<0? -1:0;
                dy -= dy>0? 1:dy<0? -1:0;
            }
        };
        // runs the task every DRAG_INTERVAL milliseconds
        timer.schedule(task, 0, DRAG_INTERVAL);
        health = 10;
        energy = 10;
        isKeyPressed = new Boolean[9];
        loadImage();
        // Initialize socket and input/output streams
        try {
            socket = new Socket(HOSTNAME, PORT);
            System.out.println("Connected to server");
            // initialize output and input stream
            output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (UnknownHostException uhE) {
            System.out.println(uhE.getMessage() + "\n" + uhE.getStackTrace());
        } catch (IOException ioE) {
            System.out.println(ioE.getMessage() + "\n" + ioE.getStackTrace());
        } 
        writer.println("a");
    }

    private void loadImage() {
        ImageIcon imageIcon = new ImageIcon("c:/Users/adria/Downloads/Spaceship-PNG-File-1398389704 (Custom) (1).png");
        image = imageIcon.getImage();

        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
    }

    public void move() {
        int oldX = x;
        int oldY = y;
        x += dx;
        y += dy;

        // keep the ship from sending redundant information
        if (x != oldX || y != oldY) {
            // write to server the position of the player
            writer.println(NAME + ":" + x + "," + y);
        }
    }

    // change dx and dy based on key pressed (TODO want left/right arrow keys to be turn)
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // switch for left/right movement
        switch (key) {
            case KeyEvent.VK_A: dx -= Math.abs(dx)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_D: dx += Math.abs(dx)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_LEFT: dx -= Math.abs(dx)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_RIGHT: dx += Math.abs(dx)>=MAX_SPEED?0:1; break;
        }
        // switch for up/down movement
        switch (key) {
            case KeyEvent.VK_W: dy -= Math.abs(dy)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_S: dy += Math.abs(dy)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_UP: dy -= Math.abs(dy)>=MAX_SPEED?0:1; break;
            case KeyEvent.VK_DOWN: dy += Math.abs(dy)>=MAX_SPEED?0:1; break;
        }
        // switch for other
        switch (key) {
            case KeyEvent.VK_SPACE: board.getBoardLasers().add(new Laser(this)); break;
        }

    }

    // reset dx and dy
    public void keyReleased(KeyEvent e) {
        /* 
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W: dy = 0; isKeyPressed[0] = false; break;
            case KeyEvent.VK_A: dx = 0; isKeyPressed[1] = false; break;
            case KeyEvent.VK_S: dy = 0; isKeyPressed[2] = false; break;
            case KeyEvent.VK_D: dx = 0; isKeyPressed[3] = false; break;
            case KeyEvent.VK_UP: dy = 0; isKeyPressed[4] = false; break;
            case KeyEvent.VK_LEFT: dx = 0; isKeyPressed[5] = false; break;
            case KeyEvent.VK_DOWN: dy = 0; isKeyPressed[6] = false; break;
            case KeyEvent.VK_RIGHT: dx = 0; isKeyPressed[7] = false; break;
            case KeyEvent.VK_SPACE: isKeyPressed[8] = false; break;
        }
        */
    }

    // Accessor methods (getters and setters)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return dx;
    }

    public int getVelY() {
        return dy;
    }

    public int getWidth() {
        return imgWidth;
    }
    
    public int getHeight() {
        return imgHeight;
    }    

    public Image getImage() {
        return image;
    }

    public int getShipHealth() {
        return health;
    }

    public int getShipEnergy() {
        return energy;
    }
}
