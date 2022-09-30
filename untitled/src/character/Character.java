package character; /**
 * Character Class - An abstract class which models a character containing basic information pertaining to the game.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */


import combat.CombatType;

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
}