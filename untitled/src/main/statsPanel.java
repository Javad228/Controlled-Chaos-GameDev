package main;

import save.GameSaveState;
import save.SaveData;
import save.SimpleCharacter;
import save.SimpleEnemy;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class statsPanel extends JPanel {
    GameSaveState savedData;
    JPanel basicDetailsPanel = new JPanel();
    JPanel enemiesKilledPanel = new JPanel();
    JTextArea[] enemyDescriptionTextBoxes;

    public statsPanel() {
        savedData = SaveData.restoreGameState("dummy");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        basicDetailsPanel.setLayout(new GridLayout(3, 2)); // number of rows depends on number of fields you'd like to add
        basicDetailsPanel.setBackground(Color.BLACK);
        enemiesKilledPanel.setBackground(Color.BLACK);

        // the below objects are added to the statsPanel
        add(Box.createVerticalStrut(25));
        addTitleLabel();
        add(Box.createVerticalStrut(25));

        // the three sets of labels are added to the basicDetailsPanel before the panel itself is added to the statsPanel
        addBasicDetailsLabel(); // added to statsPanel
        addNameLabels();
        addMaxHealthLabels();
        addNumCoinsLabels();
        add(basicDetailsPanel); // add basicDetailsPanel to statsPanel

        // add enemies killed title, then labels and text boxes to input descriptions
        addEnemiesKilledTitleLabel(); // added to statsPanel
        if (savedData != null) {
            addEnemiesKilled();
            add(enemiesKilledPanel); // add enemiesKilledPanel to statsPanel
        }

        // add button to save descriptions
        addSaveButton();

        setName("Stats");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("STATS");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addBasicDetailsLabel() {
        JLabel titleLabel = new JLabel("Basic Details:");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addNameLabels() {
        JLabel nameCategoryLabel = new JLabel("Player Name:");
        nameCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        nameCategoryLabel.setForeground(Color.WHITE);


        JLabel nameLabel = new JLabel();
        if (savedData != null) {
            nameLabel.setText(savedData.player.name);
        } else {
            nameLabel.setText("");
        }
        nameLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        nameLabel.setForeground(Color.WHITE);

        basicDetailsPanel.add(nameCategoryLabel);
        basicDetailsPanel.add(nameLabel);
    }

    private void addMaxHealthLabels() {
        JLabel maxHealthCategoryLabel = new JLabel("Max Health:");
        maxHealthCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        maxHealthCategoryLabel.setForeground(Color.WHITE);

        JLabel maxHealthLabel = new JLabel();
        if (savedData != null) {
            maxHealthLabel.setText(Integer.toString(savedData.player.maxHealth));
        } else {
            maxHealthLabel.setText("");
        }
        maxHealthLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        maxHealthLabel.setForeground(Color.WHITE);

        basicDetailsPanel.add(maxHealthCategoryLabel);
        basicDetailsPanel.add(maxHealthLabel);
    }

    private void addNumCoinsLabels() {
        JLabel numCoinsCategoryLabel = new JLabel("Coins in Bank:");
        numCoinsCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        numCoinsCategoryLabel.setForeground(Color.WHITE);

        JLabel numCoinsLabel = new JLabel();
        if (savedData != null) {
            numCoinsLabel.setText(Integer.toString(savedData.player.getNumCoins()));
        } else {
            numCoinsLabel.setText("");
        }
        numCoinsLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        numCoinsLabel.setForeground(Color.WHITE);

        basicDetailsPanel.add(numCoinsCategoryLabel);
        basicDetailsPanel.add(numCoinsLabel);
    }

    public void addEnemiesKilledTitleLabel() {
        JLabel enemiesKilledLabel = new JLabel("Enemies Killed:");
        enemiesKilledLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        enemiesKilledLabel.setForeground(Color.WHITE);
        enemiesKilledLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(enemiesKilledLabel);
    }

    public void addEnemiesKilled() {
        enemiesKilledPanel.setLayout(new GridLayout(savedData.player.getEnemiesKilled().size(), 2));
        enemyDescriptionTextBoxes = new JTextArea[savedData.player.getEnemiesKilled().size()];

        for (int i = 0; i < savedData.player.getEnemiesKilled().size(); i++) {
            // make label for name of enemy
            JLabel enemyNameLabel = new JLabel(savedData.player.getEnemiesKilled().get(i).getName() + ":");
            enemyNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
            enemyNameLabel.setForeground(Color.WHITE);

            // make text field for description of enemy
            enemyDescriptionTextBoxes[i] = new JTextArea(savedData.player.getEnemiesKilled().get(i).getDescription());
            enemyDescriptionTextBoxes[i].setBackground(Color.BLACK);
            enemyDescriptionTextBoxes[i].setForeground(Color.WHITE);
            enemyDescriptionTextBoxes[i].setFont(new Font("Monospaced", Font.PLAIN, 15));
            LineBorder line = new LineBorder(Color.WHITE, 2, false);
            enemyDescriptionTextBoxes[i].setBorder(line);

            enemiesKilledPanel.add(enemyNameLabel);
            enemiesKilledPanel.add(enemyDescriptionTextBoxes[i]);
        }
    }

    public void addSaveButton() {
        JButton saveButton = new JButton("Save Descriptions");
        saveButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save results
                if (savedData != null) {
                    for (int i = 0; i < savedData.player.getEnemiesKilled().size(); i++) {
                        savedData.player.getEnemiesKilled().get(i).setDescription(enemyDescriptionTextBoxes[i].getText());
                    }
                    SaveData.saveGameState(savedData.player);
                }

                // return user to main menu
                Main.view.showMainMenuPanel();
            }
        });
        add(saveButton);
    }
}

