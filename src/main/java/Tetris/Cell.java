package Tetris;


import javafx.scene.paint.Color;

public class Cell {
    private Color color;
    private int x, y;

    public Cell(Block block) {
        color = block.getColor();

    }

    public Color getColor() {
        return color;
    }
}
