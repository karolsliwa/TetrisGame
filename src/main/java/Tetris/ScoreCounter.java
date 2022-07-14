package Tetris;

import java.util.ArrayList;

public class ScoreCounter {
    private int score = 0, lvl = 0;
    private int lines = 0;
    private int quadras = 0;
    private int multiplier = 1;
    private boolean paused = false;
    private ArrayList<ScoreMultiplier> threads = new ArrayList<ScoreMultiplier>();

    public ScoreCounter() {}

    public void addPoints(int points) {
        score += points * multiplier;
        if (score > (lvl + 1) * 200) lvl += 1;

    }
    public void increaseMultiplier(int factor) {
        ScoreMultiplier scoreMultiplier = new ScoreMultiplier(this);
        scoreMultiplier.setFactor(factor);
        threads.add(scoreMultiplier);
        scoreMultiplier.startCountdown();
    }
    public void linesRemoved(int linesNumber) {
        addPoints(linesNumber * 10);
        if (linesNumber > 0) increaseMultiplier(linesNumber);
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
    public void removeThread(ScoreMultiplier scoreMultiplier) {
        threads.remove(scoreMultiplier);
    }
    public synchronized void pauseThreads() throws InterruptedException {
        paused = true;
    }
    public synchronized void renewThreads() throws InterruptedException {
        paused = false;
        for (ScoreMultiplier s : threads) {
            s.renewCountdown();
        }
    }
    public void endThreads() {
        for (ScoreMultiplier thread : threads) {
            thread.stopCountdown();
        }
    }

    public boolean isPaused() {
        return paused;
    }

}
