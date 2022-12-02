package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class RapidFire extends PassiveItem {

    public static transient final String[] DEFAULT_IMAGE_PATHS = new String[]{"/items/rapid-fire.png"};

    private transient GamePanel gp;

    public RapidFire(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Arrow Storm");
        this.setDescription("???");
    }

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);

        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            player.setShotAvailableTimer(0);
            player.setShotTimerMax(player.getShotTimerMax() - 20);
        }
    }
}

