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

import java.io.IOException;


public class App extends Application {
     private final int cols = 10;
     private final int rows = 20;
     Canvas canvas = new Canvas(300, 600);
     private final double cellSize = 300/10d;
     private GameArea gameArea = new GameArea(rows, cols, this);
     private Scene gameScreen;
     private GameEngine engine = new GameEngine(gameArea, this,300);
     private Thread engineThread = new Thread(engine);
     private Stage primaryStage;
     private ScoreCounter scoreCounter = new ScoreCounter();
     private ScoresBoard scoresBoard = new ScoresBoard(scoreCounter);
     private boolean pauseCondition = false;
     private EventHandler<KeyEvent> arrowKeysHandler;
     private ScoreSaver scoreSaver = new ScoreSaver();
     private Button start, exit, pause;
     private NameForm nameForm;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameArea.setScoreCounter(scoreCounter);
        this.primaryStage = primaryStage;
        initGameScreen();
        this.primaryStage.setTitle("Tetris");
        this.primaryStage.setScene(gameScreen);
        this.primaryStage.show();
    }


    public void initGameScreen() {
        VBox root = new VBox();
        HBox buttons = initButtons();
        gameArea.draw(canvas, cellSize);
        root.getChildren().add(canvas);
        root.getChildren().add(buttons);
        HBox game = new HBox();
        game.getChildren().addAll(root, scoresBoard);
        gameScreen = new Scene(game);

        arrowKeysHandler = new EventHandler<KeyEvent>() {
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
    public void exitGame() {
        scoresBoard.stopTimer();
        scoreCounter.endThreads();
        engineThread.stop();
        primaryStage.close();
    }
    public void pauseGame() {
        gameScreen.setOnKeyPressed(null);
        try {
            scoreCounter.pauseThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseCondition = true;
        scoresBoard.pauseTimer();
    }
    public void renewGame() {
        scoresBoard.renewTimer();
        pauseCondition = false;
        gameScreen.setOnKeyPressed(arrowKeysHandler);
        try {
            scoreCounter.renewThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        engine.renewGame();
    }
    public boolean isPaused() {
        return pauseCondition;
    }
    public void actualizeScoresBoard() {
        Platform.runLater(() -> {
            scoresBoard.actualizeScores();
        });
    }
    public void actualizeGameArea() {
        Platform.runLater(() -> {
            gameArea.draw(canvas, cellSize);
        });
    }
    public void endGame() {
        Platform.runLater(() -> {
            pauseGame();
            nameForm = new NameForm(this);
            Scene scene = new Scene(nameForm);
            primaryStage.setScene(scene);
        });
    }
    public void showScores() {
        EndGameBoard endGameBoard = new EndGameBoard(scoreSaver, scoresBoard);
        endGameBoard.getChildren().add(exit);
        Scene scene = new Scene(endGameBoard);
        primaryStage.setScene(scene);
    }
    public void saveScores() {
        String name = nameForm.getName();
        if (name != null) {
            try {
                scoreSaver.saveScore(name, scoreCounter.getScore(), scoreCounter.getLvl(), scoreCounter.getLines(),
                        scoreCounter.getQuadras(), scoresBoard.getTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public HBox initButtons() {
        start = new Button("Start");
        pause = new Button("Pause");
        exit = new Button("Exit");
        prepButton(exit);
        prepButton(start);
        prepButton(pause);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(50);
        buttons.setPadding(new Insets(10, 10, 10,10));
        buttons.getChildren().addAll(start, pause, exit);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engineThread.start();
                scoresBoard.startTimer();
                start.setOnAction(null);
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitGame();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!pauseCondition) {
                    pause.setText("Play");
                    pauseGame();
                }
                else {
                    pause.setText("Pause");
                    renewGame();
                }
            }
        });
        return buttons;
    }
    public void prepButton(Button button) {
        button.setPrefSize(60, 40);
        button.setFocusTraversable(false);
    }
}
