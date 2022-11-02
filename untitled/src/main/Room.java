package main;

import character.Enemy;
import character.NonPlayableCharacter;
import enemy.Skeleton;
import enemy.Slime;
import enemy.Wizard;
import loot.*;

import java.util.ArrayList;

public class Room {
    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
    private ArrayList<NonPlayableCharacter> NPCs;
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
            case 3:
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

                break;
            case 2:
                items = null;
        }
    }

    private void initializeEnemies() {
        switch(roomNum) {
            case 1:
                break;
            case 2:
                enemies = new ArrayList<>();
                enemies.add(new Slime(100, 100));
                enemies.add(new Skeleton(500, 500));
                enemies.add(new Wizard(this.gp, 100, 500));
                break;
        }
    }

    private void initializeNPCs() {
        NPCs = null;
        /* Add something like this here when we have friendly NPCs
        switch(roomNum) {
            case 0:
                NPCs = new ArrayList<>();
                NPCs.add(new Skeleton());
                NPCs.add(new Wizard(this.gp));
                break;
            case 1:
                NPCs = null;
        }
        */
    }

    private void initializeCoins() {
        switch(roomNum) {
            case 1:
                String[] coinImages = {"/items/coin.png"};
                //Coin coin = new Coin(keyH, 7, coinImages, 600, 500, 1);
                Coin coin = new Coin(7, coinImages, 600, 500, 1);
                coins = new ArrayList<>();
                coins.add(coin);
                break;
            case 2:
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

    public ArrayList<NonPlayableCharacter> getNPCs() {
        return NPCs;
    }

    public void setNPCs(ArrayList<NonPlayableCharacter> NPCs) {
        this.NPCs = NPCs;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }
}
