package main;

import character.PlayerCharacter;
import loot.Boot;
import loot.RapidFire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class PauseMenuPanel extends JPanel {
    GamePanel gp;
    public PauseMenuPanel(GamePanel gp) {
        this.gp = gp;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(40));
        addTitle();

        add(Box.createVerticalStrut(10));
        addSettingsButton();

        add(Box.createVerticalStrut(300));
        addBackButton();

        setName("Pause Menu");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Menu");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }


    private void addSettingsButton() {
        JButton settingsButton = new JButton("Bug Report");
        settingsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.togglePause = true;
                Main.view.showBugReportPanel();

            }
        });
        add(settingsButton);
    }

    private void addBackButton() {
        JButton settingsButton = new JButton("Exit");
        settingsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.togglePause = true;
                gp.resumeThread();
                Main.view.showGamePanel();

            }
        });
        add(settingsButton);
    }

}
