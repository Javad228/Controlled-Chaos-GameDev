package main;

import character.PlayerCharacter;
import loot.Item;
import save.GameSaveState;
import save.SaveData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

public class StatsPanel extends JPanel {
    GamePanel gp;
    SaveData sd;
    boolean[] pu;
    GameSaveState savedData;

    JPanel basicDetailsPanel = new JPanel();
    JPanel permanentUnlocksPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(this);

    public StatsPanel(GamePanel gp) {
        this.gp = gp;
        sd = new SaveData(gp);
        savedData = sd.restoreGameState();
        pu = sd.restorePermanentUnlocks();


        if (savedData == null) {
            gp.newGame(false);
            if (gp.readThreadState()) System.out.println("Game restore Failed\nUsing starting values");
            else System.out.println("Game restore Failed");
        } else {
            savedData = new GameSaveState(savedData, sd);
            gp.newGame(
                    savedData.player,
                    new Time(savedData.currentRunTimeNS),
                    new Time(savedData.currentLevelTimeNS),
                    sd.initializeRooms(savedData.rooms, savedData.player),
                    savedData.currentRoomNum,
                    savedData.currentLevelNum,
                    false); // use this method but make sure it doesn't start the game thread
            System.out.println("Game restore Succeeded");
        }

        if (pu == null) {
            pu = new boolean[]{false, false, false, false, false, false};
        }

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollPane.setLayout(new ScrollPaneLayout());
        basicDetailsPanel.setLayout(new GridLayout(4, 2)); // number of rows depends on number of fields you'd like to add
        basicDetailsPanel.setBackground(Color.BLACK);

        // the below objects are added to the statsPanel
        add(Box.createVerticalStrut(25));
        addTitleLabel();
        add(Box.createVerticalStrut(25));

        // the four sets of labels are added to the basicDetailsPanel before the panel itself is added to the statsPanel
        addNameLabels();
        addClockLabels();
        addMaxHealthLabels();
        addNumCoinsLabels();
        add(basicDetailsPanel); // add basicDetailsPanel to statsPanel

        scrollPane.setBackground(Color.BLACK);

        addPermanentUnlocksTitleLabel();
        if (savedData != null) {
            addPermanentUnlocks();
            add(permanentUnlocksPanel);
        }
        // add button to save descriptions
        addReturnButton();

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

        basicDetailsPanel.add(clockCategoryLabel);
        basicDetailsPanel.add(clockLabel);
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

    public void addPermanentUnlocksTitleLabel() {
        JLabel itemsDiscoveredLabel = new JLabel("Permanent Unlocks:");
        itemsDiscoveredLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        itemsDiscoveredLabel.setForeground(Color.WHITE);
        itemsDiscoveredLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(itemsDiscoveredLabel);
    }

    public void addPermanentUnlocks() {
        permanentUnlocksPanel.setLayout(new BoxLayout(permanentUnlocksPanel, BoxLayout.Y_AXIS));
        permanentUnlocksPanel.add(Box.createVerticalStrut(10));
        for (int i = 0; i < pu.length; i++) {
            // make label for name of enemy
            Item item = Item.getItemByID(i, gp);
            JLabel itemNameLabel = new JLabel(item.getName());

            if (pu == null || !pu[i] ) {
                itemNameLabel.setText("Hidden Item");
                itemNameLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
            } else {
                itemNameLabel.setText(Item.getItemByID(i, gp).getName() + ":");
                itemNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
            }

            //itemNameLabel.setBackground(Color.BLACK);
            itemNameLabel.setForeground(Color.WHITE);
            itemNameLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);


            permanentUnlocksPanel.add(itemNameLabel);
        }

        permanentUnlocksPanel.add(Box.createVerticalStrut(20));
    }

    public void addReturnButton() {
        JButton saveButton = new JButton("Return");

        saveButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // return user to main menu
                Main.view.showMainMenuPanel();
            }
        });
        add(saveButton);
    }
}