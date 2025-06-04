package Platform;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Entity {
    protected int dx;
    protected int dy;
    protected int x;
    protected int y;
    protected int health;
    protected int energy;
    protected int imgWidth;
    protected int imgHeight;
    protected Image image;

    // Constructors
    public Entity() {
        x = 300;
        y = 450/2;
        dx = 0;
        dy = 0;
        health = 10;
        energy = 10;
        loadImage("c:/Users/adria/Downloads/Spaceship-PNG-File-1398389704 (Custom) (1).png");
    }

    public Entity(String filename) {
        x = 300;
        y = 450/2;
        dx = 0;
        dy = 0;
        health = 10;
        energy = 10;
        loadImage(filename);
    }

    private void loadImage(String filename) {
        ImageIcon imageIcon = new ImageIcon(filename);
        image = imageIcon.getImage();

        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    // Accessor methods (getters and setters)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return dx;
    }

    public int getVelY() {
        return dy;
    }

    public int getWidth() {
        return imgWidth;
    }
    
    public int getHeight() {
        return imgHeight;
    }    

    public Image getImage() {
        return image;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

}
