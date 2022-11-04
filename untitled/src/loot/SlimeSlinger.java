package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class SlimeSlinger extends Weapon {
    // TODO: add new fields, modify constructor(s) as necessary

    private transient GamePanel gp;
    //private int xCoord;
    //private int yCoord;
    //private boolean isEquippedOnPlayer;

    public SlimeSlinger(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);

        //setDefaultValues();
        getImage(imagePaths);
        this.setName("Slime Slinger");
        this.setDescription("Basic SlimeSlinger");
    }

    /*public SlimeSlinger(Sword b) {
        super(b.getName(), b.getLootType(), b.getxCoord(), b.getyCoord(),
                b.getDescription(), b.getPrice(), b.isEquipped());
        setDefaultValues();
        this.setLootImages(b.getLootImages());
    }*/

    /*public SlimeSlinger (Weapon p, KeyHandler keyH) {
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
            player.setProjectileName("SlimeBall");
            player.setShotAvailableTimer(0);
            player.setShotTimerMax(25);
        }
    }
}
