package main;

import character.Enemy;
import character.NonPlayableCharacter;
import enemy.Skeleton;
import enemy.Slime;
import enemy.Wizard;
import loot.*;
import tile.Button;
import tile.TrapTile;

import java.util.ArrayList;

public class Room {
    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
    private ArrayList<NonPlayableCharacter> NPCs;
    private transient KeyHandler keyH;
    private transient GamePanel gp;
    private ArrayList<Button> buttons;
    private ArrayList<Coin> coins;

    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;
        initializeItems();
        initializeEnemies();
        initializeNPCs();
        initializeCoins();
        initializeButtons();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 3:
                String[] swordImages = {"/weapons/wooden_sword.png"};
                String[] slimeSlingerImages = {"/items/slingshot.png"};

                String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                String[] appleImages = {"/consumables/apple.png"};
                String[] bootImages = {"/items/boot.png"};

                //Sword sword = new Sword(swordImages, this.gp, 300, 300);
                SlimeSlinger slimeSlinger = new SlimeSlinger(slimeSlingerImages, this.gp, 400, 400);
                slimeSlinger.setxCoord(500);
                slimeSlinger.setyCoord(400);
                Effect effect = new Effect(effectImages);
                Consumable apple = new Consumable(appleImages, false);
                Boot boot = new Boot(bootImages, this.gp, 500, 500);
                boot.setxCoord(500);
                boot.setyCoord(500);

                items = new ArrayList<>();
                //items.add(sword);
                items.add(slimeSlinger);
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
            case 2:
                coins = null;
        }
    }

    private void initializeButtons() {
        switch(roomNum) {
            case 0:
                buttons = null;
            case 1:
                buttons = null;
            case 2:
                buttons = new ArrayList<>(5);
                Button button1 = new Button();
                button1.setOn(false);
                button1.setx(2 * gp.tileSize);
                button1.sety(2 * gp.tileSize);

                for (int i = 0; i < gp.maxScreenRow; i++) {
                    TrapTile trapTile = new TrapTile();
                    trapTile.setx(2 * gp.tileSize);
                    trapTile.sety(i * gp.tileSize);
                    button1.addTile(trapTile);
                }

                buttons.add(button1);

        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
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
