package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * HealthBar - A Graphical Element, as part of the PlayerCharacter class,
 */
public class HealthBar extends BufferedImage {

    private int health;
    private final int maxHealth;
    private int width;
    private int height;
    private final Color red = Color.RED;
    private final Color blank = new Color(70, 70, 70);

    public HealthBar(int hp, int maxHealth, int width, int height) {
        super(width, height, TYPE_INT_RGB);
        this.width = width;
        this.height = height;
        this.health = hp;
        this.maxHealth = maxHealth;
    }

    public void setHealth(int hp) {
        if (hp < 0) hp = 0;
        this.health = hp;
    }

    public int getHealth() {return this.health;}

    public void update(int hp) {
        this.setHealth(hp);
    }

    public void draw(Graphics2D g2, int charX, int charY) {

        if (this.getHealth() == 100) return;

        // Removed bottom double for-loop due to graphical issues
        //for (int y = charY; y < height+charY; y++) {
        //    for (int x = charX; x < width+charX; x++) {
        //        if (((double)(x-charX))/((double)(width))*100 < getHealth()) {
        //            g2.setColor(red);
        //        } else g2.setColor(blank);

        //        g2.draw(new Rectangle(x, y, 1, 1));
        //    }
        //}

        double scale = (double)Main.view.getGamePanel().tileSize/maxHealth;
        double scaledHP = scale*health;

        g2.setColor(blank);
        g2.fillRect(charX-15, charY-4, Main.view.getGamePanel().tileSize+2, 12);
        g2.setColor(red);
        g2.fillRect(charX-14, charY-3, (int) scaledHP, 10);
    }


}

class TestHealthBar {
    public static void main(String[] args) {
        Main.view = new View();
        Main.window = Main.view.getWindow();
        GamePanel gamePanel = Main.view.getGamePanel();

        gamePanel.getPlayer().setxCoord(gamePanel.getWidth()/2);
        gamePanel.getPlayer().setyCoord(gamePanel.getHeight()/2);
        gamePanel.enemy.setxCoord(gamePanel.getWidth()/2 - gamePanel.tileSize);
        gamePanel.enemy.setyCoord(gamePanel.getHeight()/2 - gamePanel.tileSize);

        gamePanel.startGameThread();
    }
}