package Platform;

// Demonstrating Server-side Programming
import java.net.*;
//import javax.swing.Timer;

//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import java.io.*;

public class NewServer {
    public static void main(String args[])
    {
        int port = 9090;

        // initialize server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            System.out.println("Server is listening on " + port);
            System.out.println("Waiting for client...");

            // connect client to server socket
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected");
            boolean run = true;

            // Setup input and output streams for communication with the client
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            while (run) {
 
                // variable to collect commands from client
                String text;
                
                // keeps reading input from client until it receives a non-empty message
                do {
                    // Read input from client
                    text = clientInput.readLine();
                    //writer.println(text);
                    if (text != null) {
                        System.out.println("Server received message: " + text);
                        System.out.println("Server finished receiving message.");
                    }

                    // TODO Creating a new board here seems to get System output from the client side as well
                    // since outputs aren't separated by instance of board, but rather shared
                    sendDataToClient(clientOutput, new SpaceShip(new Board()));
 
                } while (text == null || text.isEmpty());
                
                if (text.equals("exit")) {
                    System.out.println("Server received exit command. Shutting down...");
                    run = false;
                }
            }
            clientSocket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Sends a report of an entity
    // Format: "Name Type x-pos y-pos Health"
    // Name: String
    // Type: 4-digit Integer w/ format xxxx
    // x-pos/y-pos: 2-digit Integer w/ format xx
    // Health: 3-digit Integer w/ format xxx
    public static void sendDataToClient(PrintWriter output, Entity e) {
        output.println(e.toString());
    }
}
