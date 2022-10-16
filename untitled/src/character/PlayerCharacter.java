package character;

import main.GamePanel;
import main.HealthBar;
import main.KeyHandler;
import loot.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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


    public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
        super();
        this.characterType = CharacterType.DEFAULT;
        this.inventory = new Inventory();
        this.gp = gp;
        this.keyH = keyH;
        this.solidArea.x = 3;
        this.solidArea.y = 18;
        this.setWidth(18);
        this.setHeight(46);
        this.solidArea.width = 9;
        this.solidArea.height = 23;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
        this.healthBar = new HealthBar(this.health, this.maxHealth, 40, 10);
    }

    public PlayerCharacter(PlayerCharacter pc) {
        this.characterType = pc.getCharacterType();
        this.inventory = pc.getInventory();
        this.gp = pc.gp;
        this.keyH = pc.keyH;
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
    }

    public void setDefaultValues() {
        this.setxCoord(100);
        this.setyCoord(100);
        this.setMovementSpeed(4);
        this.setDirection("down");
        this.solidArea = new Rectangle(8, 16, 32, 32);
        this.attackArea.width = 36;
        this.attackArea.height = 36;
//        this.setWidth(18);
//        this.setHeight(46);
//        this.collisionAreaDefaultX = solidArea.x;
//        this.collisionAreaDefaultY = solidArea.y;
    }

    public void getPlayerImage() {
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/player_character/up_1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/player_character/up_2.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/player_character/down_1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/player_character/down_2.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/player_character/left_1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/player_character/left_2.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/player_character/right_1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/player_character/right_2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH == null) return;

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter>30){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(keyH.kPressed){
            attacking();
        }

        if (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) {
            int currentX = this.getxCoord();
            int currentY = this.getyCoord();
            int speed = this.getMovementSpeed();
            if (keyH.wPressed && !keyH.sPressed && currentY > 0) {
                this.setyCoord(currentY - speed);
                this.setDirection("up");
            }
            if (keyH.sPressed && !keyH.wPressed && currentY < (gp.screenHeight - this.getHeight())) {
                this.setyCoord(currentY + speed);
                this.setDirection("down");
            }
            if (keyH.aPressed && !keyH.dPressed && currentX > 0) {
                this.setxCoord(currentX - speed);
                this.setDirection("left");
            }
            if (keyH.dPressed && !keyH.aPressed && currentX < (gp.screenWidth - this.getWidth())) {
                this.setxCoord(currentX + speed);
                this.setDirection("right");
            }

            this.setSpriteCounter(this.getSpriteCounter() + 1);
            if (this.getSpriteCounter() > 12) {
                if (this.getSpriteNum() == 1) {
                    this.setSpriteNum(2);
                } else if (this.getSpriteNum() == 2) {
                    this.setSpriteNum(1);
                }
                this.setSpriteCounter(0);
            }
        }
        /* if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed && !keyH.downPressed) {
                this.setDirection("up");
            }
            if (keyH.downPressed && !keyH.upPressed) {
                this.setDirection("down");
            }
            if (keyH.leftPressed && !keyH.rightPressed) {
                this.setDirection("left");
            }
            if (keyH.rightPressed && !keyH.leftPressed) {
                this.setDirection("right");
            }
        } */

        this.healthBar.update(this.getHealth());
    }

    public void attacking(){

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
//        System.out.println(solidArea);
        Boolean isHit = gp.checker.checkEntityAttack(this, gp.enemy);

//        System.out.println(isHit);
        if(isHit){
            damageMonster();
            System.out.println("Hit");
        }

        isHit = gp.checker.checkConsumableCollision(this, gp.apple);

        if(isHit && gp.apple.isVisible) {
            heal(gp.apple.consume());
        }

        //After checking collision, restore original data
        xCoord = currentWorldX;
        yCoord = currentWorldY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }

    public void damageMonster(){
        if(!gp.enemy.invincible){
            gp.enemy.health -= 1;
            gp.enemy.invincible = true;
            System.out.println(gp.enemy.health);

            if(gp.enemy.health <= 0){
                gp.enemy.isAlive = false;
            }
        }

    }

    public void damagePlayer() {
        if(!gp.getPlayer().invincible){
            //gp.getPlayer().setHealth(gp.getPlayer().getHealth()-gp.enemy.getDamagePerHit());
            gp.getPlayer().damage(gp.enemy.getDamagePerHit());
            gp.getPlayer().invincible = true;
            //System.out.println(gp.getPlayer().getHealth());     //TODO DEBUG PlayerCharacter Invincibility
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(this.getDirection()) {
            case "up":
                if (this.getSpriteNum() == 1) {
                    image = this.getUp1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getUp2();
                }
                break;
            case "down":
                if (this.getSpriteNum() == 1) {
                    image = this.getDown1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getDown2();
                }
                break;
            case "left":
                if (this.getSpriteNum() == 1) {
                    image = this.getLeft1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getLeft2();
                }
                break;
            case "right":
                if (this.getSpriteNum() == 1) {
                    image = this.getRight1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getRight2();
                }
                break;
        }

        g2.drawImage(image, this.getxCoord(), this.getyCoord(), this.getWidth(), this.getHeight(), null);

        this.healthBar.draw(g2,
                this.getxCoord(),
                this.getyCoord()-this.healthBar.getHeight());

    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setGamePanel(GamePanel gp) {
        this.gp = gp;
    }

    public void setKeyHandler(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public Item getStartingItem() {
        return this.startingItem;
    }

    public void setStartingItem(Item startingItem) {
        this.startingItem = startingItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;

        PlayerCharacter pc = (PlayerCharacter) o;
        if (this.characterType != pc.getCharacterType()) return false;
        if (!this.inventory.equals(pc.getInventory())) return false;
        return super.equals(o);
    }

}
