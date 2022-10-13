import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25; // this will basically act as a helper unit for creating grid lines on
    // the screen
    private static final int noOfTiles = (SCREEN_HEIGHT * SCREEN_WIDTH) / (UNIT_SIZE * UNIT_SIZE); // no. of tiles on
    // the screen

    // initial coordinate for the apple to appear
    private int appleX;
    private int appleY;
    private static int score = 0;
    private final Random applePosition; // this will be used to randomly position the apple on the screen

    private static int snakeLength = 4; // initial body length of snake
    private final int[] snakeXPosition = new int[noOfTiles]; // x coordinates of the snake body
    private final int[] snakeYPosition = new int[noOfTiles]; // y coordinates of the snake body
    private boolean isMoving = false; // initially the snake is not moving
    private static char direction = 'R'; // initial direction of the snake

    private static Timer timer;
    private static final int DELAY = 100; // time interval in which two action events occurs (in milliseconds)

    public GamePanel() {
        applePosition = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter()); // adds the keyListener object to listen for the keys from the
        // component and if it is null then no action is performed
        startGame();
    }

    private void startGame() {
        newApple(); // first creating coordinates for apple to appear
        isMoving = true;

        // without the timer the actionPerformed method won't be called again and again which will cause our game to be
        // static - meaning we won't be able to move snake as we need to draw the screen repeatedly in order to do that
        timer = new Timer(DELAY, this); // fires action event at specified interval
        timer.start(); // starts the timer to send action events to the listeners
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }

    private void draw(Graphics graphics) { // this method will paint(draw) our panel which is our game screen
        if (isMoving) {
            for (int i = 1; i <= SCREEN_WIDTH / UNIT_SIZE; i++) { // for horizontal grid lines
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            for (int i = 1; i <= SCREEN_HEIGHT / UNIT_SIZE; i++) { // for vertical lines
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            }

            // drawing the apple
            graphics.setColor(Color.red);
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // drawing the snake
            graphics.setColor(Color.green);
            for (int i = 0; i < snakeLength; i++) {
                graphics.fillOval(snakeXPosition[i], snakeYPosition[i], UNIT_SIZE, UNIT_SIZE);
            }

            // this will be used to display the current score on the screen
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ubuntu Mono", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : " + score,
                    (SCREEN_WIDTH - metrics.stringWidth("Score : " + score)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    private void newApple() { // method to create random coordinates for the apple
        appleX = applePosition.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = applePosition.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }


    private void moveSnake() { // moving a snake (basically continuously drawing rectangles in front of each other)
        for (int i = snakeLength; i > 0; i--) {
            snakeXPosition[i] = snakeXPosition[i - 1];
            snakeYPosition[i] = snakeYPosition[i - 1];
        }
        switch (direction) { // this switch condition is used to move the snake as well as change its direction
            case 'U' -> snakeYPosition[0] -= UNIT_SIZE;
            case 'D' -> snakeYPosition[0] += UNIT_SIZE;
            case 'L' -> snakeXPosition[0] -= UNIT_SIZE;
            case 'R' -> snakeXPosition[0] += UNIT_SIZE;
        }
    }

    private void eatApple() { // method to disappear the apple when the snake eats it then again recreate it
        if (snakeXPosition[0] == appleX && snakeYPosition[0] == appleY) { // coordinates of the head of the snake and
            // apple should be same for it to be eaten
            snakeLength++; // increase the length of the snake as soon he eats the apple
            score++;
            newApple();
        }
    }

    private void checkCollision() { // check collision of the snake with the walls and itself

        // if the snake collides with itself
        for (int i = snakeLength; i > 0; i--) {
            if (snakeXPosition[i] == snakeXPosition[0] && snakeYPosition[i] == snakeYPosition[0]) {
                isMoving = false;
                break;
            }
        }

        // if the snake collides with the wall
        if (snakeXPosition[0] < 0 || snakeXPosition[0] > SCREEN_WIDTH || snakeYPosition[0] < 0 || snakeYPosition[0] > SCREEN_HEIGHT) {
            isMoving = false;
        }
        if (!isMoving) { // if the snake is not moving then stop the timer
            timer.stop();
        }
    }

    private void gameOver(Graphics g) { // method to display the score at the end of the match
        g.setColor(Color.red);
        g.setFont(new Font("Ubuntu Mono", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : " + score, (SCREEN_WIDTH - metrics.stringWidth("Score : " + score)) / 2,
                g.getFont().getSize()); // centering it horizontally not vertically

        //set the game over text
        g.setFont(new Font("Ubuntu Mono", Font.BOLD, 75));
        // this class is useful in aligning the font on the screen as it will contain all the metrics about a font
        // (its width and height etc...)
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        // draw string method will be used to draw string on the screen at the center of the screen both horizontally
        // and vertically
        g.drawString("Game over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

    }

    @Override
    public void actionPerformed(ActionEvent event) { // overridden from Action even listener interface (this function
        // is invoked if any action occurs on our game screen) - we will repaint our screen with the new position of
        // the snake ,so it will look as if the snake is moving
        if (isMoving) { // if the snake is moving
            moveSnake();
            eatApple();
            checkCollision();
        }
        repaint(); // this will call the paintComponent() method again and again
    }

    private class MyKeyAdapter extends KeyAdapter { // inner class which will be used when we press the arrow keys to
        // change the direction of our snake
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
            }
        }
    }
}
