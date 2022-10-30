package tile;

import main.GamePanel;
import main.Main;
import main.View;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int GRASS = 0;
    public static final int STONE = 1;
    public static final int DOOR = 2;
    public static final int TRAP = 3;
    public static final int BUTTON = 4;

    public BufferedImage image;
    public boolean collision = false;
    public boolean damageTile = false;
    public int tileType;
}
