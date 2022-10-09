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
    GamePanel gp;
    KeyHandler keyH;
    private BufferedImage woodenSword;
    private int spriteCounter = 0; // gives current position of weapon in its movement
    private boolean isBobbingUp = true;
    private int frameCounter = 0;
    private int bobIncrement = 1;
    private int maxSpriteCounter = 6;

    public Weapon(GamePanel gp, KeyHandler keyH) {
        super();
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getWeaponImage();
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
                setyCoord(getyCoord() + bobIncrement);
            } else if (spriteCounter < maxSpriteCounter && isBobbingUp) { // in the middle of bobbing up
                spriteCounter++;
                setyCoord(getyCoord() + bobIncrement);
            } else if (spriteCounter == maxSpriteCounter) { // reached the top, need to go down
                isBobbingUp = false;
                spriteCounter--;
                setyCoord(getyCoord() - bobIncrement);
            } else if (spriteCounter < maxSpriteCounter && !isBobbingUp) { // in the middle of bobbing down
                spriteCounter--;
                setyCoord(getyCoord() - bobIncrement);
            } else {
                System.out.format("Received bad sprite counter value (%d)/\n", spriteCounter);
            }
        }

        frameCounter++;

        // reset the frame counter when we reach 20 frames
        if (frameCounter == 7) {
            frameCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(woodenSword, this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
    }
}
