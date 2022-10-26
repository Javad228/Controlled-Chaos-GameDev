package loot;

import main.GamePanel;
import main.KeyHandler;
import main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Consumable extends Item {

    private final int healthGiven;

    public int frameCounter = 0;
    public boolean isVisible = true;
    public int disappearTimer;
    public int disappearTimerDefault;

    public BufferedImage consumableImage = null;

    public Consumable(KeyHandler keyH, String[] consumableImages) {
        super(keyH, 7, consumableImages);
        this.healthGiven = 20;
        this.disappearTimer = 60 * 5;   //TODO: Set constant timer to respawn item instead of user-set FPS
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

        super.update();

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
