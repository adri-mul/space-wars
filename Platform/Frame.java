package Platform;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Frame extends JFrame {
    // Variables
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

    // Accessors
    public Board getBoard() {
        return this.board;
    }

    public static void main(String[] args) {
        System.out.println(java.time.LocalTime.now());
        EventQueue.invokeLater(() -> {
            Frame gameFrame = new Frame();
            gameFrame.setVisible(true);
        });
        
    }
}