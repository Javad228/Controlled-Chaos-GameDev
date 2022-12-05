package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ChangeListener {
    GamePanel gamePanel;
    JSlider musicSlider;
    JSlider soundEffectSlider;
    JPanel savePage;
    JButton returnButton;
    JTextField name;
    JButton enterNameButton;

    JTextField mapnote;
    JButton entermapnote;
    private String priorPage;

    public SettingsPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(25));
        addTitleLabel();
        add(Box.createVerticalStrut(25));
        //addFrameRateSelection();
        //add(Box.createVerticalStrut(15));
        addMusicVolumeSelection();
        add(Box.createVerticalStrut(15));
        addSoundEffectVolumeSelection();
        add(Box.createVerticalStrut(15));
        addMapNote();
        addSaveButtons();
        addReturnButton();
        priorPage = "";

        setName("Settings");
        setBackground(Color.BLACK);
        setVisible(false);
    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addReturnButton() {
        returnButton = new JButton("Return");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priorPage.equals("Main Menu")) {
                    Main.view.showMainMenuPanel();
                } else if (priorPage.equals("Game Panel")) {
                    Main.view.showGamePanel();
                    Main.view.getGamePanel().resumeThread();
                    Audio.stopMusic();
                    Audio.openingMusic();
                }
            }
        });

        add(returnButton);
    }

    private void addMapNote() {
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new FlowLayout());
        mapPanel.setBackground(Color.BLACK);
        mapPanel.setPreferredSize(new Dimension(300, 100));

        mapnote = new JTextField("Enter your map note for the given room");
        mapnote.setFont(new Font("Monospaced", Font.BOLD, 15));
        mapnote.setForeground(Color.WHITE);
        mapnote.setBackground(Color.BLACK);
        mapnote.setPreferredSize(new Dimension(500, 100));
        mapnote.setHorizontalAlignment(JTextField.CENTER);
        mapnote.setAlignmentX(Component.CENTER_ALIGNMENT);
        LineBorder line = new LineBorder(Color.WHITE, 2, false);
        mapnote.setBorder(line);

        entermapnote = new JButton("Enter map note");
        entermapnote.setFont(new Font("Monospaced", Font.BOLD, 20));
        entermapnote.setAlignmentX(Component.CENTER_ALIGNMENT);

        entermapnote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.minimap.setText(mapnote.getText());
            }
        });

        mapPanel.add(mapnote);
        add(mapPanel);
        //add(Box.createVerticalStrut(15));
        add(entermapnote);
    }

    private void addSoundEffectVolumeSelection() {
        JLabel soundEffectLabel = new JLabel("Sound Effects Volume: ");
        soundEffectLabel.setForeground(Color.WHITE);
        soundEffectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        soundEffectLabel.setFont(new Font("Monospaced", Font.BOLD, 15));

        soundEffectSlider = new JSlider(0, 10, 5);
        soundEffectSlider.setPaintTrack(true);
        soundEffectSlider.setPaintTicks(true);
        soundEffectSlider.setPaintLabels(true);
        soundEffectSlider.setMajorTickSpacing(5);
        soundEffectSlider.setMinorTickSpacing(1);
        soundEffectSlider.addChangeListener(this);
        soundEffectSlider.setBackground(Color.BLACK);
        soundEffectSlider.setForeground(Color.WHITE);

        add(soundEffectLabel);
        add(soundEffectSlider);
    }

    public void addMusicVolumeSelection() {
        JLabel musicLabel = new JLabel("Music Volume: ");
        musicLabel.setForeground(Color.WHITE);
        musicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicLabel.setFont(new Font("Monospaced", Font.BOLD, 15));

        musicSlider = new JSlider(0, 10, 5);
        musicSlider.setPaintTrack(true);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);
        musicSlider.setMajorTickSpacing(5);
        musicSlider.setMinorTickSpacing(1);
        musicSlider.addChangeListener(this);
        musicSlider.setBackground(Color.BLACK);
        musicSlider.setForeground(Color.WHITE);

        add(musicLabel);
        add(musicSlider);
    }

    private void addSaveButtons() {
        // Add Save Page
        //savePage = new JPanel();
        //savePage.setLayout(new BorderLayout());
        //savePage.add(gamePanel.saveData.saveGameButton, BorderLayout.WEST);
        //savePage.add(gamePanel.saveData.restoreGameButton, BorderLayout.CENTER);
        //savePage.add(gamePanel.saveData.resetGameProgressButton, BorderLayout.EAST);
        ///add(savePage);

        gamePanel.saveData.saveGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.saveData.restoreGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.saveData.resetGameProgressButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.saveData.saveGameButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        gamePanel.saveData.restoreGameButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        gamePanel.saveData.resetGameProgressButton.setFont(new Font("Monospaced", Font.BOLD, 20));

        add(gamePanel.saveData.saveGameButton);
        add(gamePanel.saveData.restoreGameButton);
        add(gamePanel.saveData.resetGameProgressButton);
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == musicSlider) {
            Audio.setClipVolume(Audio.musicClip, (float) (-75 + 7.5 * musicSlider.getValue()));
            Audio.musicVolume = (float) (-75 + 7.5 * musicSlider.getValue());
        } else if (e.getSource() == soundEffectSlider) {
            Audio.soundEffectVolume = (float) (-75 + 7.5 * soundEffectSlider.getValue());
        }
    }

    public void addFrameRateSelection() {
        String currentFrameRateStr = "Current Frame Rate = " + gamePanel.getFps();
        JLabel currentFrameRate = new JLabel(currentFrameRateStr);

        JLabel frameRateLabel = new JLabel("New Frame Rate: ");

        Integer[] frameRateChoices = {24, 30, 50, 60, 120};
        final JComboBox<Integer> dropDown = new JComboBox<>(frameRateChoices);

        add(currentFrameRate);
        add(frameRateLabel);
        add(dropDown);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer newFrameRate = (Integer) dropDown.getSelectedItem();
                if (newFrameRate != null) {
                    getSettingsPanel().remove(0);
                    gamePanel.setFps(newFrameRate);
                    String newFrameRateLabel = "Current Frame Rate = " + newFrameRate + " New Frame Rate: ";
                    frameRateLabel.setText(newFrameRateLabel);
                    getSettingsPanel().add(frameRateLabel, 0);
                } else {
                    System.out.println("Frame rate update failed; fps input is null");
                }
            }
        });

        add(applyButton);


        //move into own function
        JLabel currentVSync = new JLabel("<html><br/>VSync: off<br/></html>");
        add(currentVSync);
    }

    public SettingsPanel getSettingsPanel() {
        return this;
    }

    public String getPriorPage() {
        return priorPage;
    }

    public void setPriorPage(String priorPage) {
        this.priorPage = priorPage;
    }
}
