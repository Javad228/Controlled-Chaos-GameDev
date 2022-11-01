package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private SettingsPanel settingsPage;
    private JPanel savePage;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setLayout(new ScrollPaneLayout());
        window.setPreferredSize(new Dimension(800, 600));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.addKeyListener(gamePanel.keyH);

        window.addWindowListener(new WindowAdapter() {  // Add save functionality when closing the game window
            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.pauseThread();

                if (JOptionPane.showConfirmDialog(null, "Save?", "Controlled Chaos",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    // Note: Returns false if save is successful
                    if (gamePanel.saveData.saveGameState())
                        JOptionPane.showMessageDialog(null, "Save Failed!\nExiting game!",
                                "Controlled Chaos", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Game Saved Successfully!\nExiting game",
                            "Controlled Chaos", JOptionPane.INFORMATION_MESSAGE);
                }

                super.windowClosing(e);
            }
        });

        window.pack();

        window.setLocationRelativeTo(null);

        //set up settings page
        settingsPage = new SettingsPanel(gamePanel);
        //settingsPage.requestFocusInWindow();
        //settingsPage.addKeyListener(gamePanel.keyH);
        window.add(settingsPage);


        // Add Save Page
        //savePage = new JPanel();
        //savePage.setLayout(new BorderLayout());
        //savePage.add(gamePanel.saveData.saveGameButton, BorderLayout.WEST);
        //savePage.add(gamePanel.saveData.restoreGameButton, BorderLayout.CENTER);
        //savePage.add(gamePanel.saveData.resetGameProgressButton, BorderLayout.EAST);


        //temporary window for testing settings
        /*
        JFrame tempWindow = new JFrame();
        tempWindow.setSize(400, 400);
        tempWindow.getContentPane().setSize(400, 400);
        tempWindow.setLayout(new BorderLayout());
        tempWindow.add(settingsPage, BorderLayout.NORTH);
        tempWindow.add(savePage, BorderLayout.SOUTH);
        tempWindow.setVisible(true);
         */
        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(50, 25));

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.getSettingsPage().setVisible(true);
                Main.view.getGamePanel().setVisible(false);

                Main.view.getGamePanel().pauseThread();

                Audio.stopMusic();
                Audio.settingsMusic();
            }
        });
        window.add(settingsButton, BorderLayout.SOUTH);

        window.setVisible(true);                                    // currently opens up the game window
    }

    public JFrame getWindow() {
        return window;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public SettingsPanel getSettingsPage() {
        return settingsPage;
    }

}
