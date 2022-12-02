package main;

import save.GameSaveState;
import save.SaveData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ChangeSkinPanel extends JPanel {
    JPanel characterFlipPanel = new JPanel(); // panel that contains the arrow buttons and the picture of the character
    JPanel bottomPanel = new JPanel(); // panel that contains the characterFlipPanel and exit buttons
    JButton flipLeftButton;
    JButton flipRightButton;
    JLabel characterIconLabel;
    JLabel characterNameLabel;
    GamePanel gp;
    SaveData sd;
    GameSaveState savedData;
    ArrayList<String> characters;
    int characterShown;

    public ChangeSkinPanel(GamePanel gp) {
        this.gp = gp;
        sd = new SaveData(gp);
        savedData = sd.restoreGameState();

        if (savedData == null) {
            gp.newGame(false);
            sd.saveGameState();
            savedData = sd.restoreGameState();
            if (gp.readThreadState()) {
                System.out.println("Game restore Failed\nUsing starting values");
            } else {
                System.out.println("Game restore Failed");
            }
        }

        characters = new ArrayList<>();
        characters.add("Warrior");
        characters.add("Healer");
        String currentCharacter = gp.player.getCharacterAppearance();
        characterShown = characters.indexOf(currentCharacter.substring(0, 1).toUpperCase() + currentCharacter.substring(1)); // this will cause issues if you have a character with multiple words in the name

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        characterFlipPanel.setLayout(new BoxLayout(characterFlipPanel, BoxLayout.X_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        characterFlipPanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);

        add(Box.createVerticalStrut(25));
        addTitleLabel(); // add title directly to the changeSkinPanel
        add(Box.createVerticalStrut(25));
        // add these to the characterFlipPanel
        addFlipLeftButton();
        addCharacterIcon();
        addFlipRightButton();
        bottomPanel.add(characterFlipPanel); // add the characterFlipPanel to the bottomPanel
        // add these to the bottomPanel
        addCharacterName();
        bottomPanel.add(Box.createVerticalStrut(25));
        addExitButtons();
        add(bottomPanel); // add the final product to the changeSkinPanel

        setName("Change Player Skin");
        setBackground(Color.BLACK);
        setVisible(true);
    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("Choose Your Character");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addFlipLeftButton() {
        flipLeftButton = new JButton("\u2190");
        flipLeftButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        if (characterShown == 0) {
            flipLeftButton.setEnabled(false); // if there is nothing to the left
        }
        System.out.println(characterShown);

        flipLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterShown--;
                System.out.println(characterShown);
                if (characterShown < characters.size() - 1) {
                    flipRightButton.setEnabled(true);
                }
                if (characterShown == 0) {
                    flipLeftButton.setEnabled(false); // if there is nothing to the left
                }
                updateIconAndName();
            }
        });

        characterFlipPanel.add(flipLeftButton);
    }

    private void addCharacterIcon() {
        characterIconLabel = new JLabel();

        try {
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/player_character/" + characters.get(characterShown).toLowerCase() + "/larger_than_life.png"));
            characterIconLabel.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        characterFlipPanel.add(characterIconLabel);
    }

    private void addFlipRightButton() {
        flipRightButton = new JButton("\u2192");
        if (characterShown == characters.size() - 1) {
            flipRightButton.setEnabled(false); // if there is nothing to the right
        }
        flipRightButton.setFont(new Font("Monospaced", Font.PLAIN, 25));

        flipRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterShown++;
                System.out.println(characterShown);
                if (characterShown > 0) {
                    flipLeftButton.setEnabled(true);
                }
                if (characterShown == characters.size() - 1) {
                    flipRightButton.setEnabled(false); // if there is nothing to the right
                }
                updateIconAndName();
            }
        });

        characterFlipPanel.add(flipRightButton);
    }

    private void updateIconAndName() {
        characterNameLabel.setText(characters.get(characterShown));
        try {
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/player_character/" + characters.get(characterShown).toLowerCase() + "/larger_than_life.png"));
            characterIconLabel.setIcon(new ImageIcon(icon));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void addCharacterName() {
        characterNameLabel = new JLabel(characters.get(characterShown));
        characterNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        characterNameLabel.setForeground(Color.WHITE);
        characterNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(characterNameLabel);
    }

    private void addExitButtons() {
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new BoxLayout(flowPanel, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("Exit Without Saving");
        backButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setVerticalAlignment(JLabel.CENTER);
        JButton saveButton = new JButton("Save and Return");
        saveButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setVerticalAlignment(JLabel.CENTER);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showMainMenuPanel();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save results
                if (savedData != null) {
                    gp.player.setCharacterAppearance(characters.get(characterShown).toLowerCase());
                    if(Objects.equals(characters.get(characterShown).toLowerCase(), "healer")){
                        gp.getPlayer().setMovementSpeed(6);
                        gp.getPlayer().setMaxSpeed(6);
                        gp.getPlayer().setMaxSpeed(7);
                        gp.getPlayer().setDamageMod(3);

                    }else{
                        gp.getPlayer().setMovementSpeed(4);
                        gp.getPlayer().setMaxSpeed(4);
                        gp.getPlayer().setMaxSpeed(5);

                    }
                    sd.saveGameState();
                }
                gp.player.getPlayerImage(); // reinitialize sprite images
                Main.view.showMainMenuPanel();
            }
        });

        flowPanel.setBackground(Color.BLACK);
        flowPanel.add(backButton);
        flowPanel.add(Box.createVerticalStrut(10));
        flowPanel.add(saveButton);
        flowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(flowPanel);
    }
}
