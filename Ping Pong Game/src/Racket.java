import java.awt.*;
import java.awt.event.KeyEvent;

public class Racket extends Rectangle {
    private Player id;

    public Racket(int x, int y, int width, int height, Player id) {
        super(x, y, width, height);
        this.id = id;
    }

    public void draw(Graphics graphics) {
        if (this.id == Player.FIRST) {
            graphics.setColor(Color.red);
        } else {
            graphics.setColor(Color.green);
        }
        graphics.fillRect(x, y, width, height);
    }

    public void move(){

    }

    public void keyPressed(KeyEvent event){

    }

    public void keyReleased(KeyEvent event) {

    }
}
