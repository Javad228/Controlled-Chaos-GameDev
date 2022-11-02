package save;

import loot.Sword;

/**
 * SimpleWeapon
 *
 * @author Cameron Hofbauer
 * @version
 */
public class SimpleWeapon {
    public String description;
    public double price;
    public boolean isEquipped;
    public String[] imagePaths;
    //TODO: Insert weapon attributes here

    public SimpleWeapon(Sword sword) {
        this.description = sword.getDescription();
        this.price = sword.getPrice();
        this.isEquipped = sword.isEquipped();
        this.imagePaths = sword.getImagePaths();
    }
}
