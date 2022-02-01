package Tetris;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
     private final int cols = 10;
     private final int rows = 20;
     Canvas canvas = new Canvas(300, 600);
     private final double cellSize = 300/10d;
     private GameArea gameArea = new GameArea(rows, cols, this);

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        gameArea.draw(canvas, cellSize);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> gameArea.turnBlock();
                    case RIGHT -> gameArea.moveBLockRight();
                    case LEFT -> gameArea.moveBlockLeft();
                }
            }
        });
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();
        GameEngine gameEngine = new GameEngine(gameArea, this, 300);
        Thread engineThread = new Thread(gameEngine);
        engineThread.start();
    }

    public void actualize() {
        gameArea.draw(canvas, cellSize);
    }
}
