import character.PlayerCharacter;
import enemy.Slime;
import main.GamePanel;
import main.KeyHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonPlayableCharacterTest {

    @Test
    void testInvincible() {
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
        gp.enemy.invincible = true;
        for(int i = 0; i<31;i++){
            gp.enemy.update();
        }
        assertEquals(gp.enemy.invincibleCounter,0);
        assertEquals(gp.enemy.invincible,false);
    }

    @Test
    void testNotInvincible() {
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
        gp.enemy.invincible = true;
        for(int i = 0; i<30;i++){
            gp.enemy.update();
        }
        assertEquals(gp.enemy.invincibleCounter,30);
        assertEquals(gp.enemy.invincible,true);
    }
}