import java.awt.*;

public class Score extends Rectangle {
    private static int WIDTH; // these metrics are used to display score on the screen at an appropriate position
    private static int HEIGHT;
    private int playerOneScore;
    private int playerTwoScore;

    public Score(int width, int height) {
        Score.WIDTH = width;
        Score.HEIGHT = height;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }


    public void draw(Graphics graphics) { // used to draw the score on the screen
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Source Code Pro", Font.PLAIN, 60));
        graphics.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        graphics.drawString(playerOneScore / 10 + String.valueOf(playerOneScore % 10),
                WIDTH / 2 - 80, 50);
        graphics.drawString(playerTwoScore / 10 + String.valueOf(playerTwoScore % 10),
                WIDTH / 2 + 20, 50);
    }
}
