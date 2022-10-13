import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = (int) (SCREEN_WIDTH * 0.55); // ratio of the ping pong table is 5:9
    private static final int UNIT_SIZE = 25;
    private static final int RACKET_WIDTH = UNIT_SIZE;
    private static final int RACKET_HEIGHT = 100;
    private static final int BALL_DIAMETER = UNIT_SIZE;

    private Racket leftRacket;
    private Racket rightRacket;
    private Ball ball;


    public GamePanel() {
        newRacket();
        newBall();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        Thread gameThread = new Thread(this); // here target is object whose run method will be invoked when the
        // thread will start
        gameThread.start();
    }

    private void newBall() {
        ball = new Ball(0, 0, BALL_DIAMETER, BALL_DIAMETER);
    }

    private void newRacket() {
        leftRacket = new Racket(0, (SCREEN_HEIGHT - RACKET_HEIGHT) / 2, RACKET_WIDTH,
                RACKET_HEIGHT, Player.FIRST);
        rightRacket = new Racket((SCREEN_WIDTH - RACKET_WIDTH), (SCREEN_HEIGHT - RACKET_HEIGHT) / 2, RACKET_WIDTH,
                RACKET_HEIGHT, Player.SECOND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics graphics) {

        // drawing horizontal and vertical lines
        for (int i = 0; i < SCREEN_WIDTH; i++) {
            graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }

        // drawing left and right racket
        leftRacket.draw(graphics);
        rightRacket.draw(graphics);

        // drawing a ball
        ball.draw(graphics);
    }

    private void move() { // as this method is called in the run method and to make our rackets move much smother we
        // need to also call move methods of the Racket class
        leftRacket.move();
        rightRacket.move();
        ball.move();
    }

    private void checkCollision() {

        // stops the racket from colliding vertically on the window edges
        if (leftRacket.y <= 0) {
            leftRacket.y = 0;
        }
        if (leftRacket.y >= (SCREEN_HEIGHT - RACKET_HEIGHT)) {
            leftRacket.y = SCREEN_HEIGHT - RACKET_HEIGHT;
        }
        if (rightRacket.y <= 0) {
            rightRacket.y = 0;
        }
        if (rightRacket.y >= (SCREEN_HEIGHT - RACKET_HEIGHT)) {
            rightRacket.y = SCREEN_HEIGHT - RACKET_HEIGHT;
        }

        // stops the ball from colliding on the horizontal window edges - we want the ball to bounce off the
        // horizontal window edges and pass off from the vertical edges
        if (ball.y <= 0 || ball.y >= (SCREEN_HEIGHT - BALL_DIAMETER)) {
            ball.y = -ball.getYVelocity(); // reversing the movement of the ball
        }

        // check if the ball collides with the racket
        // if the ball collides with left racket
        if (leftRacket.intersects(ball)){
        }

        // if the ball collides with the right racket
        else if (rightRacket.intersects(ball)){

        }

        // a player gets a point if his opponent misses the ping pong - meaning the ball passes through the vertical
        // edges

    }

    @Override
    public void run() { // this run method will be executed when the thread will start executing
        long lastTime = System.nanoTime(); // get the current system timer in nanosecond (mainly it is used in place
        // of millieSecond() method because this gives more precision and that gives more accuracy) - nanoTime()
        // methods are generally used when we want to find difference between two moments in time with very high
        // precision and not the actual clock time

        double amountOfTicks = 60.0;
        double nanoSeconds = 1000000000 / amountOfTicks;
        double delta = 0;

        // game loop (used for running the game in a loop)
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSeconds;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }


    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            leftRacket.keyPressed(e);
            rightRacket.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            leftRacket.keyReleased(e);
            rightRacket.keyReleased(e);
        }
    }
}
