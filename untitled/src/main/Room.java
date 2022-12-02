package main;

import character.*;
import enemy.*;

import loot.*;
import tile.Button;
import tile.DoorTile;
import tile.TrapTile;

import java.util.ArrayList;
import java.lang.Math.*;
import java.util.Random;

public class Room {
    public static final int VOLCANOROOM = 1;
    public static final int GRASSROOM = 2;
    public static final int SPOOKYROOM = 3;
    public static final int ICEROOM = 4;
    public static final int SPACEROOM = 5;

    private int roomNum;
    private ArrayList<Item> items;
    private ArrayList<Chest> chests;

    private ArrayList<Sign> signs;
    private ArrayList<Enemy> enemies;
    private ArrayList<Friendly> NPCs;
    private transient KeyHandler keyH;
    private transient GamePanel gp;
    private ArrayList<Button> buttons;
    private ArrayList<Coin> coins;
    private ArrayList<TrapTile> trapTiles;
    private DoorTile doorTile;
    private int roomType;

    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;

        if (gp.player.roomSetNum == 1) {
            switch(roomNum) {
                case 1:
                    roomType = VOLCANOROOM;
                    break;
                case 2:
                    roomType = GRASSROOM;
                    break;
                case 3:
                    roomType = SPOOKYROOM;
                    break;
                case 4:
                case 7:                 //hidden room is in the ice room, so makes sense for it to be ice themed
                    roomType = ICEROOM;
                    break;
                case 5:
                    roomType = SPACEROOM;
                    break;
                case 6:
                    roomType = 6;
            }
        } else if (gp.player.roomSetNum == 2) {
            switch(roomNum) {
                case 1:
                    roomType = SPACEROOM;
                    break;
                case 2:
                    roomType = ICEROOM;
                    break;
                case 3:
                    roomType = VOLCANOROOM;
                    break;
                case 4:
                    roomType = GRASSROOM;
                    break;
                case 5:
                    roomType = SPOOKYROOM;
                    break;
                case 6:
                    roomType = 6;
            }
        } else if (gp.player.roomSetNum == 3) {
            switch(roomNum) {
                case 1:
                    roomType = SPOOKYROOM;
                    break;
                case 2:
                    roomType = SPACEROOM;
                    break;
                case 3:
                    roomType = ICEROOM;
                    break;
                case 4:
                    roomType = VOLCANOROOM;
                    break;
                case 5:
                    roomType = GRASSROOM;
                    break;
                case 6:
                    roomType = 6;
            }
        } else {
            System.out.println("Received bad roomSetNum.");
        }

        items = new ArrayList<>();
        chests = new ArrayList<>();
        signs = new ArrayList<>();
        enemies = new ArrayList<>();
        NPCs = new ArrayList<>();
        coins = new ArrayList<>();


        initializeItems();
        initializeEnemies();
        initializeCoins();
        initializeButtons();
        initializeTrapTiles();
        initializeNPCs();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 1:
                String [] healthImages = {"/items/health.png"};
                HealthUp healthUp = new HealthUp(healthImages, this.gp, 500, 500);
                healthUp.setxCoord(200);
                healthUp.setyCoord(200);
                items.add(healthUp);
                String [] damageImages = {"/items/damage.png"};
                DamageUp damageUp = new DamageUp(damageImages, this.gp, 500, 500);
                damageUp.setxCoord(300);
                damageUp.setyCoord(300);
                items.add(damageUp);
                String [] rapidFireImages = {"/items/rapid-fire.png"};
                RapidFire rapidFire = new RapidFire(rapidFireImages, this.gp, 500, 500);
                rapidFire.setxCoord(400);
                rapidFire.setyCoord(400);
                items.add(rapidFire);

                String [] bombBuddyImages = {"/items/bomb-buddy.png"};
                BombBuddy bombBuddy = new BombBuddy(bombBuddyImages, this.gp, 500, 500);
                rapidFire.setxCoord(300);
                rapidFire.setyCoord(400);
                items.add(bombBuddy);

                Item random = getRandomItem();
                random.setxCoord(500);
                random.setyCoord(500);
                items.add(random);
                break;

            case 2:
                String[] appleImages = {"/consumables/apple.png"};
                Consumable apple = new Consumable(appleImages, false);
                items.add(apple);
                break;
            case 3:
                String[] swordImages = {"/weapons/wooden_sword.png"};
                String[] slimeSlingerImages = {"/items/slingshot.png"};
                String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                String[] bootImages = {"/items/boot.png"};

                //Sword sword = new Sword(swordImages, this.gp, 300, 300);
                SlimeSlinger slimeSlinger = new SlimeSlinger(slimeSlingerImages, this.gp, 400, 400);
                slimeSlinger.setxCoord(500);
                slimeSlinger.setyCoord(400);
                Effect effect = new Effect(effectImages);
                Boot boot = new Boot(bootImages, this.gp, 500, 500);
                boot.setxCoord(500);
                boot.setyCoord(500);

                //items.add(sword);
                items.add(slimeSlinger);
                items.add(effect);
                items.add(boot);

