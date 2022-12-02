package main;

import character.*;
import enemy.*;

import etc.CoordinateWizard;
import loot.*;
import tile.Button;
import tile.DoorTile;
import tile.TrapTile;

import java.util.ArrayList;
import java.util.Random;

public class Room {

    public static final int DEFAULT_NUM_ROOMS = 6;

    // (room0.txt - room13.txt), shop.txt, boss.txt
    public static final int MAX_NUM_ROOMS = DEFAULT_NUM_ROOMS + PlayerCharacter.HIGHEST_DIFF;

    public static final int VOLCANOROOM = 1;
    public static final int GRASSROOM = 2;
    public static final int SPOOKYROOM = 3;
    public static final int ICEROOM = 4;
    public static final int SPACEROOM = 5;
    public static final int SHOPROOM = 6;

    public static final int[] roomTypes =
            new int[]{VOLCANOROOM, GRASSROOM, SPOOKYROOM, ICEROOM, SPACEROOM};

    public static int numOfRooms = DEFAULT_NUM_ROOMS;

    private ArrayList<Item> items;
    private ArrayList<Chest> chests;
    private ArrayList<Sign> signs;
    private ArrayList<Enemy> enemies;
    private ArrayList<Friendly> NPCs;
    private ArrayList<Button> buttons;
    private ArrayList<Coin> coins;
    private ArrayList<TrapTile> trapTiles;

    private DoorTile doorTile;
    private int roomNum;
    private int roomType;

    private transient KeyHandler keyH;
    private transient GamePanel gp;

