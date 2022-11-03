package character;

import loot.LootType;
import main.GamePanel;
import save.SimpleEnemy;
import save.SimpleEnemyClassification;

import java.awt.*;

public class Enemy extends NonPlayableCharacter {
    private EnemyType enemyType;
    private LootType lootType;

    public Enemy(GamePanel gp) {
        super();
        this.enemyType = EnemyType.DEFAULT;
        this.lootType = LootType.DEFAULT;
    }

    public Enemy(EnemyType e, LootType l) {
        super();
        this.enemyType = e;
        this.lootType = l;

        // Override attackArea assignment based on the EnemyType inputted
        switch (enemyType) {
            case SMALL -> this.attackArea = new Rectangle(30,30);
            case MEDIUM -> this.attackArea = new Rectangle(40,40);
            case LARGE -> this.attackArea = new Rectangle(50,50);
            case DEFAULT -> {}
            default -> {
                System.out.println("Enemy Constructor: EnemyType invalid\n");
                System.exit(1);
            }
        }
    }
    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public LootType getLootType() {
        return lootType;
    }

    public void setLootType(LootType lootType) {
        this.lootType = lootType;
    }

    public Object getSubClass() {
        return null;
    }

    public void checkIfDead(PlayerCharacter player) {
        if (this.health <= 0) {
            this.isAlive = false;
            boolean isNewEnemyKilled = true;

            for (int i = 0; i < player.getEnemiesKilled().size(); i++) {
                if (player.getEnemiesKilled().get(i).getName() == this.getName()) {
                    isNewEnemyKilled = false;
                    break;
                }
            }

            if (isNewEnemyKilled) {
                player.getEnemiesKilled().add(new SimpleEnemy(this.getName(), "input a description..."));
            }
        }
    }
}
