package enemy;

import character.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SlimeBall extends Projectile {

    transient GamePanel gp;
    private transient BufferedImage arrowUp;
    private transient BufferedImage arrowDown;
    private transient BufferedImage arrowRight;
    private transient BufferedImage arrowLeft;

    public SlimeBall(GamePanel gp, int xCoord, int yCoord, String direction, boolean isPlayerShooting) {
        super(gp, xCoord, yCoord, direction, isPlayerShooting);
        this.gp = gp;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        this.setName("SlimeBall");
        this.setMovementSpeed(2);
        this.setWidth(200);
        this.setHeight(200);
        this.setHealth(200);
        this.setDamage(20);
        //this.setDamage(2);
        //this.isInvincible(true);
    }

    public void getImage() {
        try {
            this.setArrowUp(ImageIO.read(getClass().getResourceAsStream("/wizard/projectile/tile000.png")));
            this.setArrowDown(ImageIO.read(getClass().getResourceAsStream("/wizard/projectile/tile000.png")));
            this.setArrowRight(ImageIO.read(getClass().getResourceAsStream("/wizard/projectile/tile000.png")));
            this.setArrowLeft(ImageIO.read(getClass().getResourceAsStream("/wizard/projectile/tile000.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update(GamePanel gp) {
        if (getArrowUp() == null)   getImage();

        super.update(gp);
    }

    public void draw(Graphics2D g2) {
        if (this.isMoving()) {
            switch (this.getDirection()) {
                case "up":
                    this.setProjectileImage(arrowUp);
                    this.setWidth(25);
                    this.setHeight(50);
                    break;
                case "down":
                    this.setProjectileImage(arrowDown);
                    this.setWidth(25);
                    this.setHeight(50);
                    break;
                case "left":
                    this.setProjectileImage(arrowLeft);
                    this.setWidth(25);
                    this.setHeight(50);
                    break;
                case "right":
                    this.setProjectileImage(arrowRight);
                    this.setWidth(25);
                    this.setHeight(50);
            }
        }
        g2.drawImage(this.getProjectileImage(), this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
    }

    public BufferedImage getArrowUp() {
        return arrowUp;
    }

    public void setArrowUp(BufferedImage arrowUp) {
        this.arrowUp = arrowUp;
    }

    public BufferedImage getArrowDown() {
        return arrowDown;
    }

    public void setArrowDown(BufferedImage arrowDown) {
        this.arrowDown = arrowDown;
    }

    public BufferedImage getArrowRight() {
        return arrowRight;
    }

    public void setArrowRight(BufferedImage arrowRight) {
        this.arrowRight = arrowRight;
    }

    public BufferedImage getArrowLeft() {
        return arrowLeft;
    }

    public void setArrowLeft(BufferedImage arrowLeft) {
        this.arrowLeft = arrowLeft;
    }
}
