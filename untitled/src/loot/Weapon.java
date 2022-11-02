package loot;

import main.GamePanel;
import main.KeyHandler;
import save.SimpleWeapon;

public class Weapon extends Item {
    // TODO: add new fields, modify constructor(s) as necessary
    private transient GamePanel gp;

    public Weapon(int framesToWait, String[] imagePaths) {
        super(7 , imagePaths);

        //setDefaultValues();
        getImage(imagePaths);
    }

    public Weapon(Weapon p) {
        super(p.getName(), p.getLootType(), p.getxCoord(), p.getyCoord(),
                p.getDescription(), p.getPrice(), p.isEquipped());
        setDefaultValues();
        this.setLootImages(p.getLootImages());
    }

    public Weapon(SimpleWeapon p, KeyHandler keyH) {
        super(7, p.description, p.price, p.isEquipped, p.imagePaths);

        setDefaultValues();
        getImage(this.getImagePaths());
    }

    public void setDefaultValues() {
        this.setxCoord(100);
        this.setyCoord(500);
    }
}
