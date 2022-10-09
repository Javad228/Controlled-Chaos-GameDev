package character; /**
 * Character Class - An abstract class which models a character containing basic information pertaining to the game.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */


import combat.CombatType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Character {
    private String name;                        // Character name
    private int health;                         // Character health
    private int movementSpeed;               // Character movement speed
    private int xCoord;                         // Character x-position in a room
    private int yCoord;                         // Character y-position in a room
    private ArrayList<String> activeEffects;    // Character active effects in game
    private CombatType type;                    // Character combat type
    private double timeForInvincibility;        // Character time for invincibility after combat hit

    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private String direction;

    private int spriteCounter = 0;
    private int spriteNum = 1;

    /**
     * Empty constructor to create a generic Character
     */
    public Character() {
        this.name = "";
        this.health = 100;
        this.movementSpeed = 1;
        this.xCoord = 0;
        this.yCoord = 0;
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
     * @param activeEffects Display and apply any affects that this Character possesses
     * @param type Character Combat type
     * @param timeForInvincibility Time for invincibility given a hit occurs against this Character
     */
    public Character(String name, int health, int movementSpeed, int xCoord, int yCoord,
                     ArrayList<String> activeEffects, CombatType type, double timeForInvincibility) {
        this.name = name;
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.activeEffects = activeEffects;
        this.type=type;
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
}