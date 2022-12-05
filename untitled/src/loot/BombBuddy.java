package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class BombBuddy extends Weapon {

    public static transient final String[] DEFAULT_BOMB_BUDDY_PATHS = new String[]{"/items/bomb-buddy.png"};

    private transient GamePanel gp;

    public BombBuddy (String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Bomb Buddy");
        this.setDescription("???");
    }

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);

        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            player.setProjectileName("Bomb");
            player.setShotAvailableTimer(0);
            player.setShotTimerMax(50);
        }
    }
}
