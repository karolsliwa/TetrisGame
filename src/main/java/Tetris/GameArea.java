package Tetris;

import Tetris.BlockTypes.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.Random;

public class GameArea {
    private Color[][] fields;
    private int height, width;
    private Block block;
    private ScoreCounter scoreCounter;
    Random generator = new Random();
    private final App app;
    private Block[] blockTypes = new Block[] {new Lshape(), new Ishape(), new Tshape(),
            new Oshape(), new Zshape(), new Jshape(), new Sshape()};
    private boolean gameOver = false;
    public GameArea(int height, int width, App app) {
        fields = new Color[height][width];
        this.height = height;
        this.width = width;
        this.app = app;
        addBlock();
    }

    public void addBlock() {
        block = blockTypes[generator.nextInt(blockTypes.length)];
        block.appear();
        int attempts = 0;
        while (!hasSpace()) {
            if (attempts >= 100 * blockTypes.length) gameOver = true;
            block = blockTypes[generator.nextInt(blockTypes.length)];
            block.appear();
            attempts += 1;
        }
        attempts = 0;
        int x = generator.nextInt(width - block.getWidth());
        while (!blockFits(x)) {
            if (attempts >= 100 * width) gameOver = true;
            x = generator.nextInt(width - block.getWidth());
            attempts += 1;
        }
        block.setX(x);
        block.setY(-block.getHeight());
    }

    public void fall() {
        if (canMoveDown()) block.moveDown();
        else {
            if (block != null) {
                for (int i = 0; i < block.getHeight(); i++) {
                    for (int j = 0; j < block.getWidth(); j++) {
                        if (block.getShape()[i][j] == 1 && block.getY() + i >= 0) {
                            fields[block.getY() + i][block.getX() + j] = block.getColor();
                        }
                    }
                }
                checkGameOver();
                if (!isGameOver()) addBlock();
            }
        }
        checkLines();
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
                Color color= fields[i][j];
                if (color != null) {
                    g.setFill(color);
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
    public boolean isGameOver() {
        gameOver = false;
        int counter = 0;
        for (int i = 0; i < width; i++) {
            if (fields[0][i] != null) {
                counter += 1;
            }
        }
        return counter >= 4;
    }
    public void checkGameOver() {
        int counter = 0;
        for (int i = 0; i < width; i++) {
            if (fields[0][i] != null) {
                counter += 1;
            }
        }
        gameOver = counter >= 4;
    }
    public void moveBLockRight() {
        if (canMoveRight()) {
            block.moveRight();
            app.actualizeGameArea();
        }
    }
    public void moveBlockLeft() {
        if (canMoveLeft()) {
            block.moveLeft();
            app.actualizeGameArea();
        }

    }
    public void turnBlock() {
        int xPrev = block.getX();
        int yPrev = block.getY();
        block.turn();
        checkCollision();
        if (checkCollision()) {
            block.turnBack();
            block.setX(xPrev);
            block.setY(yPrev);
        }
        app.actualizeGameArea();
    }
    public void checkLines() {
        int linesCounter = 0;
        for (int i = height - 1; i > 0; i--) {
            int cellsInLineCounter = 0;
            for (int j = 0; j < width; j++) {
                if (fields[i][j] != null) cellsInLineCounter += 1;
            }
            if (cellsInLineCounter == width) {
                removeLine(i);
                linesCounter += 1;
                i += 1;
            }
        }
        scoreCounter.linesRemoved(linesCounter);

        app.actualizeScoresBoard();
    }

    public void removeLine(int y) {
        for (int j = 0; j < width; j++) {
            fields[y][j] = null;
        }
        app.actualizeGameArea();
        for (int i = y; i > 0; i--) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = fields[i - 1][j];
            }
        }
        for (int j = 0; j < width; j++) {
            fields[0][j] = null;
        }
    }
    public void pushBlockDown() {
        if (canMoveDown()) {
            scoreCounter.addPoints(2);
            block.moveDown();
            app.actualizeGameArea();
            app.actualizeScoresBoard();
            checkGameOver();
        }
    }
    public boolean checkCollision() {
        boolean collided = false;
        int x = block.getX();
        int y = block.getY();
        if (block.getY() + block.getHeight() > height) {
            block.setY(height - block.getHeight());
            return true;
        }
        if (block.getX() < 0) {
            block.setX(0);
            return true;
        }
        if (block.getX() + block.getWidth() > width) {
            block.setX(width - block.getWidth());
            return true;
        }
        for (int j = 0; j < block.getWidth(); j++) {
            if (block.getY() >= 0 && block.getShape()[0][j] == 1 && fields[block.getY()][block.getX() + j] != null) {
                block.setX(block.getX() - (block.getWidth() - j));
                return true;
            }
        }
        for (int i = 1; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if (block.getY() + i >= 0 && block.getY() + i < height && block.getShape()[i][j] == 1 &&
                        fields[block.getY() + i][block.getX() + j] != null) {
                    block.setY(block.getY() - (block.getHeight() - i));
                    return true;
                }
            }
        }
        return false;
    }
    public boolean blockFits(int x) {
        block.setX(x);
        block.setY(0);
        for (int j = x; j < x + block.getWidth(); j++) {
            for (int i = 0; i < block.getHeight(); i++) {
                if (block.getShape()[i][j - x] == 1 && fields[i][j] != null) return false;
            }
        }
        return true;

    }
    public boolean hasSpace() {
        int r = 0;
        for (int j = 0; j < width - block.getWidth(); j++) {
            if (blockFits(j)) return true;
        }
        return false;
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

    public void setScoreCounter(ScoreCounter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }
}
