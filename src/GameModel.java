/**
 * Class that models a game
 *
 * @author Tony Situ
 * @version July 7th, 2023
 */

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class GameModel extends Observable {
    private ArrayList<Circle> circleList;
    private ArrayList<Circle> gridShotCoords;
    private ArrayList<Circle> spiderShotCoords;
    private int highscore;
    private int circleCount;
    private int combo;
    private int highestCombo;
    private int score;
    private int misses;
    private boolean displayCurrentScore;
    private boolean gameStart;
    private String gamemode;
    private Circle prevCircle;
    private Circle prevPrevCircle;
    private final String RANDOM_SHOT = "RandomShot";
    private final String GRID_SHOT = "GridShot";
    private final String SPIDER_SHOT = "SpiderShot";

    public GameModel() {
        createNewGame();
    }

    public void createNewGame() {
        circleCount = 0;
        combo = 0;
        highestCombo = 0;
        score = 0;
        misses = 0;
        displayCurrentScore = false;
        gameStart = false;
        circleList = new ArrayList<Circle>();
        gridShotCoords = new ArrayList<Circle>();
        spiderShotCoords = new ArrayList<Circle>();

        initCoords();
        setChanged();
        notifyObservers();
    }

    public ArrayList<Circle> getCircleList() {
        return circleList;
    }

    public boolean getGameStart() {
        return gameStart;
    }

    public void setGamemode(String s) {
        gamemode = s;
    }

    public String getGamemode() {
        return gamemode;
    }

    public int getCombo() {
        return combo;
    }

    public int getScore() {
        return score;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public int getMisses(){
        return misses;
    }

    public int getHighestCombo() {
        return highestCombo;
    }

    public void setFile() {
        try (FileInputStream fileIn = new FileInputStream("highscore.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Highscore highscore = (Highscore) in.readObject();
            this.highscore = highscore.getScore();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRandomShot() {
        gameStart = true;
        for (int i = 0; i < 5; i++) {
            createCircle(i);
        }
    }

    public void startGridShot() {
        gameStart = true;
        for (int i = 0; i < 3; i++) {
            createCircle(i);
        }
    }

    public void startSpiderShot() {
        gameStart = true;
        createCircle(0);
    }

    public void createCircle(int index) {
        if (gamemode.equals(RANDOM_SHOT)) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            int width = (int) tk.getScreenSize().getWidth();
            int height = (int) tk.getScreenSize().getHeight();

            Circle circle = new Circle((int) (Math.random() * (width - 200) + 100), (int) (Math.random() * (height - 200) + 50));
            while (doesOverlap(circle)) {
                circle = new Circle((int) (Math.random() * (400)), (int) (Math.random() * (400 - 80)) + 80);
            }
            circleList.add(index, circle);
            setChanged();
            notifyObservers();
            return;
        }

        if (gamemode.equals(GRID_SHOT)) {
            Circle circle = gridShotCoords.get((int) (Math.random() * gridShotCoords.size()));
            while (doesOverlap(circle) || prevCircle != null && circle.getx() == prevCircle.getx()
                    && circle.gety() == prevCircle.gety()) {
                circle = gridShotCoords.get((int) (Math.random() * gridShotCoords.size()));
            }
            circleList.add(index, circle);
            setChanged();
            notifyObservers();
            return;
        }

        if (gamemode.equals(SPIDER_SHOT)) {
            Circle circle;
            if (circleCount%2 == 0) {
                circle = new Circle(700, 450);
            } else {
                circle = spiderShotCoords.get((int) (Math.random() * spiderShotCoords.size()));
                while (prevPrevCircle != null && circle.getx() == prevPrevCircle.getx()
                        && circle.gety() == prevPrevCircle.gety()) {
                    circle = spiderShotCoords.get((int) (Math.random() * spiderShotCoords.size()));
                }
            }
            circleList.add(index, circle);
            setChanged();
            notifyObservers();
        }
    }

    public void deleteCircle(int index) {
        prevCircle = circleList.get(index);
        // circleCount is every circle that is pressed by the user. the if statement
        // is supposed to add every other circle aside from the first one to prevprevcircle
        // every other circle aside from the first circle would be an even number since
        // circleCount is incremented BEFORE delete circle is called, and as a result
        // circleCount would be even when an odd circle is clicked and this function is called.
        if (circleCount%2 == 0) {
            prevPrevCircle = circleList.get(index);
        }
        circleList.remove(index);
    }

    private boolean doesOverlap(Circle otherCircle) {
        int CIRCLE_SIZE = 100;

        for (Circle circle : circleList) {
            double distance = Math.sqrt(Math.pow(circle.getx() - otherCircle.getx(), 2)
                    + Math.pow(circle.gety() - otherCircle.gety(), 2));

            if (distance <= CIRCLE_SIZE) {
                return true;
            }
        }
        return false;
    }

    private void initCoords() {
        gridShotCoords.add(new Circle(700, 250));
        gridShotCoords.add(new Circle(700, 450));
        gridShotCoords.add(new Circle(700, 650));
        gridShotCoords.add(new Circle(900, 250));
        gridShotCoords.add(new Circle(900, 450));
        gridShotCoords.add(new Circle(900, 650));
        gridShotCoords.add(new Circle(500, 250));
        gridShotCoords.add(new Circle(500, 450));
        gridShotCoords.add(new Circle(500, 650));

        spiderShotCoords.add(new Circle(300, 450));
        spiderShotCoords.add(new Circle(354, 250));
        spiderShotCoords.add(new Circle(354, 650));
        spiderShotCoords.add(new Circle(1100, 450));
        spiderShotCoords.add(new Circle(1046, 250));
        spiderShotCoords.add(new Circle(1084, 650));
    }

    public void incrementCombo() {
        combo += 1;
        if (combo > highestCombo) {
            highestCombo = combo;
        }
        circleCount += 1;
    }

    public void resetCombo() {
        combo = 0;
        misses += 1;
        setChanged();
        notifyObservers();
    }

    public void updateScore() {
        double multiplier = combo*Math.pow(10, -2) + 1;
        double actualScore = multiplier*100;
        score += (int) actualScore;

        if (score > highscore) {
            Highscore highscore = new Highscore(score, getHighestCombo(), getAccuracy(), getCircleCount(), getMisses(), getGamemode());
            try (FileOutputStream fileOut = new FileOutputStream("highscore.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

                out.writeObject(highscore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAccuracy() {
        double totalClicks = circleCount + misses;
        double acc = 0;
        if (totalClicks > 0) {
            acc = (circleCount / totalClicks) * 100;
        }
        return (int) acc;
    }
}