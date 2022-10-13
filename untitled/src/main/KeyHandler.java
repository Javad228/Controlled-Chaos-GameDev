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
            System.out.printf("%b\n", Main.view.getSettingsPage().isVisible());
            if (Main.view.getSettingsPage().isVisible()) {
                Main.view.getGamePanel().setVisible(true);
                Main.view.getSettingsPage().setVisible(false);
            } else {
                Main.view.getSettingsPage().setVisible(true);
                Main.view.getGamePanel().setVisible(false);
            }
            System.out.printf("%b\n", Main.view.getSettingsPage().isVisible());

        }

        //display sounds for now

        //pick up weapon or boone
        if (code == KeyEvent.VK_1) {
            Audio.itemPickUpAudio();
        }

        if (code == KeyEvent.VK_2) {
            Audio.playerDamagedAudio();
        }

        if (code == KeyEvent.VK_SPACE) {
            Audio.enemyDamagedAudio();
        }

        if (code == KeyEvent.VK_3) {
            Audio.destroyObjectAudio();
        }

        if (code == KeyEvent.VK_4) {
            Audio.pressButtonAudio();
        }

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
