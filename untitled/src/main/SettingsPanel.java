package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ChangeListener {
    GamePanel gamePanel;
    JSlider musicSlider;
    JSlider soundEffectSlider;
    JButton returnButton;

    public SettingsPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        addFrameRateSelection();
        addMusicVolumeSelection();
        addSoundEffectVolumeSelection();
        addReturnButton();


        setName("Settings");
        setBackground(Color.white);
        setVisible(false);
    }

    private void addReturnButton() {
        returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.getGamePanel().setVisible(true);
                Main.view.getSettingsPage().setVisible(false);

                Main.view.getGamePanel().resumeThread();

                Audio.stopMusic();
                Audio.openingMusic();
            }
        });

        add(returnButton);
    }

    private void addSoundEffectVolumeSelection() {
        JLabel soundEffectLabel = new JLabel("Sound Effects Volume: ");

        soundEffectSlider = new JSlider(0, 10, 5);
        soundEffectSlider.setPaintTrack(true);
        soundEffectSlider.setPaintTicks(true);
        soundEffectSlider.setPaintLabels(true);
        soundEffectSlider.setMajorTickSpacing(5);
        soundEffectSlider.setMinorTickSpacing(1);
        soundEffectSlider.addChangeListener(this);

        add(soundEffectLabel);
        add(soundEffectSlider);
    }

    public void addMusicVolumeSelection() {
        JLabel musicLabel = new JLabel("Music Volume: ");

        musicSlider = new JSlider(0, 10, 5);
        musicSlider.setPaintTrack(true);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);
        musicSlider.setMajorTickSpacing(5);
        musicSlider.setMinorTickSpacing(1);
        musicSlider.addChangeListener(this);

        add(musicLabel);
        add(musicSlider);
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

}
