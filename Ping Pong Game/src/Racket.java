import java.awt.*;
import java.awt.event.KeyEvent;

public class Racket extends Rectangle {
    private final Player id;
    private int speed; // speed at which the racket will move (it could be at DISTANCE or -DISTANCE pixels at a time)
    private final int DISTANCE = 10; // fixed distance - amount of distance the racket will move up and down when
    // pressing arrow keys

    public Racket(int x, int y, int width, int height, Player id) {
        super(x, y, width, height);
        this.id = id;
    }

    public void draw(Graphics graphics) {
        if (this.id == Player.FIRST) {
            graphics.setColor(Color.red);
        } else {
            graphics.setColor(Color.green);
        }
        graphics.fillRect(x, y, width, height);
    }

    public void move() {
        y += speed;
    }

    public void setSpeed(int speed) { // this will set the speed to positive or negative values and move the racket
        // up or down
        this.speed = speed;
    }

    public void keyPressed(KeyEvent event) { // when user presses the key we want to move the racket
        switch (this.id) {
            case FIRST -> {
                if (event.getKeyCode() == KeyEvent.VK_W) { // when the first player presses the W keyword then move up
                    setSpeed(-DISTANCE);
                    move();
                }
                if (event.getKeyCode() == KeyEvent.VK_S) { // when the player presses the 'S' keyword move down
                    setSpeed(DISTANCE);
                    move();
                }
            }
            case SECOND -> {
                if (event.getKeyCode() == KeyEvent.VK_UP) { // when the second player presses the up arrow key - move up
                    setSpeed(-DISTANCE);
                    move();
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) { // when the player presses the down arrow key move down
                    setSpeed(DISTANCE);
                    move();
                }
            }
        }
    }

    public void keyReleased(KeyEvent event) { // when the releases the key we want to stop moving the racket
        switch (this.id) {
            case FIRST -> {
                if (event.getKeyCode() == KeyEvent.VK_W) { // when the first player releases the W keyword then stop
                    setSpeed(0);
                    move();
                }
                if (event.getKeyCode() == KeyEvent.VK_S) { // when the player releases the 'S' keyword
                    setSpeed(0);
                    move();
                }
            }
            case SECOND -> {
                if (event.getKeyCode() == KeyEvent.VK_UP) { // when the second player releases the up arrow key
                    setSpeed(0);
                    move();
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) { // when the player releases the down arrow key
                    setSpeed(0);
                    move();
                }
            }
        }
    }
}
