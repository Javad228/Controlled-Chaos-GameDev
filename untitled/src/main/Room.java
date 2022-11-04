package main;

import character.*;
import enemy.*;
import loot.*;
import tile.Button;
import tile.DoorTile;
import tile.TileManager;
import tile.TrapTile;

import java.util.ArrayList;

public class Room {
    public static final int STARTINGROOM = 1;
    public static final int ENEMYROOM = 2;
    public static final int ITEMROOM = 3;
    public static final int TRAPROOM = 4;
    public static final int BOSSROOM = 5;

    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;
    private ArrayList<Friendly> NPCs;
    private transient KeyHandler keyH;
    private transient GamePanel gp;
    private ArrayList<Button> buttons;
    private ArrayList<Coin> coins;
    private ArrayList<TrapTile> trapTiles;
    private DoorTile doorTile;

    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;
        initializeItems();
        initializeEnemies();
        initializeNPCs();
        initializeCoins();
        initializeButtons();
        initializeTrapTiles();
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

                // Item description test TODO
                //weapon.setDescription("Basic sword that swings and misses sometimes, but we won't talk about that...");
                boot.setDescription("Basic Boot");

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
                enemies.add(new Wizard(100, 500));
                break;
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
            case 1:
                String[] coinImages = {"/items/coin.png"};
                //Coin coin = new Coin(keyH, 7, coinImages, 600, 500, 1);
                Coin coin = new Coin(7, coinImages, 600, 500, 1);
                coins = new ArrayList<>();
                coins.add(coin);
                break;
            case 2:
                coins = null;
            case 3:
                coins = null;
        }
    }

    private void initializeButtons() {
        switch(roomNum) {
            case 1:
                buttons = null;
            case 2:
                buttons = null;
            case 3:
                buttons = null;
            case 4:
                buttons = new ArrayList<>(5);
                Button button1 = new Button(Button.map1Button1Col * gp.tileSize, Button.map1ButtonRow * gp.tileSize);

                for (int i = 0; i < gp.maxScreenRow; i++) {
                    TrapTile trapTile = new TrapTile();
                    trapTile.setx(TrapTile.map1TrapCol1 * gp.tileSize);
                    trapTile.sety(i * gp.tileSize);
                    button1.addTrapTile(trapTile);
                }

                doorTile = new DoorTile();
                doorTile.setLocked(true);
                doorTile.setx(DoorTile.map1Room4DoorCol * gp.tileSize);
                doorTile.sety(DoorTile.map1Room4DoorRow * gp.tileSize);

                Button button2 = new Button(Button.map1Button2Col * gp.tileSize, Button.map1ButtonRow * gp.tileSize);
                button2.addDoorTile(doorTile);
                Button button3 = new Button(Button.map1Button3Col * gp.tileSize, Button.map1ButtonRow * gp.tileSize);
                button3.addDoorTile(doorTile);
                Button button4 = new Button(Button.map1Button4Col * gp.tileSize, Button.map1ButtonRow * gp.tileSize);
                button4.addDoorTile(doorTile);

                buttons.add(button1);
                buttons.add(button2);
                buttons.add(button3);
                buttons.add(button4);
        }
    }

    private void initializeTrapTiles() {
        switch(roomNum) {
            case 1:
                trapTiles = null;
            case 2:
                trapTiles = null;
            case 3:
                trapTiles = null;
            case 4:
                trapTiles = new ArrayList<>(24);
                for (int i = 0; i < gp.maxScreenRow; i++) {
                    TrapTile thisTrapTile = new TrapTile();
                    thisTrapTile.setx(TrapTile.map1TrapCol1 * gp.tileSize);
                    thisTrapTile.sety(i * gp.tileSize);
                    thisTrapTile.setOn(true);

                    trapTiles.add(thisTrapTile);
                }

                for (int i = 0; i < gp.maxScreenRow; i++) {
                    TrapTile thisTrapTile = new TrapTile();
                    thisTrapTile.setx(TrapTile.map1TrapCol2 * gp.tileSize);
                    thisTrapTile.sety(i * gp.tileSize);
                    thisTrapTile.setOn(true);

                    trapTiles.add(thisTrapTile);
                }

        }
    }

    private void initializeDoorTile() {
        switch(roomNum) {
            case 1:
                doorTile = null;
            case 2:
                doorTile = null;
            case 3:
                doorTile = null;
            case 4:
                doorTile = new DoorTile();
                doorTile.setLocked(false);
                doorTile.setx(DoorTile.map1Room4DoorCol * gp.tileSize);
                doorTile.sety(DoorTile.map1Room4DoorRow * gp.tileSize);
                doorTile.toggleDoor(DoorTile.map1Room4DoorCol, DoorTile.map1Room4DoorRow);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public ArrayList<TrapTile> getTrapTiles() {
        return trapTiles;
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

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }
}
