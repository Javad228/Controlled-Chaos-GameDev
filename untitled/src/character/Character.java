package character;

/*
 * Character Class - An abstract class which models a character containing basic information pertaining to the game.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */


import combat.CombatType;
import main.Audio;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Character {
    public boolean attacking = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean isAlive = true;
    public Rectangle attackArea = new Rectangle(0, 0,8,8);
    public String name;                        // Character name
    public int health;
    public int maxHealth;                   // Character health
    public int movementSpeed;               // Character movement speed
    public int xCoord;                         // Character x-position in a room
    public int yCoord;                         // Character y-position in a room
    public int height;                         // Character height in pixels
    public int width;                          // Character width in pixels
    public ArrayList<String> activeEffects;    // Character active effects in game
    public CombatType type;                    // Character combat type
    public double timeForInvincibility;        // Character time for invincibility after combat hit

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int actionLockCounter = 0;
    public boolean collisionOn = false;
    private BufferedImage up1, up2,up3,up4,up5,up6, down1, down2,down3,down4,down5,down6, left1, left2,left3,left4,left5,left6, right1, right2,right3,right4,right5,right6;
    public String direction = "down";

    public int spriteCounter = 0;

    public int spriteNum = 1;

    public int collisionAreaDefaultY;
    public int collisionAreaDefaultX;

    /**
     * Empty constructor to create a generic Character
     */
    public Character() {
        this.name = "";
        this.maxHealth = 100;
        this.health = maxHealth;
        this.movementSpeed = 1;
        this.xCoord = 300;
        this.yCoord = 300;
        this.height = 50;
        this.width = 50;
        this.activeEffects = new ArrayList<>();
        this.type = CombatType.DEFAULT;
        this.timeForInvincibility = 1;
    }

    /**
     * Constructor to create Character Object from the user
     *
     * @param name User-inputted name to name this Character
     * @param type User-inputted combat type for this Character
     */
    public Character(String name, CombatType type) {
        this();
        this.name = name;
        this.type = type;
    }

    /**
     * Constructor to create Character object from file
     *
     * @param name Character name
     * @param health Health this Character has
     * @param movementSpeed Speed of which this Character moves
     * @param xCoord x-Coordinate of this Character
     * @param yCoord y-Coordinate of this Character
     * @param height Height of the character
     * @param width Width of the character
     * @param activeEffects Display and apply any affects that this Character possesses
     * @param type Character Combat type
     * @param timeForInvincibility Time for invincibility given a hit occurs against this Character
     */
    public Character(String name, int health, int movementSpeed, int xCoord, int yCoord, int height, int width,
                     ArrayList<String> activeEffects, CombatType type, double timeForInvincibility) {
        this.name = name;
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = height;
        this.width = width;
        this.activeEffects = activeEffects;
        this.type = type;
        this.timeForInvincibility = timeForInvincibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (this instanceof PlayerCharacter) {
            Audio.playerDamagedAudio();
        }
        this.health = health;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<String> getActiveEffects() {
        return activeEffects;
    }

    public void setActiveEffects(ArrayList<String> activeEffects) {
        this.activeEffects = activeEffects;
    }

    public CombatType getType() {
        return type;
    }

    public void setType(CombatType type) {
        this.type = type;
    }

    public double getTimeForInvincibility() {
        return timeForInvincibility;
    }

    public void setTimeForInvincibility(double timeForInvincibility) {
        this.timeForInvincibility = timeForInvincibility;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public void setUp1(BufferedImage up1) {
        this.up1 = up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public void setUp2(BufferedImage up2) {
        this.up2 = up2;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public void setDown1(BufferedImage down1) {
        this.down1 = down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public void setDown2(BufferedImage down2) {
        this.down2 = down2;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public void setLeft1(BufferedImage left1) {
        this.left1 = left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public void setLeft2(BufferedImage left2) {
        this.left2 = left2;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public void setRight1(BufferedImage right1) {
        this.right1 = right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public void setRight2(BufferedImage right2) {
        this.right2 = right2;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        if (!this.name.equals(((Character) o).getName())) return false;
        if (this.health != ((Character) o).getHealth()) return false;
        if (this.movementSpeed != ((Character) o).getMovementSpeed()) return false;
        if (this.xCoord != ((Character) o).getxCoord()) return false;
        if (this.yCoord != ((Character) o).getyCoord()) return false;
        if (!this.activeEffects.equals(((Character) o).getActiveEffects())) return false;
        if (this.type != ((Character) o).getType()) return false;

        return this.timeForInvincibility == ((Character) o).getTimeForInvincibility();
    }
    
    public BufferedImage getUp3() {
        return up3;
    }

    public void setUp3(BufferedImage up3) {
        this.up3 = up3;
    }

    public BufferedImage getUp4() {
        return up4;
    }

    public void setUp4(BufferedImage up4) {
        this.up4 = up4;
    }

    public BufferedImage getUp5() {
        return up5;
    }

    public void setUp5(BufferedImage up5) {
        this.up5 = up5;
    }

    public BufferedImage getUp6() {
        return up6;
    }

    public void setUp6(BufferedImage up6) {
        this.up6 = up6;
    }

    public BufferedImage getDown3() {
        return down3;
    }

    public void setDown3(BufferedImage down3) {
        this.down3 = down3;
    }

    public BufferedImage getDown4() {
        return down4;
    }

    public void setDown4(BufferedImage down4) {
        this.down4 = down4;
    }

    public BufferedImage getDown5() {
        return down5;
    }

    public void setDown5(BufferedImage down5) {
        this.down5 = down5;
    }

    public BufferedImage getDown6() {
        return down6;
    }

    public void setDown6(BufferedImage down6) {
        this.down6 = down6;
    }

    public BufferedImage getLeft3() {
        return left3;
    }

    public void setLeft3(BufferedImage left3) {
        this.left3 = left3;
    }

    public BufferedImage getLeft4() {
        return left4;
    }

    public void setLeft4(BufferedImage left4) {
        this.left4 = left4;
    }

    public BufferedImage getLeft5() {
        return left5;
    }

    public void setLeft5(BufferedImage left5) {
        this.left5 = left5;
    }

    public BufferedImage getLeft6() {
        return left6;
    }

    public void setLeft6(BufferedImage left6) {
        this.left6 = left6;
    }

    public BufferedImage getRight3() {
        return right3;
    }

    public void setRight3(BufferedImage right3) {
        this.right3 = right3;
    }

    public BufferedImage getRight4() {
        return right4;
    }

    public void setRight4(BufferedImage right4) {
        this.right4 = right4;
    }

    public BufferedImage getRight5() {
        return right5;
    }

    public void setRight5(BufferedImage right5) {
        this.right5 = right5;
    }

    public BufferedImage getRight6() {
        return right6;
    }

    public void setRight6(BufferedImage right6) {
        this.right6 = right6;
    }
}