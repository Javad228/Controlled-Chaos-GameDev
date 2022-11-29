package character;

import loot.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Knight extends Friendly{
    public static final int room4Row = 6;
    public static final int room4Col = 10;
    private int questStatus;

    public Knight(int xCoord, int yCoord) {
        super();
        this.name = "Knight";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 25;
        this.questStatus = NOTSTARTED;


        //TODO: add more dialog
        //create knight dialog pool (not currently implemented)
        questDialog = new ArrayList<>();
        String currentDialog = "Hello. I seem to have misplaced my sword. Do you think you can look around and find one I could use?";
        this.questDialog.add(currentDialog);
        currentDialog = "Have you found a sword for me yet?";
        this.questDialog.add(currentDialog);
        currentDialog = "Thanks! Here's some coin for your trouble.";
        this.questDialog.add(currentDialog);

        this.dialogPool = new ArrayList<>();
        currentDialog = "I think I'll rest here a while. You go on ahead.";
        this.dialogPool.add(currentDialog);
        currentDialog = "Careful up ahead, " + getGp().getPlayer().name + ".";
        this.dialogPool.add(currentDialog);
        currentDialog = "Thanks for finding me a sword earlier.";
        this.dialogPool.add(currentDialog);



        try {
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown3(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown4(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown5(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown6(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));

        } catch (
                IOException exception) {
            exception.printStackTrace();
        }
    }

    public void displayDialog() {
        String dialog;
        if (this.questStatus == NOTSTARTED) {
            dialog = questDialog.get(NOTSTARTED);
            JOptionPane.showMessageDialog(null, dialog, "Knight", JOptionPane.INFORMATION_MESSAGE);
            questStatus = INPROGRESS;
        } else if (this.questStatus == INPROGRESS) {
            dialog = questDialog.get(INPROGRESS);
            JOptionPane.showMessageDialog(null, dialog, "Knight", JOptionPane.INFORMATION_MESSAGE);
            ArrayList<Item> inventoryList = getGp().getPlayer().getInventory().getListOfItems();
            int i;
            for (i = 0; i < inventoryList.size(); i++) {
                if (inventoryList.get(i).getDescription().equals("Basic Sword")) {
                    int give = JOptionPane.showConfirmDialog(null,
                            "Would you like to give the knight a wooden sword from your inventory?",
                            "Quest", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (give == JOptionPane.YES_OPTION) {
                        inventoryList.remove(i);
                        JOptionPane.showMessageDialog(null,
                                "Thanks! Here are some coins for your trouble.",
                                "Knight", JOptionPane.INFORMATION_MESSAGE);
                        getGp().getPlayer().setNumCoins(getGp().getPlayer().getNumCoins() + 2);
                        this.questStatus = COMPLETED;
                    }
                    i--;    //use to check if entire list has been passed through
                    break;
                }
            }

            if (i == inventoryList.size()) {
                JOptionPane.showMessageDialog(null,
                        "You have nothing to give. Find a basic sword to help the knight on her journey!",
                        "Knight's Dilemma", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            //randomize here
            dialog = "Hello!";
            JOptionPane.showMessageDialog(null, dialog, "Knight", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
