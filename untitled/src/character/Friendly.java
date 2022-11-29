package character;

import main.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Friendly extends NonPlayableCharacter {
    public ArrayList<String> dialogPool;

    public Friendly() {
        super();
        this.isInvincible = true;
        this.canMove = false;
        this.attackArea = new Rectangle(0, 0, 0, 0);
    }

    //need to change with randomization and specific npcs
    public void displayDialog() {
        System.out.printf("Current room: %d\n", getGp().getCurrentRoomNum());


        if (getGp().getCurrentRoomNum() == 1) { // room.STARTINGROOM no longer exists. plz change
            String dialog = "Hello " + getGp().getPlayer().name + "!";
            JOptionPane.showMessageDialog(null, dialog, "Satyr", JOptionPane.INFORMATION_MESSAGE);
        } else if (getGp().getCurrentRoomNum() == 3) { // room.TRAPROOM no longer exists. plz change
            String dialog = "Well done " + getGp().getPlayer().name + "!";
            JOptionPane.showMessageDialog(null, dialog, "Knight", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /*
    public ArrayList<String> getDialogPool() {
        return this.dialogPool;
    }

    public void setDialogPool(ArrayList<String> dialogPool) {
        this.dialogPool = dialogPool;
    }

     */
}
