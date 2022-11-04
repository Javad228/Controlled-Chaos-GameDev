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

public class BigSlonch extends Enemy {
    private int counter = 1;
    public BigSlonch(int xCoord, int yCoord) {
        super(EnemyType.LARGE, LootType.DEFAULT);
        name = "BigSlonch";
        movementSpeed = 1;
        maxHealth = 500;
        health = maxHealth;
        attackType = 1;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 120;
        solidArea.height = 120;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.width = 120;
        this.height = 120;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(5);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(5); // originally 15

        getImage();

    }

    public BigSlonch(SimpleEnemy enemy) {
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
        actionLockCounter++;
        int currentX = this.getxCoord();
        int currentY = this.getyCoord();

        if ((actionLockCounter == 100) || (actionLockCounter == 199)) {
            SlimeBall slimeball = new SlimeBall(gp, currentX + 40, currentY + 60, "up", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball2 = new SlimeBall(gp, currentX + 40, currentY + 60, "down", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball3 = new SlimeBall(gp, currentX + 40, currentY + 60, "left", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball4 = new SlimeBall(gp, currentX + 40, currentY + 60, "right", false); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);

        }
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
//        System.out.println(currentX+", "+ currentY);

        if (currentY > 380) {   //520
            this.setDirection("up");
        }
        if (currentY < 140) {    //20
            this.setDirection("down");
        }
        if (currentX > 594) {   //714
            this.setDirection("left");
        }
        if (currentX < 130) {    //10
            this.setDirection("right");
        }
    }

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null) getImage();
        super.update(gp);
    }

    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch2.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch3.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch4.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch5.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch6.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch2.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch3.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch4.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch5.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch6.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch2.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch3.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch4.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch5.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch6.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch2.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch3.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch4.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch5.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/bigSlonch/big-slonch6.png")));


            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigSlonch/big-slonch-death-1.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigSlonch/big-slonch-death-2.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigSlonch/big-slonch-death-3.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigSlonch/big-slonch-death-4.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigSlonch/big-slonch-death-5.png"))), 4);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return BigSlonch.class;
    }
}
