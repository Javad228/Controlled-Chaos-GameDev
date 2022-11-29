package character;

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
            questStatus = INPROGRESS;
        } else if (this.questStatus == INPROGRESS) {
            dialog = questDialog.get(INPROGRESS);
        } else {
            dialog = "Hello!";
        }

        JOptionPane.showMessageDialog(null, dialog, "Satyr", JOptionPane.INFORMATION_MESSAGE);
    }
}
