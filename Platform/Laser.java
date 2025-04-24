package Platform;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Laser {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private Image image;
    private int imgWidth;
    private int imgHeight;

    public Laser(SpaceShip ship) {
        x = ship.getX();
        y = ship.getY();
        dx = 0;
        dy = 5;
        loadImage("C:/Users/adria/Downloads/transparent_laser_2.png");
    }

    private void loadImage(String filename) {
        ImageIcon imageIcon = new ImageIcon(filename);
        image = imageIcon.getImage();

        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
    }
}
