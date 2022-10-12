import javax.swing.*;
import java.awt.*;

// our main game is drawn in the panel ,the frame is just the surrounding frame around it.
public class GameFrame extends JFrame { // this is the class which represents the window of our game (which contains
    // the minimize button ,maximize button and close button) - frame around the game panel(painting of our game)
    public GameFrame() throws HeadlessException {
        GamePanel panel = new GamePanel();
        this.add(panel); // adding the game panel to our game
        this.setTitle("Ping Pong"); // setting the title of our game frame
        this.setResizable(false); // setting this false the frame will not have the maximize button
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // here the order is very important in which we are calling these methods:
        // - first we need to set the size of the screen
        // - second we need to make the position of our game screen in the center of the window ,and we can't declare
        // the location before defining the size of our window - otherwise the top-left corner of our frame will at
        // the center of the screen not the game itself
        // lastly - at last after setting the dimension of our frame we make it visible(otherwise some undefined
        // behaviour occurs like the frame becomes very small even after defining the screen)

        //.pack() method causes the frame to fit the size and layouts of its subcomponents(gamePanel in our case)
        this.pack(); // our frame will adjust according to the size of our game panel around it and fit it perfectly
        // without this we would have had to specify the size (height and width) of our frame explicitly
        this.setLocationRelativeTo(null); // this will make our game to appear in the center(as by default it appears
        // in the top left corner)
        this.setVisible(true); // making our screen visible
    }
}
