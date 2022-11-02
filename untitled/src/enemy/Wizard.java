package enemy;

import character.Enemy;
import character.NonPlayableCharacter;

import character.Projectile;

import character.*;
import loot.LootType;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wizard extends Enemy {
    private int counter = 1;
    public Wizard(GamePanel gp, int xCoord, int yCoord) {
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
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(12);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(1); // originally 15

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
                            //int movementSpeed = projectile.getMovementSpeed();
                            canMove = false;
                            actionLockCounter++;

                            if(actionLockCounter == 70){
                                if (goalRow < startRow) {
                                    System.out.println("up arrow");
                                    SlimeBall slimeball = new SlimeBall(gp, currentX, currentY, "up", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                                } else {
                                    System.out.println("down arrow");
                                    SlimeBall slimeball = new SlimeBall(gp, currentX, currentY, "down", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
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
                            canMove = false;
                            actionLockCounter++;

                            if(actionLockCounter == 70){
                                if (goalCol < startCol) {
                                    SlimeBall slimeball = new SlimeBall(gp, currentX, currentY, "left", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                                    this.setHasThrownProjectile(true);
                                } else {
                                    SlimeBall slimeball = new SlimeBall(gp, currentX, currentY, "right", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
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


            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile000.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile001.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile002.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile003.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile004.png"))), 4);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile005.png"))), 5);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile006.png"))), 6);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile007.png"))), 7);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile008.png"))), 8);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile009.png"))), 9);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile010.png"))), 10);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/wizard/character/death/tile011.png"))), 11);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
