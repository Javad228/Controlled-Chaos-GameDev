/*
    Walking_On_Gravel from Caroline Ford on SoundBible:
    https://soundbible.com/1432-Walking-On-Gravel.html
 */

package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.*;

class Audio {
    public static Clip walkingClip;
    public static Clip musicClip;

    public static float musicVolume = -20.0f;
    public static float soundEffectVolume = -10.0f;

    private static Clip getClip(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            return clip;
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }

        return null;
    }

    public static void setClipVolume(Clip clip, float volume) {
        if (clip == null) {
            System.out.println("setClipVolume failed: clip == null");
            return;
        }

        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    public static void openingMusic() {
        musicClip = getClip("untitled/audio/Derp-Nugget.wav");
        if (musicClip == null) {
            System.out.println("openingMusic failed: getClip returned null");
            return;
        }
        setClipVolume(musicClip, musicVolume);
        musicClip.start();
    }

    public static void walking() {

        //want to find a different clip and make it more coherent w animation
        walkingClip = getClip("untitled/audio/NormalWood_Barefeet_Running.wav");
        if (walkingClip == null) {
            System.out.println("walking failed: getClip returned null");
            return;
        }
        setClipVolume(walkingClip, soundEffectVolume);
        walkingClip.start();

    }

    public static void stopWalking() {
        walkingClip.stop();
    }
}