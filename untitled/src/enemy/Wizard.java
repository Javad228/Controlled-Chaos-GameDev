package enemy;

import character.*;
import loot.LootType;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Wizard extends Enemy {
    private int counter = 1;
    public Wizard(GamePanel gp) {
        super(EnemyType.SMALL, LootType.DEFAULT);
        name = "Wizard";
        movementSpeed = 1;
        maxHealth = 10;
        health = maxHealth;
        attackType = 1;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.width = 60;
        this.height = 60;
        this.setDamagePerHit(15);
        this.setProjectile(new SlimeBall(gp));
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
                    if (Math.abs(goalRow - startRow) < 5) {
                        int nope = 0;
                        for (int i = 0; i < 3; i++) {
                            if(goalRow+i<gp.tileM.mapTileNum[goalCol].length){
                                if (gp.tileM.mapTileNum[goalCol][goalRow + i] == 1) {
                                    nope = 1;
                                }
                            }

                        }
                        if (nope == 1) {
                            counter = 1;
                            canMove = true;
                            searchPath(goalCol, goalRow, gp);
                        } else {
                            int currentX = this.getxCoord();
                            int currentY = this.getyCoord();
                            int movementSpeed = this.getProjectile().getMovementSpeed();
                            canMove = false;
                            actionLockCounter++;

                            if(actionLockCounter == 70){
                                if (goalRow < startRow) {
                                    System.out.println("up arrow");
                                    this.getProjectile().set(currentX, currentY, "up", movementSpeed); //RANGED, true (isInvinicible), this (user)
                                    this.setHasThrownProjectile(true);
                                } else {
                                    System.out.println("down arrow");
                                    this.getProjectile().set(currentX, currentY, "down", movementSpeed); //RANGED, true (isInvinicible), this (user)
                                    this.setHasThrownProjectile(true);
                                }
                                counter = 0;
                                actionLockCounter = 0;

                            }
                            System.out.println("shoot");
                        }
                    } else {
                        counter = 1;
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    }
                }
                if (goalRow == startRow) {
                    if (Math.abs(goalCol - startCol) < 5) {
                        int nope = 0;
                        for (int i = 0; i < 3; i++) {
                            if(goalCol+i<gp.tileM.mapTileNum[goalRow].length) {
                                if (gp.tileM.mapTileNum[goalCol + i][goalRow] == 1) {
                                    nope = 1;
                                }
                            }
                        }
                        if (nope == 1) {
                            counter = 1;
                            canMove = true;
                            searchPath(goalCol, goalRow, gp);
                        } else {
                            int currentX = this.getxCoord();
                            int currentY = this.getyCoord();
                            int movementSpeed = this.getProjectile().getMovementSpeed();
                            canMove = false;
                            actionLockCounter++;

                            if(actionLockCounter == 70){
                                if (goalCol < startCol) {
                                    this.getProjectile().set(currentX, currentY, "left", movementSpeed); //RANGED, true (isInvinicible), this (user)
                                    this.setHasThrownProjectile(true);
                                } else {
                                    this.getProjectile().set(currentX, currentY, "right", movementSpeed); //RANGED, true (isInvinicible), this (user)
                                    this.setHasThrownProjectile(true);
                                }
                                counter = 0;
                                actionLockCounter = 0;
                            }
                            System.out.println("shoot");
                        }
                    } else {
                        counter = 1;
                        canMove = true;
                        searchPath(goalCol, goalRow, gp);
                    }
                }
            } else {
                counter = 1;
                canMove = true;
                searchPath(goalCol, goalRow, gp);
            }
            if (this.isHasThrownProjectile()) {
                this.getProjectile().update();
            }
        }



    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile000.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile001.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile003.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile004.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile006.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile007.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile000.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile001.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile003.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile004.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile006.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile007.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile000.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile001.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile003.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile004.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile006.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile007.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile000.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile001.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile003.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile004.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile006.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/wizard/character/run/tile007.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
