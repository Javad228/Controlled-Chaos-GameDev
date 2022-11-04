package tile;

import main.GamePanel;
import main.Main;
import main.View;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int GRASS = 0;
    public static final int DOOR1 = 1;
    public static final int DOOR2 = 2;
    public static final int TREE = 3;
    public static final int TRAP = 4;
    public static final int BUTTON = 5;


    private BufferedImage image;
    private boolean collision = false;
    private boolean damageTile = false;
    private int tileType;

    private int x;
    private int y;

    public BufferedImage getImage() {
        return image;
    }

    public boolean isCollision() {
        return collision;
    }

    public boolean isDamageTile() {
        return damageTile;
    }

    public int getTileType() {
        return tileType;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getRow() {
        return x/TileManager.gp.tileSize;
    }

    public int getCol() {
        return y/TileManager.gp.tileSize;
    }

    public void setImage(BufferedImage image) {
        this.image = image;

    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setDamageTile(boolean damageTile) {
        this.damageTile = damageTile;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }


    public void setx(int x) {
        this.x = x;
    }

    public void sety(int y) {
        this.y = y;
    }
}
