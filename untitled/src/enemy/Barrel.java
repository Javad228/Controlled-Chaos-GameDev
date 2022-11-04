package enemy;

import character.*;

import loot.LootType;

import main.GamePanel;
import save.SimpleEnemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Barrel extends Enemy {

    public Barrel(int xCoord, int yCoord) {
        super(EnemyType.SMALL, LootType.DEFAULT);   //TODO DEBUG
        name = "Barrel";
        movementSpeed = 0;
        maxHealth = 10;
        health = maxHealth;

        solidArea.x = 0;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.collisionAreaDefaultX = solidArea.x;
        this.collisionAreaDefaultY = solidArea.y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        setNumDeathSprites(5);
        setDeathImages(new BufferedImage[getNumDeathSprites()]); // should be in super()

        this.setDamagePerHit(0);
        getImage();
    }

    public Barrel(SimpleEnemy enemy) {
        this(enemy.xCoord, enemy.yCoord);
        this.name = enemy.getName();
        this.health = enemy.health;
        this.maxHealth = enemy.maxHealth;
        this.movementSpeed = enemy.movementSpeed;
        this.activeEffects = enemy.activeEffects;
        this.setDamagePerHit(enemy.damagePerHit);
    }

    @Override
    public void setAction(GamePanel gp) {
    }
    public void getImage(){
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setUp3(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setUp4(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setUp5(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setUp6(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown3(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown4(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown5(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setDown6(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft3(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft4(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft5(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setLeft6(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight3(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight4(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight5(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));
            this.setRight6(ImageIO.read(getClass().getResourceAsStream("/barrel/barrel.png")));

            // Get Death Animation Images
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/barrel/barrel-death.png"))), 0);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/barrel/barrel-death.png"))), 1);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/barrel/barrel-death.png"))), 2);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/barrel/barrel-death.png"))), 3);
            setDeathImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/barrel/barrel-death.png"))), 4);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GamePanel gp) {
        if (getUp1() == null) getImage();

        super.update(gp);
    }

    public Object getSubClass() {
        return Barrel.class;
    }
}
