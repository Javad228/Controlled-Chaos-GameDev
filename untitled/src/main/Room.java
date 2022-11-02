package main;

import character.*;
import enemy.*;
import loot.*;

import java.util.ArrayList;

public class Room {
    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
    private ArrayList<Friendly> NPCs;
    private transient KeyHandler keyH;
    private transient GamePanel gp;

    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;
        initializeItems();
        initializeEnemies();
        initializeNPCs();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 0:
                String[] weaponImages = {"/weapons/wooden_sword.png"};
                String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                String[] appleImages = {"/consumables/apple.png"};
                String[] bootImages = {"/items/boot.png"};

                Weapon weapon = new Weapon(keyH, weaponImages);
                Effect effect = new Effect(keyH, effectImages);
                Consumable apple = new Consumable(keyH, appleImages, false);
                PassiveItem boot = new PassiveItem(keyH, bootImages);

                items = new ArrayList<>();
                items.add(weapon);
                items.add(effect);
                items.add(apple);
                items.add(boot);

                // Test
                weapon.setDescription("Basic sword that swings and misses sometimes, but we won't talk about that...");
                boot.setDescription("Basic Boot");

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
                enemies.add(new Skeleton());
                enemies.add(new Wizard(this.gp));
                break;
            case 1:
                enemies = null;
        }
    }

    private void initializeNPCs() {
        switch(roomNum) {
            case 0:
                NPCs = new ArrayList<>();
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

    public ArrayList<Friendly> getNPCs() {
        return NPCs;
    }

    public void setNPCs(ArrayList<Friendly> NPCs) {
        this.NPCs = NPCs;
    }

    public int getRoomNum() {
        return this.roomNum;
    }
}
