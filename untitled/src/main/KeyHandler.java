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

        if (code == KeyEvent.VK_ESCAPE) {
            Main.view.getSettingsPage().setVisible(true);
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
        //if (code == KeyEvent.VK_RIGHT) {
            //rightPressed = false;
        //}

        if (Audio.walkingClip != null) {
            Audio.stopWalking();
        }
    }
}
