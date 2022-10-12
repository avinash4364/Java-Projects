import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600; // width of our game screen
    private static final int SCREEN_HEIGHT = 600; // height of our game screen
    private static final int UNIT_SIZE = 25; // how big we want objects in this game(so each object in our game will be of
    // width and height of 25 pixels) - so each grid size will be 25 pixels
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_WIDTH) / UNIT_SIZE;
    private static final int DELAY = 75;
    private Thread animator;


    private final int[] x = new int[GAME_UNITS];// these arrays are going to hold all the coordinates for the body parts of the
    private final int[] y = new int[GAME_UNITS]; //snake including the head of the snake
    private int bodyParts = 6; // initial size of the snake
    private int applesEaten = 0; // number of apples eaten and initially they were 0
    private int appleX; // x coordinates of the apple
    private int appleY; // y coordinates of the apple (and each time the apple is eaten it will appear at a new position)
    private char direction = 'R'; // snake is going right initially
    private boolean running = false;
    private Timer timer;
    private final Random random;

    private final String path = "/home/demo4364/Documents/Java Projects/Snake/snake_eating.wav"; // mp3 not supported so convert
    // your mp3 file to wav file (pcm)
    private final File file = new File(path);
    private Clip clip;

    public GamePanel() {
        random = new Random(); // here we are creating a new random number generator
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // setting the size of the game screen
        this.setBackground(Color.black); // sets the background color
        this.setFocusable(true); // this will ensure that any action performed will be performed on the game screen
        // (meaning the mouse and key are focused on the screen) - if this set to false which by default then
        // pressing the keyboard will not move the snake
        this.addKeyListener(new MyKeyAdapter()); // adding key listeners for arrow keys
        startGame();
    }

    public void startGame() {
        newApple(); // creating an apple as soon as we start the game
        running = true;
        timer = new Timer(DELAY, this); // here are creating a timer
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics graphics) { // this method will be going to be used to draw grids on the screen
        if (running) { // if our game is running then draw on the screen
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // this method will draw lines from
                // (x1,y1) to (x2, y2) along the x-axis
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // this method will draw lines from
                // (x1,y1) to (x2, y2) along the y-axis
            }
            // drawing the apple
            graphics.setColor(Color.red);
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // drawing the head of the snake and its body
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) { // head of the snake
                    graphics.setColor(Color.green);
                    graphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    //graphics.fillArc(x[i],y[i],UNIT_SIZE,UNIT_SIZE,0,180);
                } else { // body of the snake
                    graphics.setColor(new Color(45, 180, 0));
                    graphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // this will be used to display the current score on the screen
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ubuntu Mono", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : " + applesEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Score : " + applesEaten)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void newApple() { // this function is used to create an apple at random position on the screen
            appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }


    public void move() {
        //  moving the snake using this method
        for (int i = bodyParts; i > 0; i--) { // decrementing the body parts, so it will look like the snake is moving
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    public void eatApple() { // as our snake is going to eat apples, so it will disappear and a new apple will appear
        if (x[0] == appleX && y[0] == appleY) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Error playing the song");
                e.printStackTrace();
            }
            bodyParts++; // the length of the snake increases
            applesEaten++;
            newApple(); // new apple created as soon we eat the apple
        }
    }

    public void checkCollision() {
        //check if the head of the snake collides with itself (body)
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i] && y[0] == y[i])) {
                running = false;
                break;
            }
        }
        // check if the head of the snake collides with the border
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // display final score
        g.setColor(Color.red);
        g.setFont(new Font("Ubuntu Mono", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score : " + applesEaten)) / 2,
                g.getFont().getSize()); // centering it horizontally not vertically

        //set the game over text
        g.setFont(new Font("Ubuntu Mono", Font.BOLD, 75));
        // this class is useful in aligning the font on the screen as it will contain all the metrics about a font
        // (its width and height etc...)
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        // draw string method will be used to draw string on the screen at the center of the screen both horizontally
        // and vertically
        g.drawString("Game over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        // close the resource taken by the clip instance for playing the audio file
        if (this.clip != null) { // meaning we have at-least consumed one apple then only the clip object will be
            // created as then only we will play the music which will then consume the system resource to play the music
            clip.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // this function is invoked when an action occurs
        if (running) {
            move();
            eatApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter { // inner class which will be used when we press the arrow keys to
        // change the direction of our snake
        @Override
        public void keyPressed(KeyEvent e) {
            // in all the case there is an if case to avoid a 180-degree direction change of the snake on its axis
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
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
            }
        }
    }
}
