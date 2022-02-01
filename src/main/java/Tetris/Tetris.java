package Tetris;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tetris {

    public static void main(String[] args) {
        try {
            App.launch(App.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
