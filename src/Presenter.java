

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Presenter extends JFrame implements ActionListener {
    private GameModel gameModel;
    private ClockModel clockModel;
    private JLayeredPane layeredPane;
    private final String RANDOM_SHOT = "RandomShot";
    private final String GRID_SHOT = "GridShot";
    private final String SPIDER_SHOT = "SpiderShot";


    public Presenter(GameModel gameModel,ClockModel clockModel, View view) {
        this.gameModel = gameModel;
        this.clockModel = clockModel;
        File file = new File("highscore.ser");

        if (!file.exists()) {
            try (FileOutputStream fileOut = new FileOutputStream("highscore.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(new Highscore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gameModel.setFile();

        populateFrame(view);
    }

    private void populateFrame(View view) {
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize);
        this.setResizable(false);
        this.setVisible(true);

        IntroPanel introPanel = new IntroPanel();
        StatPanel statPanel = new StatPanel(gameModel);

        layeredPane = new JLayeredPane();
        layeredPane.setSize(screenSize);
        layeredPane.add(view, Integer.valueOf(0));
        layeredPane.add(introPanel, Integer.valueOf(1));
        layeredPane.add(statPanel, Integer.valueOf(2));
        createActionListeners();

        this.add(layeredPane);
        this.revalidate();
    }

    private void createActionListeners() {
        ((IntroPanel) layeredPane.getComponent(1)).getGridShotButton().addActionListener(this);
        ((IntroPanel) layeredPane.getComponent(1)).getSpiderShotButton().addActionListener(this);
        ((IntroPanel) layeredPane.getComponent(1)).getRandomShotButton().addActionListener(this);
        ((IntroPanel) layeredPane.getComponent(1)).getHighscoreButton().addActionListener(this);
        ((StatPanel) layeredPane.getComponent(0)).getReturnToGamemodesButton().addActionListener(this);
    }

    public void deleteCircle(int index) {
        gameModel.deleteCircle(index);
        gameModel.createCircle(index);
    }

    public void incrementCombo() {
        gameModel.incrementCombo();
    }

    public void resetCombo() {
        gameModel.resetCombo();
    }

    public void updateScore() {
        gameModel.updateScore();
    }

    public void showStats() {
        layeredPane.getComponent(0).setVisible(true);
    }

    public void resetModel() {
        ((StatPanel) layeredPane.getComponent(0)).setScoreLabel(gameModel.getScore() + "");
        ((StatPanel) layeredPane.getComponent(0)).setComboLabel(gameModel.getHighestCombo() + "");
        ((StatPanel) layeredPane.getComponent(0)).setAccLabel(gameModel.getAccuracy() + "");
        ((StatPanel) layeredPane.getComponent(0)).setHitLabel(gameModel.getCircleCount() + "");
        ((StatPanel) layeredPane.getComponent(0)).setMissLabel(gameModel.getMisses() + "");
        ((StatPanel) layeredPane.getComponent(0)).setGamemodeLabel(gameModel.getGamemode() + "");
        gameModel.createNewGame();
        clockModel.setDisplayCountdownTrue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(GRID_SHOT)) {
            layeredPane.getComponent(1).setVisible(false);
            clockModel.countdown();

            java.util.Timer clockTimer = new Timer();
            TimerTask clockTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (clockModel.getCountdownTime() == 0) {
                        clockTimer.cancel();
                        gameModel.setGamemode(GRID_SHOT);
                        gameModel.startGridShot();
                        clockModel.startTime();
                    }
                }
            };
            clockTimer.scheduleAtFixedRate(clockTimerTask, 0, 1000);
            return;
        }

        if (action.equals("SpiderShot")) {
            layeredPane.getComponent(1).setVisible(false);
            clockModel.countdown();

            java.util.Timer clockTimer = new Timer();
            TimerTask clockTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (clockModel.getCountdownTime() == 0) {
                        clockTimer.cancel();
                        gameModel.setGamemode(SPIDER_SHOT);
                        gameModel.startSpiderShot();
                        clockModel.startTime();
                    }
                }
            };
            clockTimer.scheduleAtFixedRate(clockTimerTask, 0, 1000);
            return;
        }

        if (action.equals(RANDOM_SHOT)) {
            layeredPane.getComponent(1).setVisible(false);
            clockModel.countdown();

            java.util.Timer clockTimer = new Timer();
            TimerTask clockTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (clockModel.getCountdownTime() == 0) {
                        clockTimer.cancel();
                        gameModel.setGamemode(RANDOM_SHOT);
                        gameModel.startRandomShot();
                        clockModel.startTime();
                    }
                }
            };
            clockTimer.scheduleAtFixedRate(clockTimerTask, 0, 1000);
            return;
        }

        if (action.equals("Highscore")) {
            layeredPane.getComponent(0).setVisible(true);

            try (FileInputStream fileIn = new FileInputStream("highscore.ser");
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

                Highscore highscore = (Highscore) in.readObject();
                ((StatPanel) layeredPane.getComponent(0)).setScoreLabel(highscore.getScore() + "");
                ((StatPanel) layeredPane.getComponent(0)).setComboLabel(highscore.getCombo() + "");
                ((StatPanel) layeredPane.getComponent(0)).setAccLabel(highscore.getAcc() + "");
                ((StatPanel) layeredPane.getComponent(0)).setHitLabel(highscore.getHits() + "");
                ((StatPanel) layeredPane.getComponent(0)).setMissLabel(highscore.getMisses() + "");
                ((StatPanel) layeredPane.getComponent(0)).setGamemodeLabel(highscore.getGamemode() + "");

            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
            return;
        }

        if (action.equals("Return to gamemodes")) {
            layeredPane.getComponent(0).setVisible(false);
            layeredPane.getComponent(1).setVisible(true);
        }
    }
}