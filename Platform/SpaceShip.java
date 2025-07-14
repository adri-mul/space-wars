package Platform;

// dependencies
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

// class to contain spaceship objects
public class SpaceShip extends Entity {
    public Boolean[] isKeyPressed;
    private Timer timer;
    private TimerTask task;
    private Board board;
    // constants
    
    public final String NAME = "SpaceShip0";
    public final int MAX_SPEED = 4;
    public final long DRAG_INTERVAL = 500;
    

    // main constructor
    public SpaceShip(Board board) {
        super();
        type = 2;
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
            case KeyEvent.VK_SPACE: board.getBoardEntities().add(new Laser(this)); break;
            case KeyEvent.VK_M: board.getBoardEntities().add(new SpaceShip(board)); break;
        }

    }

    // reset dx and dy
    public void keyReleased(KeyEvent e) {
    }

    // toString method
    @Override
    public String toString() {
        return String.format("%s %s", NAME, super.toString());
    }
}
