package save;

import loot.PassiveItem;

public class SimplePassiveItem {
    public String description;
    public double price;
    public boolean isEquipped;
    public String[] imagePaths;
    //TODO: Insert item attributes here

    public SimplePassiveItem(PassiveItem p) {
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.isEquipped = p.isEquipped();
        this.imagePaths = p.getImagePaths();
    }
}
