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

    public SettingsPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        addFrameRateSelection();
        addMusicVolumeSelection();
        addSoundEffectVolumeSelection();

        setName("Settings");
        setBackground(Color.white);
        setVisible(true);
    }

    private void addSoundEffectVolumeSelection() {
        JLabel soundEffectLabel = new JLabel("Sound Effects Volume: ");
        soundEffectLabel.setVisible(true);

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
        musicLabel.setVisible(true);

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
        frameRateLabel.setVisible(true);

        Integer[] frameRateChoices = {24, 30, 50, 60, 120};
        final JComboBox<Integer> dropDown = new JComboBox<>(frameRateChoices);
        dropDown.setVisible(true);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer newFrameRate = (Integer) dropDown.getSelectedItem();
                if (newFrameRate != null) {
                    gamePanel.setFps(newFrameRate);
                } else {
                    System.out.println("Frame rate update failed; fps input is null");
                }
            }
        });

        add(currentFrameRate);
        add(frameRateLabel);
        add(dropDown);
        add(applyButton);


        //move into own function
        JLabel currentVSync = new JLabel("<html><br/>VSync: off<br/></html>");
        add(currentVSync);
    }

}
