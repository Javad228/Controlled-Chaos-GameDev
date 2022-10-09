package loot;

import character.CharacterType;
import character.Inventory;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Weapon extends Item {
    // TODO: add new fields, modify constructor(s) as necessary

    public Weapon(GamePanel gp, KeyHandler keyH, String[] imagePaths) {
        super(gp, keyH, 7);

        setDefaultValues();
        getImage(imagePaths);
    }

    public void setDefaultValues() {
        this.setxCoord(200);
        this.setyCoord(200);
    }
}
