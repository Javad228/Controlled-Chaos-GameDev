package loot;

import main.GamePanel;
import main.KeyHandler;
import save.SimpleWeapon;

public class Weapon extends Item {
    // TODO: add new fields, modify constructor(s) as necessary

    public Weapon(String[] imagePaths) {
        super(7, imagePaths);

        setDefaultValues();
        getImage(imagePaths);
    }

    public Weapon(Weapon w) {
        super(w.getName(), w.getLootType(), w.getxCoord(), w.getyCoord(),
                w.getDescription(), w.getPrice(), w.isEquipped());
        setDefaultValues();
        this.setLootImages(w.getLootImages());
    }

    public Weapon(SimpleWeapon weapon, KeyHandler keyH) {
        super(7, weapon.description, weapon.price, weapon.isEquipped, weapon.imagePaths);

        setDefaultValues();
        getImage(this.getImagePaths());
    }

    public void setDefaultValues() {
        this.setxCoord(200);
        this.setyCoord(200);
    }
}
