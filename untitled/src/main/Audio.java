package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

class Audio {
    public static Clip walkingClip;

    private static Clip play(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
            return clip;
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
        return null;
    }

    public static void openingMusic() {
        play("untitled/audio/Derp-Nugget.wav");
    }

    public static void walking() {
        walkingClip = play("untitled/audio/Walking_On_Gravel.wav");
    }

    public static void stopWalking() {
        walkingClip.stop();
    }
}