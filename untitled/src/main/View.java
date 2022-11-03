package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private SettingsPanel settingsPanel;
    private JPanel savePage;
    private JPanel coinPanel;
    private JPanel mainMenuPanel;
    private JPanel statsPanel;
    private JButton settingsButton;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(800, 600));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        coinPanel = new JPanel();
        coinPanel.setOpaque(true);
        coinPanel.setLayout(new FlowLayout());
        LineBorder line = new LineBorder(Color.BLACK, 2, true);
        coinPanel.setBorder(line);
        coinPanel.setVisible(false);

        statsPanel = new statsPanel();

        gamePanel = new GamePanel();
        gamePanel.setVisible(false);
        coinPanel.setBounds(0, 0, 75, 30);

        window.addKeyListener(gamePanel.keyH);
        window.pack();
        window.setLocationRelativeTo(null);

        //set up settings page
        settingsPanel = new SettingsPanel(gamePanel);

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
        /*
        settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(50, 25));
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsPanel("Game Panel");
                Main.view.getGamePanel().pauseThread();
            }
        });
        settingsButton.setVisible(false);
         */

        mainMenuPanel = new MainMenuPanel();
        window.add(mainMenuPanel);

        window.setVisible(true);                                    // currently opens up the main menu
    }

    public void showSettingsPanel(String priorPage) {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getSettingsPanel());
        Main.view.getSettingsPanel().setVisible(true);
        Main.view.getSettingsPanel().setPriorPage(priorPage);
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();

        Audio.stopMusic();
        Audio.settingsMusic();
    }

    public void showMainMenuPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getMainMenuPanel());
        Main.view.getMainMenuPanel().setVisible(true);
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();

        Audio.stopMusic();
        // TODO: add main menu music?
    }

    public void showGamePanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getCoinPanel());
        Main.view.getWindow().add(Main.view.getGamePanel());
        Main.view.getGamePanel().setVisible(true);
        Main.view.getCoinPanel().setVisible(true);
        Main.view.getWindow().requestFocusInWindow();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void showStatsPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getStatsPanel());
        Main.view.getStatsPanel().setVisible(true);
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void updateCoinLabel(Graphics2D g2) {
        int numCoins = gamePanel.player.getNumCoins();

        JLabel numCoinsLabel = new JLabel(Integer.toString(numCoins));
        JLabel coinIconLabel = new JLabel();

        try {
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/items/coin_icon.png"));
            coinIconLabel.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        coinPanel.removeAll();
        coinIconLabel.setBounds(6, 6, 23,18);
        numCoinsLabel.setBounds(35, 2, 50,25);
        coinPanel.add(numCoinsLabel);
        coinPanel.add(coinIconLabel);
        coinPanel.update(g2);
    }

    public JFrame getWindow() {
        return window;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public JPanel getCoinPanel() {
        return coinPanel;
    }

    public void setCoinPanel(JPanel coinPanel) {
        this.coinPanel = coinPanel;
    }

    public JPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public void setMainMenuPanel(JPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(JButton settingsButton) {
        this.settingsButton = settingsButton;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JPanel statsPanel) {
        this.statsPanel = statsPanel;
    }
}
