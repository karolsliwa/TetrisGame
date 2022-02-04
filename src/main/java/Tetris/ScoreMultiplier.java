package Tetris;


import java.util.ArrayList;

public class ScoreMultiplier implements Runnable {
    private ScoreCounter scoreCounter;
    private int factor = 1;
    public ScoreMultiplier(ScoreCounter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void run() {
        scoreCounter.setMultiplier(scoreCounter.getMultiplier() * factor);
        int f = factor;
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scoreCounter.setMultiplier(scoreCounter.getMultiplier() / f);
        scoreCounter.removeThread();
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
    public synchronized void pauseGame() throws InterruptedException{

    }
}
