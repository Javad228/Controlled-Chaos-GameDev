package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame window;
    private GamePanel gamePanel;
    private JPanel settingsPage;

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
        settingsPage = new JPanel();

        JLabel frameRateLabel = new JLabel("Frame Rate: ");
        frameRateLabel.setVisible(true);

        Integer[] frameRateChoices = {24, 30, 50, 60, 120};
        final JComboBox<Integer> dropDown = new JComboBox<Integer>(frameRateChoices);
        dropDown.setVisible(true);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer newFrameRate = (Integer) dropDown.getSelectedItem();
                if (newFrameRate != null) {
                    gamePanel.setFps(newFrameRate);
                } else {
                    System.out.println("Frame rate update failed; fps input is null");
                }
            }
        });

        settingsPage.setName("Settings");
        settingsPage.setBackground(Color.white);
        settingsPage.add(frameRateLabel);
        settingsPage.add(dropDown);
        settingsPage.add(applyButton);
        settingsPage.setVisible(true);

        //temporary window for testing settings
        JFrame tempWindow = new JFrame();
        tempWindow.setSize(400, 400);
        tempWindow.getContentPane().setSize(400, 400);
        tempWindow.add(settingsPage);
        tempWindow.setVisible(true);

        window.setVisible(true);
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
