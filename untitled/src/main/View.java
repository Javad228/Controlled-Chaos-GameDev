package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private SettingsPanel settingsPanel;
    private JScrollPane tutorialPanel;
    private JPanel savePage;
    private JPanel coinPanel;
    private JPanel mainMenuPanel;
    private JScrollPane statsPanel;
    private JScrollPane nameDescPanel;
    private JButton settingsButton;
    private JPanel changeSkinPanel;

    public JPanel getBugReportPanel() {
        return bugReportPanel;
    }

    public void setBugReportPanel(JPanel bugReportPanel) {
        this.bugReportPanel = bugReportPanel;
    }

    private JPanel bugReportPanel;

    public JPanel getPauseMenuPanel() {
        return pauseMenuPanel;
    }

    public void setPauseMenuPanel(JPanel pauseMenuPanel) {
        this.pauseMenuPanel = pauseMenuPanel;
    }

    private JPanel pauseMenuPanel;
    public Boolean showingGamePanel = false;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);    // This close operation will be overridden in
                                                                        // the added window listener
        //window.setLayout(new ScrollPaneLayout());

        coinPanel = new JPanel();
        coinPanel.setOpaque(true);
        coinPanel.setLayout(new FlowLayout());
        LineBorder line = new LineBorder(Color.BLACK, 2, true);
        coinPanel.setBorder(line);
        coinPanel.setVisible(false);

        gamePanel = new GamePanel();
        gamePanel.setVisible(false);
        coinPanel.setBounds(0, 0, 75, 30);

        window.setPreferredSize(new Dimension(gamePanel.screenWidth + 15, gamePanel.screenHeight + 39));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        nameDescPanel = new NameDescPanel(gamePanel).scrollPane;
        statsPanel = new StatsPanel(gamePanel).scrollPane;
        changeSkinPanel = new ChangeSkinPanel(gamePanel);
        tutorialPanel = new TutorialPanel(gamePanel).scrollPane;

        pauseMenuPanel = new PauseMenuPanel(gamePanel);
        bugReportPanel = new BugReportPanel(gamePanel);

        // because we set the room number twice :( we have to set the rooms now rather than in the GamePanel constructor.
        gamePanel.initializeFirstLevel();
        gamePanel.initializeLevelClocks();
        gamePanel.tileM.update();

        window.addWindowListener(new WindowAdapter() {  // Add save functionality when closing the game window
            @Override
            public void windowClosing(WindowEvent e) {
            // If the gamepanel is not in view, simply perform ordinary close operation
            if (getWindow().getContentPane().getComponentZOrder(gamePanel) == -1) {
                super.windowClosing(e);
                System.exit(0);
            }

            // Pause game execution
            gamePanel.pauseThread();

            // Show save prompt
            switch (JOptionPane.showConfirmDialog(null, "Save?", "Controlled Chaos",
                    JOptionPane.YES_NO_CANCEL_OPTION)) {

                case JOptionPane.YES_OPTION -> {
                    // Note: Returns false if save is successful
                    if (gamePanel.saveData.saveGameState())
                        JOptionPane.showMessageDialog(null, "Save Failed!\nExiting game!",
                                "Controlled Chaos", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Game Saved Successfully!\nExiting game",
                                "Controlled Chaos", JOptionPane.INFORMATION_MESSAGE);
                    super.windowClosing(e);
                    System.exit(0);
                }

                case JOptionPane.NO_OPTION -> {
                    super.windowClosing(e);
                    System.exit(0);
                }

                case JOptionPane.CANCEL_OPTION -> gamePanel.resumeThread();
            }
            }
        });

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

    public void showTutorialPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getTutorialPanel());
        Main.view.getTutorialPanel().setVisible(true);
        Main.view.getTutorialPanel().revalidate();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void showChangeSkinPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getChangeSkinPanel());
        Main.view.getChangeSkinPanel().setVisible(true);
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();

        Audio.stopMusic();
        // TODO: add music?
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
        showingGamePanel = true;
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getCoinPanel());
        Main.view.getWindow().add(Main.view.getGamePanel());
        Main.view.getGamePanel().setVisible(true);
        Main.view.getCoinPanel().setVisible(true);
        Main.view.getWindow().requestFocusInWindow();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void showPausePanel() {
        showingGamePanel = false;
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getPauseMenuPanel());
        Main.view.getPauseMenuPanel().setVisible(true);
        Main.view.getPauseMenuPanel().revalidate();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void showBugReportPanel() {
        showingGamePanel = false;
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getBugReportPanel());
        Main.view.getBugReportPanel().setVisible(true);
        Main.view.getBugReportPanel().revalidate();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }
    public void showStatsPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getStatsPanel());
        Main.view.getStatsPanel().setVisible(true);
        Main.view.getStatsPanel().revalidate();
        Main.view.getWindow().revalidate();
        Main.view.getWindow().repaint();
    }

    public void showNameDescPanel() {
        Main.view.getWindow().getContentPane().removeAll();
        Main.view.getWindow().add(Main.view.getNameDescPanel());
        Main.view.getNameDescPanel().setVisible(true);
        Main.view.getNameDescPanel().revalidate();
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

    public JScrollPane getTutorialPanel() {
        return tutorialPanel;
    }

    public void showPanel(JPanel panel) {
        getGamePanel().pauseThread();
        getWindow().remove(getGamePanel());
        getWindow().add(panel);
        getWindow().setVisible(false);
        panel.setVisible(true);
        getWindow().setVisible(true);
    }

    /**
     * hidePanel() - Method to hide desired component that is inside the View.java Window
     * If the component does not exist within the View container, this method does nothing.
     * This method serves to reduce instances of redundant code between related JPanels with
     * the View and GamePanel classes
     *
     * @param panel Panel to hide in the View
     */
    public void hidePanel(JPanel panel) {
        // If component is not a part of this window,
        // return without performing any action
        if (this.getWindow().getContentPane().getComponentZOrder(panel) == -1)   return;

        // If the gamePanel is null or if the inputted panel is already hidden,
        // return without performing any action
        if (gamePanel == null || !panel.isVisible())    return;

        this.getWindow().setVisible(false);
        panel.setVisible(false);
        this.getWindow().remove(panel);
        this.getWindow().add(gamePanel);
        this.getWindow().toFront();
        this.getWindow().setAutoRequestFocus(true);
        this.getWindow().setVisible(true);
        this.getWindow().requestFocusInWindow();
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

    public JScrollPane getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JScrollPane statsPanel) {
        this.statsPanel = statsPanel;
    }

    public JScrollPane getNameDescPanel() {
        return nameDescPanel;
    }

    public void setNameDescPanel(JScrollPane nameDescPanel) {
        this.nameDescPanel = nameDescPanel;
    }

    public JPanel getChangeSkinPanel() {
        return changeSkinPanel;
    }

    public void setChangeSkinPanel(JPanel changeSkinPanel) {
        this.changeSkinPanel = changeSkinPanel;
    }
}
