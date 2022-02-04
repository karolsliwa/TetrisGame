package Tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class ScoresBoard extends VBox {
    private Label score = new Label(), lines = new Label(), x4lines = new Label(), lvl = new Label(), multiplier = new Label();

    public ScoresBoard() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(75);
        setValues(score);
        setValues(lines);
        setValues(x4lines);
        setValues(lvl);
        setValues(multiplier);
        actualizeScores(0, 0, 0, 0, 1);
        this.getChildren().addAll(score, lvl, lines, x4lines, multiplier);
    }

    public void actualizeScores(int score, int lvl, int lines, int quadrupleLines, int multiplier) {
        this.score.setText("Score: " + score);
        this.lines.setText("Lines: " + lines);
        this.x4lines.setText("Quadras: " + quadrupleLines);
        this.lvl.setText("Level: " + lvl);
        if (multiplier > 1) this.multiplier.setText("X" + multiplier + "!");
        else this.multiplier.setText("");
    }

    public void setValues(Label label) {
        label.setPrefSize(100, 20);
        label.setFont(Font.font("Gill Sans MS", FontWeight.BOLD, 18));
        label.setTextFill(Color.RED);
    }
}
