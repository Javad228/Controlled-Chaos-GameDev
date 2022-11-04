package enemy;

import character.Enemy;
import character.EnemyType;
import loot.LootType;
import main.GamePanel;
import save.SimpleEnemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class BigSkull extends Enemy {
    private int counter = 1;
    public BigSkull(int xCoord, int yCoord) {
        super(EnemyType.LARGE, LootType.DEFAULT);
        name = "BigSkull";
        movementSpeed = 2;
        maxHealth = 500;
        health = maxHealth;
        attackType = 1;
        solidArea.x = 15;
        solidArea.y = 5;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.width = 42;
        this.height = 40;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(5);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(5); // originally 15

        getImage();

    }

    public BigSkull(SimpleEnemy enemy) {
        this(enemy.xCoord, enemy.yCoord);
        this.name = enemy.getName();
        this.health = enemy.health;
        this.maxHealth = enemy.maxHealth;
        this.movementSpeed = enemy.movementSpeed;
        this.activeEffects = enemy.activeEffects;
        this.setDamagePerHit(enemy.damagePerHit);
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

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null) getImage();
        super.update(gp);
    }

    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull2.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull3.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull4.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull5.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull6.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull2.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull3.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull4.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull5.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull6.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull2.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull3.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull4.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull5.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull6.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull2.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull3.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull4.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull5.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/bigskull/big-skull6.png")));


            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigskull/big-skull-death1.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigskull/big-skull-death2.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigskull/big-skull-death3.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigskull/big-skull-death4.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigskull/big-skull-death5.png"))), 4);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return BigSkull.class;
    }
}
