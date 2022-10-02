package loot;

public class Item extends Loot {
    private String description;
    private double price;
    private boolean isEquipped;

    public Item() {
        super();
        this.description = "";
        this.price = 0.0;
        this.isEquipped = false;
    }

    public Item(String description, double price, boolean isEquipped) {
        super();
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
