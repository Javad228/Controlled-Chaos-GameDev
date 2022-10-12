/*
    Walking_On_Gravel from Caroline Ford on SoundBible:
    https://soundbible.com/1432-Walking-On-Gravel.html
 */

package main;

import javax.sound.sampled.*;
import java.io.*;

class Audio {
    public static Clip walkingClip;
    public static Clip musicClip;

    public static float musicVolume = -20.0f;
    public static float soundEffectVolume = -10.0f;

    private static Clip getClip(String filename) {
        Clip clip = null;
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            return clip;
        }
        catch (UnsupportedAudioFileException | NullPointerException | FileNotFoundException exc)
        {
            String message = "Cannot find audio file " + filename + "\n";
            exc.printStackTrace();
            System.out.println(message);
            System.exit(0);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

        return clip;
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
        Clip music = getClip("untitled/audio/Derp-Nugget.wav");
        if (music == null) {
            System.out.println("opening music failed: getClip returned null");
            System.exit(0);
        }
        float volume = -20.0f;
        setClipVolume(music, volume);
        music.start();
    }

    public static void walking() {

        //want to find a different clip and make it more coherent w animation
        walkingClip = getClip("untitled/audio/NormalWood_Barefeet_Running.wav");
        if (walkingClip == null) {
            System.out.println("walking audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10.0f;
        setClipVolume(walkingClip, volume);
        walkingClip.start();

    }

    public static void stopWalking() {
        walkingClip.stop();
    }

    public static void swordHitAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip swordHitClip = getClip();
        if (swordHitClip == null) {
            System.out.println("Sword hit audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(swordHitClip, volume);
        swordHitClip.start;

         */
    }

    public static void swordMissAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip swordMissClip = getClip();
        if (swordMissClip == null) {
            System.out.println("Sword miss audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(swordMissClip, volume);
        swordMissClip.start;

         */
    }

    public static void itemPickUpAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip itemPickUpClip = getClip();
        if (itemPickUpClip == null) {
            System.out.println("Item pick up audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(itemPickUpClip, volume);
        itemPickUpClip.start;

         */
    }

    public static void playerDamagedAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip playerDamagedClip = getClip();
        if (playerDamagedCip == null) {
            System.out.println("Player damaged audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(playerDamagedClip, volume);
        playerDamaged.start;

         */
    }

    public static void destroyObjectAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip destroyObjectClip = getClip();
        if (destroyObjectClip == null) {
            System.out.println("Destroy object audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(destroyObjectClip, volume);
        destroyObjectClip.start;

         */
    }

    public static void pressButtonAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip pressButtonClip = getClip();
        if (pressButtonClip == null) {
            System.out.println("Press button audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(pressButtonClip, volume);
        pressButtonClip.start;

         */
    }

    public static void doorOpenAudio() {
        /*

        //uncomment when there are clips for these sounds

        Clip doorOpenClip = getClip();
        if (doorOpenClip == null) {
            System.out.println("Door open audio failed: getClip returned null");
            System.exit(0);
        }
        float volume = -10f; //may need changed
        setClipVolume(doorOpenClip, volume);
        doorOpenClip.start;

         */
    }
}