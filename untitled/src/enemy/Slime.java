package enemy;


import character.*;

import loot.LootType;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Slime extends Enemy {

    public Slime(int xCoord, int yCoord) {
        super(EnemyType.SMALL, LootType.DEFAULT);   //TODO DEBUG
        name = "Slime";
        movementSpeed = 1;
        maxHealth = 10;
        health = maxHealth;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(5);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(5);
        getImage();

    }
    @Override
    public void setAction(GamePanel gp){
        actionLockCounter++;

        if(actionLockCounter == 200){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }

            actionLockCounter = 0;
        }
        int currentX = this.getxCoord();
        int currentY = this.getyCoord();
//        System.out.println(currentX+", "+ currentY);

        if (currentY > 520) {
            this.setDirection("up");
        }
        if (currentY < 20) {
            this.setDirection("down");
        }
        if (currentX > 714) {
            this.setDirection("left");
        }
        if (currentX < 10) {

            this.setDirection("right");
        }
    }
    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/Slime/tile007.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/Slime/tile008.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/Slime/tile009.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/Slime/tile010.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/Slime/tile011.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/Slime/tile012.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/Slime/tile007.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/Slime/tile008.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/Slime/tile009.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/Slime/tile010.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/Slime/tile011.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/Slime/tile012.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/Slime/tile007.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/Slime/tile008.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/Slime/tile009.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/Slime/tile010.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/Slime/tile011.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/Slime/tile012.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/Slime/tile007.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/Slime/tile008.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/Slime/tile009.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/Slime/tile010.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/Slime/tile011.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/Slime/tile012.png")));

            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/Slime/tile028.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/Slime/tile029.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/Slime/tile030.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/Slime/tile031.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/Slime/tile032.png"))), 4);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Update method relative to Slime Enemy
    // Slime will attack when it is on the ground (spriteNum == 1, 2, 6)
    public void update(GamePanel gp) {
        super.update(gp);

        if (this.spriteNum < 2 || this.spriteNum == 6)  attacking(gp);
    }

    // Attacking method pertaining to the Slime Enemy
    public void attacking(GamePanel gamePanel) {
        int currX = this.xCoord;
        int currY = this.yCoord;
        int collisionAreaWidth = this.solidArea.width;
        int collisionAreaHeight = this.solidArea.height;

        //switch (direction) {
        //   case "up" -> yCoord -= attackArea.height;
        //    case "down" -> yCoord += attackArea.height;
        //    case "left" -> xCoord -= attackArea.width;
        //    case "right" -> xCoord += attackArea.width;
        //}

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        boolean isHit = gamePanel.checker.checkEntityCollision(this, gamePanel.getPlayer());
        //boolean isHit = gamePanel.checker.checkEntity(gamePanel.getPlayer(), this);

        if (isHit) {
            //System.out.println("Player took damage");
            gamePanel.getPlayer().damagePlayer(this);
        }

        xCoord = currX;
        yCoord = currY;
        solidArea.width = collisionAreaWidth;
        solidArea.height = collisionAreaHeight;
    }

}
