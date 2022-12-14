package main;

import character.PlayerCharacter;
import loot.Boot;
import loot.RapidFire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenuPanel extends JPanel {


    public MainMenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(25));
        addTitle();
        add(Box.createVerticalStrut(50));
        addNewRunButton();
        add(Box.createVerticalStrut(10));
        addContinueRunButton();
        add(Box.createVerticalStrut(10));
        addChangeSkinButton();
        add(Box.createVerticalStrut(10));
        addNameDescButton();
        add(Box.createVerticalStrut(10));
        addStatsButton();
        add(Box.createVerticalStrut(10));
        addSettingsButton();
        add(Box.createVerticalStrut(10));
        addTutorialButton();

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
        JButton newRun = new JButton("New Run");
        newRun.setFont(new Font("Monospaced", Font.PLAIN, 25));
        newRun.setAlignmentX(Component.CENTER_ALIGNMENT);
        newRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showGamePanel();
                Main.view.getGamePanel().newGame(true);
                addItemsPerCharacter();
            }
        });
        add(newRun);

    }

    private void addContinueRunButton() {
        JButton continueRun = new JButton("Continue Run");
        continueRun.setFont(new Font("Monospaced", Font.PLAIN, 25));
        continueRun.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showGamePanel();
                Main.view.getGamePanel().startGameThread();
                addItemsPerCharacter();
            }
        });
        add(continueRun);

    }

    private void addItemsPerCharacter() {
        if(Objects.equals(Main.view.getGamePanel().player.getCharacterAppearance(), "healer")){
            String[] bootImages = {"/items/boot.png"};
            Boot boot = new Boot(bootImages, Main.view.getGamePanel(), 500, 500);
            Main.view.getGamePanel().player.getInventory().addItem(boot);
        }else{
            String [] rapidFireImages = {"/items/rapid-fire.png"};
            RapidFire rapidFire = new RapidFire(rapidFireImages,Main.view.getGamePanel(), 500, 500);
            Main.view.getGamePanel().player.getInventory().addItem(rapidFire);
        }
    }

    private void addChangeSkinButton() {
        JButton newRunButton = new JButton("Change Character");
        newRunButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        newRunButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showChangeSkinPanel();
            }
        });
        add(newRunButton);
    }

    private void addNameDescButton() {
        JButton nameDescButton = new JButton("Change Name and Descriptions");
        nameDescButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        nameDescButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameDescButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showNameDescPanel();
            }
        });
        add(nameDescButton);
    }

    private void addStatsButton() {
        JButton statsButton = new JButton("Stats");
        statsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showStatsPanel();
            }
        });
        add(statsButton);
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

    private void addTutorialButton() {
        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showTutorialPanel();
            }
        });
        add(tutorialButton);
    }
}
