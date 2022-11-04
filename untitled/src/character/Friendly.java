package character;

import java.awt.*;

public class Friendly extends NonPlayableCharacter {
    public Friendly() {
        super();
        this.isInvincible = true;
        this.canMove = false;
        this.attackArea = new Rectangle(0, 0, 0, 0);
    }
}
