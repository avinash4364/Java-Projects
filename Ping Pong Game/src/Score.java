import java.awt.*;

public class Score extends Rectangle {
    private static int GAME_WIDTH;
    private static int GAME_HEIGHT;
    private int playerOneScore;
    private int playerTwoScore;

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

    public Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics graphics) { // used to draw the score on the screen
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Source Code Pro", Font.PLAIN, 60));
        graphics.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
        graphics.drawString(playerOneScore / 10 + String.valueOf(playerOneScore % 10),
                GAME_WIDTH / 2 - 80, 50);
        graphics.drawString(playerTwoScore / 10 + String.valueOf(playerTwoScore % 10),
                GAME_WIDTH / 2 + 20, 50);
    }
}
