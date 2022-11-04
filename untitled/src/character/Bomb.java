package character;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends Projectile {

    GamePanel gp;
    private BufferedImage bombUp;
    private BufferedImage bombDown;
    private BufferedImage bombRight;
    private BufferedImage bombLeft;
    private BufferedImage explode1;
    private BufferedImage explode2;
    private BufferedImage explode3;
    private BufferedImage explode4;
    private BufferedImage explode5;

    public Bomb(GamePanel gp, int xCoord, int yCoord, String direction, boolean isPlayerShooting) {
        super(gp, xCoord, yCoord, direction, isPlayerShooting);
        this.gp = gp;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        this.setName("Bomb");
        this.setMovementSpeed(3);
        this.setWidth(7);
        this.setHeight(26);
        this.setHealth(80);
        this.setDamage(5);
        //this.isInvincible(true);
    }

    public void getImage() {
        try {
            this.setBombUp(ImageIO.read(getClass().getResourceAsStream("/items/bomb/Sprite-0001.png")));
            this.setBombDown(ImageIO.read(getClass().getResourceAsStream("/items/bomb/Sprite-0001.png")));
            this.setBombRight(ImageIO.read(getClass().getResourceAsStream("/items/bomb/Sprite-0001.png")));
            this.setBombLeft(ImageIO.read(getClass().getResourceAsStream("/items/bomb/Sprite-0001.png")));
            this.setExplode1(ImageIO.read(getClass().getResourceAsStream("/items/bomb/tile002.png")));
            this.setExplode2(ImageIO.read(getClass().getResourceAsStream("/items/bomb/tile004.png")));
            this.setExplode3(ImageIO.read(getClass().getResourceAsStream("/items/bomb/tile006.png")));
            this.setExplode4(ImageIO.read(getClass().getResourceAsStream("/items/bomb/tile008.png")));
            this.setExplode5(ImageIO.read(getClass().getResourceAsStream("/items/bomb/tile010.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if(bombExploded){
            BufferedImage image = null;
            switch (getSpriteNum()) {       // Set width and height parameters according to death image

                case 0 -> {
                    image = this.getExplode1();
                    this.setWidth(200);
                    this.setHeight(200);
                }
                case 1 -> {
                    image = this.getExplode2();
                    this.setWidth(200);
                    this.setHeight(200);
                }
                case 2 -> {
                    image = this.getExplode3();
                    this.setWidth(200);
                    this.setHeight(200);
                }
            }
            g2.drawImage(image, xCoord-40, yCoord-40, this.getWidth(), this.getHeight(), null);
        }else if (this.isMoving()) {
            switch (this.getDirection()) {
                case "up":
                    this.setProjectileImage(bombUp);
                    this.setWidth(26);
                    this.setHeight(26);
                    break;
                case "down":
                    this.setProjectileImage(bombDown);
                    this.setWidth(26);
                    this.setHeight(26);
                    break;
                case "left":
                    this.setProjectileImage(bombLeft);
                    this.setWidth(26);
                    this.setHeight(26);
                    break;
                case "right":
                    this.setProjectileImage(bombRight);
                    this.setWidth(26);
                    this.setHeight(26);
                    break;
            }
            g2.drawImage(this.getProjectileImage(), this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
        }

    }

    public BufferedImage getBombUp() {
        return bombUp;
    }

    public void setBombUp(BufferedImage bombUp) {
        this.bombUp = bombUp;
    }

    public BufferedImage getBombDown() {
        return bombDown;
    }

    public void setBombDown(BufferedImage bombDown) {
        this.bombDown = bombDown;
    }

    public BufferedImage getBombRight() {
        return bombRight;
    }

    public void setBombRight(BufferedImage bombRight) {
        this.bombRight = bombRight;
    }

    public BufferedImage getBombLeft() {
        return bombLeft;
    }

    public void setBombLeft(BufferedImage bombLeft) {
        this.bombLeft = bombLeft;
    }

    public BufferedImage getExplode1() {
        return explode1;
    }

    public void setExplode1(BufferedImage explode1) {
        this.explode1 = explode1;
    }

    public BufferedImage getExplode2() {
        return explode2;
    }

    public void setExplode2(BufferedImage explode2) {
        this.explode2 = explode2;
    }

    public BufferedImage getExplode3() {
        return explode3;
    }

    public void setExplode3(BufferedImage explode3) {
        this.explode3 = explode3;
    }

    public BufferedImage getExplode4() {
        return explode4;
    }

    public void setExplode4(BufferedImage explode4) {
        this.explode4 = explode4;
    }

    public BufferedImage getExplode5() {
        return explode5;
    }

    public void setExplode5(BufferedImage explode5) {
        this.explode5 = explode5;
    }
}
