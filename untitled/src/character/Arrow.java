package character;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Arrow extends Projectile {

    GamePanel gp;

    public Arrow(GamePanel gp) {
        super();
        this.gp = gp;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        this.setName("Arrow");
        this.setxCoord(50);
        this.setyCoord(50);
        this.setMovementSpeed(5);
        this.setDirection("down");
        this.setWidth(18);
        this.setHeight(46);
        this.setHealth(80);
        //this.setDamage(2);
        //this.isInvincible(true);
    }

    public void getImage() {
        try {
            this.setProjectileImage(ImageIO.read(getClass().getResourceAsStream("/weapons/wooden_sword.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
