import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    private int yVelocity;
    private int xVelocity;
    private static final int initialSpeed = 3; // speed at the which the ball moves(relative to velocity values)

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);

        Random randomDirection = new Random(); // random direction in which the ball moves
        // random direction for moving horizontally
        int randomXDirection = randomDirection.nextInt(2);
        if (randomXDirection == 0) { // go left
            randomXDirection--;
        }
        setXVelocity(randomXDirection * initialSpeed);

        // random direction for moving vertically
        int randomYDirection = randomDirection.nextInt(2);
        if (randomYDirection == 0) { // go up
            randomYDirection--;
        }
        setYVelocity(randomYDirection * initialSpeed);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.fillOval(x, y, width, height);
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
}
