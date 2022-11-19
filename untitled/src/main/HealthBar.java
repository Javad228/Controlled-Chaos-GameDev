package main;

import character.Enemy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Main.view;

/**
 * HealthBar - A Graphical Element, as part of the PlayerCharacter class,
 */
public class HealthBar extends BufferedImage {

    private final int DEFAULT_MAXHEALTH;
    private final int DEFAULT_WIDTH;
    private final int DEFAULT_HEIGHT;

    private int health;
    private int maxHealth;
    private transient final Color red = Color.RED;
    private transient final Color blank = Color.BLACK;
    private transient final Color gold = new Color(255, 215, 0);

    public HealthBar(double hp, int maxHealth, int width, int height) {
        super(width, height, TYPE_INT_RGB);
        this.DEFAULT_MAXHEALTH = maxHealth;
        this.DEFAULT_WIDTH = width;
        this.DEFAULT_HEIGHT = height;
        this.health = (int)hp;
        this.maxHealth = maxHealth;
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

    public void update(double hp) {
        this.setHealth(hp);
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

class TestHealthBar {

    static JButton consumable = new JButton("Eat Consumable (Gain HP)");

    public static void main(String[] args) {
        Main.view = new View();
        Main.window = Main.view.getWindow();
        Main.window.setLayout(new FlowLayout());
        GamePanel gamePanel = Main.view.getGamePanel();

        initConsumableButton();
        //Main.window.add(consumable);

        Point w = Main.window.getLocation();


        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        testFrame.add(consumable);
        testFrame.pack();

        w.setLocation(w.getX()-testFrame.getWidth(), w.getY());
        testFrame.setLocation(w);
        testFrame.setVisible(true);

        gamePanel.getPlayer().setxCoord(gamePanel.getWidth()/2);
        gamePanel.getPlayer().setyCoord(gamePanel.getHeight()/2);

        if (gamePanel.getRooms().get(gamePanel.getCurrentRoomNum()).getEnemies() != null){
            ArrayList<Enemy> currentList = gamePanel.getRooms().get(gamePanel.getCurrentRoomNum()).getEnemies();
            for (int i = 0; i < currentList.size(); i++) {
                Enemy enemy = currentList.get(i);
                enemy.setxCoord(gamePanel.getWidth()/2 - gamePanel.tileSize);
                enemy.setyCoord(gamePanel.getHeight()/2 - gamePanel.tileSize);
                enemy.setDamagePerHit(25);
            }
        }

        gamePanel.startGameThread();
    }

    public static void initConsumableButton() {
        consumable.setPreferredSize(new Dimension(100, 100));
        consumable.addActionListener((a) -> {
            Main.view.getGamePanel().getPlayer().heal(20);
            System.out.println(Main.view.getGamePanel().getPlayer().getHealth());
        });

        //new JFrame("Controlled Chaos").setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE).add(consumable).setVisible(true);
    }
}

class TestExpandedHealthBar {

    public static void main(String[] args) {
        Main.main(args);                    // Launch new instance of game

        int health = 200;

        view.getGamePanel().player.setMaxHealth(health);   // Set player max h to 250;
        view.getGamePanel().player.setHealth(health);      // Ensures health is 250

        view.showGamePanel();
        view.getGamePanel().startGameThread();
    }
}