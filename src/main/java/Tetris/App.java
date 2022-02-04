package Tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {
     private final int cols = 10;
     private final int rows = 20;
     Canvas canvas = new Canvas(300, 600);
     private final double cellSize = 300/10d;
     private GameArea gameArea = new GameArea(rows, cols, this);
     private Scene startScreen, gameScreen, scoresScreen;
     private GameEngine engine = new GameEngine(gameArea, this,300);
     private Thread engineThread = new Thread(engine);
     private Stage primaryStage;
     private ScoresBoard scoresBoard = new ScoresBoard();
     private ScoreCounter scoreCounter = gameArea.getScoreCounter();
     private boolean pauseCondition = false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initGameScreen();
        this.primaryStage.setTitle("Tetris");
        this.primaryStage.setScene(gameScreen);
        this.primaryStage.show();
    }

    public void actualize() {
        Platform.runLater(() -> {
            gameArea.draw(canvas, cellSize);
            scoresBoard.actualizeScores(scoreCounter.getScore(), scoreCounter.getLvl(), scoreCounter.getLines(),
                    scoreCounter.getQuadras(), scoreCounter.getMultiplier());
        });
    }

    public void initGameScreen() {
        VBox root = new VBox();
        Button start = new Button("Start");
        start.setPrefSize(60, 40);
        Button pause = new Button("Pause");
        pause.setPrefSize(60, 40);
        Button exit = new Button("Exit");
        exit.setPrefSize(60, 40);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(50);
        buttons.setPadding(new Insets(10, 10, 10,10));
        buttons.getChildren().addAll(start, pause, exit);
        start.setFocusTraversable(false);
        pause.setFocusTraversable(false);
        exit.setFocusTraversable(false);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engineThread.start();
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engineThread.stop();
                primaryStage.close();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!pauseCondition) {
                    pause.setText("Play");
//                    try {
//                        scoreCounter.pauseThreads();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    pauseCondition = true;
                }
                else {
                    pause.setText("Pause");
                    pauseCondition = false;
//                    scoreCounter.renewThreads();
                    engine.renewGame();
                }
            }
        });
        gameArea.draw(canvas, cellSize);
        root.getChildren().add(canvas);
        root.getChildren().add(buttons);
        HBox game = new HBox();
        game.getChildren().addAll(root, scoresBoard);
        gameScreen = new Scene(game);

        EventHandler<KeyEvent> arrowKeysHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> gameArea.turnBlock();
                    case RIGHT -> gameArea.moveBLockRight();
                    case LEFT -> gameArea.moveBlockLeft();
                    case DOWN -> gameArea.pushBlockDown();
                }
            }
        };
        gameScreen.setOnKeyPressed(arrowKeysHandler);
    }

    public boolean isPaused() {
        return pauseCondition;
    }
}
