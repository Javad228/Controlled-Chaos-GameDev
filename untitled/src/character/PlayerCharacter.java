package character;

import enemy.SlimeBall;
import loot.*;
import main.*;
import save.SimpleCharacter;
import tile.Tile;
import tile.TileManager;
import save.SimpleEnemy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * PlayerCharacter - A class which models a user-controlled character and contains attributes for a Character.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class PlayerCharacter extends Character {

    public static transient final int EASY_PEESY     = 0;
    public static transient final int EASY           = 1;
    public static transient final int EASY_ADVANCED  = 2;
    public static transient final int MID            = 3;
    public static transient final int MEDIUM         = 4;
    public static transient final int KINDA_HARD     = 5;
    public static transient final int PRETTY_HARD    = 6;
    public static transient final int HARD           = 7;
    public static transient final int VERY_HARD      = 8;
    public static transient final int DEMON          = 9;

    public static transient final int LOWEST_DIFF    = EASY_PEESY;
    public static transient final int HIGHEST_DIFF   = DEMON;

    public static transient final String[] difficultyNamTab
            = new String[]{"Easy-Peesy", "Easy", "Easy-Advanced", "Mid", "Medium", "Kinda-Hard", "Pretty-Hard", "HARD", "VERY HARD", "DEMON"};


    public static transient final int STARTING_LEVEL = 1;
    public static transient final int MAX_LEVEL = 3;

    private CharacterType characterType;    // Player Character Type
    private Item startingItem;              // Player Starting Item
    private Inventory inventory;            // Player character.Inventory
    private final HealthBar healthBar;
    private PowerBar powerBar;
    private GamePanel gp;
    private KeyHandler keyH;
    public String projectileName = "Arrow";
    private int shotAvailableTimer = 0;
    private int shotTimerMax = 50;
    private double damageMod = 1;
    private int gameDifficulty;

    private int numCoins;
    private boolean isDying;                // Used for performing death animation
    private boolean isDamaged;              // Used to determine if player has been damaged
    private ArrayList<SimpleEnemy> enemiesKilled;
    public ArrayList<SimpleEnemy> allEnemiesKilled;
    private ArrayList<Item> itemsDiscovered;
    private boolean[] itemsUnlocked;
    private int itemPriority;
    private Tile currentTile;


    public int roomSetNum;


    private String characterAppearance;

    public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
        super();
        this.characterType = CharacterType.DEFAULT;
        this.inventory = new Inventory(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.isDying = false;
        this.isDamaged = false;
        this.gameDifficulty = PlayerCharacter.LOWEST_DIFF;       // Set to easiest difficulty (default)

        this.solidArea.x = 0;
        this.solidArea.y = 10;
        this.setWidth(32);
        this.setHeight(32);
        this.solidArea.width = 9;
        this.solidArea.height = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        setDeathImages(new BufferedImage[3]); // should be in super()
        setAttackImages(new BufferedImage[4][3]); // should be in super()
        setWalkingImages(new BufferedImage[4][3]); // should be in super()
        this.currentTile = null;
        characterAppearance = "warrior";
        getPlayerImage();

        this.setHasThrownProjectile(false);

        this.healthBar = new HealthBar(this.health, getMaxHealth(), gp.tileSize+2, 10);

        this.name = "Intrepid Adventurer";
        Random r = new Random();
        roomSetNum = 1;//r.nextInt(3) + 1;
        System.out.println("it is room set number" + roomSetNum);
        this.numCoins = 0;
        enemiesKilled = new ArrayList<>();
        allEnemiesKilled = new ArrayList<>();
        itemsDiscovered = new ArrayList<>();
        this.powerBar = new PowerBar(this.allEnemiesKilled.size(),2,gp.tileSize+2, 10,gp);
        setDefaultValues();
    }

    public PlayerCharacter(PlayerCharacter pc) {
        this.characterType = pc.getCharacterType();
        this.inventory = pc.getInventory();
        this.gp = pc.gp;
        this.keyH = pc.keyH;
        this.isDying = false;
        this.setName(pc.getName());
        this.setHealth(pc.getHealth());
        this.setMovementSpeed(pc.getMovementSpeed());
        this.setMaxSpeed(pc.getMaxSpeed());
        this.setxCoord(pc.getxCoord());
        this.setyCoord(pc.getyCoord());
        this.setActiveEffects(pc.getActiveEffects());
        this.setCharacterType(pc.getCharacterType());
        this.setTimeForInvincibility(pc.getTimeForInvincibility());
        this.setDirection(pc.getDirection());
        this.setSpriteCounter(pc.getSpriteCounter());
        this.setSpriteNum(pc.getSpriteNum());
        this.setStartingItem(pc.getStartingItem());
        this.healthBar = pc.healthBar;
        this.currentTile = pc.currentTile;
        this.numCoins = pc.numCoins;
        enemiesKilled = new ArrayList<>(pc.getEnemiesKilled());
        allEnemiesKilled = new ArrayList<>(pc.getEnemiesKilled());
        this.characterAppearance = pc.getCharacterAppearance();

    }

    public PlayerCharacter(SimpleCharacter c, GamePanel gp, KeyHandler keyH) {
        this(gp, keyH);
        this.name = c.name;
        setMaxHealth(c.maxHealth);
        setHealth(c.health);
        this.movementSpeed = c.movementSpeed;
        this.xCoord = c.xCoord;
        this.yCoord = c.yCoord;
        this.activeEffects = c.activeEffects;
        this.combatType = c.combatType;
        this.inventory = c.inventory;
        this.characterType = c.characterType;
        this.numCoins = c.getNumCoins();
        this.currentTile = null;

        enemiesKilled = new ArrayList<>(c.getEnemiesKilled());
        allEnemiesKilled = new ArrayList<>(c.getEnemiesKilled());
        itemsDiscovered = c.itemsDiscovered;
        this.isDamaged = c.isDamaged;
        this.characterAppearance = c.characterAppearance;
    }

    public void setDefaultValues() {
        this.setxCoord(50);
        this.setyCoord(200);
        this.setMovementSpeed(4);
        this.setMaxSpeed(4);
        this.setMaxSpeed(5);
        this.setDirection("down");
        this.solidArea = new Rectangle(0, 16, 30, 30);
        this.attackArea.width = 36;
        this.attackArea.height = 36;
        String[] stringArray = {"/weapons/wooden_sword.png"};
        String[] stringArray1 = {"/weapons/wooden_sword.png"};
        Item item = new Item(7,stringArray);
        item.setName("Sword");
        item.setDescription("wooden sword");
        Item item1 = new Item(7,stringArray1);
        item.setName("Sword #2");
        item1.setDescription("wooden sword #2");
        this.itemsUnlocked = new boolean[]{ true, true, true, true, false, false};
        this.itemPriority = -1;

        this.getInventory().addItem(item);
        getItemsDiscovered().add(item);
        this.getInventory().addItem(item1);
        getItemsDiscovered().add(item1);
//        this.setWidth(18);
//        this.setHeight(46);
//        this.collisionAreaDefaultX = solidArea.x;
//        this.collisionAreaDefaultY = solidArea.y;
        this.characterAppearance = "warrior";

    }

    public void setMaxHealth(int maxHealth) {
        super.setMaxHealth(maxHealth);
        this.healthBar.setMaxHealth(maxHealth);
    }

    public int getRow() {
        return yCoord/gp.tileSize;
    }

    public int getCol() {
        return xCoord/gp.tileSize;
    }

    public Tile getCurrentTile() {

        int tileNum = TileManager.mapTileNum[getCol()][getRow()];

        return TileManager.tile[tileNum];
    }

    public void getPlayerImage() {
        try {
            // get walking sprites
            // Row 0 = up, Row 1 = down, Row 2 = left, Row 3 = right
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/up_1.png")), 0, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/up_2.png")), 0, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/up_3.png")), 0, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/down_1.png")), 1, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/down_2.png")), 1, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/down_3.png")), 1, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/left_1.png")), 2, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/left_2.png")), 2, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/left_3.png")), 2, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/right_1.png")), 3, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/right_2.png")), 3, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/walk/right_3.png")), 3, 2);

            // get attack sprites
            // again, Row 0 = up, Row 1 = down, Row 2 = left, Row 3 = right
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/up_1.png")), 0, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/up_2.png")), 0, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/up_3.png")), 0, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/down_1.png")), 1, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/down_2.png")), 1, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/down_3.png")), 1, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/left_1.png")), 2, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/left_2.png")), 2, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/left_3.png")), 2, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/right_1.png")), 3, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/right_2.png")), 3, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/" + characterAppearance + "/attack/right_3.png")), 3, 2);

            // get death sprites
            // there's only 3 so no need for multidimensional arrays
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/" + characterAppearance + "/death/death_1.png"))), 0);
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/" + characterAppearance + "/death/death_2.png"))), 1);
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/" + characterAppearance + "/death/death_3.png"))), 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SimpleEnemy> getAllEnemiesKilled() {
        return allEnemiesKilled;
    }

    public void setAllEnemiesKilled(ArrayList<SimpleEnemy> allEnemiesKilled) {
        this.allEnemiesKilled = allEnemiesKilled;
    }

    public void update() {
        // damage player by 1 every 5th of a second while in volcano room
        if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.VOLCANOROOM && volcHealthCounter == 0) {
            damagePlayerInt(1);
        }
        volcHealthCounter++;
        volcHealthCounter %= gp.getFps();


        this.healthBar.update(this.getHealth());
        this.powerBar.update();


        // Escape clause for death animation
        // Only update the sprite counter
        if (getHealth() <= 0) {

            // If this is the first instance of performing the death animation,
            // set values to perform death animation as intended
            if (!isDying()) {
                setSpriteNum(0);
                setSpriteCounter(0);
            }

            setIsDying(true);

            setSpriteCounter(getSpriteCounter() + 1);   // Increment sprite counter

            if (getSpriteNum() < 2 && getSpriteCounter() == 20) {
                setSpriteNum(getSpriteNum() + 1);   // Increment sprite num for animation
                setSpriteCounter(0);
            }

            if (getSpriteNum() > 1 && getSpriteCounter() == 40) {
                setIsAlive(false);  // Flag for the death panel to be shown
            }

            /*Alternate spriteCounter incrementation here*/
            return;
        }

        if (keyH == null) return;

        if(isInvincible){
            invincibleCounter++;
            if(invincibleCounter>30){
                isInvincible = false;
                invincibleCounter = 0;
            }
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            int currentX = this.getxCoord();
            int currentY = this.getyCoord();

            if (keyH.upPressed && !keyH.downPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "up", true, damageMod);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.downPressed && !keyH.upPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "down", true, damageMod);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.leftPressed && !keyH.rightPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "left", true, damageMod);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.rightPressed && !keyH.leftPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "right", true, damageMod);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
        }
        if (shotAvailableTimer < shotTimerMax) {
            shotAvailableTimer++;
        }

        if (keyH.kPressed || (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed)) {

            if (keyH.kPressed) {
                attacking();
                isAttacking = true;
            } else {
                isAttacking = false;
            }

            this.setSpriteCounter(this.getSpriteCounter() + 1);
            int maxSprite;
            if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.SPACEROOM) {
                maxSprite = 24;
            } else {
                maxSprite = 12;
            }
            if (this.getSpriteCounter() > maxSprite) {
                if (this.getSpriteNum() == 1) {
                    this.setSpriteNum(2);
                    this.setWasOne(true);
                } else if (this.getSpriteNum() == 2 && this.isWasOne()) { // if 2 and the last sprite number was 1
                    this.setSpriteNum(3);
                } else if (this.getSpriteNum() == 2 && !this.isWasOne()) { // if 2 and the last sprite number was NOT 1 (i.e. 3)
                    this.setSpriteNum(1);
                } else if (this.getSpriteNum() == 3) {
                    this.setSpriteNum(2);
                    this.setWasOne(false);
                } else {
                    System.out.println("Experiencing issue with setting the sprite number within PlayerCharacter.java's update method.");
                }
                this.setSpriteCounter(0);
            }

            if (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) {
                collisionOn = false;
                gp.checker.checkTile(this);
                int currentX = this.getxCoord();
                int currentY = this.getyCoord();
                int speed = this.getMovementSpeed();

                if (keyH.wPressed) {
                    this.setDirection("up");
                }
                if (keyH.sPressed && !keyH.wPressed) {
                    this.setDirection("down");
                }
                if (keyH.aPressed && !keyH.dPressed) {
                    this.setDirection("left");
                }
                if (keyH.dPressed && !keyH.aPressed) {
                    this.setDirection("right");
                }

                if(!collisionOn){
                    if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() != Room.SPACEROOM) {
                        if (direction.equals("up") && currentY > 0) {
                            this.setyCoord(currentY - speed);
                        }
                        if (direction.equals("down") && currentY < (gp.screenHeight - this.getHeight())) {
                            this.setyCoord(currentY + speed);
                        }
                        if (direction.equals("left") && currentX > 0) {
                            this.setxCoord(currentX - speed);
                        }
                        if (direction.equals("right") && currentX < (gp.screenWidth - this.getWidth())) {
                            this.setxCoord(currentX + speed);
                        }
                    } else {
                        if ((direction.equals("up") && currentY > 0) || (direction.equals("down") && currentY < (gp.screenHeight - this.getHeight()))) {
                            if (isUp && direction.equals("down")) {
                                // just switched directions. make numBounces = 0 so we restart our bouncing motion.
                                numBounces = 0;
                                isUp = false;
                            } else if (!isUp && direction.equals("up")) {
                                numBounces = 0;
                                isUp = true;
                            }

                            if (numBounces == 0) {
                                origY = currentY;
                            }
                            int distToBounce = (int) (-0.033 * Math.pow(numBounces, 2) + speed / 1.5 * numBounces);
                            //this.setyCoord(origY + distToBounce);
                            numBounces++;
                            numBounces = numBounces % (2 * speed * 5);

                            if (direction.equals("up")) {
                                this.setyCoord(origY - distToBounce);
                            } else {
                                this.setyCoord(origY + distToBounce);
                            }
                        } else {
                            //origY = currentY;
                            numBounces = 0;
                        }

                        /*if (direction.equals("up") && currentY > 0) {
                            this.setyCoord(currentY - speed);
                            *//*if (numBounces == 0) {
                                origY = currentY;
                            }
                            int distToBounce = (int) (-0.033 * Math.pow(numBounces, 2) + speed / 1.5 * numBounces);
                            this.setyCoord(origY - distToBounce);
                            numBounces++;
                            numBounces = numBounces % (2 * speed * 5);*//*
                        }*//* else {
                            origY = currentY;
                            numBounces = 0;
                        }*//*
                        if (direction.equals("down") && currentY < (gp.screenHeight - this.getHeight())) {
                            if (numBounces == 0) {
                                origY = currentY;
                            }
                            int distToBounce = (int) (-0.033 * Math.pow(numBounces, 2) + speed / 1.5 * numBounces);
                            this.setyCoord(origY + distToBounce);
                            numBounces++;
                            numBounces = numBounces % (2 * speed * 5);
                        } else {
                            origY = currentY;
                            numBounces = 0;
                        }*/
                        if (direction.equals("left") && currentX > 0) {
                            this.setxCoord(currentX - speed);
                            if (currentBounce == 0) {
                                shouldBounce = true;
                                currentBounce++;
                            }else if (shouldBounce && currentBounce < maxBounce) {
                                currentBounce++;
                                this.setyCoord(currentY + 1);
                            } else if (currentBounce == maxBounce) {
                                shouldBounce = false;
                                currentBounce--;
                            } else if (!shouldBounce && currentBounce < maxBounce) {
                                currentBounce--;
                                this.setyCoord(currentY - 1);
                            } else {
                                System.out.println("Bad bouncing effect within PlayerCharacter.java");
                            }
                        }
                        if (direction.equals("right") && currentX < (gp.screenWidth - this.getWidth())) {
                            this.setxCoord(currentX + speed);
                            if (currentBounce == 0) {
                                shouldBounce = true;
                                currentBounce++;
                            }else if (shouldBounce && currentBounce < maxBounce) {
                                currentBounce++;
                                this.setyCoord(currentY + 1);
                            } else if (currentBounce == maxBounce) {
                                shouldBounce = false;
                                currentBounce--;
                            } else if (!shouldBounce && currentBounce < maxBounce) {
                                currentBounce--;
                                this.setyCoord(currentY - 1);
                            } else {
                                System.out.println("Bad bouncing effect within PlayerCharacter.java");
                            }
                        }
                    }
                }

                // GamePanel has an arraylist of rooms. We are in the room indicated by the currentRoomNum, which
                // corresponds to the rooms index in the arraylist. Each room has an arraylist of items. Must check if
                // it is null before proceeding.
                if (gp.getRooms().get(gp.getCurrentRoomNum()).getItems() != null){
                    ArrayList<Item> currentList = gp.getRooms().get(gp.getCurrentRoomNum()).getItems();
                    for (int i = 0; i < currentList.size(); i++) {
                        Item item = currentList.get(i);
                        if (gp.checker.checkLootCollision(this, item)) {
                            System.out.println("Just collided with item of type " + item.toString());

                            if (item instanceof Consumable && ((Consumable) item).isVisible) {
                                heal(((Consumable) item).consume());
                            }
                            else {
                                inventory.addItem(item);
                                if (!(item instanceof Weapon)) {
                                    item.setEquipped(true);
                                }
                            }
                            currentList.remove(i);
                            Audio.itemPickUpAudio();
                        }
                    }
                }

                if (gp.getRooms().get(gp.getCurrentRoomNum()).getCoins() != null){
                    ArrayList<Coin> currentList = gp.getRooms().get(gp.getCurrentRoomNum()).getCoins();
                    for (int i = 0; i < currentList.size(); i++) {
                        Coin coin = currentList.get(i);
                        if (gp.checker.checkLootCollision(this, coin)) {
                            //this.numCoins = this.numCoins + coin.getValue();
                            setNumCoins(getNumCoins() + coin.getValue());
                            currentList.remove(coin);
                            Audio.itemPickUpAudio();
                        }
                    }
                }
            }

        }
    }

    public void attacking() {

        int currentWorldX = xCoord;
        int currentWorldY = yCoord;
        int collisionAreaWidth = solidArea.width;
        int collisionAreaHeight = solidArea.height;

        switch (direction) {
            case "up" -> yCoord -= attackArea.height;
            case "down" -> yCoord += attackArea.height;
            case "left" -> xCoord -= attackArea.width;
            case "right" -> xCoord += attackArea.width;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        if (gp.getRooms().get(gp.getCurrentRoomNum()).getEnemies() != null){
            ArrayList<Enemy> currentList = gp.getRooms().get(gp.getCurrentRoomNum()).getEnemies();
            for (int i = 0; i < currentList.size(); i++) {
                Enemy enemy = currentList.get(i);
                Boolean isHit = gp.checker.checkEntityAttack(this, enemy);
                if(isHit){
                    //Audio.enemyDamagedAudio();
                    damageMonster(enemy);
                    System.out.println("Hit");
                }

            }
        }

//        System.out.println(isHit);

        /*
        isHit = gp.checker.checkConsumableCollision(this, gp.apple);

        if(isHit && gp.apple.isVisible) {
            heal(gp.apple.consume());
        }
         */

        //After checking collision, restore original data
        xCoord = currentWorldX;
        yCoord = currentWorldY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }

    public void damageMonster (Enemy enemy) {
        if (!enemy.isInvincible) {
            enemy.health -= 1;
            enemy.isInvincible = true;
            System.out.println(enemy.health);
            Audio.enemyDamagedAudio();

            enemy.checkIfDead(this);
        }

    }

    @Override
    public void damage(double damageTaken) {
        if (!isDamaged)  setIsDamaged(true);
        super.damage(damageTaken);
    }

    public void damagePlayer(Projectile projectile) {
        gp.getPlayer().damage(projectile.getDamage());
        gp.getPlayer().isInvincible = true;
    }

    public void damagePlayer(NonPlayableCharacter entity) {
        if(!gp.getPlayer().isInvincible){
            //gp.getPlayer().setHealth(gp.getPlayer().getHealth()-gp.enemy.getDamagePerHit());
            gp.getPlayer().damage(entity.getDamagePerHit());
            gp.getPlayer().isInvincible = true;
            //System.out.println(gp.getPlayer().getHealth());     //TODO DEBUG PlayerCharacter Invincibility
        }
    }

    public void shoot(String projectileName, GamePanel gp, int currentX, int currentY, String direction, boolean isPlayerShooting, double damage) {
        if (projectileName.equals("Arrow")) {
            Arrow arrow = new Arrow(gp, currentX, currentY, direction, isPlayerShooting, damage); //RANGED, true (isInvincible), this (user)
        }
        if (projectileName.equals("SlimeBall")) {
            SlimeBall slimeBall = new SlimeBall(gp, currentX, currentY, direction, isPlayerShooting, damage);
        }
        if (projectileName.equals("Bomb")) {
            Bomb bomb = new Bomb(gp, currentX, currentY, direction, isPlayerShooting, damage);
        }
    }
    public void damagePlayerInt(int damageAmount) {
        if(!gp.getPlayer().isInvincible){
            //gp.getPlayer().setHealth(gp.getPlayer().getHealth()-gp.enemy.getDamagePerHit());
            gp.getPlayer().damage(damageAmount);
            gp.getPlayer().isInvincible = true;

            //Needs same invincibility debugging as above method
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        this.healthBar.draw(g2,
                this.getxCoord(),
                this.getyCoord() - this.healthBar.getHeight());
        this.powerBar.draw(g2,
                this.getxCoord(),
                this.getyCoord() - this.healthBar.getHeight()-10);

        // If player is signaled to do death animation, show death animation
        if (isDying()) {
            image = getDeathImage(this.getSpriteNum());
            g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);

            return;
        }

        if (!isAttacking) { // if we are NOT attacking (i.e. walking)
            switch (this.getDirection()) {
                case "up":
                    image = this.getWalkingImage(0, this.getSpriteNum() - 1);
                    break;
                case "down":
                    image = this.getWalkingImage(1, this.getSpriteNum() - 1);
                    break;
                case "left":
                    image = this.getWalkingImage(2, this.getSpriteNum() - 1);
                    break;
                case "right":
                    image = this.getWalkingImage(3, this.getSpriteNum() - 1);
            }
        } else { // we are attacking
            switch (this.getDirection()) {
                case "up":
                    image = this.getAttackImage(0, this.getSpriteNum() - 1);
                    break;
                case "down":
                    image = this.getAttackImage(1, this.getSpriteNum() - 1);
                    break;
                case "left":
                    image = this.getAttackImage(2, this.getSpriteNum() - 1);
                    break;
                case "right":
                    image = this.getAttackImage(3, this.getSpriteNum() - 1);
            }
        }

        g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);


        this.healthBar.draw(g2,
                this.getxCoord(),
                this.getyCoord() - this.healthBar.getHeight());
        this.powerBar.draw(g2,
                this.getxCoord(),
                this.getyCoord() - this.healthBar.getHeight()-10);
    }

    public CharacterType getCharacterType () {
        return characterType;
    }

    public void setCharacterType (CharacterType characterType){
        this.characterType = characterType;
    }

    public Inventory getInventory () {
        return inventory;
    }

    public void setInventory (Inventory inventory){
        this.inventory = inventory;
    }

    public void setGamePanel (GamePanel gp){
        this.gp = gp;
    }

    public void setKeyHandler (KeyHandler keyH){
        this.keyH = keyH;
    }

    public Item getStartingItem () {
        return this.startingItem;
    }

    public void setStartingItem (Item startingItem){
        this.startingItem = startingItem;
    }

    public void setIsDying(boolean isDying) {
        this.isDying = isDying;
    }

    public boolean isDying() {
        return this.isDying;
    }

    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public boolean isDamaged() {
        return this.isDamaged;
    }

    @Override
    public boolean equals (Object o){
        if (this.getClass() != o.getClass()) return false;

        PlayerCharacter pc = (PlayerCharacter) o;
        if (this.characterType != pc.getCharacterType()) return false;
        if (!this.inventory.equals(pc.getInventory())) return false;
        return super.equals(o);
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public int getShotTimerMax() {
        return shotTimerMax;
    }

    public void setShotTimerMax(int shotTimerMax) {
        this.shotTimerMax = shotTimerMax;
    }

    public String getProjectileName() {
        return projectileName;
    }

    public void setProjectileName(String projectileName) {
        this.projectileName = projectileName;
    }

    public int getShotAvailableTimer() { return shotAvailableTimer; }

    public void setShotAvailableTimer(int shotAvailableTimer) { this.shotAvailableTimer = shotAvailableTimer; }

    public ArrayList<SimpleEnemy> getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(ArrayList<SimpleEnemy> enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public ArrayList<Item> getItemsDiscovered() {
        return itemsDiscovered;
    }

    public void setItemsDiscovered(ArrayList<Item> itemsDiscovered) {
        this.itemsDiscovered = itemsDiscovered;
    }

    public double getDamageMod() {
        return damageMod;
    }

    public void setDamageMod(double damageMod) {
        this.damageMod = damageMod;
    }

    public boolean[] getItemsUnlocked() {
        return itemsUnlocked;
    }

    public void setItemsUnlocked(boolean[] itemsUnlocked) {
        this.itemsUnlocked = itemsUnlocked;
    }
    public void unlockItem(int itemID) {
        this.itemsUnlocked[itemID] = true;

        setItemPriority(itemID);

        ImageIcon icon = new ImageIcon(getClass().getResource("/items/health.png"));
        if(itemID == 4) {
            icon = new ImageIcon(getClass().getResource("/items/rapid-fire.png"));
        }
        if(itemID == 5) {
            icon = new ImageIcon(getClass().getResource("/items/bomb-buddy.png"));
        }
        JOptionPane.showMessageDialog(
                null,
                "A new item will appear in the realm!",
                "New Unlock!", JOptionPane.INFORMATION_MESSAGE,
                icon);
    }

    public int getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(int itemPriority) {
        this.itemPriority = itemPriority;
    }

    public int getGameDifficulty() {
        return this.gameDifficulty;
    }

    public void setGameDifficulty(int gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public void incrementDifficulty() {
        if (this.gameDifficulty == PlayerCharacter.DEMON)   return;

        this.gameDifficulty++;
    }

    public String getCharacterAppearance() {
        return characterAppearance;
    }

    public void setCharacterAppearance(String characterAppearance) {
        this.characterAppearance = characterAppearance;
    }

    /**
     * incrementLevel() - Increments the player's level and other relative variables
     * in the game
     * <p>
     */
    public void incrementLevel() {
        this.roomSetNum = (this.roomSetNum)%MAX_LEVEL+1;
    }
}

