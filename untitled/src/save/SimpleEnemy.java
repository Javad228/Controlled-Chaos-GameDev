package save;

import character.*;
import combat.*;
import enemy.*;
import loot.LootType;

import java.util.ArrayList;

public class SimpleEnemy {
    private String name;
    private String description;
    public int health;
    public int maxHealth;
    public int movementSpeed;
    public int xCoord;
    public int yCoord;
    public ArrayList<String> activeEffects;
    public int damagePerHit;
    public SimpleEnemyClassification classification;

    public SimpleEnemy(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SimpleEnemy(Enemy enemy, Object enemyClass) {
        this.name = enemy.name;
        this.health = enemy.health;
        this.maxHealth = enemy.maxHealth;
        this.movementSpeed = enemy.movementSpeed;
        this.xCoord = enemy.xCoord;
        this.yCoord = enemy.yCoord;
        this.activeEffects = enemy.activeEffects;
        this.damagePerHit = enemy.getDamagePerHit();

        if (Skeleton.class.equals(enemyClass)) {
            this.classification = SimpleEnemyClassification.SKELETON;
        }

        if (Slime.class.equals(enemyClass)) {
            this.classification = SimpleEnemyClassification.SLIME;
        }

        if (Wizard.class.equals(enemyClass)) {
            this.classification = SimpleEnemyClassification.WIZARD;
        }

        if (Barrel.class.equals(enemyClass)) {
            this.classification = SimpleEnemyClassification.BARREL;
        }

        if (BigSlonch.class.equals(enemyClass)) {
            this.classification = SimpleEnemyClassification.BIGSLONCH;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
