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
    GamePanel gp;
    KeyHandler keyH;
    private BufferedImage woodenSword;
    private int spriteCounter = 0; // gives current position of weapon in its movement
    private boolean isBobbingUp = true;
    private int frameCounter = 0;
    private int bobDistance = 3;

    public Weapon(GamePanel gp, KeyHandler keyH) {
        super();
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getWeaponImage();
    }

    public Weapon(Weapon w) {
        super(w.getName(), w.getLootType(), w.getxCoord(), w.getyCoord(),
                w.getDescription(), w.getPrice(), w.isEquipped());
        this.gp = w.gp;
        this.keyH = w.keyH;
        this.setWoodenSword(w.getWoodenSword());
    }

    public void setDefaultValues() {
        this.setxCoord(200);
        this.setyCoord(200);
    }

    public void getWeaponImage() {
        try {
            this.woodenSword = ImageIO.read(getClass().getResourceAsStream("/weapons/wooden_sword.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (frameCounter == 0) { // only update the animation once a ____ (depending on if statement after this)
            if (spriteCounter == 0) { // at the bottom, need to bob up
                isBobbingUp = true;
                spriteCounter++;
                setyCoord(getyCoord() + bobDistance);
            } else if (spriteCounter == 1 && isBobbingUp) { // in the middle of bobbing up
                spriteCounter++;
                setyCoord(getyCoord() + bobDistance);
            } else if (spriteCounter == 2) { // reached the top, need to go down
                isBobbingUp = false;
                spriteCounter--;
                setyCoord(getyCoord() - bobDistance);
            } else if (spriteCounter == 1 && !isBobbingUp) { // in the middle of bobbing down
                spriteCounter--;
                setyCoord(getyCoord() - bobDistance);
            }
        }

        frameCounter++;

        // reset the frame counter when we reach 20 frames
        if (frameCounter == 20) {
            frameCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(woodenSword, this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
    }

    public GamePanel getGp() {
        return this.gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public KeyHandler getKeyH() {
        return this.keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public BufferedImage getWoodenSword() {
        return this.woodenSword;
    }

    public void setWoodenSword(BufferedImage woodenSword) {
        this.woodenSword = woodenSword;
    }
}
