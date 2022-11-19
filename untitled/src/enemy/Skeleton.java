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

public class Skeleton extends Enemy {

    //public Skeleton(GamePanel gp) {
    //    super(gp);
    public Skeleton(int xCoord, int yCoord) {
        super(EnemyType.MEDIUM, LootType.DEFAULT);
        name = "Skeleton";
        movementSpeed = 1;
        setMaxHealth(75);
        health = getMaxHealth();

        solidArea.x = 15;
        solidArea.y = 5;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(13);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(1); // originally 10
        getImage();

    }

    public Skeleton(SimpleEnemy enemy) {
        this(enemy.xCoord, enemy.yCoord);
        this.name = enemy.getName();
        this.health = enemy.health;
        setMaxHealth(enemy.maxHealth);
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
        if (getUp1() == null)   getImage();
        super.update(gp);
    }

    public void getImage() {
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

            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile000.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile001.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile002.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile003.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile004.png"))), 4);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile005.png"))), 5);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile006.png"))), 6);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile007.png"))), 7);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile008.png"))), 8);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile009.png"))), 9);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile010.png"))), 10);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile011.png"))), 11);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/skeleton/death/tile012.png"))), 12);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return Skeleton.class;
    }
}
