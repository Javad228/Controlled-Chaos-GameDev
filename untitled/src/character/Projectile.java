package character;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Projectile extends Character {

    transient GamePanel gp;
    private transient BufferedImage projectileImage;
    private boolean isMoving;
    private double damage;
    private boolean isPlayerShooting;
    public boolean bombExploded;
    private double damageMod;

    public Projectile(GamePanel gp, int xCoord, int yCoord, String direction, boolean isPlayerShooting, double damageMod) {
        super();
        this.gp = gp;
                                                                //CombatType type
                                                                //boolean isInvincible
                                                                //Entity user (whether it damages the player or enemies)
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);
        this.solidArea.x = 20;
        this.solidArea.y = 20;
        this.setDirection(direction);
        this.setMovementSpeed(movementSpeed);
        this.setMoving(true);
        this.setIsAlive(true);
        this.maxHealth = 300;
        this.health = this.maxHealth;
        this.isPlayerShooting = isPlayerShooting;
        gp.projectileList.add(this);
        this.damageMod = damageMod;

        //this.type = RANGED;
        //this.user = user;
    }

    public void update(GamePanel gp) {
        health--;
        if (health < 1) {
            this.kill();
        }
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

        if (this.isPlayerShooting) {
            if (gp.getRooms().get(gp.getCurrentRoomNum()).getEnemies() != null){
                ArrayList<Enemy> currentList = gp.getRooms().get(gp.getCurrentRoomNum()).getEnemies();
                for (int i = 0; i < currentList.size(); i++) {
                    Enemy enemy = currentList.get(i);
                    Boolean isHit = gp.checker.checkEntityAttack(this, enemy);
                    if(isHit) {
                        if(Objects.equals(this.getName(), "Bomb")){
                            setSpriteNum(0);
                            setSpriteCounter(0);
                            setSpriteCounter(getSpriteCounter() + 1);
                            if (getSpriteNum() < 2 && getSpriteCounter() == 20) {
                                setSpriteNum(getSpriteNum() + 1);   // Increment sprite num for animation
                                setSpriteCounter(0);
                            }
                            bombExploded = true;
                            for(int j = 0; j< currentList.size(); j++){
                                double xy1 = Math.sqrt(Math.pow(currentList.get(j).getxCoord(),2) + Math.pow(currentList.get(j).getyCoord(),2));
                                double xy2 = Math.sqrt(Math.pow(enemy.getxCoord(),2) + Math.pow(enemy.getyCoord(),2));
                                if(Math.abs(xy1-xy2)<30 && Math.abs(xy1-xy2)>5){
                                    currentList.get(j).setHealth(currentList.get(j).getHealth() - this.damage);
//                                    this.setIsAlive(false);
                                }
                            }
                            currentList.get(i).setHealth(currentList.get(i).getHealth() - this.damage);
                            isMoving = false;
                        }else{
                            currentList.get(i).setHealth(currentList.get(i).getHealth() - this.damage);
                            //Potentially add if statement for piercing effects where projectile isn't destroyed
                            this.setIsAlive(false);
                        }

                        enemy.checkIfDead(gp.player);

                    }
                }
            }
        }
        else {
            boolean isHit = gp.checker.checkEntityCollision(this, gp.getPlayer());

            if (isHit) {
                gp.getPlayer().damagePlayer(this);
                this.setIsAlive(false);
            }
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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage * damageMod;
    }

    public boolean getIsPlayerShooting() {
        return isPlayerShooting;
    }

    public void setIsPlayerShooting(boolean isPlayerShooting) {
        this.isPlayerShooting = isPlayerShooting;
    }
}