package character;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Character {

    GamePanel gp;
    private BufferedImage projectileImage;
    private boolean isMoving;

    public Projectile(GamePanel gp) {
        super();
        this.gp = gp;
    }

    public void set(int xCoord, int yCoord, String direction, int movementSpeed) {  //CombatType type
                                                                //boolean isInvincible
                                                                //Entity user (whether it damages the player or enemies)
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);
        this.setDirection(direction);
        this.setMovementSpeed(movementSpeed);
        this.setMoving(true);
        this.setIsAlive(true);
        //this.type = RANGED;
        //this.user = user;
    }

    public void update(Character entity) {

        if (isMoving) {
            switch (direction) {
                case "up":
                    this.setyCoord(this.getyCoord() - movementSpeed);
                    break;
                case "down":
                    this.setyCoord(this.getyCoord() + movementSpeed);
                    break;
                case "left":
                    this.setxCoord(this.getxCoord() - movementSpeed);
                    break;
                case "right":
                    this.setxCoord(this.getxCoord() + movementSpeed);
            }
        }

        int currentWorldX = xCoord;
        int currentWorldY = yCoord;
        int collisionAreaWidth = solidArea.width;
        int collisionAreaHeight = solidArea.height;


        switch (direction) {
            case "up" -> yCoord -= attackArea.height;
            case "down" -> yCoord += attackArea.height;
            case "left" -> xCoord -= attackArea.width;
            case "right" -> xCoord += attackArea.width;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        Boolean isHit = gp.checker.checkEntityCollision(this, gp.enemies[0]);
        if(isHit){
            System.out.println("Hit");
            //Potentially add if statement for piercing effects where projectile isn't destroyed
            this.setIsAlive(false);
        }


        //After checking collision, restore original data
        xCoord = currentWorldX;
        yCoord = currentWorldY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;        
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(projectileImage, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);
    }

    public BufferedImage getProjectileImage() {
        return projectileImage;
    }

    public void setProjectileImage(BufferedImage projectileImage) {
        this.projectileImage = projectileImage;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}