package character;

import combat.CombatType;
import main.Audio;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Character Class - An abstract class which models a character containing basic information pertaining to the game.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */
public abstract class Character {
    public int attackType = 0;
    public boolean isInvincible = false;
    public int invincibleCounter = 0;
    public boolean isAlive = true;
    public Rectangle attackArea = new Rectangle(0, 0,8,8);
    public String name;                        // Character name
    public double health;
    private int maxHealth;                   // Character health
    public int movementSpeed;               // Character movement speed
    public int maxSpeed;                    // maximum character movement speed. used to reset speed after character leaves tiles that affect its speed (like mud)
    public int xCoord;                         // Character x-position in a room
    public int yCoord;                         // Character y-position in a room
    public int height;                         // Character height in pixels
    public int width;                          // Character width in pixels
    public ArrayList<String> activeEffects;    // Character active effects in game
    public CombatType combatType;                    // Character combat type
    public double timeForInvincibility;        // Character time for invincibility after combat hit
    public Projectile projectile;
    private boolean hasThrownProjectile;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public Rectangle solidArea_2;

    public int solidAreaDefaultX, solidAreaDefaultY;
    public int actionLockCounter = 0;
    public boolean collisionOn = false;
    private transient BufferedImage up1, up2,up3,up4,up5,up6, down1, down2,down3,down4,down5,down6, left1, left2,left3,left4,left5,left6, right1, right2,right3,right4,right5,right6;
    private transient BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;
    private transient BufferedImage death1, death2, death3, death4, death5;
    private transient BufferedImage[] deathImages;
    private transient BufferedImage[][] walkingImages; // Row 0 = up, Row 1 = down, Row 2 = left, Row 3 = right
    private transient BufferedImage[][] attackImages; // Row 0 = up, Row 1 = down, Row 2 = left, Row 3 = right

    public String direction = "down";
    public boolean isAttacking = false;

    public int spriteCounter = 0;
    public int spriteNum = 1; // TODO: make this zero indexed and update the sprite increments accordingly
    private boolean wasOne = false; // boolean meant to help with sprite number selection when spriteNum = 2
    public int maxBounce = 20; // variables used for bounce effect in space room
    public int currentBounce = 0;
    public boolean shouldBounce = true; // if currentBounce != maxBounce, then this should be true
    public int numBounces = 0;
    public int origY;
    public boolean isUp = false;

    public int collisionAreaDefaultY;
    public int collisionAreaDefaultX;

