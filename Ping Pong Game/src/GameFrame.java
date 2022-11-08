import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() throws HeadlessException {
        this.add(new GamePanel());
        this.setTitle("Ping Pong");
        this.setResizable(false);
        this.setBackground(Color.black); // this is used to make the background color of the game not in the panel
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
