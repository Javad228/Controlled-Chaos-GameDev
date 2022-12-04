package loot;

import character.Character;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Chest extends Character {
    private GamePanel gp;
    private boolean isOpen;
    private int rarity;
    private Item[] commonSet;
    private Item[] rareSet;
    private Item[] epicSet;
    public Chest(int xCoord, int yCoord, GamePanel gp, int rarity) {
        super();
        isOpen = false;
        this.rarity = rarity;
        this.name = "Chest";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 50;
        this.gp = gp;
        Sword sword = new Sword(Sword.DEFAULT_IMAGE_PATHS, this.gp, this.xCoord, this.yCoord-10);
        SlimeSlinger slimeSlinger = new SlimeSlinger(SlimeSlinger.DEFAULT_IMAGE_PATHS, this.gp, this.xCoord, this.yCoord-10);
        HealthUp healthUp = new HealthUp(HealthUp.DEFAULT_HEALTHUP_PATHS, this.gp, this.xCoord, this.yCoord-10);
        Boot boot = new Boot(Boot.DEFAULT_IMAGE_PATHS, this.gp, this.xCoord, this.yCoord-10);
        DamageUp damage =  new DamageUp(DamageUp.DEFAULT_DAMAGEUP_PATHS, gp,  this.xCoord, this.yCoord-10);
        String [] bombBuddyImages = {"/items/bomb-buddy.png"};
        BombBuddy bomb = new BombBuddy(bombBuddyImages, gp, this.xCoord, this.yCoord-10);
        commonSet = new Item[3];
        commonSet[0] = healthUp;
        commonSet[1] = healthUp;
        commonSet[2] = damage;

        rareSet = new Item[4];
        rareSet[0] = damage;
        rareSet[1] = damage;
        rareSet[2] = boot;
        rareSet[3] = boot;

        epicSet = new Item[4];
        epicSet[0] = slimeSlinger;
        epicSet[1] = damage;
        epicSet[2] = bomb;
        epicSet[3] = boot;


    }
    public void open() {
        Random rand = new Random();
        if (!isOpen) {
            int numCoin = gp.player.getNumCoins();
            if(rarity==0){
                if(numCoin-1>=0){
                    gp.player.setNumCoins(numCoin-1);
                    isOpen = true;
                    gp.getRooms().get(gp.getCurrentRoomNum()).getItems().add(commonSet[rand.nextInt(3)]);
                }
            }else if(rarity==1){
                if(numCoin-2>=0){
                    gp.player.setNumCoins(numCoin-2);
                    isOpen = true;
                    gp.getRooms().get(gp.getCurrentRoomNum()).getItems().add(rareSet[rand.nextInt(4)]);
                }
            }else{
                if(numCoin-4>=0){
                    gp.player.setNumCoins(numCoin-4);
                    isOpen = true;
                    gp.getRooms().get(gp.getCurrentRoomNum()).getItems().add(epicSet[rand.nextInt(4)]);
                }
            }
        }
    }
    public int getRow() {
        return this.getyCoord()/TileManager.gp.tileSize;
    }

    public int getCol() {return this.getxCoord()/TileManager.gp.tileSize;}

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        if(!isOpen){
            g2.drawImage(this.getDown1(), this.getxCoord(), this.getyCoord(), gamePanel.tileSize+20, gamePanel.tileSize+10, null);
        }else{
            g2.drawImage(this.getDown2(), this.getxCoord(), this.getyCoord(), gamePanel.tileSize+20, gamePanel.tileSize+10, null);
        }
    }

    public void update() {
        //collisions don't work
//        boolean isHit = gp.checker.checkEntityCollision(this,gp.getPlayer());
//        System.out.println(isHit);
//        if(isHit){
//            gp.getPlayer().collisionOn = true;
//            this.collisionOn =true;
//        }
        try {
            if(rarity==0){
                this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile000.png"))));
                this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile008.png"))));
            }else if(rarity==1){
                this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile010.png"))));
                this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile019.png"))));
            }else{
                this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile020.png"))));
                this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/chest/tile029.png"))));
            }
        } catch (
                IOException exception) {
            exception.printStackTrace();
        }
    }
}
