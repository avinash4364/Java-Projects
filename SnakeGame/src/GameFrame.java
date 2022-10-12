import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame { // a frame acts as boundary around our game screen. It contains close , minimize
    // and maximize button.
    public GameFrame() throws HeadlessException {
        this.add(new GamePanel());
        this.setBackground(Color.black);
        this.setResizable(false); // remove the maximize button
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
