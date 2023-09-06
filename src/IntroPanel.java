import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {
    private JButton gridShotButton = new JButton("GridShot");
    private JButton spiderShotButton = new JButton("SpiderShot");
    private JButton randomShotButton = new JButton("RandomShot");
    private JButton highscoreButton = new JButton("Highscore");

    public IntroPanel(){
        Color DARK_BLUE = new Color(0, 0, 153);
        Color LIGHT_BLUE = new Color(102, 178, 255);

        JLabel introLabel = new JLabel("Choose Your Gamemode");
        introLabel.setForeground(LIGHT_BLUE);
        introLabel.setLayout(null);
        introLabel.setHorizontalAlignment(JLabel.CENTER);
        introLabel.setFont(new Font(null, Font.BOLD, 60));
        introLabel.setBounds(370, 100, 770, 130);
        introLabel.setBackground(DARK_BLUE);

        this.add(introLabel);

        gridShotButton.setHorizontalAlignment(JButton.CENTER);
        gridShotButton.setLayout(null);
        gridShotButton.setFont(new Font(null, Font.BOLD, 40));
        gridShotButton.setBounds(600, 300, 320, 100);
        gridShotButton.setBackground(LIGHT_BLUE);

        randomShotButton.setHorizontalAlignment(JButton.CENTER);
        randomShotButton.setLayout(null);
        randomShotButton.setFont(new Font(null, Font.BOLD, 40));
        randomShotButton.setBounds(600, 440, 320, 100);
        randomShotButton.setBackground(LIGHT_BLUE);

        spiderShotButton.setHorizontalAlignment(JButton.CENTER);
        spiderShotButton.setLayout(null);
        spiderShotButton.setFont(new Font(null, Font.BOLD, 40));
        spiderShotButton.setBounds(600, 580, 320, 100);
        spiderShotButton.setBackground(LIGHT_BLUE);

        highscoreButton.setHorizontalAlignment(JButton.CENTER);
        highscoreButton.setLayout(null);
        highscoreButton.setFont(new Font(null, Font.BOLD, 40));
        highscoreButton.setBounds(600, 720, 320, 100);
        highscoreButton.setBackground(LIGHT_BLUE);

        this.add(gridShotButton);
        this.add(spiderShotButton);
        this.add(randomShotButton);
        this.add(highscoreButton);

        this.setBackground(DARK_BLUE);
        this.setLayout(null);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize);
        this.setVisible(true);
    }

    public JButton getGridShotButton() {
        return gridShotButton;
    }

    public JButton getSpiderShotButton() {
        return spiderShotButton;
    }

    public JButton getRandomShotButton() {
        return randomShotButton;
    }

    public JButton getHighscoreButton() {
        return highscoreButton;
    }
}