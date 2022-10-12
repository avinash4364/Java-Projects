import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() { // constructor for the game frame class
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // set the operations that will happen by default when the user
        // closes the window - and EXIT_ON_CLOSE property tells us that the window will be exited when we click on
        // our window close icon
        this.setResizable(false); // sets whether the frame(screen of our game) can be resized
        this.pack(); // this method causes our window to be sized to fit the preferred size and layouts of its children
        this.setVisible(true); // makes the window visible
        this.setLocationRelativeTo(null); // this well position our game window in the center of the screen
    }
}
