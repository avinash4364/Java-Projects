import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    private int xVelocity; // how much the ball will move in the x direction
    private int yVelocity; // how much the ball will move in the y direction
    private static final int initialSpeed = 3; // speed at the which the ball moves(relative to velocity values)
    private Random randomDirection;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);

        randomDirection = new Random(); // random direction in which the ball moves
        // random direction for moving horizontally
        int randomXDirection = randomDirection.nextInt(2);
        if (randomXDirection == 0) { // go left, otherwise by default it will go right
            randomXDirection--;
        }
        setXDirection(randomXDirection * initialSpeed);

        // random direction for moving vertically
        int randomYDirection = randomDirection.nextInt(2);
        if (randomYDirection == 0) { // go up, otherwise by default it will go down
            randomYDirection--;
        }
        setYDirection(randomYDirection * initialSpeed);
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

    // as the ball will move in both x and y direction ,so we set both the x and y direction
    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
}
