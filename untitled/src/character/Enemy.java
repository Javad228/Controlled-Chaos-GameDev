package character;

import loot.LootType;

public class Enemy extends NonPlayableCharacter {
    private EnemyType enemyType;
    private LootType lootType;

    public Enemy() {
        super();
        this.enemyType = EnemyType.DEFAULT;
        this.lootType = LootType.DEFAULT;
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
