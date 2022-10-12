package character;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Character {

    private BufferedImage projectileImage;
    private boolean isMoving;

    public Projectile() {
        super();
    }

    public void set(int xCoord, int yCoord, String direction, int movementSpeed) {  //CombatType type
                                                                //boolean isInvincible
                                                                //Entity user (whether it damages the player or enemies)
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);
        this.setDirection(direction);
        this.setMovementSpeed(movementSpeed);
        this.setMoving(true);
        //this.type = RANGED;
        //this.user = user;
    }

    public void update() {
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