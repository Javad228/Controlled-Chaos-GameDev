package loot;

import main.GamePanel;
import main.KeyHandler;
import save.SimplePassiveItem;

public class PassiveItem extends Item {
    // TODO: add new fields, modify constructor(s) as necessary

    public PassiveItem(KeyHandler keyH, String[] imagePaths) {
        super(keyH, 7, imagePaths);

        setDefaultValues();
        getImage(imagePaths);
    }

    public PassiveItem(PassiveItem p) {
        super(p.getName(), p.getLootType(), p.getxCoord(), p.getyCoord(),
                p.getDescription(), p.getPrice(), p.isEquipped());
        this.keyH = p.keyH;
        setDefaultValues();
        this.setLootImages(p.getLootImages());
    }

    public PassiveItem(SimplePassiveItem p, KeyHandler keyH) {
        super(keyH, 7, p.description, p.price, p.isEquipped, p.imagePaths);
        this.keyH = keyH;
        /*
        this.setDescription(weapon.description);
        this.setPrice(weapon.price);
        this.setEquipped(weapon.isEquipped);

         */

        setDefaultValues();
        getImage(this.getImagePaths());
    }

    public void setDefaultValues() {
        this.setxCoord(100);
        this.setyCoord(500);
    }
}
