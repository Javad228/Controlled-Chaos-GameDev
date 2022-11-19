package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class changeSkinPanel extends JPanel {
    JPanel characterFlipPanel = new JPanel(); // panel that contains the arrow buttons and the picture of the character
    JPanel bottomPanel = new JPanel(); // panel that contains the characterFlipPanel and exit buttons

    public changeSkinPanel() {
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
        JButton flipLeftButton = new JButton("\u2190");
        flipLeftButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        flipLeftButton.setEnabled(false); // if there is nothing to the left

        flipLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: ADD FLIPPING FUNCTIONALITY
            }
        });

        characterFlipPanel.add(flipLeftButton);
    }

    private void addCharacterIcon() {
        // TODO: modify given new characters
        JLabel characterIconLabel = new JLabel();

        try {
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/largerThanLife.png"));
            characterIconLabel.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        characterFlipPanel.add(characterIconLabel);
    }

    private void addFlipRightButton() {
        JButton flipRightButton = new JButton("\u2192");
        flipRightButton.setEnabled(false); // if there is nothing to the left
        flipRightButton.setFont(new Font("Monospaced", Font.PLAIN, 25));

        flipRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: ADD FLIPPING FUNCTIONALITY
            }
        });

        characterFlipPanel.add(flipRightButton);
    }

    private void addCharacterName() {
        JLabel characterNameLabel = new JLabel("Woman Warrior"); // TODO: change given shown character
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
                // TODO: ADD SAVING FUNCTIONALITY
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
