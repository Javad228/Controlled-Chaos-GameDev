package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    public MainMenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(25));
        addTitle();
        add(Box.createVerticalStrut(50));
        addNewRunButton();
        add(Box.createVerticalStrut(10));
        addSettingsButton();

        setName("Main Menu");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("CONTROLLED CHAOS");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addNewRunButton() {
        JButton newRunButton = new JButton("New Run");
        newRunButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        newRunButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showGamePanel();
                Main.view.getGamePanel().startGameThread();
            }
        });
        add(newRunButton);
    }

    private void addSettingsButton() {
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showSettingsPanel("Main Menu");
            }
        });
        add(settingsButton);
    }

}
