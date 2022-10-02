package loot;

public class Loot {
    private String name;
    private LootType lootType;
    private int xCoord;                         // x-position in a room
    private int yCoord;                         // y-position in a room

    public Loot() {
        this.name = "";
        this.lootType = LootType.DEFAULT;
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public Loot(String name, LootType lootType, int xCoord, int yCoord) {
        this.name = name;
        this.lootType = lootType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LootType getLootType() {
        return lootType;
    }

    public void setLootType(LootType lootType) {
        this.lootType = lootType;
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
}
