import javax.swing.*;
import java.io.Serializable;

public class Highscore implements Serializable {
    private int score;
    private int combo;
    private int acc;
    private int hits;
    private int misses;
    private String gamemode;

    public Highscore() {
        score = 0;
        combo = 0;
        acc = 0;
        hits = 0;
        misses = 0;
        gamemode = "";
    }

    public Highscore(int score, int combo, int acc, int hits, int misses, String gamemode) {
        this.score = score;
        this.combo = combo;
        this.acc = acc;
        this.hits = hits;
        this.misses = misses;
        this.gamemode = gamemode;
    }

    public int getScore() {
        return score;
    }

    public int getCombo() {
        return combo;
    }

    public int getAcc() {
        return acc;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public String getGamemode() {
        return gamemode;
    }
}