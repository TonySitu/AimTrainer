import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View extends JPanel implements Observer {
    private JLabel countdownLabel;
    private JLabel clockLabel;
    private JLabel scoreLabel;
    private JLabel comboLabel;
    private Presenter presenter;
    private ArrayList<JLabel> circleList;
    private final ImageIcon circleIcon = new ImageIcon("Images/circle.png");

    public View() {
        circleList = new ArrayList<JLabel>();
        Color DARK_BLUE = new Color(0, 0, 153);

        Color LIGHT_BLUE = new Color(102, 178, 255);

        countdownLabel = new JLabel();
        countdownLabel.setForeground(LIGHT_BLUE);
        countdownLabel.setLayout(null);
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        countdownLabel.setFont(new Font(null, Font.BOLD, 100));
        countdownLabel.setBounds(490, 400, 480, 100);
        countdownLabel.setVisible(false);

        clockLabel = new JLabel();
        clockLabel.setForeground(LIGHT_BLUE);
        clockLabel.setLayout(null);
        clockLabel.setHorizontalAlignment(JLabel.CENTER);
        clockLabel.setFont(new Font(null, Font.BOLD, 50));
        clockLabel.setBounds(450, 0, 210, 40);
        clockLabel.setVisible(false);

        comboLabel = new JLabel();
        comboLabel.setForeground(LIGHT_BLUE);
        comboLabel.setLayout(null);
        comboLabel.setHorizontalAlignment(JLabel.LEFT);
        comboLabel.setFont(new Font(null, Font.BOLD, 100));
        comboLabel.setBounds(0, 890, 600, 80);
        comboLabel.setVisible(false);

        scoreLabel = new JLabel();
        scoreLabel.setForeground(LIGHT_BLUE);
        scoreLabel.setLayout(null);
        scoreLabel.setHorizontalAlignment(JLabel.LEFT);
        scoreLabel.setFont(new Font(null, Font.BOLD, 50));
        scoreLabel.setBounds(800, 0, 700, 40);
        scoreLabel.setVisible(false);

        this.add(countdownLabel);
        this.add(clockLabel);
        this.add(comboLabel);
        this.add(scoreLabel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenter.resetCombo();
            }
        });

        this.setBackground(DARK_BLUE);
        this.setLayout(null);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize);
        this.setVisible(true);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    private JLabel createCircle(GameModel gameModel, int i) {
        JLabel circle = new JLabel();
        circle.setLayout(null);
        ImageIcon circleImage = new ImageIcon(circleIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        circle.setIcon(circleImage);
        circle.setBounds(gameModel.getCircleList().get(i).getx(), gameModel.getCircleList().get(i).gety(), 100, 100);
        return circle;
    }

    private void drawCircles(GameModel gameModel) {
        if (!circleList.isEmpty()) {
            for (int i = 0; i < circleList.size(); i++) {
                this.remove(circleList.get(i));
            }
        }
        circleList.clear();
        for (int i = 0; i < gameModel.getCircleList().size(); i++) {
            JLabel circle = createCircle(gameModel, i);
            circleList.add(i, circle);
            circle.putClientProperty("index", i);
            circle.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel clickedLabel = (JLabel) e.getSource();
                    if ((int) clockLabel.getClientProperty("time") >= 1) {
                        presenter.incrementCombo();
                        presenter.deleteCircle((int) clickedLabel.getClientProperty("index"));
                        presenter.updateScore();
                        comboLabel.setText(gameModel.getCombo() + "x");
                        scoreLabel.setText("Score: " + gameModel.getScore());
                    }
                }
            });
            this.add(circleList.get(i));
        }
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof ClockModel clockModel) {
            if (!clockModel.getDisplayCountdown()) {
                countdownLabel.setVisible(false);
                clockLabel.setVisible(true);
                scoreLabel.setVisible(true);
                comboLabel.setVisible(true);
                clockLabel.setText("Time: " + clockModel.getClockTime());
                clockLabel.putClientProperty("time", clockModel.getClockTime());
                if (clockModel.getClockTime() == 0) {
                    try {
                        Thread.sleep(1500);
                        clockLabel.setVisible(false);
                        scoreLabel.setVisible(false);
                        comboLabel.setVisible(false);
                        presenter.resetModel();
                        presenter.showStats();
                        View.this.drawCircles(new GameModel());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (clockModel.getDisplayCountdown()) {
                countdownLabel.setVisible(true);
                countdownLabel.setText("Start in " + clockModel.getCountdownTime());
            }
        }

        if (obs instanceof GameModel gameModel) {
            if (gameModel.getGameStart()) {
                comboLabel.setText(gameModel.getCombo() + "x");
                scoreLabel.setText("Score: " + gameModel.getScore());

                if (!gameModel.getCircleList().isEmpty()) {
                    drawCircles(gameModel);
                    this.repaint();
                }
            }
        }
    }
}