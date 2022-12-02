package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class NextLevelTile extends DoorTile {

    public static final int NEXTLEVEL_REG = 0;
    public static final int NEXTLEVEL_DIFFICULT = 1;
    public static final int NEXTLEVEL_INVALID = -1;

    public static final String NEXTLEVEL_REG_PATH = "/tiles/door.png";
    public static final String NEXTLEVEL_RED_PATH = "/tiles/red_door.png";
    public static final ArrayList<int[]> nextLevelTiles = new ArrayList<>();

    public transient BufferedImage greenDoor;
    public transient BufferedImage redDoor;
    public transient BufferedImage invalidDoor;

    private final TileManager manager;


    public NextLevelTile(TileManager m) {
        this.manager = m;
        try {
            this.greenDoor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(NEXTLEVEL_REG_PATH)));
            this.redDoor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(NEXTLEVEL_RED_PATH)));
            this.invalidDoor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(manager.getFloorTilePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTile(int x, int y) {
        nextLevelTiles.add(new int[]{x , y});
    }

    public void setNextLevelTileImage(int x, int y) {
        try {
            int result = findTile(x,y);
            if (TileManager.gp.levelComplete) {
                switch (result) {
                    case NEXTLEVEL_REG -> this.setImage(greenDoor);
                    case NEXTLEVEL_DIFFICULT -> this.setImage(redDoor);
                    case NEXTLEVEL_INVALID -> this.setImage(invalidDoor);
                    default -> this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/locked_door.png"))));
                }
            } else  this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(manager.getFloorTilePath()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findTile(int x, int y) {
        int[] currTile;

        for (int i = 0; i < nextLevelTiles.size() && i < 2; i++) {
            currTile = nextLevelTiles.get(i);
            if (currTile[0] == x && currTile[1] == y)   return i;
        }

        return NEXTLEVEL_INVALID;
    }
}
