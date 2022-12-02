package main;

import save.GameSaveState;
import save.SaveData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

public class NameDescPanel extends JPanel {
    GamePanel gp;
    SaveData sd;
    GameSaveState savedData;
    JPanel namePanel = new JPanel();
    JPanel enemiesKilledPanel = new JPanel();
    JPanel itemsDiscoveredPanel = new JPanel();
    JTextField nameTextBox;
    JTextArea[] enemyDescriptionTextBoxes;
    JTextArea[] itemDescriptionTextBoxes;
    JScrollPane scrollPane = new JScrollPane(this);

    public NameDescPanel(GamePanel gp) {
        this.gp = gp;
        sd = new SaveData(gp);
        savedData = sd.restoreGameState();

        if (savedData == null) {
            gp.newGame(false);
            sd.saveGameState();
            savedData = sd.restoreGameState();
            if (gp.readThreadState()) System.out.println("Game restore Failed\nUsing starting values");
            else System.out.println("Game restore Failed");
        } else {
            savedData = new GameSaveState(savedData, sd);
            gp.newGame(
                    savedData.player,
                    new Time(savedData.currentRunTimeNS),
                    new Time(savedData.currentLevelTimeNS),
                    sd.initializeRooms(savedData.rooms, savedData.player),
                    savedData.currentRoomNum, savedData.currentLevelNum,
                    false); // use this method but make sure it doesn't start the game thread
            System.out.println("Game restore Succeeded");
        }

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        //savedData = SaveData.restoreGameState("dummy");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollPane.setLayout(new ScrollPaneLayout());
        namePanel.setLayout(new GridLayout(1, 1)); // number of rows depends on number of fields you'd like to add
        namePanel.setBackground(Color.BLACK);
        enemiesKilledPanel.setBackground(Color.BLACK);
        itemsDiscoveredPanel.setBackground(Color.BLACK);

        // the below objects are added to the statsPanel
        add(Box.createVerticalStrut(25));
        addTitleLabel();
        add(Box.createVerticalStrut(25));

        // the four sets of labels are added to the basicDetailsPanel before the panel itself is added to the statsPanel
        //addBasicDetailsLabel(); // added to statsPanel
        //addNameLabels();
        //addClockLabels();
        //addMaxHealthLabels();
        //addNumCoinsLabels();
        addNameLabel();
        addNameTextbox();
        add(namePanel); // add namePanel to statsPanel

        // add enemies killed title, then labels and text boxes to input descriptions
        addEnemiesKilledTitleLabel(); // added to statsPanel
        if (savedData != null) {
            addEnemiesKilled();
            add(enemiesKilledPanel); // add enemiesKilledPanel to statsPanel
        }

        addItemsDiscoveredTitleLabel();
        if (savedData != null) {
            addItemsDiscovered();
            add(itemsDiscoveredPanel);
        }

        // add button to save descriptions
        addSaveButton();

        scrollPane.setBackground(Color.BLACK);

        setName("Change Name and Descriptions");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("PLAYER NAME AND DESCRIPTIONS");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addNameLabel() {
        JLabel titleLabel = new JLabel("Change Player Name:");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addNameTextbox() {
        namePanel.setLayout(new GridLayout(savedData.player.getEnemiesKilled().size(), 1));
        nameTextBox = new JTextField(savedData.player.name);
        nameTextBox.setHorizontalAlignment(JTextField.CENTER);

        nameTextBox.setBackground(Color.BLACK);
        nameTextBox.setForeground(Color.WHITE);
        nameTextBox.setFont(new Font("Monospaced", Font.PLAIN, 20));
        LineBorder line = new LineBorder(Color.WHITE, 2, false);
        nameTextBox.setBorder(line);

        namePanel.add(nameTextBox);
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

        namePanel.add(nameCategoryLabel);
        namePanel.add(nameLabel);
    }

    private void addClockLabels() {
        Font font = new Font("Monospaced", Font.PLAIN, 25);

        JLabel clockCategoryLabel = new JLabel("Time Elapsed:");
        clockCategoryLabel.setFont(font);
        clockCategoryLabel.setForeground(Color.WHITE);

        JLabel clockLabel = new JLabel();
        if (savedData != null)  clockLabel.setText(savedData.currentRunTimeS);
        else    clockLabel.setText("0:00:00.000");
        clockLabel.setFont(font);
        clockLabel.setForeground(Color.WHITE);

        namePanel.add(clockCategoryLabel);
        namePanel.add(clockLabel);
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

        namePanel.add(maxHealthCategoryLabel);
        namePanel.add(maxHealthLabel);
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

        namePanel.add(numCoinsCategoryLabel);
        namePanel.add(numCoinsLabel);
    }

    public void addEnemiesKilledTitleLabel() {
        JLabel enemiesKilledLabel = new JLabel("Enemy Descriptions:");
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

    public void addItemsDiscoveredTitleLabel() {
        JLabel itemsDiscoveredLabel = new JLabel("Item Descriptions:");
        itemsDiscoveredLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        itemsDiscoveredLabel.setForeground(Color.WHITE);
        itemsDiscoveredLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(itemsDiscoveredLabel);
    }

    public void addItemsDiscovered() {
        itemsDiscoveredPanel.setLayout(new GridLayout(savedData.player.inventory.getListOfItems().size(), 2));
        itemDescriptionTextBoxes = new JTextArea[savedData.player.inventory.getListOfItems().size()];

        for (int i = 0; i < savedData.player.inventory.getListOfItems().size(); i++) {
            // make label for name of enemy
            JLabel itemNameLabel = new JLabel("");

            if (savedData.player.inventory.getListOfItems().get(i).getName() == null ||
                    savedData.player.inventory.getListOfItems().get(i).getName().equals("")) {
                itemNameLabel.setText("Item:");
                itemNameLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
            } else {
                itemNameLabel.setText(savedData.player.inventory.getListOfItems().get(i).getName() + ":");
                itemNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
            }

            //itemNameLabel.setBackground(Color.BLACK);
            itemNameLabel.setForeground(Color.WHITE);

            // make text field for description of enemy
            itemDescriptionTextBoxes[i] = new JTextArea(savedData.player.inventory.getListOfItems().get(i).getDescription());
            itemDescriptionTextBoxes[i].setToolTipText("Enter item Description");
            itemDescriptionTextBoxes[i].setBackground(Color.BLACK);
            itemDescriptionTextBoxes[i].setForeground(Color.WHITE);
            itemDescriptionTextBoxes[i].setFont(new Font("Monospaced", Font.PLAIN, 15));
            LineBorder line = new LineBorder(Color.WHITE, 2, false);
            itemDescriptionTextBoxes[i].setBorder(line);

            itemsDiscoveredPanel.add(itemNameLabel);
            itemsDiscoveredPanel.add(itemDescriptionTextBoxes[i]);
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
                    gp.player.setName(nameTextBox.getText());

                    for (int i = 0; i < savedData.player.getEnemiesKilled().size(); i++) {
                        gp.player.getEnemiesKilled().get(i).setDescription(enemyDescriptionTextBoxes[i].getText());
                        //savedData.player.getEnemiesKilled().get(i).setDescription(enemyDescriptionTextBoxes[i].getText());
                    }
                    //SaveData.saveGameState(savedData.player);
                    for (int i = 0; i < savedData.player.inventory.getListOfItems().size(); i++) {
                        gp.player.getInventory().getListOfItems().get(i).setDescription(itemDescriptionTextBoxes[i].getText());
                    }
                    sd.saveGameState();
                }

                // return user to main menu
                Main.view.showMainMenuPanel();
            }
        });
        add(saveButton);
    }
}

