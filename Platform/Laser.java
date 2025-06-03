package Platform;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Laser {
    // class variables
    public static Image image = loadImage("C:/Users/adria/Downloads/transparent_laser_3.png");
    private static int imgWidth;
    private static int imgHeight;
    // variables
    private int x;
    private int y;
    private int dx;
    private int dy;

    public Laser(SpaceShip ship) {
        x = ship.getX();
        y = ship.getY();
        dx = 0;
        dy = 5;
    }

    private static Image loadImage(String filename) {
        ImageIcon imageIcon = new ImageIcon(filename);
        image = imageIcon.getImage();

        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
        return image;
    }

    // Kill entity if off-screen
    public static void kill() {
        // TODO kill the entity if the x/y coords are out of bounds
    }

    // Getter and setter methods
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getVelX() {
        return this.dx;
    }

    public int getVelY() {
        return this.dy;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setVelX(int newDx) {
        this.dx = newDx;
    }

    public void setVelY(int newDy) {
        this.dy = newDy;
    }

    // static variable getter methods
    public static int getImgWidth() {
        return imgWidth;
    }

    public static int getImgHeight() {
        return imgHeight;
    }
}
