package main;

import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Objects;

public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
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

        if (code == KeyEvent.VK_E) {
            if (gp.getPlayer().getCurrentTile().tileType == Tile.BUTTON) {
                try {
                    TileManager.tile[Tile.BUTTON].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_down.png")));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
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

        if (gp.gameState == 4) {
            if (code == KeyEvent.VK_C){gp.gameState = 1;}
            if(code == KeyEvent.VK_W) {if (gp.inventory.slotRow != 0) {gp.inventory.slotRow--;}}
            if(code == KeyEvent.VK_A) {if (gp.inventory.slotCol != 0) {gp.inventory.slotCol--;}}
            if(code == KeyEvent.VK_S){if (gp.inventory.slotRow != 3) {gp.inventory.slotRow++;}}
            if(code == KeyEvent.VK_D){if (gp.inventory.slotCol != 4) {gp.inventory.slotCol++;}}
//            if(code == KeyEvent.VK_ENTER){gp.player.selectItem();}
        } else{
            if (code == KeyEvent.VK_C){gp.gameState = 4;}
        }


        //can't make the settings page go away
        if (code == KeyEvent.VK_ESCAPE) {
            if (!Main.view.getSettingsPage().isVisible()) {
                //Main.view.getSettingsPage().setVisible(true);
                //Main.view.getGamePanel().setVisible(false);

                //Main.view.getGamePanel().pauseThread();

                Audio.stopMusic();
                Audio.settingsMusic();

                Main.view.getGamePanel().pauseThread();

                Main.view.getSettingsPage().showSettingsPanel();

            } else {
                //Main.view.getGamePanel().setVisible(true);
                //Main.view.getSettingsPage().setVisible(false);
                Main.view.getSettingsPage().hideSettingsPanel();

                Main.view.getGamePanel().resumeThread();

                Audio.stopMusic();
                Audio.openingMusic();
            }
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
