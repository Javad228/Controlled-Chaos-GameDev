package main;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

/**
 * GameStatisticsPanel - A class which displays the current game statistics the player has accomplished
 *
 * @author Cameron Hofbauer
 * @version November 1, 2022
 */
public class GameStatisticsPanel extends JPanel {

    public JButton openStatisticsPageButton;
    public JButton closeStatisticsPageButton;
    public JTextArea gameTimeElapsed;
    public JScrollPane mainBody;

    public GameStatisticsPanel(GamePanel gp) {
        this.openStatisticsPageButton = new JButton();
        this.closeStatisticsPageButton = new JButton();
        this.gameTimeElapsed = new JTextArea("<Empty>");
        this.mainBody = new JScrollPane();
        this.setLayout(new BorderLayout());

        initializeStatisticsPanel(gp);
    }

    private void initializeStatisticsPanel(GamePanel gp) {
        this.openStatisticsPageButton.addActionListener((a) -> {
            showStatisticsPanel();
            this.gameTimeElapsed.setText(getFormattedTime(gp.getCurrentRunTime()));
        });
        this.closeStatisticsPageButton.addActionListener((a) -> {
            hideStatisticsPanel();
        });
    }

    public void showStatisticsPanel() {

    }

    public void hideStatisticsPanel() {
        Main.view.hidePanel(this);
    }

    /**
     * getFormattedTime() - Return a formatted string in the format of "s.ms"
     *
     * @param t Current Time elapsed in this game run
     * @return Formatted String which contains the current game run time in "s.ms"
     */
    public String getFormattedTime(Time t) {
        return t.getTime() / 1000000000 + "." + (t.getTime() / 1000000) % 1000;
    }
}
