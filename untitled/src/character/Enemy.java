package character;

import loot.LootType;
import main.GamePanel;

import java.awt.*;

public class Enemy extends NonPlayableCharacter {
    private EnemyType enemyType;
    private LootType lootType;

    public Enemy() {
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
            case SMALL -> this.attackArea = new Rectangle(48,48);
            case MEDIUM -> this.attackArea = new Rectangle(96,96);
            case LARGE -> this.attackArea = new Rectangle(144,144);
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
}
