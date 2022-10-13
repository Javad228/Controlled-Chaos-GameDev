package loot;

import character.CharacterType;
import character.Inventory;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Key;

public class Weapon extends Item {
    // TODO: add new fields, modify constructor(s) as necessary

    public Weapon(GamePanel gp, KeyHandler keyH, String[] imagePaths) {
        super(gp, keyH, 7);

        setDefaultValues();
        getImage(imagePaths);
    }

    public Weapon(Weapon w) {
        super(w.getName(), w.getLootType(), w.getxCoord(), w.getyCoord(),
                w.getDescription(), w.getPrice(), w.isEquipped());
        this.gp = w.gp;
        this.keyH = w.keyH;
        this.setWoodenSword(w.getWoodenSword());
    }

    public Weapon(SimpleWeapon weapon, GamePanel gp, KeyHandler keyH) {
        super();
        this.gp = gp;
        this.keyH = keyH;
        this.setDescription(weapon.description);
        this.setPrice(weapon.price);
        this.setEquipped(weapon.isEquipped);

        setDefaultValues();
        getWeaponImage();
    }

    public void setDefaultValues() {
        this.setxCoord(200);
        this.setyCoord(200);
    }
}
