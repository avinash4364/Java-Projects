import java.awt.*;

public class Ball extends Rectangle {
    private int yVelocity;
    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.fillOval(x, y, width, height);
    }

    public int getYVelocity(){
        return yVelocity;
    }

    public void move(){

    }
}
