package Platform;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.JPanel;

// class to display & update the screen
public class Board extends JPanel implements ActionListener {
    // Constants
    public final String HOSTNAME = "localhost";
    public final int PORT = 9090;
    public static final Color BLACK = new Color(0, 0, 0);
    private final int DELAY = 17;
    private Timer timer;
    private SpaceShip spaceShip;
    private ArrayList<Entity> entities;
    private Socket socket;
    // Sends output to the socket
    private PrintWriter output;
    private BufferedReader input;
    // Variable to collect data from server
    private String serverData;

    public Board() {
        initBoard();
    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        // create an object for storing entities on this board, then connect to server
        spaceShip = new SpaceShip(this);
        entities = new ArrayList<>();
        entities.add(spaceShip);
        connectToServer();

        // Starts a timer with a set delay to start game loop
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Getter methods
    public SpaceShip getSpaceShip() {
        return this.spaceShip;
    }

    public ArrayList<Entity> getBoardEntities() {
        return this.entities;
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
        for (Entity e : entities) {
            g2d.drawImage(e.image, e.getX(), e.getY(), this);
        }

        if (serverData != null) {
                // Visualizes the data received from the server
                visualizeServerData(g2d);
                //System.out.println("Server data visualized");
            } else {
                System.out.println("No data received from server yet.");
        }
        
        /*EventQueue.invokeLater(() -> {
            if (serverData != null) {
                // Visualizes the data received from the server
                visualizeServerData(g2d);
                //System.out.println("Server data visualized");
            } else {
                System.out.println("No data received from server yet.");
            }
        });*/
        
    }

    // calls the action
    @Override
    public void actionPerformed(ActionEvent e) {
        step(this.output);
    }

    // One step of the game loop
    public void step(PrintWriter output) {
        for (Entity e : entities) {
            e.move();
        }
        EventQueue.invokeLater(() -> {
            sendData(output, this, spaceShip);
            // Recieves & Prints data from Server
            /*try {
                System.out.println("Message from Server: " + input.readLine());
            } catch(IOException io) {
                io.printStackTrace();
            }*/
            try {
                serverData = input.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
        });
        
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

    private void connectToServer() {
        
        // Initialize socket and input/output streams
        try {
            System.out.println("Start connectToServer");
            socket = new Socket(HOSTNAME, PORT);
            System.out.println("Initialized Client Socket and Connected to server");
            // initialize output and input streams
            this.output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Initialized the output");
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Initialized the input");
        } catch (UnknownHostException uhE) {
            System.out.println(uhE.getMessage() + "\n" + uhE.getStackTrace());
        } catch (IOException ioE) {
            System.out.println(ioE.getMessage() + "\n" + ioE.getStackTrace());
        }
    }

    public void sendData(PrintWriter output, Board board, Entity e) {
        if (e.toString() == null) {
            System.out.println("Entity toString is null");
        }
        output.println(e.toString());
        // write to server the position of the player
    }

    // Converts the messages from the server into actual graphics
    public void visualizeServerData(Graphics2D g2d) {
        // Collects data into a string array
        //System.out.println("Server data: " + serverData);
        String[] entityData = serverData.split(" "); //TODO Issue
        //System.out.println("Split server data: " + Arrays.toString(entityData));
        try {
            int entityType = Integer.parseInt(entityData[1]); // Check if type is a valid integer
            int xPos = Integer.parseInt(entityData[2]);
            int yPos = Integer.parseInt(entityData[3]);
            switch (entityType) {
                case 1: System.out.println("Type 'Entity' is not supported"); break;
                case 2: g2d.drawImage(new SpaceShip(this).getImage(), xPos, yPos, this); System.out.println("Visualized SpaceShip"); break;
                case 3: g2d.drawImage(new Laser().getImage(), xPos, yPos, this); break;
                default: System.out.println("Unknown or invalid entity type");
        }
        } catch (NumberFormatException e) {
            System.out.println("Invalid entity type: " + entityData[1]);
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server data is incomplete or malformed: " + serverData);
            return;
        } catch (NullPointerException e) {
            System.out.println("Server data is null or not properly formatted: " + serverData);
            return;
        }

    }

}
