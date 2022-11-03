package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class Boot extends PassiveItem {

    private transient GamePanel gp;
    //private int xCoord;
    //private int yCoord;
    //private boolean isEquippedOnPlayer;

    public Boot(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        //setDefaultValues();
        getImage(imagePaths);
    }

    /*public Boot(Boot b) {
        super(b.getName(), b.getLootType(), b.getxCoord(), b.getyCoord(),
                b.getDescription(), b.getPrice(), b.isEquipped());
        setDefaultValues();
        this.setLootImages(b.getLootImages());
    }*/

    /*public Boot (SimplePassiveItem p, KeyHandler keyH) {
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
            player.setMovementSpeed(player.getMovementSpeed() * 2);
        }
    }
}
