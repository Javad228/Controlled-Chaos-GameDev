package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private SettingsPanel settingsPage;
    private JPanel savePage;

    private JLabel coinLabel;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setLayout(new ScrollPaneLayout());
        window.setPreferredSize(new Dimension(800, 600));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        coinLabel = new JLabel();
        coinLabel.setOpaque(true);
        coinLabel.setBackground(Color.BLACK);
        coinLabel.setForeground(Color.WHITE);
        window.add(coinLabel);
        gamePanel = new GamePanel();
        window.add(gamePanel);
        coinLabel.setBounds(0, 0, 100, 25);

        window.addKeyListener(gamePanel.keyH);

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

        // temporary button to get to settings

        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(50, 25));

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.getSettingsPage().setVisible(true);
                Main.view.getGamePanel().setVisible(false);
                Main.view.coinLabel.setVisible(false);

                Main.view.getGamePanel().pauseThread();

                Audio.stopMusic();
                Audio.settingsMusic();
            }
        });

        window.add(settingsButton, BorderLayout.SOUTH);


        window.setVisible(true);                                    // currently opens up the game window
    }

    public void updateCoinLabel(Graphics2D g2) {
        coinLabel.setText(Integer.toString(gamePanel.player.getNumCoins()));
        //Main.view.getCoinLabel().setBounds(this.screenWidth - 100, 0, 100, 25);
        //this.coinLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.coinLabel.update(g2);
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

    public JLabel getCoinLabel() {
        return coinLabel;
    }

    public void setCoinLabel(JLabel coinLabel) {
        this.coinLabel = coinLabel;
    }
}
