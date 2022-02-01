package Tetris;


import javafx.scene.paint.Color;

import java.util.Random;

public class Block {
    private int[][] shape;
    Color color;
    private int[][][] shapes = new int[4][][];
    private int height, width, x, y, currentShape;
    private GameArea gameArea;
    Random generator = new Random();

    public Block(int[][] shape, Color color, GameArea gameArea) {
        this.shape = shape;
        this.color = color;
        height = shape.length;
        width = shape[0].length;
//        this.x = x;
        this.y = -height;
//        y = 0;
        this.gameArea = gameArea;
        this.x = generator.nextInt(gameArea.getWidth() - width);
    }
    public Block(GameArea gameArea) {
        generateShape();
        this.gameArea = gameArea;
        height = shape.length;
        width = shape[0].length;
        color = generateColor();
        y = -height;
        x = generator.nextInt(gameArea.getWidth() - width);
    }

    public void moveDown() {y += 1;}
    public void moveRight() {
        if (x + width < this.gameArea.getWidth()) x += 1;
    }
    public void moveLeft() {
        if (x > 0) this.x -= 1;
    }
    public void turn() {
        currentShape = (currentShape + 1) % 4;
        shape = shapes[currentShape];
        height = shape.length;
        width = shape[0].length;
        if (x + width > gameArea.getWidth()) x -= (x + width - gameArea.getWidth());
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
    public void generateShape() {
        int i = generator.nextInt(7);
        switch (i) {
            case 0 -> shape = Shape.L.toIntMatrix();
            case 1 -> shape = Shape.SQUARE.toIntMatrix();
            case 2 -> shape = Shape.LINE.toIntMatrix();
            case 3 -> shape = Shape.HALFX.toIntMatrix();
            case 4 -> shape = Shape.ZIGZAG.toIntMatrix();
            case 5 -> shape = Shape.LOPPOSITE.toIntMatrix();
            case 6 -> shape = Shape.ZIGZAGOPPOSITE.toIntMatrix();
        }
        shapes[0] = shape;
        for (int j = 1; j < 4; j++) {
            shapes[j] = rotate(shapes[j-1]);
        }
        i = generator.nextInt(4);
        shape = shapes[i];
        currentShape = i;
    }
    public int getX() {
        return x;
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
    public Color generateColor() {
        return new Color(generator.nextFloat(), generator.nextFloat(), generator.nextFloat(), 1.0);
    }

    public int[][] getTurnedShape() {
        return shapes[(currentShape + 1) % 4];
    }
}
