package save;

import character.CharacterType;
import character.Inventory;
import character.PlayerCharacter;
import combat.*;
import loot.Item;

import java.util.ArrayList;

public class SimpleCharacter {
    public String name;
    public double health;
    public int maxHealth;
    public int movementSpeed;
    public int xCoord;
    public int yCoord;
    public ArrayList<String> activeEffects;
    public CombatType combatType;
    public Inventory inventory;
    public CharacterType characterType;
    private int numCoins;
    private ArrayList<SimpleEnemy> enemiesKilled;
    public ArrayList<Item> itemsDiscovered;
    public int difficulty;
    public boolean isDamaged;
    public String characterAppearance;

    public SimpleCharacter(PlayerCharacter c) {
        this.name = c.getName();
        this.health = c.getHealth();
        this.maxHealth = c.getMaxHealth();
        this.movementSpeed = c.getMovementSpeed();
        this.xCoord = c.getxCoord();
        this.yCoord = c.getyCoord();
        this.activeEffects = c.getActiveEffects();
        this.combatType = c.getCombatType();
        this.inventory = c.getInventory();
        this.characterType = c.getCharacterType();
        this.numCoins = c.getNumCoins();
        this.enemiesKilled = c.getEnemiesKilled();
        this.itemsDiscovered = c.getItemsDiscovered();
        this.difficulty = c.getGameDifficulty();
        this.isDamaged = c.isDamaged();
        this.characterAppearance = c.getCharacterAppearance();

    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public ArrayList<SimpleEnemy> getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(ArrayList<SimpleEnemy> enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }
}
