

import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {
    GameModel gamemodel;
    JLabel scoreLabel;
    JLabel comboLabel;
    JLabel accLabel;
    JLabel hitLabel;
    JLabel missLabel;
    JLabel gamemodeLabel;
    JButton returnToGamemodes;

    public StatPanel(GameModel gameModel) {
        this.gamemodel = gameModel;
        this.setLayout(new GridLayout(7, 1));

        Color DARK_BLUE = new Color(0, 0, 153);
        Color LIGHT_BLUE = new Color(102, 178, 255);

        scoreLabel = new JLabel("Your score is: ");
        scoreLabel.setForeground(LIGHT_BLUE);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font(null, Font.BOLD, 50));

        comboLabel = new JLabel(("Your combo is: "));
        comboLabel.setForeground(LIGHT_BLUE);
        comboLabel.setHorizontalAlignment(JLabel.CENTER);
        comboLabel.setFont(new Font(null, Font.BOLD, 50));

        accLabel = new JLabel(("Your accuracy is: "));
        accLabel.setForeground(LIGHT_BLUE);
        accLabel.setHorizontalAlignment(JLabel.CENTER);
        accLabel.setFont(new Font(null, Font.BOLD, 50));

        hitLabel = new JLabel(("Number of hits: "));
        hitLabel.setForeground(LIGHT_BLUE);
        hitLabel.setHorizontalAlignment(JLabel.CENTER);
        hitLabel.setFont(new Font(null, Font.BOLD, 50));

        missLabel = new JLabel(("Number of misses: "));
        missLabel.setForeground(LIGHT_BLUE);
        missLabel.setHorizontalAlignment(JLabel.CENTER);
        missLabel.setFont(new Font(null, Font.BOLD, 50));

        gamemodeLabel = new JLabel(("Gamemode: "));
        gamemodeLabel.setForeground(LIGHT_BLUE);
        gamemodeLabel.setHorizontalAlignment(JLabel.CENTER);
        gamemodeLabel.setFont(new Font(null, Font.BOLD, 50));

        returnToGamemodes = new JButton("Return to gamemodes");
        returnToGamemodes.setBackground(LIGHT_BLUE);
        returnToGamemodes.setVerticalAlignment(JButton.CENTER);
        returnToGamemodes.setForeground(DARK_BLUE);
        returnToGamemodes.setFont(new Font(null, Font.BOLD, 50));

        this.add(scoreLabel);
        this.add(comboLabel);
        this.add(accLabel);
        this.add(hitLabel);
        this.add(missLabel);
        this.add(gamemodeLabel);
        this.add(returnToGamemodes);

        this.setBackground(DARK_BLUE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setSize(screenSize);
        this.setVisible(false);
    }

    public JButton getReturnToGamemodesButton() {
        return returnToGamemodes;
    }

    public void setScoreLabel(String s) {
        scoreLabel.setText("Your score is: " + s);
    }

    public void setComboLabel(String s) {
        comboLabel.setText("Your highest combo is " + s);
    }

    public void setAccLabel(String s) {
        accLabel.setText("Your accuracy is " + s + "%");
    }

    public void setHitLabel(String s) {
        hitLabel.setText("Number of hits: " + s);
    }

    public void setMissLabel(String s) {
        missLabel.setText("Number of misses: " + s);
    }

    public void setGamemodeLabel(String s) {
        gamemodeLabel.setText("Gamemode: " + s);
    }
}