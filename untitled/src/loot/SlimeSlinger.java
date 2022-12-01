package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class SlimeSlinger extends Weapon {

    public static transient final String[] DEFAULT_IMAGE_PATHS = new String[]{"/items/slingshot.png"};

    private transient GamePanel gp;

    public SlimeSlinger(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Slime Slinger");
        this.setDescription("???");
    }

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
