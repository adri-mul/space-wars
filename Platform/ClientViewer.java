package Platform;

import java.awt.Color;

import javax.swing.JFrame;

public class ClientViewer {
    public static final Color BLACK = new Color(0, 0, 0);
    private JFrame gameFrame;
    public ClientViewer() {
        gameFrame = new JFrame("Client");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.getContentPane().setBackground(BLACK);
        gameFrame.setVisible(true);
        gameFrame.setSize(600,450);
    }

    public JFrame getGameFrame() {
        return gameFrame;
    }
}
