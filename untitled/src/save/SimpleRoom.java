package save;

import character.Enemy;
import loot.Item;
import main.Room;

import java.util.ArrayList;

public class SimpleRoom {
    public int roomNum;
    public ArrayList<Item> items;
    public ArrayList<SimpleEnemy> enemies;
    public ArrayList<SimpleFriendly> friendlies;

    public SimpleRoom(Room room) {
        this.roomNum = room.getRoomNum();
        this.items = room.getItems();

        this.enemies = new ArrayList<>();

        if (room.getEnemies() != null) {
            for (Enemy enemy : room.getEnemies()) {
                this.enemies.add(new SimpleEnemy(enemy, enemy.getSubClass()));
            }
        }

        this.friendlies = new ArrayList<>();

        //TODO: Implement friendly NPC saving functionality later
    }
}
