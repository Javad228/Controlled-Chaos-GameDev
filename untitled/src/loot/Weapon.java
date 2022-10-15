package loot;

import main.GamePanel;
import main.KeyHandler;
import save.SimpleWeapon;

public class Weapon extends Item {
    // TODO: add new fields, modify constructor(s) as necessary

    public Weapon(KeyHandler keyH, String[] imagePaths) {
        super(keyH, 7, imagePaths);

        setDefaultValues();
        getImage(imagePaths);
    }

    public Weapon(Weapon w) {
        super(w.getName(), w.getLootType(), w.getxCoord(), w.getyCoord(),
                w.getDescription(), w.getPrice(), w.isEquipped());
        this.keyH = w.keyH;
        setDefaultValues();
        this.setLootImages(w.getLootImages());
    }

    public Weapon(SimpleWeapon weapon, KeyHandler keyH) {
        super(keyH, 7, weapon.description, weapon.price, weapon.isEquipped, weapon.imagePaths);
        this.keyH = keyH;
        /*
        this.setDescription(weapon.description);
        this.setPrice(weapon.price);
        this.setEquipped(weapon.isEquipped);

         */

        setDefaultValues();
        getImage(this.getImagePaths());
        //this.setLootImages(weapon.getLootImages());
    }

    public void setDefaultValues() {
        this.setxCoord(200);
        this.setyCoord(200);
    }
}
