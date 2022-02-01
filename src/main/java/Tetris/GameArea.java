package Tetris;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.Random;

public class GameArea {
    private Cell[][] fields;
    private int height, width;
    private Block block;
    Random generator = new Random();
    private final App app;

    public GameArea(int height, int width, App app) {
        fields = new Cell[height][width];
        this.height = height;
        this.width = width;
        this.app = app;
        this.block = new Block(this);
    }

    public void addBlock() {
        int[][] shape = {{1, 0}, {1, 0}, {1, 1}};
        this.block = new Block(this);
    }

    public void fall() {
        if (gameOver()) System.out.println("GAME OVER");
//        if (block == null) addBlock();
        if (canMoveDown()) block.moveDown();
        else {
            if (block != null) {
                for (int i = 0; i < block.getHeight(); i++) {
                    for (int j = 0; j < block.getWidth(); j++) {
                        if (block.getShape()[i][j] == 1 && block.getY() + i >= 0) {
                            fields[block.getY() + i][block.getX() + j] = new Cell(block);
                        }
                    }
                }
                addBlock();
            }
        }
    }

    public boolean canMoveDown() {
        if (block == null) return false;
        int y = block.getY();
        int x  = block.getX();
        if (y + block.getHeight() == height) return false;
        for (int j = 0; j < block.getHeight(); j++) {
            for (int k = 0; k < block.getWidth(); k++) {
                if (j + y + 1>= 0 && block.getShape()[j][k] == 1 && fields[y + j + 1][x + k] != null) return false;
            }
        }
        return true;
    }
    public boolean canMoveRight() {
        if (block == null) return false;
        int y = block.getY();
        int x  = block.getX();
        if (x + block.getWidth() == width) return false;
        for (int j = 0; j < block.getHeight(); j++) {
            for (int k = 0; k < block.getWidth(); k++) {
                if (j + y >= 0 && block.getShape()[j][k] == 1 && fields[y + j][x + k + 1] != null) return false;
            }
        }
        return true;
    }
    public boolean canMoveLeft() {
        if (block == null) return false;
        int y = block.getY();
        int x  = block.getX();
        if (x == 0) return false;
        for (int j = 0; j < block.getHeight(); j++) {
            for (int k = 0; k < block.getWidth(); k++) {
                if (j + y >= 0 && block.getShape()[j][k] == 1 && fields[y + j][x + k - 1] != null) return false;
            }
        }
        return true;
    }
    public boolean canTurn() {
        int[][] shape = block.getTurnedShape();
        return true;

    }
    public void draw(Canvas canvas, double cellSize) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0, 300,600);
        if (block != null) {
            int y = block.getY();
            int x = block.getX();
            for (int i = 0; i < block.getHeight(); i++) {
                for (int j = 0; j < block.getWidth(); j++) {
                    if (i + block.getY() >= 0 && block.getShape()[i][j] == 1) {
                        g.setFill(block.getColor());
                        g.fillRect((j + x) * cellSize, (i + y) * cellSize, cellSize, cellSize);
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = fields[i][j];
                if (cell != null) {
                    g.setFill(cell.getColor());
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        g.setStroke(Color.BLACK);
        g.setLineWidth(0.1);
        for (int i = 0; i < height; i++) {
            g.strokeLine(0, i * cellSize,width * cellSize, i * cellSize);
        }
        for (int j = 0; j < width; j++) {
            g.strokeLine(j * cellSize, 0, j * cellSize, height * cellSize);
        }
    }
    public boolean gameOver() {
        int counter = 0;
        for (int i = 0; i < width; i++) {
            if (fields[0][i] != null) {
                counter += 1;
            }
        }
        return counter >= 4;
    }

    public void moveBLockRight() {
        if (canMoveRight()) {
            block.moveRight();
            app.actualize();
        }
    }
    public void moveBlockLeft() {
        if (canMoveLeft()) {
            block.moveLeft();
            app.actualize();
        }

    }
    public void turnBlock() {
        block.turn();
        app.actualize();
    }
    public void checkLines() {
        for (int i = 0; i < height; i++) {
            int cellsInLineCounter = 0;
            for (int j = 0; j < width; j++) {
                if (fields[i][j] != null) cellsInLineCounter += 1;
            }

        }
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public Block getBlock() {
        return block;
    }
}
