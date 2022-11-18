package loot;

import main.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item extends Loot {
    private String description;
    private double price;
    private boolean isEquipped;
    public transient BufferedImage still;
    public int amount;
    private transient GamePanel gp;

    public Item(int framesToWait, String[] imagePaths) {
        super(framesToWait, imagePaths);
        this.description = "";
        this.price = 0.0;
        this.isEquipped = false;
        this.gp = gp;
    }

    public Item(int framesToWait, String[] imagePaths, int xCoord, int yCoord) {
        super(framesToWait, imagePaths, xCoord, yCoord);
    }

    public Item(int framesToWait, String description, double price, boolean isEquipped, String[] imagePaths) {
        super(framesToWait, imagePaths);
        this.description = description;
        this.price = price;
        this.isEquipped = isEquipped;
    }

    public Item(String name, LootType lootType, int xCoord, int yCoord,
                String description, double price, boolean isEquipped) {
        super(name, lootType, xCoord, yCoord);
        this.description = description;
        this.price = price;
        this.isEquipped = isEquipped;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        super.draw(g2, gp);

        // Determine if the player is within proximity of the item
        // If so, draw the description string on the gamepanel
        if (gp.checker.checkLootApproach(gp.player, this)) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(this.getWrappedDescription(), this.getxCoord()-10, this.getyCoord()-10);
        }
    }

    public String getDescription() {
        return description;
    }

    public String getWrappedDescription() {
        if (this.description.length() > 12) return this.description.substring(0, 12) + "...";
        else    return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public static void setUpItemListImages(ArrayList<Item> list) {
        if (list == null)   return;
        for (Item item : list) {
            item.getImage(item.getImagePaths());
        }
    }

    /*
    Item IDs
    0 - HealthUp
    1 - DamageUp
    2 - Boot
    3 - SlimeSlinger
    4 - RapidFire

     */
}
