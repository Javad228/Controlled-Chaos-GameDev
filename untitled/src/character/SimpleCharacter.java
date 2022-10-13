package character;

import combat.*;

import java.util.ArrayList;

public class SimpleCharacter {
    public String name;
    public int health;
    public int maxHealth;
    public int movementSpeed;
    public int xCoord;
    public int yCoord;
    public ArrayList<String> activeEffects;
    public CombatType combatType;
    public String currentRoom;
    public Inventory inventory;
    public CharacterType characterType;

    public SimpleCharacter(PlayerCharacter c) {
        this.name = c.getName();
        this.health = c.getHealth();
        this.maxHealth = c.maxHealth;
        this.movementSpeed = c.getMovementSpeed();
        this.xCoord = c.getxCoord();
        this.yCoord = c.getyCoord();
        this.activeEffects = c.getActiveEffects();
        this.combatType = c.getType();
        this.currentRoom = "Test";
        this.inventory = c.getInventory();
        this.characterType = c.getCharacterType();
    }
}
