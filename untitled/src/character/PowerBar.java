package character;
import character.Enemy;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static main.Main.view;

/**
 * HealthBar - A Graphical Element, as part of the PlayerCharacter class,
 */
public class PowerBar extends BufferedImage {

    private final int DEFAULT_MAXHEALTH;
    private final int DEFAULT_WIDTH;
    private final int DEFAULT_HEIGHT;

    private int health;
    private int maxHealth;
    private transient final Color red = Color.magenta;
    private transient final Color blank = Color.GRAY;
    private transient final Color gold = new Color(255, 215, 0);
    private GamePanel gp;

    public PowerBar(double hp, int maxHealth, int width, int height, GamePanel gp) {
        super(width, height, TYPE_INT_RGB);
        this.DEFAULT_MAXHEALTH = maxHealth;
        this.DEFAULT_WIDTH = width;
        this.DEFAULT_HEIGHT = height;
        this.health = (int)hp;
        this.maxHealth = maxHealth;
        this.gp = gp;
    }

    public void setHealth(double hp) {
        if (hp < 0) hp = 0;
        if (hp > maxHealth) hp = maxHealth;
        this.health = (int)hp;
    }

    public double getHealth() {return this.health;}

    public int getHealthInteger() {
        return this.health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void update() {
        this.setHealth(gp.getPlayer().getAllEnemiesKilled().size());
        if(gp.getPlayer().getAllEnemiesKilled().size()>=2){
            if(Objects.equals(gp.getPlayer().getCharacterAppearance(), "healer")){
                gp.getPlayer().setHealth(gp.getPlayer().getMaxHealth());
                gp.getPlayer().getAllEnemiesKilled().clear();
            }else{
                gp.getPlayer().setNumCoins( gp.getPlayer().getNumCoins()+2);
                gp.getPlayer().getAllEnemiesKilled().clear();
            }

        }

    }

    public void draw(Graphics2D g2, int charX, int charY) {

        int healthBarWidth = DEFAULT_WIDTH;
        int barX = charX;
        int barY = charY - 3;
        int currHealth;
        double scaledWidth;
        double scaledHP;

        // If character health is greater than normal,
        // offset health bar by adjusting character X and width parameters
        if ((currHealth = this.getHealthInteger()) > DEFAULT_MAXHEALTH) {
            healthBarWidth += (int)((currHealth-DEFAULT_MAXHEALTH)*(((double) DEFAULT_WIDTH)/DEFAULT_MAXHEALTH));
            barX -= (7 + (int)((double)currHealth-DEFAULT_MAXHEALTH)/4);

        } else {
            barX = charX - 7;
        }

        //scaledWidth = (double)(healthBarWidth-2)/maxHealth;
        scaledWidth = (double)(healthBarWidth-2)/DEFAULT_MAXHEALTH;
        scaledHP = scaledWidth*currHealth;


        g2.setColor(blank);
        g2.fillRect(barX-1, barY-1, healthBarWidth, DEFAULT_HEIGHT+2);
        g2.setColor(red);

        if (currHealth > DEFAULT_MAXHEALTH) {
            g2.fillRect(
                    barX,
                    barY,
                    DEFAULT_WIDTH,
                    DEFAULT_HEIGHT
            );
            g2.setColor(gold);
            g2.fillRect(
                    barX+ DEFAULT_WIDTH,
                    barY,
                    Math.max(healthBarWidth - DEFAULT_WIDTH - 2, 0),
                    DEFAULT_HEIGHT
            );
            g2.setColor(blank);
            g2.fillRect(barX+ DEFAULT_WIDTH -1, barY, 1, DEFAULT_HEIGHT);
        } else {
            g2.fillRect(barX, barY, (int) scaledHP, DEFAULT_HEIGHT);
        }

        /*

        Expanding red bar implementation

        // If character health is greater than normal,
        // offset health bar by adjusting character X and width parameters
        int health;
        double scaledWidth;
        double scaledHP;
        if ((health = this.getHealth()) > 100) {
            healthBarWidth+=(health-100);
            charX-=(health-100)/2;
            scaledHP = healthBarWidth-2;
        } else {
            scaledWidth = (double)(healthBarWidth-2)/DEFAULT_MAXHEALTH;
            scaledHP = scaledWidth*health;
        }


        g2.setColor(blank);
        g2.fillRect(charX-8, charY-4, healthBarWidth, DEFAULT_HEIGHT+2);
        g2.setColor(red);
        g2.fillRect(charX-7, charY-3, (int) scaledHP, DEFAULT_HEIGHT);

        */
    }

}
