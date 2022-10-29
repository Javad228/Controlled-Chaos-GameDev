package main;

import character.Enemy;
import enemy.Slime;
import loot.Consumable;
import loot.Effect;
import loot.Item;
import loot.Weapon;

import java.util.ArrayList;

public class Room {
    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
    private KeyHandler keyH;

    public Room(int roomNum, KeyHandler keyH) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        initializeItems();
        initializeEnemies();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 0:
                String[] weaponImages = {"/weapons/wooden_sword.png"};
                String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                String[] appleImages = {"/consumables/apple.png"};
                Weapon weapon = new Weapon(keyH, weaponImages);
                Effect effect = new Effect(keyH, effectImages);
                Consumable apple = new Consumable(keyH, appleImages, false);
                items = new ArrayList<>();
                items.add(weapon);
                items.add(effect);
                items.add(apple);
                break;
            case 1:
                items = null;
        }
    }

    private void initializeEnemies() {
        switch(roomNum) {
            case 0:
                enemies = new ArrayList<>();
                enemies.add(new Slime());
                break;
            case 1:
                enemies = null;
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
