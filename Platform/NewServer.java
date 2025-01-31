package Platform;

// Demonstrating Server-side Programming
import java.net.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class NewServer {
  
    // Initialize socket and input stream
    private Socket socket = null;
    private ServerSocket ss = null;

    // Constructor with port
    public NewServer(int port) {
        // Starts server and waits for a connection
        try
        {
            ss = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = ss.accept();
            System.out.println("Client accepted");

            // buffered reader to translate input stream from client to list
            BufferedReader sReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("reader initialized");
            try {
                System.out.println(sReader.readLine());
            } catch (IOException i) {
                i.printStackTrace();
            }
            System.out.println("Received msg from client");

        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

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
 
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, false);

                // variable to collect commands from client
                String text;
                
                do {
                    text = reader.readLine();
                    writer.println(text);
                    System.out.println("Server recieved command: " + text);
 
                } while (!text.equals(Character.toString(KeyEvent.VK_SLASH)));
                
                run = false;
            }
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
