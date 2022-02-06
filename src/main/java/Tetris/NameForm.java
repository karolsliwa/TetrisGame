package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NameForm extends VBox {
    private Button ok, cancel;
    private TextField nameField = new TextField();
    private Text gameOver = new Text();
    private String name = null;
    private App app;
    public NameForm(App app) {
        this.app = app;
        this.setPrefSize(250, 300);
        gameOver.setFont(Font.font("Gill Sans MS", FontWeight.BOLD, 18));
        gameOver.setTextAlignment(TextAlignment.CENTER);
        gameOver.setText("Game over!\nEnter your nickname to save scores");

        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.getChildren().add(gameOver);
        initButtons();
    }

    public void initButtons() {

        ok = new Button("OK");
        cancel = new Button("Cancel");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = nameField.getText();
                app.saveScores();
                app.showScores();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.exitGame();
            }
        });
        this.getChildren().addAll(nameField, cancel, ok);
    }
    public String getName() {
        return name;
    }
}
