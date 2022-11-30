package character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static main.GamePanel.g2;

public class Satyr extends Friendly {
    private int row = 4;
    private int col = 10;

    private boolean firstTalk = true;

    public Satyr(int xCoord, int yCoord) {
        super();
        this.name = "Satyr";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 25;

        col = xCoord / getGp().tileSize;
        row = yCoord / getGp().tileSize;

        //TODO: add more dialog
        //create satyr dialog pool (not currently implemented)
        this.dialogPool = new ArrayList<>();
        String currentDialog = "Be careful of monsters, " + getGp().getPlayer().name + ".";
        this.dialogPool.add(currentDialog);
        currentDialog = "Tip: You can pick up items to help you on your journey.";
        this.dialogPool.add(currentDialog);


        try {
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown3(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown4(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown5(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown6(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public void displayDialog() {
        String dialog;
        if (firstTalk) {
            dialog = "Hello " + getGp().getPlayer().name + "!";
            firstTalk = false;
        } else {
            Random r = new Random();
            dialog = dialogPool.get(r.nextInt(dialogPool.size()));
        }
        JOptionPane.showMessageDialog(null, dialog, "Satyr", JOptionPane.INFORMATION_MESSAGE);
    }


    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
