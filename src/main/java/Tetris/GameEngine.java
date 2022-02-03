package Tetris;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class GameEngine implements Runnable {

    private GameArea gameArea;
    private App app;
    private int refreshTime;

    public GameEngine(GameArea gameArea, App app, int refreshTime) {
        this.gameArea = gameArea;
        this.app = app;
        this.refreshTime = refreshTime;

    }

    @Override
    public void run() {
        while (true) {
            if (gameArea.gameOver()) break;
            gameArea.fall();
            try {
                Thread.sleep(this.refreshTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            app.actualize();

        }
    }
}
