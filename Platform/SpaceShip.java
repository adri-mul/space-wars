package Platform;

// dependencies
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

// class to contain spaceship objects
public class SpaceShip extends Entity {
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
        super();
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
        isKeyPressed = new Boolean[9];
        connectToServer();
        writer.println("a");
    }

    private void connectToServer() {
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
    }

    @Override
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
    }
}
