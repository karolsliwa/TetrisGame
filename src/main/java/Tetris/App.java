package Tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class App extends Application {
     private final int cols = 10;
     private final int rows = 20;
     Canvas canvas = new Canvas(300, 600);
     private final double cellSize = 300/10d;
     private GameArea gameArea = new GameArea(rows, cols, this);
     private Scene startScreen, gameScreen, scoresScreen;
     private Thread engineThread;
     private Stage primaryStage;
     private VBox scoreValues;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initGameScreen();
        this.primaryStage.setTitle("Tetris");
        this.primaryStage.setScene(gameScreen);
        this.primaryStage.show();
        GameEngine gameEngine = new GameEngine(gameArea, this, 300);
        engineThread = new Thread(gameEngine);
        engineThread.start();
    }

    public void actualize() {
        Platform.runLater(() -> {
            gameArea.draw(canvas, cellSize);
            scoreValues.getChildren().clear();
            scoreValues.getChildren().addAll(new Label("Score: " + gameArea.getScore()),
                    new Label("Lines" + gameArea.getLinesRemoved()));
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
//                primaryStage.setScene(scoresScreen);
                primaryStage.close();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pause.setText("Play");
            }
        });
        gameArea.draw(canvas, cellSize);
        root.getChildren().add(canvas);
        root.getChildren().add(buttons);
//        gameScreen = new Scene(root);
        scoreValues = new VBox();
        scoreValues.setAlignment(Pos.CENTER);
        scoreValues.setPrefWidth(60);
        scoreValues.setSpacing(50);
        scoreValues.getChildren().addAll(new Label("Score: " + gameArea.getScore()),
                new Label("Lines" + gameArea.getLinesRemoved()));
        HBox game = new HBox();
        game.getChildren().addAll(root,scoreValues);
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
//        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, arrowKeysHandler);
//        canvas.setOnKeyPressed(arrowKeysHandler);
        gameScreen.setOnKeyPressed(arrowKeysHandler);

    }

    public void setStartButton(Button button) {

    }
    public void initStartScreen() {

    }
}
