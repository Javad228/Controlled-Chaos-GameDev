package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
//TODO: <<<<<<< Cameron-PlayerTime
import java.awt.event.*;
import java.util.ArrayList;
//=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
//>>>>>>> Cameron-Merge-PlayerTime

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private SettingsPanel settingsPage;
    private JPanel savePage;
    private JPanel coinPanel;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);    // This close operation will be overridden in
                                                                        // the added window listener
        //window.setLayout(new ScrollPaneLayout());
        window.setPreferredSize(new Dimension(800, 600));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        coinPanel = new JPanel();
        coinPanel.setOpaque(true);
        coinPanel.setLayout(new FlowLayout());
        LineBorder line = new LineBorder(Color.BLACK, 2, true);
        coinPanel.setBorder(line);
        window.add(coinPanel);

        gamePanel = new GamePanel();
        window.add(gamePanel);
        coinPanel.setBounds(0, 0, 75, 30);


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

//TODO: =======
        window.addKeyListener(gamePanel.keyH);
//>>>>>>> Cameron-Merge-PlayerTime
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
                Main.view.coinPanel.setVisible(false);

                Main.view.getGamePanel().pauseThread();

                Audio.stopMusic();
                Audio.settingsMusic();
            }
        });

        window.add(settingsButton, BorderLayout.SOUTH);


        window.setVisible(true);                                    // currently opens up the game window
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

    public SettingsPanel getSettingsPage() {
        return settingsPage;
    }

    public void showPanel(JPanel panel) {

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
}
