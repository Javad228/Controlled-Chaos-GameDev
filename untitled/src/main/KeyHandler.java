package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    //TODO implement movement and attack
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if ((!upPressed && !leftPressed && !downPressed && !rightPressed) || !Audio.walkingClip.isRunning()) {
            Audio.walking();
        }

//        if (code == KeyEvent.VK_W) {
//            upPressed = true;
//        }
//        if (code == KeyEvent.VK_A) {
//            leftPressed = true;
//        }
//        if (code == KeyEvent.VK_S) {
//            downPressed = true;
//        }
//        if (code == KeyEvent.VK_D) {
//            rightPressed = true;
//        }

        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        Audio.stopWalking();
    }
}
