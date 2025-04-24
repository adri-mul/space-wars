package Platform;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Frame extends JFrame {
    private Board board;

    public Frame() {
        board = new Board();
        initUI();
    }
    
    private void initUI() {

        add(board);

        setTitle("Moving sprite");
        setSize(800, 600);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Board getBoard() {
        return this.board;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Frame gameFrame = new Frame();
            gameFrame.setVisible(true);

            // Check key state periodically
            /*new Thread(() -> {
                SpaceShip spaceShip = gameFrame.getBoard().getSpaceShip();
                System.out.println("reached thread");
                while (spaceShip.isKeyPressed[0] != null) {
                    System.out.println("reached if statement");
                    if (!spaceShip.isKeyPressed[0]) {
                        gameFrame.getBoard().getSpaceShip().dy -= 1;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } 
            }).start();*/
        });
        
    }
}