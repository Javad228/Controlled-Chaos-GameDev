package tile;

import character.Inventory;
import main.GamePanel;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class TrapTile extends Tile {
    public static final int map1TrapCol1 = 5;
    public static final int map1TrapCol2 = 9;

    private boolean isOn;

    public TrapTile() {
        isOn = true;
    }

    public boolean getOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void toggleTrap(int row, int col) {
        if (this.isOn) {
            TileManager.mapTileNum[col][row] = 6;
            isOn = false;
        } else {
            TileManager.mapTileNum[col][row] = 4;
            isOn = true;
        }

    }
}
