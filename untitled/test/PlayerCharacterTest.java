import character.PlayerCharacter;
import enemy.Slime;
import main.GamePanel;
import main.KeyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCharacterTest {

    @Test
    void attackingHit() {
        KeyHandler k = new KeyHandler();
        GamePanel gp = new GamePanel();
        PlayerCharacter player = new PlayerCharacter(gp,k);
        Slime slime = new Slime(gp);
        System.out.println(slime.solidArea);
        System.out.println(player.solidArea);
        player.xCoord = 303;
        player.yCoord = 280;
        int deductedHealth = gp.enemy.health-1;
        player.attacking();
        assertEquals(gp.enemy.health,deductedHealth);
    }

    @Test
    void attackingMiss() {
        KeyHandler k = new KeyHandler();
        GamePanel gp = new GamePanel();
        PlayerCharacter player = new PlayerCharacter(gp,k);
        Slime slime = new Slime(gp);
        System.out.println(slime.solidArea);
        System.out.println(player.solidArea);
        player.xCoord = 303;
        player.yCoord = 10;
        int deductedHealth = gp.enemy.health;
        player.attacking();
        assertEquals(gp.enemy.health,deductedHealth);
    }


}