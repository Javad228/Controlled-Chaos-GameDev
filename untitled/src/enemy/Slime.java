package enemy;

import character.NonPlayableCharacter;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Slime extends NonPlayableCharacter {

    public Slime() {
        super();
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

            attacking(gp);
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
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
