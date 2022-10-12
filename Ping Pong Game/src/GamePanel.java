import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

// Game panel is the kind of canvas on which we are going to paint(draw our game)
public class GamePanel extends JPanel implements Runnable { // we are implementing the Runnable interface so that we
    // can run it on a thread
    private static final int GAME_WIDTH = 1000; // width and height of our game screen(ratio of a standard ping pong
    // table width to height is 5:9)
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * 0.55);
    private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT); // dimension of our screen
    private static final int BALL_DIAMETER = 20;
    private static final int RACKET_WIDTH = 25;
    private static final int RACKET_HEIGHT = 100;
    private static final String path1 = "/home/demo4364/Documents/Java Projects/Ping Pong Game/ping_pong.wav";
    private static final String path2 = "/home/demo4364/Documents/Java Projects/Ping Pong Game/Swoosh sound.wav";
    private static final File file1 = new File(path1);
    private static final File file2 = new File(path2);
    private Ball ball;
    private final Score score;
    private Racket leftRacket;
    private Racket rightRacket;

    public GamePanel() {
        newRacket();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true); // this is set to true ,so that when we press any keys the focus should be on our game
        // screen - this will cause our game to register those pressed keys and without this if we press any key
        // there will no response even if the game is running
        this.addKeyListener(new Al()); // listen for any key pressed (without this also pressing keys our game will
        // not respond)
        this.setPreferredSize(SCREEN_SIZE);
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() { // used for creating new ball
        Random random = new Random();
        ball = new Ball((GAME_WIDTH / 2 - BALL_DIAMETER / 2), (GAME_HEIGHT / 2 - BALL_DIAMETER / 2), BALL_DIAMETER,
                BALL_DIAMETER); // here we are instantiating the ball at the center of the screen
    }

    public void newRacket() { // used for creating new paddle
        // for the first player the paddle should be placed at the left corner and vertical-center
        leftRacket = new Racket(0, (GAME_HEIGHT / 2) - (RACKET_HEIGHT / 2), RACKET_WIDTH, RACKET_HEIGHT, Player.FIRST);
        // for the second player the racket should be placed at the right corner and vertical-center
        rightRacket = new Racket(GAME_WIDTH - RACKET_WIDTH, (GAME_HEIGHT / 2) - (RACKET_HEIGHT / 2), RACKET_WIDTH,
                RACKET_HEIGHT, Player.SECOND);
    }

    public void paint(Graphics graphics) {
        Image image = createImage(getWidth(), getHeight()); // get the height and width of the current panel
        draw(image.getGraphics());
        graphics.drawImage(image, 0, 0, this); // drawing the image on our screen starting from top left corner
    }

    public void draw(Graphics graphics) {
        // drawing the rackets
        leftRacket.draw(graphics);
        rightRacket.draw(graphics);

        // drawing the ball
        ball.draw(graphics);

        // drawing the line
        score.draw(graphics);
    }

    public void move() { // as this method is called in the run method and to make our rackets move much smother we
        // need to also call move methods of the Racket class
        leftRacket.move();
        rightRacket.move();
        ball.move();
    }

    public void checkCollision() {
        // stops rackets from colliding on window edges
        if (leftRacket.y <= 0) {
            leftRacket.y = 0;
        }
        if (leftRacket.y >= (GAME_HEIGHT - RACKET_HEIGHT)) {
            leftRacket.y = GAME_HEIGHT - RACKET_HEIGHT;
        }
        if (rightRacket.y <= 0) {
            rightRacket.y = 0;
        }
        if (rightRacket.y >= (GAME_HEIGHT - RACKET_HEIGHT)) {
            rightRacket.y = GAME_HEIGHT - RACKET_HEIGHT;
        }

        // stops the ball from colliding from the window edges (bouncing the ball off the window edges along x-axis)
        if (ball.y <= 0 || ball.y >= (GAME_HEIGHT - BALL_DIAMETER)) {
            ball.setYDirection(-ball.getYVelocity()); // reversing the movement of the ball so that it bounces off
        }

        // check if the ball collides with the racket
        if (ball.intersects(leftRacket)) { // if the ball collides with the left racket
            // make a smash sound
            playSound(file1);
            ball.setXVelocity(Math.abs(ball.getXVelocity() + 1)); // as the ball hits the racket its speed increases
            if (ball.getYVelocity() > 0) { // in this x direction - optional (adding the 1)
                ball.setYVelocity(ball.getYVelocity() + 1); // speed increases in the y direction - optional
            } else {
                ball.setYVelocity(ball.getYVelocity() - 1);
            }
            ball.setXDirection(ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        } else if (ball.intersects(rightRacket)) { // if the ball collides with the right racket
            playSound(file1);
            ball.setXVelocity(Math.abs(ball.getXVelocity() + 1)); // as the ball hits the racket its speed increases
            if (ball.getYVelocity() > 0) { // in this x direction - optional (adding the 1)
                ball.setYVelocity(ball.getYVelocity() + 1); // speed increases in the y direction - optional
            } else {
                ball.setYVelocity(ball.getYVelocity() - 1);
            }
            ball.setXDirection(-ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        }

        // give the player 1 point whose racket collides with the ball and then recreate the new ball and racket
        if (ball.x <= 0) { // player two scored a point - meaning it touched the left boundary (missed by the player 1)
            // play the swoosh sound
            playSound(file2);
            score.setPlayerTwoScore(score.getPlayerTwoScore() + 1);
            newRacket();
            newBall();
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) { // player one scored a point - it touched the right boundary
            playSound(file2);
            score.setPlayerOneScore(score.getPlayerOneScore() + 1);
            newBall();
            newRacket();
        }
    }

    public void run() {
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
                repaint(); // repaint this component by calling the paint method of this component
                delta--;
            }
        }
    }

    public class Al extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) { // when we press the key
            leftRacket.keyPressed(e);
            rightRacket.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) { // when we release the key after pressing
            leftRacket.keyReleased(e);
            rightRacket.keyReleased(e);
        }
    }

    public void playSound(File file){
        try {
            AudioInputStream smash = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(smash);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing the audio");
            e.printStackTrace();
        }
    }
}
