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
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/arrow/up_1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/arrow/up_2.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/arrow/down_1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/arrow/down_2.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/arrow/left_1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/arrow/left_2.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/arrow/right_1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/arrow/right_2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(this.getDirection()) {
            case "up":
                if (this.getSpriteNum() == 1) {
                    image = this.getUp1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getUp2();
                }
                break;
            case "down":
                if (this.getSpriteNum() == 1) {
                    image = this.getDown1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getDown2();
                }
                break;
            case "left":
                if (this.getSpriteNum() == 1) {
                    image = this.getLeft1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getLeft2();
                }
                break;
            case "right":
                if (this.getSpriteNum() == 1) {
                    image = this.getRight1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getRight2();
                }
                break;
        }

        g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
    }
}
