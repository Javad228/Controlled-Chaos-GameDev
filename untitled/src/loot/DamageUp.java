package loot;

import character.PlayerCharacter;
import main.GamePanel;

public class DamageUp extends PassiveItem {

    public static final transient String[] DEFAULT_DAMAGEUP_PATHS = new String[] {"/items/damage.png"};

    private transient GamePanel gp;

    public DamageUp(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Inner Fire");
        this.setDescription("???");
    }

    public void setEquipped(boolean equipped) {
        super.setEquipped(equipped);

        if (equipped) {
            PlayerCharacter player = gp.getPlayer();
            player.setDamageMod(player.getDamageMod() * 1.5);
        }
    }
}
