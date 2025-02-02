package Platform;

import java.net.*;

import javax.swing.JFrame;

import java.io.*;
import java.awt.Color;
import java.awt.event.*;

public class NewClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9090;
        Color black = new Color(0, 0, 0);

        // Initialize socket and input/output streams
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to server");
            JFrame gameFrame = new JFrame("Client");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.getContentPane().setBackground(black);
            gameFrame.setVisible(true);
            gameFrame.setSize(600,450);
            
            // Sends output to the socket
            OutputStream output = socket.getOutputStream();
            PrintWriter keyWriter = new PrintWriter(output, true);
            
            
            String keyPressed;

            do {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                gameFrame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
    
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        String key = Character.toString(e.getKeyChar());
                        switch (keyCode) {
                            case KeyEvent.VK_W: keyWriter.println(key); break;
                            case KeyEvent.VK_S: keyWriter.println(key); break;
                            case KeyEvent.VK_D: keyWriter.println(key); break;
                            case KeyEvent.VK_A: keyWriter.println(key); break;
                            case KeyEvent.VK_SLASH: keyWriter.println(key); break;
                            case KeyEvent.VK_SPACE: keyWriter.println("space"); break;
                            default: System.out.println("Error with key press");
                        }
                        System.out.println("Sent data to server");
                        
                    }
    
                    @Override
                    public void keyReleased(KeyEvent e) {
                        
                    }
                });
                // gets the most recently pressed key
                keyPressed = reader.readLine();
                //System.out.println("Key: " + key);
            } while (keyPressed != null);
            gameFrame.dispose();
            output.close();
            socket.close();
        } catch (UnknownHostException uhE) {
            System.out.println(uhE.getMessage() + "\n" + uhE.getStackTrace());
        } catch (IOException ioE) {
            System.out.println(ioE.getMessage() + "\n" + ioE.getStackTrace());
        } 
    }
}
