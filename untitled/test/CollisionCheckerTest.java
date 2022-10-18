import character.PlayerCharacter;
import enemy.Slime;
import main.GamePanel;
import main.KeyHandler;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {

    @org.junit.jupiter.api.Test
    void checkHitTrue() {
        KeyHandler k = new KeyHandler();
        GamePanel gp = new GamePanel();
        PlayerCharacter player = new PlayerCharacter(gp,k);
        Slime slime = new Slime(gp);
        System.out.println(slime.solidArea);
        System.out.println(player.solidArea);
        player.xCoord = 303;
        player.yCoord = 303;
        assertTrue(gp.checker.checkEntity(player, slime));
    }

    @org.junit.jupiter.api.Test
    void checkHitFalse() {
        KeyHandler k = new KeyHandler();
        GamePanel gp = new GamePanel();
        PlayerCharacter player = new PlayerCharacter(gp,k);
        Slime slime = new Slime(gp);
        System.out.println(slime.solidArea);
        System.out.println(player.solidArea);
        player.xCoord = 400;
        player.yCoord = 303;
        assertFalse(gp.checker.checkEntity(player, slime));
    }
}