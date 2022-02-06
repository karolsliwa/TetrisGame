package Tetris;

public class Timer implements Runnable {
    private long passedTime, startTime;
    private ScoresBoard scoresBoard;
    public Timer(ScoresBoard scoresBoard) {
        this.scoresBoard = scoresBoard;
        passedTime = 0;
    }


    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        long timeToUpdate;
        while (true) {
            try {
                pauseCountdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            passedTime = System.currentTimeMillis() - startTime;
            timeToUpdate = 1000 - (passedTime % 1000);
            try {
                Thread.sleep(timeToUpdate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scoresBoard.setTime(this.timeToString());
        }
    }

    public synchronized void pauseCountdown() throws  InterruptedException {
        while (scoresBoard.isPaused()) {
            wait();
        }
    }
    public synchronized void renewCountdown() {
        startTime = System.currentTimeMillis() - passedTime;
        notify();
    }

    public String timeToString() {
        long seconds, minutes;
        String s = "", m = "";
        seconds = passedTime / 1000;
        minutes = seconds / 60;
        seconds = seconds % 60;
        if (seconds < 10) s = "0";
        if (minutes < 10) m = "0";
        return m + minutes + ":" + s + seconds;
    }
}
