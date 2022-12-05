/*
    Derp Nugget by Kevin MacLeod | https://incompetech.com/
    Music promoted by https://www.chosic.com/free-music/all/
    Creative Commons CC BY 3.0
    https://creativecommons.org/licenses/by/3.0/

    Lurking Sloth by Alexander Nakarada | https://www.serpentsoundstudios.com
    Music promoted on https://www.chosic.com/free-music/all/
    Creative Commons Attribution 4.0 International (CC BY 4.0)
    https://creativecommons.org/licenses/by/4.0/

    Walking_On_Gravel from Caroline Ford on SoundBible:
    https://soundbible.com/1432-Walking-On-Gravel.html

    Slime-Splash from Mike Koenig on SoundBible
    https://soundbible.com/1097-Slime-Splash.html

    Frying-Pan-Hit from Mike Koenig on SoundBible
    https://soundbible.com/1004-Frying-Pan-Hit.html

    Button-Click from Mike Koenig on SoundBible
    https://soundbible.com/893-Button-Click.html
 */

package main;

import javax.sound.sampled.*;
import java.io.*;

public class Audio {
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
        //musicClip = getClip("C:\\Users\\Javad\\Downloads\\Controlled-Chaos-GameDev-main-nbranch\\untitled\\audio\\Derp-Nugget.wav");
        musicClip = getClip("untitled/audio/Derp-Nugget.wav");
        if (musicClip == null) {
            System.out.println("opening music failed: getClip returned null");
            return;
        }
        setClipVolume(musicClip, musicVolume);
        musicClip.start();
    }

    public static void settingsMusic() {
        musicClip = getClip("untitled/audio/Background_for_a_Shoe_Commercial.wav");
        if (musicClip == null) {
            System.out.println("opening music failed: getClip returned null");
            return;
        }

        setClipVolume(musicClip, musicVolume);
        musicClip.loop(Integer.MAX_VALUE);
        musicClip.start();
    }

    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
        }
    }

    public static void walking() {

        //want to find a different clip and make it more coherent w animation
        walkingClip = getClip("untitled/audio/Walking_On_Gravel.wav");
        //walkingClip = getClip("C:\\Users\\Javad\\Downloads\\Controlled-Chaos-GameDev-main-nbranch\\untitled\\audio\\Walking_On_Gravel.wav");
        if (walkingClip == null) {
            System.out.println("walking audio failed: getClip returned null");
            return;
        }
        setClipVolume(walkingClip, soundEffectVolume);
        walkingClip.start();

    }

    public static void stopWalking() {
        if (walkingClip != null) {
            walkingClip.stop();
        }
    }

    public static void swordHitAudio() {

        //uncomment when there are clips for these sounds

        Clip swordHitClip = getClip("untitled/audio/mixkit-dagger-woosh-1487.wav");
        if (swordHitClip == null) {
            System.out.println("Sword hit audio failed: getClip returned null");
            return;
        }
        setClipVolume(swordHitClip, soundEffectVolume);
        swordHitClip.start();
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
        swordMissClip.start();

         */
    }

    public static void itemPickUpAudio() {


        //uncomment when there are clips for these sounds

        Clip itemPickUpClip = getClip("untitled/audio/jump-coin-216.wav");
        if (itemPickUpClip == null) {
            System.out.println("Item pick up audio failed: getClip returned null");
            return;
        }
        setClipVolume(itemPickUpClip, soundEffectVolume);
        itemPickUpClip.start();
    }

    public static void playerDamagedAudio() {

        //uncomment when there are clips for these sounds

        Clip playerDamagedClip = getClip("untitled/audio/short-knife-whoosh-fx.wav");
        //Clip playerDamagedClip = getClip("C:\\Users\\Javad\\Downloads\\Controlled-Chaos-GameDev-main-nbranch\\untitled\\audio\\short-knife-whoosh-fx.wav");
        if (playerDamagedClip == null) {
            System.out.println("Player damaged audio failed: getClip returned null");
            return;
        }
        setClipVolume(playerDamagedClip, soundEffectVolume);
        playerDamagedClip.start();
    }

    public static void enemyDamagedAudio() {

        //uncomment when there are clips for these soundsds

        Clip playerDamagedClip = getClip("untitled/audio/Slime-Splash.wav");
        //Clip playerDamagedClip = getClip("C:\\Users\\Javad\\Downloads\\Controlled-Chaos-GameDev-main-nbranch\\untitled\\audio\\Slime-Splash.wav");
        if (playerDamagedClip == null) {
            System.out.println("Player damaged audio failed: getClip returned null");
            return;
        }
        setClipVolume(playerDamagedClip, soundEffectVolume);
        playerDamagedClip.start();
    }

    public static void destroyObjectAudio() {
        //uncomment when there are clips for these sounds

        Clip destroyObjectClip = getClip("untitled/audio/Frying-Pan-Hit.wav");
        if (destroyObjectClip == null) {
            System.out.println("Destroy object audio failed: getClip returned null");
            return;
        }
        setClipVolume(destroyObjectClip, soundEffectVolume);
        destroyObjectClip.start();
    }

    public static void pressButtonAudio() {
        //uncomment when there are clips for these sounds

        Clip pressButtonClip = getClip("untitled/audio/Button-Click.wav");
        if (pressButtonClip == null) {
            System.out.println("Press button audio failed: getClip returned null");
            return;
        }
        setClipVolume(pressButtonClip, soundEffectVolume);
        pressButtonClip.start();
    }

    public static void doorOpenAudio() {
        //uncomment when there are clips for these sounds

        Clip doorOpenClip = getClip("untitled/audio/Large-Metal-Rusty-Door.wav");
        if (doorOpenClip == null) {
            System.out.println("Door open audio failed: getClip returned null");
            return;
        }
        setClipVolume(doorOpenClip, soundEffectVolume);
        doorOpenClip.start();
    }
}