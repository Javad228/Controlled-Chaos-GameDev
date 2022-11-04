package tile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Button extends Tile{
    public static final int map1ButtonRow = 3;
    public static final int map1Button1Col = 3;
    public static final int map1Button2Col = 12;
    public static final int map1Button3Col = 13;
    public static final int map1Button4Col = 14;

    private boolean isOn;
    private ArrayList<TrapTile> trapTiles;
    private ArrayList<DoorTile> doorTiles;

    public Button(int x, int y) {
        isOn = false;
        trapTiles = new ArrayList<TrapTile>();
        doorTiles = new ArrayList<DoorTile>();
        this.setx(x);
        this.sety(y);
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public ArrayList<TrapTile> getTrapTiles() {
        return this.trapTiles;
    }

    public ArrayList<DoorTile> getDoorTiles() {
        return doorTiles;
    }

    public void addTrapTile(TrapTile trapTile) {
        trapTiles.add(trapTile);
    }

    public void addDoorTile(DoorTile doorTile) {
        doorTiles.add(doorTile);
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
