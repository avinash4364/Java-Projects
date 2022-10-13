import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 1000;
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
        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }

        // drawing left and right racket
        leftRacket.draw(graphics);
        rightRacket.draw(graphics);

        // drawing a ball
        ball.draw(graphics);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private static class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