    /**
     * Empty constructor to create a generic Character
     */
    public Character() {
        this.name = "";
        this.maxHealth = 100;
        this.health = maxHealth;
        this.movementSpeed = 2; // must be greater than or equal to 2
        this.maxSpeed = 2;
        this.xCoord = 50;
        this.yCoord = 50;
        this.height = 50;
        this.width = 50;
        this.activeEffects = new ArrayList<>();
        this.combatType = CombatType.DEFAULT;
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
        this.combatType = type;
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
    public Character(String name, int health, int movementSpeed, int maxSpeed, int xCoord, int yCoord, int height, int width,
                     ArrayList<String> activeEffects, CombatType type, double timeForInvincibility) {
        this.name = name;
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.maxSpeed = maxSpeed;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = height;
        this.width = width;
        this.activeEffects = activeEffects;
        this.combatType = type;
        this.timeForInvincibility = timeForInvincibility;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if (this instanceof PlayerCharacter) {
            Audio.playerDamagedAudio();
        }
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void damage(double damageTaken) {
        if (damageTaken > this.health) setHealth(0);
        else setHealth(this.health-damageTaken);
    }

    public void heal(int healthRegained) {
        if (this.health == this.maxHealth)  return;
        if (healthRegained >= this.maxHealth-this.health) setHealth(this.maxHealth);
        else setHealth(this.health+healthRegained);
    }

    /**
     * kill(): Sets the <code>isAlive</code> boolean variable to <code>false</code>.
     * Used for the GameThread to determine that the player death
     * animation is finished.
     * <p>
     * Important: This method does not set the player health to 0.
     */
    public void kill() {
        this.setIsAlive(false);
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

    public CombatType getCombatType() {
        return combatType;
    }

    public void setCombatType(CombatType combatType) {
        this.combatType = combatType;
    }

    public double getTimeForInvincibility() {
        return timeForInvincibility;
    }

    public void setTimeForInvincibility(double timeForInvincibility) {
        this.timeForInvincibility = timeForInvincibility;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
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
        if (this.combatType != ((Character) o).getCombatType()) return false;

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

    public boolean isHasThrownProjectile() {
        return hasThrownProjectile;
    }

    public void setHasThrownProjectile(boolean hasThrownProjectile) {
        this.hasThrownProjectile = hasThrownProjectile;
    }
    public BufferedImage getAttackUp1() {
        return attackUp1;
    }

    public void setAttackUp1(BufferedImage attackUp1) {
        this.attackUp1 = attackUp1;
    }

    public BufferedImage getAttackDown1() {
        return attackDown1;
    }

    public void setAttackDown1(BufferedImage attackDown1) {
        this.attackDown1 = attackDown1;
    }

    public BufferedImage getAttackRight1() {
        return attackRight1;
    }

    public void setAttackRight1(BufferedImage attackRight1) {
        this.attackRight1 = attackRight1;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public void setAttackLeft1(BufferedImage attackLeft1) {
        this.attackLeft1 = attackLeft1;
    }

    public BufferedImage getAttackUp2() {
        return attackUp2;
    }

    public void setAttackUp2(BufferedImage attackUp2) {
        this.attackUp2 = attackUp2;
    }

    public BufferedImage getAttackDown2() {
        return attackDown2;
    }

    public void setAttackDown2(BufferedImage attackDown2) {
        this.attackDown2 = attackDown2;
    }

    public BufferedImage getAttackRight2() {
        return attackRight2;
    }

    public void setAttackRight2(BufferedImage attackRight2) {
        this.attackRight2 = attackRight2;
    }

    public BufferedImage getAttackLeft2() {
        return attackLeft2;
    }

    public void setAttackLeft2(BufferedImage attackLeft2) {
        this.attackLeft2 = attackLeft2;
    }

    public BufferedImage getDeath1() {
        return death1;
    }

    public void setDeath1(BufferedImage death1) {
        this.death1 = death1;
    }

    public BufferedImage getDeath2() {
        return death2;
    }

    public void setDeath2(BufferedImage death2) {
        this.death2 = death2;
    }

    public BufferedImage getDeath3() {
        return death3;
    }

    public void setDeath3(BufferedImage death3) {
        this.death3 = death3;
    }

    public BufferedImage getDeath4() {
        return death4;
    }

    public void setDeath4(BufferedImage death4) {
        this.death4 = death4;
    }

    public BufferedImage getDeath5() {
        return death5;
    }

    public void setDeath5(BufferedImage death5) {
        this.death5 = death5;
    }

    public BufferedImage[] getDeathImages() {
        return deathImages;
    }

    public void setDeathImages(BufferedImage[] deathImages) {
        this.deathImages = deathImages;
    }

    public void setDeathImage(BufferedImage image, int index) {
        this.deathImages[index] = image;
    }

    public BufferedImage getDeathImage(int index) {
        return deathImages[index];
    }

    public boolean isWasOne() {
        return wasOne;
    }

    public void setWasOne(boolean wasOne) {
        this.wasOne = wasOne;
    }

    public BufferedImage[][] getWalkingImages() {
        return walkingImages;
    }

    public void setWalkingImages(BufferedImage[][] walkingImages) {
        this.walkingImages = walkingImages;
    }

    public void setWalkingImage(BufferedImage image, int direction, int index) {
        this.walkingImages[direction][index] = image;
    }

    public BufferedImage getWalkingImage(int direction, int index) {
        return walkingImages[direction][index];
    }

    public BufferedImage[][] getAttackImages() {
        return attackImages;
    }

    public void setAttackImages(BufferedImage[][] attackImages) {
        this.attackImages = attackImages;
    }

    public void setAttackImage(BufferedImage image, int direction, int index) {
        this.attackImages[direction][index] = image;
    }

    public BufferedImage getAttackImage(int direction, int index) {
        return attackImages[direction][index];
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}