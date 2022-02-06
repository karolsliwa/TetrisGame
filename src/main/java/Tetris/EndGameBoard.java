package Tetris;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class EndGameBoard extends HBox {
    private ScoresBoard scoresBoard;
    private ScoreSaver scoreSaver;
    private List<List<String>> bestScores = null;
    private GridPane grid = new GridPane();
    private VBox scoresTable, playerScores;
    public EndGameBoard(ScoreSaver scoreSaver, ScoresBoard scoresBoard) {
        this.setAlignment(Pos.CENTER);
        this.scoreSaver = scoreSaver;
        this.scoresBoard = scoresBoard;
        this.setSpacing(50);
        initPlayerScores();
        initBestScores();
        this.getChildren().addAll(playerScores, scoresTable);

    }
    public void initBestScores() {
        scoresTable = new VBox();
        scoresTable.setAlignment(Pos.CENTER);
        scoresTable.setSpacing(40);
        Label header = new Label("Best scores: ");
        setHeader(header);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        Label[] columnTitles = {new Label("Name"), new Label("Level"), new Label("Score"), new Label("Lines"),
                new Label("Quadras"), new Label("Time")};
        for (int j = 0; j < 6; j++) {
            Label l = columnTitles[j];
            setTableCell(l);
            GridPane.setConstraints(l, j,0);
            grid.getChildren().add(l);
        }
        try {
            bestScores = scoreSaver.getScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int h = Math.min(15, bestScores.size());
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < bestScores.get(i).size(); j++) {
                Label l = new Label(bestScores.get(i).get(j));
                setTableCell(l);
                GridPane.setConstraints(l, j, i + 1);
                grid.getChildren().add(l);
            }
        }
        scoresTable.setAlignment(Pos.CENTER);
        scoresTable.getChildren().addAll(header, grid);
    }
    public void initPlayerScores() {

        playerScores = new VBox();
        playerScores.setSpacing(40);
        playerScores.setAlignment(Pos.CENTER);
        Label header = new Label("Your scores: ");
        setHeader(header);
        playerScores.setAlignment(Pos.CENTER);
        playerScores.getChildren().addAll(header, scoresBoard);

    }
    public void setHeader(Label label) {
        label.setPrefSize(130, 20);
        label.setFont(Font.font("Gill Sans MS", FontWeight.BOLD, 18));
        label.setTextFill(Color.BLUE);
    }
    public void setTableCell(Label label) {
        label.setPrefSize(60, 15);
        label.setFont(Font.font("Gill Sans MS", FontWeight.MEDIUM, 12));
        label.setTextFill(Color.STEELBLUE);
    }
}
