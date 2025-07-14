package Platform;

public class Laser extends Entity {

    public Laser(SpaceShip ship) {
        super("C:/Users/adria/Downloads/transparent_laser_3.png");
        x = ship.getX();
        y = ship.getY();
        dx = 0;
        dy = -5;
        type = 3;
    }

    public Laser() {
        super("C:/Users/adria/Downloads/transparent_laser_3.png");
        x = 0;
        y = 0;
        dx = 0;
        dy = -5;
        type = 3;
    }

    // Kill entity if off-screen
    public static void kill() {
        // TODO kill the entity if the x/y coords are out of bounds
    }
}
