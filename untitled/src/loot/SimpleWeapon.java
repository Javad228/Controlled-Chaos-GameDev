package loot;

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
    //TODO: Insert weapon attributes here

    public SimpleWeapon(Weapon weapon) {
        this.description = weapon.getDescription();
        this.price = weapon.getPrice();
        this.isEquipped = weapon.isEquipped();
    }
}
