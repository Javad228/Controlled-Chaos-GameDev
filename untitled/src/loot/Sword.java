package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class Sword extends Weapon {
    // TODO: add new fields, modify constructor(s) as necessary
    public static transient final String[] DEFAULT_SWORD_IMAGEPATHS = new String[]{"/weapons/wooden_sword.png"};

    private transient GamePanel gp;
    //private int xCoord;
    //private int yCoord;
    //private boolean isEquipped;

    public Sword(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        //setDefaultValues();
        getImage(imagePaths);

        this.setName("Sword");
        this.setDescription("Basic Sword");
    }

    /*public Sword(Sword b) {
        super(b.getName(), b.getLootType(), b.getxCoord(), b.getyCoord(),
                b.getDescription(), b.getPrice(), b.isEquipped());
        setDefaultValues();
        this.setLootImages(b.getLootImages());
    }*/

    /*public Sword (Weapon p, KeyHandler keyH) {
        super(7, p.description, p.price, p.isEquipped, p.imagePaths);

        setDefaultValues();
        getImage(this.getImagePaths());
    }*/

    /*public void setDefaultValues() {
        this.setxCoord(100);
        this.setyCoord(500);
    }*/

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);
        
        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            //
        }
    }
}
