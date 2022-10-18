package loot;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Consumable extends Item {

    private final int healthGiven;

    public int frameCounter = 0;
    public int spriteCounter = 0;
    public boolean isBobbingUp = false;
    public int bobDistance = 3;
    public boolean isVisible = true;
    public int disappearTimer;
    public int disappearTimerDefault;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX = solidArea.x;
    public int collisionAreaDefaultY = solidArea.y;

    public BufferedImage consumableImage = null;

    public Consumable(GamePanel gp, String[] consumableImages) {
        super(gp.keyH, 7, consumableImages);
        this.healthGiven = 20;
        this.disappearTimer = gp.getFps() * 5;
        this.disappearTimerDefault = disappearTimer;

        setDefaultValues();
        getConsumableImage();
    }

    public Consumable(String name, LootType lootType, int xCoord, int yCoord,
                      String description, double price, boolean isEquipped, int healthGiven) {
        super(name, lootType, xCoord, yCoord, description, price, isEquipped);
        this.healthGiven = healthGiven;

        setDefaultValues();
        getConsumableImage();
    }

    private void setDefaultValues() {
        this.setxCoord(500);
        this.setyCoord(500);
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

        if (!isVisible && --disappearTimer == 0) {
            isVisible = true;
            disappearTimer = disappearTimerDefault;
        }
    }

    public void getConsumableImage() {
        try {
            this.consumableImage = ImageIO.read(getClass().getResourceAsStream("/consumables/apple.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        if (!isVisible) return;
        g2.drawImage(consumableImage, this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
    }

    public int getHealthGiven() {
        return healthGiven;
    }

    public int consume() {
        this.isVisible = false;
        return getHealthGiven();
    }
}
