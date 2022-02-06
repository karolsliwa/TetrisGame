package Tetris;


import javafx.scene.paint.Color;

import java.util.Random;

public class Block {
    private int[][] shape;
    Color color;
    private Color[] colors = new Color[] {Color.BLUE, Color.RED, Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE, Color.INDIGO, Color.YELLOW, Color.ORANGE, Color.FIREBRICK, Color.GREEN};
    private int[][][] shapes = new int[4][][];
    private int height, width, x, y, currentShape;
    Random generator = new Random();

    public Block(int[][] shape) {
        this.shape = shape;

    }

    public void appear() {
        generateShape();
        generateColor();
    }

    public void moveDown() {y += 1;}
    public void moveRight() {
        x += 1;
    }
    public void moveLeft() {
        this.x -= 1;
    }

    public void turn() {
        currentShape = (currentShape + 1) % 4;
        setShape();
    }

    public void generateShape() {
        shapes[0] = shape;
        for (int j = 1; j < 4; j++) {
            shapes[j] = rotate(shapes[j-1]);
        }
        currentShape = generator.nextInt(4);
        setShape();
    }
    public int getX() {
        return x;
    }

    public int[][] rotate(int[][] shape) {
        int w = shape[0].length;
        int h = shape.length;
        int[][] newShape = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                newShape[i][j] = shape[h - j - 1][i];
            }
        }
        return newShape;
    }
    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public int[][] getShape() {
        return shape;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void generateColor() {
        color = colors[generator.nextInt(colors.length)];
    }

    public void setY(int y) {
        this.y = y;
    }
    public void turnBack() {
        if (currentShape == 0) currentShape += 1;
        else currentShape -= 1;
        setShape();
    }
    public void setShape() {
        shape = shapes[currentShape];
        height = shape.length;
        width = shape[0].length;
    }
}
