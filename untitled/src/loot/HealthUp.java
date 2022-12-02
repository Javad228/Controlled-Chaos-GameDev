package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class HealthUp extends PassiveItem {

    public static transient final String[] DEFAULT_HEALTHUP_PATHS = new String[]{"/items/health.png"};

    private transient GamePanel gp;

    public HealthUp(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Heart");
        this.setDescription("???");
    }

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);

        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            player.setMaxHealth(player.getMaxHealth() + 15);
            player.setHealth(player.getMaxHealth());
            //Healthbar reset to new health, max health, width
        }//25 health up, 10 width up
    }
}
