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

public class BigWizard extends Enemy {
    private int counter = 1;
    public BigWizard(int xCoord, int yCoord) {
        super(EnemyType.LARGE, LootType.DEFAULT);
        name = "BigWizard";
        movementSpeed = 1;
        setMaxHealth(150);
        health = getMaxHealth();
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

    public BigWizard(SimpleEnemy enemy) {
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
        int currentX = this.getxCoord();
        int currentY = this.getyCoord();
        int maxX = 630;
        int maxY = 470;

        //Teleport to center to start
        if (actionLockCounter == 1) {
            setxCoord(maxX/2);
            setyCoord(maxY/2);
            setMovementSpeed(0);
        }

        //Firing orbs
        if ((actionLockCounter == 0) || (actionLockCounter % 40 == 0)) {
          /*  SlimeBall slimeball = new SlimeBall(gp, currentX + 40, currentY + 60, "up", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball2 = new SlimeBall(gp, currentX + 40, currentY + 60, "down", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball3 = new SlimeBall(gp, currentX + 40, currentY + 60, "left", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
            SlimeBall slimeball4 = new SlimeBall(gp, currentX + 40, currentY + 60, "right", false, 1); //RANGED, true (isInvinicible), this (user)                                    this.setHasThrownProjectile(true);
          */
            Orb orb = new Orb(gp, currentX + 40, currentY + 60, "up", false, 1);
            Orb orb2 = new Orb(gp, currentX + 40, currentY + 60, "down", false, 1);
            Orb orb3 = new Orb(gp, currentX + 40, currentY + 60, "left", false, 1);
            Orb orb4 = new Orb(gp, currentX + 40, currentY + 60, "right", false, 1);
        }

        //Teleporting around then moving side-to-side
        int xLeft = 32;
        int xRight = maxX - 32;
        int yTop = 32;
        int yBottom = maxY - 32;

        switch (actionLockCounter) {

            case 500:
                //set x and y so it's on left side
                setxCoord(xLeft);
                setyCoord(maxY/2);
                break;
            case 600:
                //top
                setxCoord(maxX/2);
                setyCoord(yTop);
                break;
            case 700:
                //bottom
                setxCoord(maxX/2);
                setyCoord(yBottom);
                break;
            case 800:
                //right
                setxCoord(xRight);
                setyCoord(maxY/2);
                break;
            case 850:
                direction = "left";
                setMovementSpeed(2);
                break;
            case 2000:
                actionLockCounter = 0;
        }
        if (currentX > 614) {
            this.setDirection("left");
        }
        if (currentX < 16) {
            this.setDirection("right");
        }
        actionLockCounter++;
    }

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null) getImage();
        super.update(gp);
    }

    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard2.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard3.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard4.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard5.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard6.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard2.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard3.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard4.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard5.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard6.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard2.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard3.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard4.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard5.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard6.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard2.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard3.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard4.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard5.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/bigWizard/big-wizard6.png")));

            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigWizard/big-wizard-death1.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigWizard/big-wizard-death2.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigWizard/big-wizard-death3.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigWizard/big-wizard-death4.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/bigWizard/big-wizard-death5.png"))), 4);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSubClass() {
        return BigWizard.class;
    }
}
