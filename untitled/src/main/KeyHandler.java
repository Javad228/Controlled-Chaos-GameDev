package main;

import character.Knight;
import character.Satyr;
import loot.Chest;
import tile.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    public boolean kPressed, wPressed, sPressed, aPressed, dPressed, upPressed, downPressed, leftPressed, rightPressed, bPressed;

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
        if (code == KeyEvent.VK_B) {
            /*bPressed = true;
            if(!Objects.equals(gp.getPlayer().projectileName, "Bomb")){
                gp.getPlayer().projectileName = "Bomb";
            }else{
                gp.getPlayer().projectileName = "Arrow";

            }

             */

        }
        if (code == KeyEvent.VK_K) {
            kPressed = true;
        }

        if (code == KeyEvent.VK_E) {
            if (gp.getCurrentRoomNum() == 3) { // room.TRAPROOM no longer exists. plz change

                //Commented out puzzle code for now, needs adjustment

                /*
                if (gp.getPlayer().roomSetNum == 1) {
                    Button button1 = gp.getRooms().get(2).getButtons().get(0);
                    Button button2 = gp.getRooms().get(2).getButtons().get(1);
                    Button button3 = gp.getRooms().get(2).getButtons().get(2);
                    Button button4 = gp.getRooms().get(2).getButtons().get(3);

                    if ((gp.getPlayer().getRow() >= Button.map1Button1Row - 1) && (gp.getPlayer().getRow() <= Button.map1Button1Row + 1) &&
                            (gp.getPlayer().getCol() >= Button.map1Button1Col - 1) && (gp.getPlayer().getCol() <= Button.map1Button1Col + 1)) {
                        button1.toggle();
                        for (int i = 0; i < button1.getTrapTiles().size(); i++) {
                            TrapTile trapTile = button1.getTrapTiles().get(i);
                            trapTile.toggleTrap(i, TrapTile.map1TrapCol1);
                        }
                    } else if ((gp.getPlayer().getRow() >= Button.map1Button2Row - 1) && (gp.getPlayer().getRow() <= Button.map1Button2Row + 1) &&
                            (gp.getPlayer().getCol() >= Button.map1Button2Col - 1) && (gp.getPlayer().getCol() <= Button.map1Button2Col + 1)) {
                        button2.toggle();

                        if (button2.getIsOn() && button3.getIsOn() && !button4.getIsOn()) {
                            button2.getDoorTiles().get(0).toggleDoor(DoorTile.map1Room4DoorCol, DoorTile.map1Room4DoorRow);
                            Audio.doorOpenAudio();
                        }
                    } else if ((gp.getPlayer().getRow() >= Button.map1Button3Row - 1) && (gp.getPlayer().getRow() <= Button.map1Button3Row + 1) &&
                            (gp.getPlayer().getCol() >= Button.map1Button3Col - 1) && (gp.getPlayer().getCol() <= Button.map1Button3Col + 1)) {
                        button3.toggle();

                        if (button2.getIsOn() && button3.getIsOn() && !button4.getIsOn()) {
                            button2.getDoorTiles().get(0).toggleDoor(DoorTile.map1Room4DoorCol, DoorTile.map1Room4DoorRow);
                            Audio.doorOpenAudio();
                        }
                    } else if ((gp.getPlayer().getRow() >= Button.map1Button4Row - 1) && (gp.getPlayer().getRow() <= Button.map1Button4Row + 1) &&
                            (gp.getPlayer().getCol() >= Button.map1Button4Col - 1) && (gp.getPlayer().getCol() <= Button.map1Button4Col + 1)) {
                        button4.toggle();

                        if (button2.getIsOn() && button3.getIsOn() && !button4.getIsOn()) {
                            button2.getDoorTiles().get(0).toggleDoor(DoorTile.map1Room4DoorCol, DoorTile.map1Room4DoorRow);
                            Audio.doorOpenAudio();
                        }
                    }
                }

                 */

                if ((gp.player.getRow() >= Knight.room4Row - 1) && (gp.getPlayer().getRow() <= Knight.room4Row + 1) &&
                        (gp.player.getCol() >= Knight.room4Col - 1) && (gp.getPlayer().getCol() <= Knight.room4Col + 1)) {
                    Knight knight = (Knight) gp.getRooms().get(3).getNPCs().get(0);
                    knight.displayDialog();
                }

            } else if (gp.getCurrentRoomNum() == 1) { // Room.STARTINGROOM no longer exists. please change
                Satyr satyr = (Satyr) gp.getRooms().get(1).getNPCs().get(0);
                if ((gp.player.getRow() >= satyr.getRow() - 1) && (gp.getPlayer().getRow() <= satyr.getRow() + 1) &&
                        (gp.player.getCol() >= satyr.getCol() - 1) && (gp.getPlayer().getCol() <= satyr.getCol() + 1)) {
                    satyr.displayDialog();
                }
            }else if(gp.getCurrentRoomNum() == 5){
                for(int i =0; i<gp.getRooms().get(5).getChests().size();i++){
                    Chest chest = gp.getRooms().get(5).getChests().get(i);
                    if ((gp.player.getRow() >= chest.getRow() - 1) && (gp.getPlayer().getRow() <= chest.getRow() + 1) &&
                            (gp.player.getCol() >= chest.getCol() - 1) && (gp.getPlayer().getCol() <= chest.getCol() + 1)) {
                        chest.open();
                    }
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
        }else{
            if (code == KeyEvent.VK_C){gp.gameState = 4;}
        }


        //
        // can't make the settings page go away
        if (code == KeyEvent.VK_ESCAPE) {
            //if (!Main.view.getSettingsPanel().isVisible()) {
            if (!Main.view.getSettingsPanel().isDisplayable()) { // checks if the settings panel is not connected to the frame
                //Main.view.getSettingsPage().setVisible(true);
                //Main.view.getGamePanel().setVisible(false);

                //Main.view.getGamePanel().pauseThread();

                //Audio.stopMusic();
                //Audio.settingsMusic();

                Main.view.showSettingsPanel("Game Panel");
                Main.view.getGamePanel().pauseThread();

                //Main.view.getSettingsPanel().showSettingsPanel();

            } else {
                //Main.view.getGamePanel().setVisible(true);
                //Main.view.getSettingsPage().setVisible(false);
                //Main.view.getSettingsPanel().hideSettingsPanel();
                Main.view.showGamePanel();
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
        if (code == KeyEvent.VK_B) {
            bPressed = false;
        }
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
