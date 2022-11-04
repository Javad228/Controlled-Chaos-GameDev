package character;

import main.Room;

import javax.swing.*;
import java.awt.*;

public class Friendly extends NonPlayableCharacter {
    public Friendly() {
        super();
        this.isInvincible = true;
        this.canMove = false;
        this.attackArea = new Rectangle(0, 0, 0, 0);
    }

    public void displayDialog() {


        if (getGp().getCurrentRoomNum() == Room.STARTINGROOM) {
            String dialog = "Hello " + getGp().getPlayer().name + "!";
            JOptionPane.showMessageDialog(null, dialog, "Satyr", JOptionPane.INFORMATION_MESSAGE);
        } else if (getGp().getCurrentRoomNum() == Room.TRAPROOM) {
            String dialog = "Well done " + getGp().getPlayer().name + "!";
            JOptionPane.showMessageDialog(null, dialog, "Knight", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
