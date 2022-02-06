package Tetris;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class ScoresBoard extends VBox {
    private Label score = new Label(), lines = new Label(), x4lines = new Label(), lvl = new Label(), multiplier = new Label(),
            time = new Label("00:00");
    private ScoreCounter scoreCounter;
    private boolean gamePaused = false;
    private Timer timer;
    private Thread timerThread;

    public ScoresBoard(ScoreCounter scoreCounter) {
        this.timer = new Timer(this);
        this.scoreCounter = scoreCounter;
        this.setPrefWidth(250);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(75);
        setValues(score);
        setValues(lines);
        setValues(x4lines);
        setValues(lvl);
        setValues(multiplier);
        setValues(time);
        actualizeScores();
        this.getChildren().addAll(score, lvl, lines, x4lines, time, multiplier);
    }

    public void actualizeScores() {
        this.score.setText("Score: " + scoreCounter.getScore());
        this.lines.setText("Lines: " + scoreCounter.getLines());
        this.x4lines.setText("Quadras: " + scoreCounter.getQuadras());
        this.lvl.setText("Level: " + scoreCounter.getLvl());
        if (scoreCounter.getMultiplier() > 1) this.multiplier.setText("X" + scoreCounter.getMultiplier() + "!");
        else this.multiplier.setText("");
    }

    public void setValues(Label label) {
        label.setPrefSize(100, 20);
        label.setFont(Font.font("Gill Sans MS", FontWeight.BOLD, 18));
        label.setTextFill(Color.RED);
    }
    public void pauseTimer() {
        gamePaused = true;
    }
    public void renewTimer() {
        gamePaused = false;
        timer.renewCountdown();
    }
    public boolean isPaused() {
        return gamePaused;
    }
    public void startTimer() {
        timerThread = new Thread(timer);
        timerThread.start();
    }
    public void setTime(String time) {
        Platform.runLater(() -> {
            this.time.setText(time);
        });

    }
    public void stopTimer() {
        timerThread.stop();
    }
    public String getTime() {
        return time.getText();
    }

}
