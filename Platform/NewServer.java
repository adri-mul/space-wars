package Platform;

// Demonstrating Server-side Programming
import java.net.*;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");
            boolean run = true;
            while (run) {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                //OutputStream output = socket.getOutputStream();
                //PrintWriter writer = new PrintWriter(output, false);

                // variable to collect commands from client
                String text;
                
                do {
                    text = reader.readLine();
                    //writer.println(text);
                    if (text != null) {
                        System.out.println("Server recieved command: " + text);
                    }
 
                } while (text == null || text != "player0:0,0");
                
                run = false;
            }
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
