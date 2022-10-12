import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    private Random random;
    private int xVelocity; // how much the ball will move in the x direction
    private int yVelocity; // how much the ball will move in the y direction
    private final int initialSpeed = 3; // relative speed(to the velocity values) at which the ball moves

    public Ball(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        random = new Random();

        // as the ball will start moving from the center
        int randomXDirection = random.nextInt(2); // if it is zero we make the ball go left and if it is one we make
        // ball go right
        if (randomXDirection == 0) { // go left
            randomXDirection--;
        }
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2); // if it is zero we make the ball go up and if it is one we make
        // ball go down
        if (randomYDirection == 0) { // go left
            randomYDirection--;
        }
        setYDirection(randomYDirection * initialSpeed);
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
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

    public void draw(Graphics graphics) { // drawing the ball
        graphics.setColor(Color.white);
        graphics.fillOval(x, y, width, height);
    }
}
