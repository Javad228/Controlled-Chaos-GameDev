package main;

import character.Character;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    public boolean kPressed, wPressed, sPressed, aPressed, dPressed, upPressed, downPressed, leftPressed, rightPressed;

    //TODO implement movement and attack
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        boolean eventW = (code == KeyEvent.VK_W);
        boolean eventS = (code == KeyEvent.VK_S);
        boolean eventA = (code == KeyEvent.VK_A);
        boolean eventD = (code == KeyEvent.VK_D);

        // walking audio should be started if one of the wasd keys has been pressed AND it wasn't being pressed before
        // or it has stopped playing
        if ((eventW || eventS || eventA || eventD) &&
                ((!wPressed && !sPressed && !aPressed && !dPressed) || !Audio.walkingClip.isRunning())) {
            Audio.walking();
        }

        /*
         * problem: this system is good for walking because it keeps updating while they're holding
         * down the button, but for attacks and everything else it keeps attacking for as long as they
         * hold the button, which results in some funky damage and sound effects
         */
        if (code == KeyEvent.VK_K) {
            kPressed = true;
        }
        if (code == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        //can't make the settings page go away
        if (code == KeyEvent.VK_ESCAPE) {
            Main.view.getSettingsPage().setVisible(true);
            Main.view.getGamePanel().setVisible(false);

            Main.view.getGamePanel().pauseThread();

            Audio.stopMusic();
            Audio.settingsMusic();
        }

        //display sounds for now

        //pick up weapon or boone
        if (code == KeyEvent.VK_1) {
            Audio.itemPickUpAudio();
        }

        //player takes damage
        if (code == KeyEvent.VK_2) {
            Audio.playerDamagedAudio();
        }

        //enemy is damaged
        if (code == KeyEvent.VK_SPACE) {
            Audio.enemyDamagedAudio();
        }

        //object is destroyed
        if (code == KeyEvent.VK_3) {
            Audio.destroyObjectAudio();
        }

        //button is pressed
        if (code == KeyEvent.VK_4) {
            Audio.pressButtonAudio();
        }

        //door is opened
        if (code == KeyEvent.VK_5) {
            Audio.doorOpenAudio();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_K) {
            kPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        if (Audio.walkingClip != null) {
            Audio.stopWalking();
        }

    }

    public void reset() {
        kPressed = false;
        wPressed = false;
        sPressed = false;
        aPressed = false;
        dPressed = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    public String toBitString() {
        StringBuilder s = new StringBuilder();

        if (kPressed) s.append(1); else s.append(0);
        if (wPressed) s.append(1); else s.append(0);
        if (aPressed) s.append(1); else s.append(0);
        if (sPressed) s.append(1); else s.append(0);
        if (dPressed) s.append(1); else s.append(0);
        if (upPressed) s.append(1); else s.append(0);
        if (downPressed) s.append(1); else s.append(0);
        if (leftPressed) s.append(1); else s.append(0);
        if (rightPressed) s.append(1); else s.append(0);

        return s.toString();
    }
}
