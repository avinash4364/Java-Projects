import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREE_HEIGHT = 1000;
    private static final int UNIT_SIZE = 25; // this will basically act as a helper unit for creating grid lines on
    // the screen

    // initial coordinate for the apple to appear
    private static final int appleX = 0;
    private static final int appleY = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREE_HEIGHT));
        this.setBackground(Color.green);
    }

    public void startGame() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.draw(graphics);
    }

    public void draw(Graphics graphics) { // this method will paint(draw) our panel which is our game screen
        graphics.setColor(Color.black);
        for (int i = 1; i <= SCREEN_WIDTH / UNIT_SIZE; i++) { // for horizontal grid lines
            graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        for (int i = 1; i <= SCREE_HEIGHT / UNIT_SIZE; i++) { // for vertical lines
            graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREE_HEIGHT);
        }

        // drawing the apple
        graphics.setColor(Color.red);
        graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }
}
