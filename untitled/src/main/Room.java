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
    private ArrayList<Coin> coins;

    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;
        initializeItems();
        initializeEnemies();
        initializeNPCs();
        initializeCoins();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 0:
                String[] weaponImages = {"/weapons/wooden_sword.png"};
                String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                String[] appleImages = {"/consumables/apple.png"};
                String[] bootImages = {"/items/boot.png"};

                Weapon weapon = new Weapon(weaponImages);
                Effect effect = new Effect(effectImages);
                Consumable apple = new Consumable(appleImages, false);
                PassiveItem boot = new PassiveItem(bootImages);

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
//TODO: <<<<<<< Cameron-PlayerTime
//                enemies.add(new Slime());
//                enemies.add(new Skeleton());
//                enemies.add(new Wizard(this.gp));
//=======
                enemies.add(new Slime(100, 100));
                enemies.add(new Skeleton(500, 500));
                enemies.add(new Wizard(this.gp, 100, 500));
//>>>>>>> Cameron-Merge-PlayerTime
                break;
            case 1:
                enemies = null;
        }
    }

    private void initializeNPCs() {
        NPCs = null;
        /* Add something like this here when we have friendly NPCs
        switch(roomNum) {
            case 0:
                NPCs = new ArrayList<>();
                break;
            case 1:
                NPCs = null;
        }
        */
    }

    private void initializeCoins() {
        switch(roomNum) {
            case 0:
                String[] coinImages = {"/items/coin.png"};
                //Coin coin = new Coin(keyH, 7, coinImages, 600, 500, 1);
                Coin coin = new Coin(7, coinImages, 600, 500, 1);
                coins = new ArrayList<>();
                coins.add(coin);
                break;
            case 1:
                coins = null;
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

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }
}
