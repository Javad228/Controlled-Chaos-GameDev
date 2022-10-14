package character;

import combat.DamageType;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * NonPlayableCharacter - An abstract class which defines attributes for a character that is not created by the user.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public abstract class NonPlayableCharacter extends Character {

    //public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public GamePanel gamePanel;

    private int damagePerHit;               // Amount of damage a NonPlayableCharacter can inflict on other Characters
    private DamageType damageType;          // Type of damage a NonPlayableCharacter can inflict
    //private script pathfindingScript      // Pathfinding Script
                                            // TODO: Update pathfindingScript when fully implemented
    //private pattern projectilePattern     // Projectile Pattern
                                            // TODO: Update projectilePattern when fully implemented
    private double attackCooldown;          // Amount of time for a NonPlayableCharacter has to wait in between attacks


    /**
     *  Empty constructor to create a generic NonPlayableCharacter
     */
    public NonPlayableCharacter(GamePanel gp) {
        super();
        this.gamePanel = gp;
        this.damagePerHit = 0;
        this.damageType = DamageType.DEFAULT;
        this.attackCooldown = 1;
    }
    public void setAction(){}

    public void update(){
        setAction();
//        System.out.println(direction);
        if(spriteNum!=1&&spriteNum!=2&&spriteNum!=6) {
            switch (direction) {
                case "up" -> yCoord -= movementSpeed;
                case "down" -> yCoord += movementSpeed;
                case "left" -> xCoord -= movementSpeed;
                case "right" -> xCoord += movementSpeed;
            }
        }

        attacking();

        int frameAdjust = 12;
        spriteCounter++;
        if(spriteCounter > frameAdjust){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2){
                spriteNum = 3;
            } else if (spriteNum == 3){
                spriteNum = 4;
            } else if (spriteNum == 4){
                spriteNum = 5;
            }else if (spriteNum == 5){
                spriteNum = 6;
            }else if (spriteNum == 6){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public void attacking() {
        int currX = this.xCoord;
        int currY = this.yCoord;
        int collisionAreaWidth = this.solidArea.width;
        int collisionAreaHeight = this.solidArea.height;

        switch (direction) {
            case "up" -> yCoord -= attackArea.height;
            case "down" -> yCoord += attackArea.height;
            case "left" -> xCoord -= attackArea.width;
            case "right" -> xCoord += attackArea.width;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        boolean isHit = gamePanel.checker.checkEntity(this, gamePanel.getPlayer());
        //boolean isHit = gamePanel.checker.checkEntity(gamePanel.getPlayer(), this);

        if (isHit) {
            System.out.println("Player took damage");
            gamePanel.getPlayer().setHealth(gamePanel.getPlayer().getHealth()-this.damagePerHit);
        }

        xCoord = currX;
        yCoord = currY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }

    public int getDamagePerHit() {
        return damagePerHit;
    }

    public void setDamagePerHit(int damagePerHit) {
        this.damagePerHit = damagePerHit;
    }

    public double getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(double attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(this.getDirection()) {
            case "up":
                if (this.getSpriteNum() == 1) {
                    image = this.getUp1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getUp2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getUp3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getUp4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getUp5();
                }
                if (this.getSpriteNum() == 6 ) {
                    image = this.getUp6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
            case "down":
                if (this.getSpriteNum() == 1) {
                    image = this.getDown1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getDown2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getDown3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getDown4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getDown5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getDown6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
            case "left":
                if (this.getSpriteNum() == 1) {
                    image = this.getLeft1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getLeft2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getLeft3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getLeft4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getLeft5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getLeft6();
                }
                g2.drawImage(image, this.getxCoord()+this.getWidth(), this.getyCoord(), -this.getWidth(), this.getHeight(), null);
                break;
            case "right":
                if (this.getSpriteNum() == 1) {
                    image = this.getRight1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getRight2();
                }
                if (this.getSpriteNum() == 3) {
                    image = this.getRight3();
                }
                if (this.getSpriteNum() == 4) {
                    image = this.getRight4();
                }
                if (this.getSpriteNum() == 5) {
                    image = this.getRight5();
                }
                if (this.getSpriteNum() == 6) {
                    image = this.getRight6();
                }
                g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
                break;
        }



    }
}
