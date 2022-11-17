package loot;

import character.PlayerCharacter;
import main.GamePanel;
import java.awt.image.BufferedImage;
import main.HealthBar;

public class DamageUp extends PassiveItem {

    private transient GamePanel gp;

    public DamageUp(String[] imagePaths, GamePanel gp, int xCoord, int yCoord) {
        super(7 , imagePaths);
        this.gp = gp;
        setxCoord(xCoord);
        setyCoord(yCoord);

        getImage(imagePaths);
        this.setName("Flaming Sword");
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
