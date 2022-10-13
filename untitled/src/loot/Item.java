package loot;

import main.GamePanel;
import main.KeyHandler;

public class Item extends Loot {
    private String description;
    private double price;
    private boolean isEquipped;

    public Item(GamePanel gp, KeyHandler keyH, int framesToWait) {
        super(gp, keyH, framesToWait);
        this.description = "";
        this.price = 0.0;
        this.isEquipped = false;
    }

    public Item(GamePanel gp, KeyHandler keyH, int framesToWait, String description, double price, boolean isEquipped) {
        super(gp, keyH, framesToWait);
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

    public String getDescription() {
        return description;
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
}
