package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class Boot extends PassiveItem {

    public static transient final String[] DEFAULT_IMAGE_PATHS = new String[]{"/items/boot.png"};

    private transient GamePanel gp;

    public Boot(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Boot");
        this.setDescription("???");
    }

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);

        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            player.setMovementSpeed(player.getMovementSpeed() * 2);
        }
    }
}
