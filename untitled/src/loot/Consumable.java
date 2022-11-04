package loot;

import main.Audio;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Consumable extends Item {

    private final int healthGiven;

    private boolean isRespawnable;
    public boolean isConsumed;
    public boolean isVisible = true;
    public int disappearTimer;
    public int disappearTimerDefault;

    public transient BufferedImage consumableImage = null;

    public Consumable(String[] consumableImages) {
        super(7, consumableImages);
        this.healthGiven = 20;
        this.disappearTimer = 60 * 5;   //TODO: Set constant timer to respawn item instead of user-set FPS
        this.disappearTimerDefault = disappearTimer;
        this.isRespawnable = true;
        this.isConsumed = false;

        setDefaultValues();
        getConsumableImage();
    }

    public Consumable(String[] consumableImages, boolean isRespawnable) {
        this(consumableImages);
        this.isRespawnable = isRespawnable;
    }

    public Consumable(String name, LootType lootType, int xCoord, int yCoord,
                      String description, double price, boolean isEquipped, int healthGiven, boolean isRespawnable) {
        super(name, lootType, xCoord, yCoord, description, price, isEquipped);
        this.healthGiven = healthGiven;
        this.isRespawnable = isRespawnable;
        this.isConsumed = false;

        setDefaultValues();
        getConsumableImage();
    }

    private void setDefaultValues() {
        //this.setxCoord(500);
        //this.setyCoord(500);
        this.setName("Apple");
        this.setDescription("Basic apple");
    }

    public void update() {

        //if (consumableImage.)

        super.update();

        if (isRespawnable && !isVisible && --disappearTimer == 0) {
            isVisible = true;
            disappearTimer = disappearTimerDefault;
        }
    }

    public void getConsumableImage() {
        try {
            this.consumableImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/consumables/apple.png")));
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
        if (!isRespawnable) this.isConsumed = true;
        return getHealthGiven();
    }
}
