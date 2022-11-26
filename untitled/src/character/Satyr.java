package character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static main.GamePanel.g2;

public class Satyr extends Friendly {
    private int row = 4;
    private int col = 10;

    private boolean dialogOn;

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
        String currentDialog = "Hello " + getGp().getPlayer().name + "!";
        this.dialogPool.add(currentDialog);
        currentDialog = "Be careful of monsters, " + getGp().getPlayer().name + ".";
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

        String dialog = "Hello " + getGp().getPlayer().name + "!";
        System.out.println(dialog);

        JOptionPane.showMessageDialog(null, dialog, "Satyr", JOptionPane.INFORMATION_MESSAGE);

        /*
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.drawString(dialog, this.xCoord-10, this.yCoord-10);
         */
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
