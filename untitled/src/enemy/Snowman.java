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

public class Snowman extends Enemy {
    private int counter = 1;
    public Snowman(int xCoord, int yCoord) {
        super(EnemyType.SMALL, LootType.DEFAULT);
        name = "Snowman";
        movementSpeed = 3;
        maxSpeed = 3;
        setMaxHealth(15);
        health = getMaxHealth();
        attackType = 1;
        solidArea.x = 10;
        solidArea.y = 5;
        solidArea.width = 45;
        solidArea.height = 45;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.width = 48;
        this.height = 48;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(3);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(1); // originally 15

        getImage();

    }

    public Snowman(SimpleEnemy enemy) {
        this(enemy.xCoord, enemy.yCoord);
        this.name = enemy.getName();
        this.health = enemy.health;
        setMaxHealth(enemy.maxHealth);
        this.movementSpeed = enemy.movementSpeed;
        this.maxSpeed = enemy.maxSpeed;
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
                                //System.out.println("up arrow");
                                SnowBall snowball = new SnowBall(gp, currentX, currentY, "up", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                            } else {
                                //System.out.println("down arrow");
                                SnowBall snowball = new SnowBall(gp, currentX, currentY, "down", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                            }
                            counter = 0;
                            actionLockCounter = 0;

                        }
                        //System.out.println("shoot");
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
                        //int movementSpeed = this.getProjectile().getMovementSpeed(); // causes error if getProjectile() is null, so commented out.
                        canMove = false;
                        actionLockCounter++;

                        if(actionLockCounter == 70){
                            if (goalCol < startCol) {
                                SnowBall snowball = new SnowBall(gp, currentX, currentY, "left", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                                this.setHasThrownProjectile(true);
                            } else {
                                SnowBall snowball = new SnowBall(gp, currentX, currentY, "right", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
                                this.setHasThrownProjectile(true);
                            }
                            counter = 0;
                            actionLockCounter = 0;
                        }
                        //System.out.println("shoot");
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

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null) getImage();
        super.update(gp);
    }

    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_1.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_2.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/snowman/snowman_3.png")));


            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/snowman/death/SnowMan_1.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/snowman/death/SnowMan_2.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/snowman/death/SnowMan_3.png"))), 2);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return Wizard.class;
    }
}