    /**
     * Room - Generate first level room. Scope of this constructor
     * is public
     *
     * @param roomNum
     * @param keyH
     * @param gp
     */
    public Room(int roomNum, KeyHandler keyH, GamePanel gp) {
        if (roomNum == 0)    numOfRooms = 0;
        else    numOfRooms++;

        this.roomNum = roomNum;
        this.keyH = keyH;
        this.gp = gp;

        if (gp.player.roomSetNum == 1) {
            switch(roomNum) {
                case 1:
                    roomType = VOLCANOROOM;
                    break;
                case 2:
                case 7:
                    roomType = GRASSROOM;
                    break;
                case 3:
                    roomType = SPOOKYROOM;
                    break;
                case 4:
                case 8:                 //hidden room is in the ice room, so makes sense for it to be ice themed
                    roomType = ICEROOM;
                    break;
                case 5:
                    roomType = SPACEROOM;
                    break;
                case 6:
                    roomType = SHOPROOM;
                    break;
                default:
                    roomType = SPOOKYROOM;
            }
        } else if (gp.player.roomSetNum == 2) {
            switch(roomNum) {
                case 1:
                    roomType = SPACEROOM;
                    break;
                case 2:
                case 7:
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
                    roomType = SHOPROOM;
                    break;
                default:
                    roomType = SPACEROOM;
            }
        } else if (gp.player.roomSetNum == 3) {
            switch(roomNum) {
                case 1:
                    roomType = SPOOKYROOM;
                    break;
                case 2:
                case 7:
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
                    roomType = SHOPROOM;
                    break;
                default:
                    roomType = VOLCANOROOM;
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

    /**
     * Room - Private constructor to create room objects after the player has completed
     * the first level. This is to ensure that the list variables which contain room elements
     * such as enemies, items, traps, coins, and NPCs are existing.
     *
     * @param thisRoomNum   The given room number
     * @param numRooms
     * @param keyH
     * @param gp
     */
    private Room(int thisRoomNum, final int numRooms, KeyHandler keyH, GamePanel gp) {
        this.keyH = keyH;
        this.gp = gp;
        this.roomNum = thisRoomNum;

        // If this room being created is the second to last room
        // in the level, make it the shop room.
        // Otherwise, choose room type from a sequential array
        if (numRooms < 0) {
            this.roomType = 0;
        } else if (numRooms - thisRoomNum == 1) {
            this.roomType = SHOPROOM;
        } else {
            this.roomType = roomTypes[thisRoomNum % roomTypes.length];
        }

        // Initialize every ArrayList
        this.items = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.signs = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.NPCs = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.trapTiles = new ArrayList<>();
    }

    private void initializeItems() {
        switch(roomNum) {
            case 1:
                //String [] healthImages = {"/items/health.png"};
                //HealthUp healthUp = new HealthUp(healthImages, this.gp, 500, 500);
                HealthUp healthUp = new HealthUp(HealthUp.DEFAULT_HEALTHUP_PATHS, this.gp, 500, 500);
                healthUp.setxCoord(200);
                healthUp.setyCoord(200);
                items.add(healthUp);
                //String [] damageImages = {"/items/damage.png"};
                //DamageUp damageUp = new DamageUp(damageImages, this.gp, 500, 500);
                DamageUp damageUp = new DamageUp(DamageUp.DEFAULT_DAMAGEUP_PATHS, this.gp, 500, 500);
                damageUp.setxCoord(300);
                damageUp.setyCoord(300);
                items.add(damageUp);
                //String [] rapidFireImages = {"/items/rapid-fire.png"};
                //RapidFire rapidFire = new RapidFire(rapidFireImages, this.gp, 500, 500);
                RapidFire rapidFire = new RapidFire(RapidFire.DEFAULT_IMAGE_PATHS, this.gp, 500, 500);
                rapidFire.setxCoord(400);
                rapidFire.setyCoord(400);
                items.add(rapidFire);

                String [] bombBuddyImages = {"/items/bomb-buddy.png"};
                BombBuddy bombBuddy = new BombBuddy(bombBuddyImages, this.gp, 500, 500);
                bombBuddy.setxCoord(300);
                bombBuddy.setyCoord(400);
                items.add(bombBuddy);

                Item random = getRandomItem();
                random.setxCoord(500);
                random.setyCoord(500);
                items.add(random);
                break;

            case 2:
                //String[] appleImages = {"/consumables/apple.png"};
                //Consumable apple = new Consumable(appleImages, false);
                Consumable apple = new Consumable(Consumable.DEFAULT_IMAGE_PATHS, false, 100, 100);
                items.add(apple);
                break;
            case 3:
                //String[] swordImages = {"/weapons/wooden_sword.png"};
                //String[] slimeSlingerImages = {"/items/slingshot.png"};
                //String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
                //String[] bootImages = {"/items/boot.png"};

                //Sword sword = new Sword(swordImages, this.gp, 300, 300);
                //SlimeSlinger slimeSlinger = new SlimeSlinger(slimeSlingerImages, this.gp, 400, 400);
                SlimeSlinger slimeSlinger = new SlimeSlinger(SlimeSlinger.DEFAULT_IMAGE_PATHS, this.gp, 400, 400);
                slimeSlinger.setxCoord(500);
                slimeSlinger.setyCoord(400);
                //Effect effect = new Effect(effectImages);
                Effect effect = new Effect(Effect.DEFAULT_IMAGE_PATHS);
                //Boot boot = new Boot(bootImages, this.gp, 500, 500);
                Boot boot = new Boot(Boot.DEFAULT_IMAGE_PATHS, this.gp, 500, 500);
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
            case 8:
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
                enemies.add(new Barrel(300, 50));
                break;
            case 2:
                enemies.add(new Skeleton(300, 500));
                enemies.add(new Skeleton(100, 300));
                enemies.add(new Skeleton(200, 200));
                break;
            case 3:
                enemies.add(new Wizard(500, 300));
                enemies.add(new Wizard(100, 100));
                enemies.add(new Wizard(200, 140));
                enemies.add(new Wizard(300, 100));
                break;
            case 4:
                enemies.add(new Snowman(200, 200));
                enemies.add(new Snowman(400, 300));
                enemies.add(new Snowman(300, 400));
                enemies.add(new Snowman(200, 200));
                break;
            case 7:
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
                //Coin coin = new Coin(7, coinImages, 600, 500, 1);
                Coin coin = new Coin(Coin.DEFAULT_FRAMES_TO_WAIT, Coin.DEFAULT_COIN_IMAGES, 600, 500, 1);
                coins.add(coin);
                break;
            case 8:
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
        itemID = random.nextInt(3 + 1);

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

    public static ArrayList<Room> generateNewLevel(int difficulty, KeyHandler k, GamePanel g) {
        numOfRooms = Math.min(DEFAULT_NUM_ROOMS + difficulty, MAX_NUM_ROOMS);

        ArrayList<Room> rooms = new ArrayList<>();
        int randRoomType = (int)(Math.random() * roomTypes.length);

        //rooms.add(new Room(0, -1, k, g)); // Add empty room

        for (int i = 0; i <= numOfRooms; i++) {
            Room r = new Room(i, numOfRooms, k, g);

            if (r.getRoomNum() == 0)    {
                rooms.add(r);
                continue;
            }

            if (r.roomType == SHOPROOM) {
                ArrayList<Item> items = new ArrayList<>();
                ArrayList<Chest> chests = new ArrayList<>();
                ArrayList<Sign> signs = new ArrayList<>();
                Chest chest = new Chest(320, 200, g,0);
                Chest chest1 = new Chest(470, 200, g,1);
                Chest chest2 = new Chest(620, 200, g,2);
                Sign sign = new Sign(320, 245, g,0);
                Sign sign1 = new Sign(470, 245, g,1);
                Sign sign2 = new Sign(620, 245, g,2);
                signs.add(sign);
                signs.add(sign1);
                signs.add(sign2);
                chests.add(chest);
                chests.add(chest1);
                chests.add(chest2);
                r.setItems(items);
                r.setChests(chests);
                r.signs = signs;
            } else {

                initializeRandomItems(r, difficulty);
                initializeRandomEnemies(r, difficulty);
                initializeRandomCoins(r, difficulty);

                //r.setNPCs(new ArrayList<>());   // TODO: ADD FURTHER IMPLEMENTATION

                //r.trapTiles = new ArrayList<>();
                //r.setRoomType(roomTypes[(randRoomType++) % roomTypes.length]);
                r.doorTile = null;  // TODO: ADD FURTHER IMPLEMENTATION
                r.buttons = new ArrayList<>();

            }

            rooms.add(r);
        }

        // Spawn boss for the last room in the level
        initializeRandomBoss(rooms.get(rooms.size()-1), difficulty);

        return rooms;
    }

    private static void initializeRandomItems(Room r, int difficulty) {
        // Duplicated random loot set from Chest.java:44,49,55

        int minNumOfItems = 3;
        int variability = 1;

        if (difficulty > PlayerCharacter.MID)   minNumOfItems = 5;

        int numOfCommonItems;
        int numOfRareItems;
        int numOfEpicItems;

        var commonItems = new Class[]{
                Sword.class,
                Sword.class,
                Sword.class,
                HealthUp.class,
                HealthUp.class
        };
        var rareItems = new Class[] {
                Sword.class,
                Sword.class,
                HealthUp.class,
                Boot.class,
                Boot.class,
                Consumable.class,
                Consumable.class
        };
        var epicItems = new Class[] {
                HealthUp.class,
                HealthUp.class,
                Boot.class,
                SlimeSlinger.class,
                SlimeSlinger.class,
                RapidFire.class,
        };


        // The total number of items that can be present in this room
        // Generated by using a constant floor variable plus a random
        // constant, giving a pseudo-random feel for randomness in item quantity
        int numOfItems = (int)((Math.random() * variability) + minNumOfItems);

        switch (difficulty) {
            // Number of items per room here is 3

            // Common is guaranteed to be in the room
            // Rare and epic cannot appear
            case PlayerCharacter.EASY_PEESY, PlayerCharacter.EASY -> {
                numOfCommonItems = numOfItems;
                numOfRareItems = 0;
                numOfEpicItems = 0;
            }
            // Common and Rare are guaranteed to be in the room
            // Epic cannot appear
            case PlayerCharacter.EASY_ADVANCED, PlayerCharacter.MID -> {
                numOfCommonItems = (int)(Math.random()*(numOfItems-1));
                numOfRareItems = numOfItems - numOfCommonItems;
                numOfEpicItems = 0;
            }
            // After this point, the number of items per room is 5

            // Common, Rare, and Epic are guaranteed to be in the room
            case PlayerCharacter.MEDIUM, PlayerCharacter.KINDA_HARD -> {
                numOfCommonItems = (int)(Math.random() * (numOfItems - 2));
                numOfRareItems = (int)(Math.random() * (numOfItems - (numOfCommonItems - 1)));
                numOfEpicItems = numOfItems - numOfCommonItems - numOfRareItems;
            }
            // Rare and Epic are guaranteed to be in the room
            // Common is not guaranteed, but still can appear
            case PlayerCharacter.PRETTY_HARD, PlayerCharacter.HARD -> {
                numOfRareItems = (int)(Math.random() * (numOfItems - 1));
                numOfEpicItems = (int)(Math.random() * (numOfItems - numOfRareItems));
                numOfCommonItems = numOfItems - numOfRareItems - numOfEpicItems;
            }
            // Epic is guaranteed to be in the room
            // Rare is not guaranteed, but still can appear
            // Common cannot appear
            case PlayerCharacter.VERY_HARD, PlayerCharacter.DEMON -> {
                numOfEpicItems = (int)(Math.random() * numOfItems);
                numOfRareItems = numOfItems - numOfEpicItems;
                numOfCommonItems = 0;
            }
            default -> {
                System.err.printf("initializeRandomItems() - Player Difficulty not valid: %d\n", difficulty);
                numOfCommonItems = 0;
                numOfRareItems = 0;
                numOfEpicItems = 0;
            }
        }


        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < numOfCommonItems; i++) {
            int j = (int)(Math.random() * commonItems.length);
            int x = CoordinateWizard.getX(r.getRoomNum());
            int y = CoordinateWizard.getY(r.getRoomNum());

            Item item;

            if (commonItems[j].equals(Sword.class)) {
                item = new Sword(Sword.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            }
            else {
                item = new HealthUp(HealthUp.DEFAULT_HEALTHUP_PATHS, r.gp, x, y);
            }

            items.add(item);
        }

        for (int i = 0; i < numOfRareItems; i++) {
            int j = (int)(Math.random() * rareItems.length);
            int x = CoordinateWizard.getX(r.getRoomNum());
            int y = CoordinateWizard.getY(r.getRoomNum());

            Item item;

            if (rareItems[j].equals(Sword.class)) {
                item = new Sword(Sword.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            } else if (rareItems[j].equals(HealthUp.class)) {
                item = new HealthUp(HealthUp.DEFAULT_HEALTHUP_PATHS, r.gp, x, y);
            } else if (rareItems[j].equals(Boot.class)) {
                item = new Boot(Boot.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            } else {
                item = new Consumable(Consumable.DEFAULT_IMAGE_PATHS, false, x, y);
            }

            items.add(item);
        }

        for (int i = 0; i < numOfEpicItems; i++) {
            int j = (int)(Math.random() * epicItems.length);
            int x = CoordinateWizard.getX(r.getRoomNum());
            int y = CoordinateWizard.getY(r.getRoomNum());

            Item item;

            if (epicItems[j].equals(HealthUp.class)) {
                item = new HealthUp(HealthUp.DEFAULT_HEALTHUP_PATHS, r.gp, x, y);
            } else if (epicItems[j].equals(Boot.class)) {
                item = new Boot(Boot.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            } else if (epicItems[j].equals(SlimeSlinger.class)) {
                item = new SlimeSlinger(SlimeSlinger.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            } else {
                item = new RapidFire(RapidFire.DEFAULT_IMAGE_PATHS, r.gp, x, y);
            }

            items.add(item);
        }

        r.setItems(items);
    }

    private static void initializeRandomEnemies(Room r, int difficulty) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        int minNumOfEnemies;
        int variability = 2 + difficulty;
        var enemyTypes = new Class[]{
                Barrel.class,
                Skeleton.class,
                Skeleton.class,
                Wizard.class,
                Wizard.class
        };

        switch (difficulty) {
            case PlayerCharacter.EASY_PEESY, PlayerCharacter.EASY -> minNumOfEnemies = difficulty;
            case PlayerCharacter.EASY_ADVANCED, PlayerCharacter.MID -> minNumOfEnemies = difficulty;
            case PlayerCharacter.MEDIUM, PlayerCharacter.KINDA_HARD -> minNumOfEnemies = difficulty;
            case PlayerCharacter.PRETTY_HARD, PlayerCharacter.HARD -> minNumOfEnemies = 10;
            case PlayerCharacter.VERY_HARD -> minNumOfEnemies = 15;
            case PlayerCharacter.DEMON -> minNumOfEnemies = 20;
            default -> {
                System.err.printf("initializeRandomEnemies() - Player Difficulty not valid: %d\n", difficulty);
                minNumOfEnemies = 0;
                variability = 0;
            }
        }


        int num = minNumOfEnemies + (int)(Math.random() * variability);
        Enemy e;

        for (int i = 0; i < num; i++) {
            int j = (int)(Math.random() * enemyTypes.length);
            int x = CoordinateWizard.getX(r.getRoomNum());
            int y = CoordinateWizard.getY(r.getRoomNum());

            if (enemyTypes[j].equals(Barrel.class)) {
                e = new Barrel(x, y);
            } else if (enemyTypes[j].equals(Skeleton.class)) {
                e = new Skeleton(x, y);
                e.setMaxHealth((difficulty < PlayerCharacter.EASY) ? e.getMaxHealth() : e.getMaxHealth() * difficulty);
                e.setDamagePerHit((difficulty < PlayerCharacter.EASY_ADVANCED) ? difficulty :  5 * difficulty);
            } else {
                e = new Wizard(x, y);
                e.setMaxHealth((difficulty < PlayerCharacter.EASY) ? e.getMaxHealth() : e.getMaxHealth() * difficulty);
                e.setDamagePerHit((difficulty < PlayerCharacter.EASY_ADVANCED) ? difficulty : 5 * difficulty);
            }

            enemies.add(e);
        }

        r.setEnemies(enemies);
    }

    public static void initializeRandomCoins(Room r, int difficulty) {
        ArrayList<Coin> coins = new ArrayList<>();

        int minNumOfCoins = 5 + difficulty;
        int variability = 3;
        int value;

        switch (difficulty) {
            case PlayerCharacter.EASY_PEESY, PlayerCharacter.EASY -> value = 1;
            case PlayerCharacter.EASY_ADVANCED, PlayerCharacter.MID -> value = 2;
            case PlayerCharacter.MEDIUM, PlayerCharacter.KINDA_HARD -> value = 3;
            case PlayerCharacter.PRETTY_HARD, PlayerCharacter.HARD -> value = 4;
            case PlayerCharacter.VERY_HARD -> value = 7;
            case PlayerCharacter.DEMON -> value = 10;
            default -> {
                System.err.printf("initializeRandomCoins() - Player Difficulty not valid: %d\n", difficulty);
                minNumOfCoins = 0;
                variability = 0;
                value = 0;
            }
        }

        int numCoins = minNumOfCoins + (int)(Math.random() * variability);

        for (int i = 0; i < numCoins; i++) {
            int x = CoordinateWizard.getX(r.getRoomNum());
            int y = CoordinateWizard.getY(r.getRoomNum());

            Coin c = new Coin(Coin.DEFAULT_FRAMES_TO_WAIT, Coin.DEFAULT_COIN_IMAGES, x, y, value);
            coins.add(c);
        }

        r.setCoins(coins);
    }

    public static void initializeRandomBoss(Room r, int difficulty) {
        var bossTypes = new Class[]{
                BigSkull.class,
                BigSlonch.class
        };

        int x = CoordinateWizard.getX(r.getRoomNum());
        int y = CoordinateWizard.getY(r.getRoomNum());

        int i = (int)(Math.random() * 2);

        if (bossTypes[i].equals(BigSkull.class)) {
            BigSkull skull = new BigSkull(x, y);
            skull.setMaxHealth(50 * difficulty);
            skull.setDamagePerHit(25 * difficulty);
            r.getEnemies().add(skull);
        } else {
            BigSlonch bigSlonch = new BigSlonch(x, y);
            bigSlonch.setMaxHealth(50 * difficulty);
            bigSlonch.setDamagePerHit(25 * difficulty);
            r.getEnemies().add(bigSlonch);
        }
    }
}

class TestNextLevel {
    public static void main(String[] args) {
        Main.main(args);
        Main.view.getGamePanel().startGameThread();
        Main.view.getGamePanel().nextLevel();
        Main.view.showGamePanel();
    }
}
