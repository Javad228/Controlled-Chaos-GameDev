package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private JPanel settingsPage;
    private JPanel savePage;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);

        //set up settings page
        settingsPage = new SettingsPanel(gamePanel);

        // Add Save Page
        savePage = new JPanel();
        savePage.setLayout(new BorderLayout());
        savePage.add(gamePanel.saveData.saveGameButton, BorderLayout.WEST);
        savePage.add(gamePanel.saveData.restoreGameButton, BorderLayout.EAST);

        //temporary window for testing settings
        JFrame tempWindow = new JFrame();
        tempWindow.setSize(400, 400);
        tempWindow.getContentPane().setSize(400, 400);
        tempWindow.setLayout(new BorderLayout());
        tempWindow.add(settingsPage, BorderLayout.NORTH);
        tempWindow.add(savePage, BorderLayout.SOUTH);
        tempWindow.setVisible(true);

        window.setVisible(true);                                    // currently opens up the game window
    }

    public JFrame getWindow() {
        return window;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public JPanel getSettingsPage() {
        return settingsPage;
    }

    public static void updateFrameRate() {
    }

}
