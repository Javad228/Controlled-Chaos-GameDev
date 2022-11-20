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
        String[] slimeSlingerImages = {"/items/slingshot.png"};
        String[] bootImages = {"/items/boot.png"};
        String [] rapidFireImages = {"/items/rapid-fire.png"};
        Sword sword = new Sword(new String[]{"/weapons/wooden_sword.png"}, gp, this.xCoord, this.yCoord-10);
        SlimeSlinger slimeSlinger = new SlimeSlinger(slimeSlingerImages, this.gp, this.xCoord, this.yCoord-10);
        RapidFire rapidFire = new RapidFire(rapidFireImages, this.gp, this.xCoord, this.yCoord-10);
        HealthUp healthUp = new HealthUp(new String[]{"/items/health.png"}, gp, this.xCoord, this.yCoord-10);
        Boot boot = new Boot(bootImages, gp, this.xCoord, this.yCoord-10);

        commonSet = new Item[3];
        commonSet[0] = sword;
        commonSet[1] = sword;
        commonSet[2] = healthUp;

        rareSet = new Item[4];
        rareSet[0] = sword;
        rareSet[1] = sword;
        rareSet[2] = healthUp;
        rareSet[3] = boot;

        epicSet = new Item[4];
        epicSet[0] = slimeSlinger;
        epicSet[1] = rapidFire;
        epicSet[2] = healthUp;
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
