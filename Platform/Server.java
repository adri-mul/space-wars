package Platform;

// Demonstrating Server-side Programming
import java.net.*;
//import javax.swing.Timer;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class Server {
    private List<List<String>> clientsDataStack;

    public Server() {
        clientsDataStack = Collections.synchronizedList(new ArrayList<>());
    }
    public static void main(String args[])
    {
        int port = 9090;
        Server server = new Server();

        // initialize server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            System.out.println("Server is listening on " + port);
            System.out.println("Waiting for client...");
            int clientId = 0; // Unique ID for each client

            // connect client to server socket
            while (clientId < 4) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                // Create a new thread for each client
                // and start it to handle client requests
                // Gives each client a unique ID
                new ClientHandler(clientSocket, clientId, server).start();
                server.getClientsDataStack().add(new ArrayList<String>());
                System.out.println("Client added to list. Total clients: " + server.getClientsDataStack().size());
                clientId++;
                System.out.println("Client ID: " + clientId);
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<List<String>> getClientsDataStack() {
        return clientsDataStack;
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

    // retrieves data from all clients
    public void sendDataToClient(PrintWriter output, int clientId) {
        for (String data : clientsDataStack.get(clientId)) {
            output.println(data);
        }
    }

    // saves data from a client to all other clients
    public void saveClientData(String data, int clientId) {
        for (int i = 0; i < clientsDataStack.size(); i++) {
            if (i != clientId) {
                clientsDataStack.get(i).add(data);
            }
        }
    }

    // For testing purposes ONLY
    public void createTestClient(int n) {
        for (int i = 0; i < n; i++) {
            clientsDataStack.add(new ArrayList<String>());
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private Server server;
    private int clientId;
    private static final String QUIT_STRING = "exit";

    public ClientHandler(Socket socket, int clientId, Server server) {
        this.clientSocket = socket;
        this.clientId = clientId;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            String message;

            // continuously reads messages from client until QUIT_STRING is received
            // sends data from other clients to the current client
            while (!(message = input.readLine()).equals(QUIT_STRING)) {
                //System.out.println("Received from client: " + message);
                // Process the message and send a response
                server.saveClientData(message, clientId);
                Server.sendDataToClient(output, new SpaceShip(new Board()));

                if (message != null) {
                        System.out.println("Server received message: " + message);
                        System.out.println("Server finished receiving message.");
                }
                
            }
        } catch (IOException e) {
            System.out.println("Error in ClientHandler: " + e.getMessage());
        } finally {
            try {
                System.out.println("Closing client socket");
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
