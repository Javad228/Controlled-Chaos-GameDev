package tile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Button extends Tile{
    private boolean isOn;
    private ArrayList<TrapTile> trapTiles;

    public Button() {
        isOn = false;
        trapTiles = new ArrayList<TrapTile>();
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public ArrayList<TrapTile> getTrapTiles() {
        return this.trapTiles;
    }

    public void addTile(TrapTile trapTile) {
        trapTiles.add(trapTile);
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void toggle() {
        if (isOn) {
            TileManager.mapTileNum[this.getRow()][this.getCol()] = 7;
            isOn = false;
        } else {
            TileManager.mapTileNum[this.getRow()][this.getCol()] = 5;
            isOn = true;
        }
    }

}
