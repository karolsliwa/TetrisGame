package Tetris;


import java.util.ArrayList;

public class ScoreMultiplier implements Runnable {
    private ScoreCounter scoreCounter;
    private int factor = 1;
    private long startTime, remainingTime = 40000;;
    private Thread thread;
    public ScoreMultiplier(ScoreCounter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void run() {
        scoreCounter.setMultiplier(scoreCounter.getMultiplier() * factor);
        startTime = System.currentTimeMillis();
        while (true) {
            if (scoreCounter.isPaused()) {
                remainingTime -= (System.currentTimeMillis() - startTime);
            }
            try {
                pauseCountdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - startTime >= remainingTime) {
                scoreCounter.setMultiplier(scoreCounter.getMultiplier() / factor);
                break;
            }
        }
        scoreCounter.removeThread(this);
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public synchronized void pauseCountdown() throws  InterruptedException {
        while (scoreCounter.isPaused()) {
            wait();
        }
    }
    public synchronized void renewCountdown() {
        startTime = System.currentTimeMillis();
        notify();
    }
    public void startCountdown() {
        thread = new Thread(this);
        thread.start();
    }
    public void stopCountdown() {
        thread.stop();
    }
}
