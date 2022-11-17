package character;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Arrow extends Projectile {

    GamePanel gp;
    private BufferedImage arrowUp;
    private BufferedImage arrowDown;
    private BufferedImage arrowRight;
    private BufferedImage arrowLeft;

    public Arrow(GamePanel gp, int xCoord, int yCoord, String direction, boolean isPlayerShooting, double damageMod) {
        super(gp, xCoord, yCoord, direction, isPlayerShooting, damageMod);
        this.gp = gp;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        this.setName("Arrow");
        this.setMovementSpeed(5);
        this.setWidth(7);
        this.setHeight(26);
        this.setHealth(80);
        this.setDamage(5);
        //this.isInvincible(true);
    }

    public void getImage() {
        try {
            this.setArrowUp(ImageIO.read(getClass().getResourceAsStream("/weapons/arrow_up.png")));
            this.setArrowDown(ImageIO.read(getClass().getResourceAsStream("/weapons/arrow_down.png")));
            this.setArrowRight(ImageIO.read(getClass().getResourceAsStream("/weapons/arrow_right.png")));
            this.setArrowLeft(ImageIO.read(getClass().getResourceAsStream("/weapons/arrow_left.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (this.isMoving()) {
            switch (this.getDirection()) {
                case "up":
                    this.setProjectileImage(arrowUp);
                    this.setWidth(7);
                    this.setHeight(26);
                    break;
                case "down":
                    this.setProjectileImage(arrowDown);
                    this.setWidth(7);
                    this.setHeight(26);
                    break;
                case "left":
                    this.setProjectileImage(arrowLeft);
                    this.setWidth(26);
                    this.setHeight(7);
                    break;
                case "right":
                    this.setProjectileImage(arrowRight);
                    this.setWidth(26);
                    this.setHeight(7);
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