                break;
            case 6:
                Chest chest = new Chest(320, 200, gp,0);
                Chest chest1 = new Chest(470, 200, gp,1);
                Chest chest2 = new Chest(620, 200, gp,2);
                Sign sign = new Sign(320, 245, gp,0);
                Sign sign1 = new Sign(470, 245, gp,1);
                Sign sign2 = new Sign(620, 245, gp,2);
                signs.add(sign);
                signs.add(sign1);
                signs.add(sign2);
                chests.add(chest);
                chests.add(chest1);
                chests.add(chest2);
                break;
            case 7:
                //add reward item
                items = new ArrayList<>();
                random = getRandomItem();
                random.setxCoord(8 * gp.tileSize);
                random.setyCoord(6 * gp.tileSize);
                items.add(random);
                random = getRandomItem();
                random.setxCoord(10 * gp.tileSize);
                random.setyCoord(1 * gp.tileSize);
                items.add(random);
                break;
            default:
                items = null;
        }
    }

    private void initializeEnemies() {
        switch(roomNum) {
            case 1:
                enemies.add(new Snowman(500, 500));
                break;
            case 2:
                if(gp.getPlayer().roomSetNum ==1){
//                    enemies.add(new Slime(100, 100));
                    enemies.add(new Skeleton(500, 500));
                    enemies.add(new Skeleton(100, 500));
                }else{
                    enemies.add(new Wizard(500, 500));
                    enemies.add(new Wizard(100, 500));
                }
                break;
            case 3:
                enemies.add(new Barrel(300, 50));
                break;
            case 6:
                if(gp.getPlayer().roomSetNum ==1){
                    enemies.add(new BigSlonch(300, 300));
                } else{
                    enemies.add(new BigSkull(300, 300));
                }
                break;
        }
    }

    private void initializeNPCs() {
        switch(roomNum) {
            case 1:
                if (gp.getPlayer().roomSetNum == 1) {
                    Satyr satyr = new Satyr(500, 200);
                    NPCs.add(satyr);
                } else {
                    Satyr satyr = new Satyr(100, 100);
                    NPCs.add(satyr);
                }
                break;
            case 3:
                Knight knight = new Knight(Knight.room4Col * gp.tileSize, Knight.room4Row * gp.tileSize);
                NPCs.add(knight);
                break;
        }
    }

    private void initializeCoins() {
        String[] coinImages = {"/items/coin.png"};
        switch(roomNum) {
            case 1:
                //Coin coin = new Coin(keyH, 7, coinImages, 600, 500, 1);
                Coin coin = new Coin(7, coinImages, 600, 500, 1);
                coins.add(coin);
                break;
            case 7:
                //add coins to hidden room
                coins = new ArrayList<>();
                coin = new Coin(7, coinImages, 1 * gp.tileSize, 1 * gp.tileSize, 1);
                coins.add(coin);
                coin = new Coin(7, coinImages, 13 * gp.tileSize, 4 * gp.tileSize, 1);
                coins.add(coin);

                break;
        }
    }

    private void initializeButtons() {
        switch(roomNum) {
            case 4:
                buttons = new ArrayList<>();
                //buttons = new ArrayList<>(5); // TODO: if no crashes, move above line to constructor
                Button button1 = new Button(Button.map1Button1Col * gp.tileSize, Button.map1Button1Row * gp.tileSize);

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

                Button button2 = new Button(Button.map1Button2Col * gp.tileSize, Button.map1Button2Row * gp.tileSize);
                button2.addDoorTile(doorTile);
                Button button3 = new Button(Button.map1Button3Col * gp.tileSize, Button.map1Button3Row * gp.tileSize);
                button3.addDoorTile(doorTile);
                Button button4 = new Button(Button.map1Button4Col * gp.tileSize, Button.map1Button4Row * gp.tileSize);
                button4.addDoorTile(doorTile);

                buttons.add(button1);
                buttons.add(button2);
                buttons.add(button3);
                buttons.add(button4);
        }
    }

    private void initializeTrapTiles() {
        switch(roomNum) {
            case 4:
                trapTiles = new ArrayList<>();
                //trapTiles = new ArrayList<>(24); // TODO: if no crashes, move above line to constructor
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
    public ArrayList<Chest> getChests() {
        return chests;
    }

    public void setChests(ArrayList<Chest> chests) {
        this.chests = chests;
    }

    public ArrayList<Sign> getSigns() {
        return signs;
    }

    public Item getRandomItem() {

        //random thing that returns an id from 0 to 4
        int min = 0;
        int max = 5;
        int itemID;

        //priority equal to item ID of item that was just unlocked, -1 if no priority
        /*
        if (gp.getPlayer().getItemPriority() != -1) {
            itemID = gp.getPlayer().getItemPriority();
            gp.getPlayer().setItemPriority(-1);
        }
        else {
            //itemID = (int) (Math.random() * (max - min) + min);
            //need in player: pickedUp[] same as Unlocked, but all start as false
            //somewhere: when item picked up, set pickedUp[itemID] = true
            while (!gp.getPlayer().getItemsUnlocked()[itemID]) {    //|| pickedUp[itemID] == true
                itemID = (int) (Math.random() * (max - min) + min);
            }
        }

         */

        Random random = new Random();
        itemID = random.nextInt(max + 1);

        switch(itemID) {
            case 0:
            default:
                String [] healthImages1 = {"/items/health.png"};
                return new HealthUp(healthImages1, this.gp, 500, 500);
            case 1:
                String [] damageImages = {"/items/damage.png"};
                return new DamageUp(damageImages, this.gp, 500, 500);
            case 2:
                String[] bootImages = {"/items/boot.png"};
                return new Boot(bootImages, this.gp, 500, 500);
            case 3:
                String[] slimeSlingerImages = {"/items/slingshot.png"};
                return new SlimeSlinger(slimeSlingerImages, this.gp, 400, 400);
            case 4:
                String [] rapidFireImages = {"/items/rapid-fire.png"};
                return new RapidFire(rapidFireImages, this.gp, 500, 500);
            case 5:
                String [] bombBuddyImages = {"/items/bomb-buddy.png"};
                return new BombBuddy(bombBuddyImages, this.gp, 500, 500);

        }
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }
}
