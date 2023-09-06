
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class ClockModel extends Observable {
    private int countdownTime;
    private int clockTime;
    private boolean displayCountdown;

    public ClockModel() {
        countdownTime = 3;
        clockTime = 5;
        displayCountdown = true;
    }

    public boolean getDisplayCountdown() {
        return displayCountdown;
    }

    public int getCountdownTime() {
        return countdownTime;
    }

    public int getClockTime() {
        return clockTime;
    }

    public void countdown() {
        Timer countdownTimer = new Timer();
        TimerTask countdownTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (countdownTime >= 1) {
                    setChanged();
                    notifyObservers();
                    countdownTime--;
                } else {
                    displayCountdown = false;
                    setChanged();
                    notifyObservers();
                    countdownTime = 3;
                    countdownTimer.cancel();
                }
            }
        };
        countdownTimer.scheduleAtFixedRate(countdownTimerTask, 0, 1000);
    }

    public void startTime() {
        Timer clockTimer = new Timer();
        TimerTask clockTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (clockTime >= 1) {
                    setChanged();
                    notifyObservers();
                    clockTime--;
                } else {
                    setChanged();
                    notifyObservers();
                    clockTime = 10;
                    clockTimer.cancel();
                }
            }
        };
        clockTimer.scheduleAtFixedRate(clockTimerTask, 0, 1000);
    }

    public void setDisplayCountdownTrue() {
        displayCountdown = true;
    }
}