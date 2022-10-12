import java.awt.*;
import java.awt.event.KeyEvent;

public class Racket extends Rectangle { // racket is like a tt racket which we will move across screen when we play the
    // ping pong (this will be created using the rectangle constructor)
    private final Player id;
    private int yVelocity; // how fast the racket wil move up and down when we press the up and down arrow keys
    private final int direction = 10; // how much the racket will move on the y-axis

    public Racket(int xPosition, int yPosition, int RACKET_WIDTH, int RACKET_HEIGHT, Player id) {
        super(xPosition, yPosition, RACKET_WIDTH, RACKET_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch (this.id) {
            case FIRST -> { // for the first player
                if (e.getKeyCode() == KeyEvent.VK_W) { // if the player presses the 'w' key - move upwards
                    setYDirection(-direction);
                    move();
                } else if (e.getKeyCode() == KeyEvent.VK_S) { // if the player presses the 's' key - move downwards
                    setYDirection(direction);
                    move();
                }
            }
            case SECOND -> { // for the second player
                if (e.getKeyCode() == KeyEvent.VK_UP) { // if the player presses the up arrow key - move upwards
                    setYDirection(-direction);
                    move();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // if the player presses the down arrow key -downwards
                    setYDirection(direction);
                    move();
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (this.id) {
            case FIRST -> { // for the first player
                if (e.getKeyCode() == KeyEvent.VK_W) { // if the player releases the 'w' key - stop moving upwards
                    setYDirection(0);
                    move();
                } else if (e.getKeyCode() == KeyEvent.VK_S) { // if the player releases the 's' key
                    setYDirection(0);
                    move();
                }
            }
            case SECOND -> { // for the second player
                if (e.getKeyCode() == KeyEvent.VK_UP) { // if the player releases the up arrow key - stop moving upwards
                    setYDirection(0);
                    move();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // if the player releases the down arrow key -downwards
                    setYDirection(0);
                    move();
                }
            }
        }
    }

    public void setYDirection(int yDirection) { // as we are only going to move the racket in y direction
        yVelocity = yDirection;
    }

    public void move() {
        y += yVelocity; // just add to the y coordinate of our racket (which is y coordinate of the upper left
        // corner of the rectangle)
    }

    public void draw(Graphics graphics) {
        if (this.id == Player.FIRST) { // for player one
            graphics.setColor(Color.blue);
        } else {
            graphics.setColor(Color.red);
        }
        graphics.fillRect(x, y, width, height);
    }
}
