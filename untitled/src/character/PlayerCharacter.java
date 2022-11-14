package character;

import enemy.SlimeBall;
import loot.*;
import main.Audio;
import main.GamePanel;
import main.HealthBar;
import main.KeyHandler;
import save.SimpleCharacter;
import tile.Tile;
import tile.TileManager;
import save.SimpleEnemy;

import javax.imageio.ImageIO;
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
    private CharacterType characterType;    // Player Character Type
    private Item startingItem;              // Player Starting Item
    private Inventory inventory;            // Player character.Inventory
    private HealthBar healthBar;
    private GamePanel gp;
    private KeyHandler keyH;

    public String projectileName = "Bomb";
    private int shotAvailableTimer = 0;
    private int shotTimerMax = 50;

    private int numCoins;
    private boolean isDying;                // Used for performing death animation
    private ArrayList<SimpleEnemy> enemiesKilled;
    private ArrayList<Item> itemsDiscovered;

    //private transient BufferedImage[] deathImages;
    private Tile currentTile;
    public int roomSetNum;

    public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
        super();
        this.characterType = CharacterType.DEFAULT;
        this.inventory = new Inventory(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.isDying = false;

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
        getPlayerImage();

        this.setHasThrownProjectile(false);

        this.healthBar = new HealthBar(this.health, this.maxHealth, 40, 10);
        this.name = "Intrepid Adventurer";
        Random r = new Random();
        roomSetNum = r.nextInt(2) + 1;
        System.out.println("it is room set number" + roomSetNum);
        this.numCoins = 0;
        enemiesKilled = new ArrayList<>();
        itemsDiscovered = new ArrayList<>();
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
    }

    public PlayerCharacter(SimpleCharacter c, GamePanel gp, KeyHandler keyH) {
        this(gp, keyH);
        this.name = c.name;
        this.health = c.health;
        this.maxHealth = c.maxHealth;
        this.movementSpeed = c.movementSpeed;
        this.xCoord = c.xCoord;
        this.yCoord = c.yCoord;
        this.activeEffects = c.activeEffects;
        this.type = c.combatType;
        this.inventory = c.inventory;
        this.characterType = c.characterType;
        this.numCoins = c.getNumCoins();
        this.currentTile = null;
        enemiesKilled = new ArrayList<>(c.getEnemiesKilled());
        itemsDiscovered = c.itemsDiscovered;
    }

    public void setDefaultValues() {
        this.setxCoord(50);
        this.setyCoord(200);
        this.setMovementSpeed(4);
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

        this.getInventory().addItem(item);
        getItemsDiscovered().add(item);
        this.getInventory().addItem(item1);
        getItemsDiscovered().add(item1);
//        this.setWidth(18);
//        this.setHeight(46);
//        this.collisionAreaDefaultX = solidArea.x;
//        this.collisionAreaDefaultY = solidArea.y;

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
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/up_1.png")), 0, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/up_2.png")), 0, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/up_3.png")), 0, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/down_1.png")), 1, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/down_2.png")), 1, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/down_3.png")), 1, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/left_1.png")), 2, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/left_2.png")), 2, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/left_3.png")), 2, 2);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/right_1.png")), 3, 0);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/right_2.png")), 3, 1);
            this.setWalkingImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/walk/right_3.png")), 3, 2);

            // get attack sprites
            // again, Row 0 = up, Row 1 = down, Row 2 = left, Row 3 = right
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/up_1.png")), 0, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/up_2.png")), 0, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/up_3.png")), 0, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/down_1.png")), 1, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/down_2.png")), 1, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/down_3.png")), 1, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/left_1.png")), 2, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/left_2.png")), 2, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/left_3.png")), 2, 2);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/right_1.png")), 3, 0);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/right_2.png")), 3, 1);
            this.setAttackImage(ImageIO.read(getClass().getResourceAsStream("/player_character/woman_warrior/attack/right_3.png")), 3, 2);

            // get death sprites
            // there's only 3 so no need for multidimensional arrays
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/woman_warrior/death/death_1.png"))), 0);
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/woman_warrior/death/death_2.png"))), 1);
            this.setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player_character/woman_warrior/death/death_3.png"))), 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        gp.checker.checkRoom(this);

        this.healthBar.update(this.getHealth());

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
                shoot(this.getProjectileName(), gp, currentX, currentY, "up", true);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.downPressed && !keyH.upPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "down", true);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.leftPressed && !keyH.rightPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "left", true);
                this.setHasThrownProjectile(true);
                shotAvailableTimer = 0;
            }
            if (keyH.rightPressed && !keyH.leftPressed && (shotAvailableTimer == shotTimerMax)) {
                shoot(this.getProjectileName(), gp, currentX, currentY, "right", true);
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
            if (this.getSpriteCounter() > 12) {
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
                    if(direction.equals("up") && currentY > 0){
                        this.setyCoord(currentY - speed);
                    }
                    if(direction.equals("down") && currentY < (gp.screenHeight - this.getHeight())){
                        this.setyCoord(currentY + speed);
                    }
                    if(direction.equals("left") && currentX > 0){
                        this.setxCoord(currentX - speed);
                    }
                    if(direction.equals("right") && currentX < (gp.screenWidth - this.getWidth())){
                        this.setxCoord(currentX + speed);
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
                            this.numCoins = this.numCoins + coin.getValue();
                            currentList.remove(i);
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

    public void shoot(String projectileName, GamePanel gp, int currentX, int currentY, String direction, boolean isPlayerShooting) {
        if (projectileName.equals("Arrow")) {
            Arrow arrow = new Arrow(gp, currentX, currentY, direction, isPlayerShooting); //RANGED, true (isInvincible), this (user)
        }
        if (projectileName.equals("SlimeBall")) {
            SlimeBall slimeBall = new SlimeBall(gp, currentX, currentY, direction, isPlayerShooting);
        }
        if (projectileName.equals("Bomb")) {
            Bomb bomb = new Bomb(gp, currentX, currentY, direction, isPlayerShooting);
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
}

