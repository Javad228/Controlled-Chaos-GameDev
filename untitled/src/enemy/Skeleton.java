package enemy;

import character.Enemy;
import character.NonPlayableCharacter;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Skeleton extends Enemy {

    public Skeleton(GamePanel gp) {
        super(gp);
        name = "Skeleton";
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
        int goalCol = (gp.player.xCoord + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.yCoord + gp.player.solidArea.y) / gp.tileSize;
        int startCol = (xCoord + solidArea.x) / gp.tileSize;
        int startRow = (yCoord + solidArea.y) / gp.tileSize;

        if (goalCol == startCol || goalRow == startRow) {
            if (goalCol == startCol) {
                if (Math.abs(goalRow - startRow) < 1) {
                    int nope = 0;
                    for (int i = 0; i < 3; i++) {
                        if(goalRow+i<gp.tileM.mapTileNum[goalCol].length){
                            if (gp.tileM.mapTileNum[goalCol][goalRow + i] == 1) {
                                nope = 1;
                            }
                        }
                    }
                    if (nope == 1) {
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    } else {
                        int currentX = this.getxCoord();
                        int currentY = this.getyCoord();
                        canMove = false;
                        actionLockCounter++;

                        if(actionLockCounter == 70){
                            actionLockCounter = 0;
                        }
                    }
                } else {
                    canMove = true;
                    searchPath(goalCol, goalRow, gp);
                }
            }
            if (goalRow == startRow) {
                if (Math.abs(goalCol - startCol) < 1) {
                    int nope = 0;
                    for (int i = 0; i < 3; i++) {
                        if(goalCol+i<gp.tileM.mapTileNum[goalRow].length) {
                            if (gp.tileM.mapTileNum[goalCol + i][goalRow] == 1) {
                                nope = 1;
                            }
                        }
                    }
                    if (nope == 1) {
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    } else {
                        int currentX = this.getxCoord();
                        int currentY = this.getyCoord();
                        canMove = false;
                        actionLockCounter++;

                        if(actionLockCounter == 70){
                            actionLockCounter = 0;
                        }
                    }
                } else {
                    canMove = true;
                    searchPath(goalCol, goalRow, gp);
                }
            }
        } else {
            canMove = true;
            searchPath(goalCol, goalRow, gp);
        }
//        if(onPath){
//
//        }else {
//            actionLockCounter ++;
//
//            if (actionLockCounter == 120){
//                Random random = new Random();
//                int i = random.nextInt(100)+1;
//
//                if (i <= 25){
//                    direction = "up";
//                } else if (i > 25 && i <= 50) {
//                    direction = "down";
//                } else if (i > 50 && i <= 75) {
//                    direction = "left";
//                }else{
//                    direction = "right";
//                }
//                actionLockCounter = 0;
//            }
//        }
    }
    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile002.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile004.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile006.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile008.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile010.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/Skeleton/tile012.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
