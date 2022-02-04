package Tetris;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ScoreCounter {
    private GameArea gameArea;
    ScoreMultiplier scoreMultiplier = new ScoreMultiplier(this);
    private int score = 0, lvl = 0;
    private int lines = 0;
    private int quadras = 0;
    private int multiplier = 1;
    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public ScoreCounter(GameArea g) {
        gameArea = g;
    }
    public void addPoints(int points) {
        score += points * multiplier;
        if (score > (lvl + 1) * 200) lvl += 1;
    }
    public void increaseMultiplier(int factor) {
        scoreMultiplier.setFactor(factor);
        Thread multiplierThread = new Thread(scoreMultiplier);
        threads.add(multiplierThread);
        multiplierThread.start();
    }
    public void linesRemoved(int linesNumber) {
        addPoints(linesNumber * 10);
        increaseMultiplier(linesNumber);
        lines += linesNumber;
        if (linesNumber == 4) quadras += 1;
    }
    public int getScore() {
        return score;
    }
    public int getMultiplier() {
        return multiplier;
    }
    public void setMultiplier(int m) {
        multiplier = m;
    }

    public int getLines() {
        return lines;
    }

    public int getQuadras() {
        return quadras;
    }
    public int getLvl() {
        return lvl;
    }
    public void removeThread() {
        threads.remove(Thread.currentThread());
        Thread.currentThread().stop();
    }
//    public synchronized void pauseThreads() throws InterruptedException {
//        for (Thread thread : threads) {
//            thread.pauseGame();
//        }
//    }
//    public synchronized void renewThreads() {
//        for (Thread thread : threads) {
//            thread.notify();
//        }
//    }

}
