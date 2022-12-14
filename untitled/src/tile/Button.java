package tile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Button extends Tile{
    public static final int button1Col = 6;
    public static final int button1Row = 5;

    public static final int button2Col = 7;
    public static final int button2Row = 7;

    public static final int button3Col = 8;
    public static final int button3Row = 5;

    //public static final int map1Button4Col = 15;
    //public static final int map1Button4Row = 2;

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
        if (!isOn) {
            TileManager.mapTileNum[this.getCol()][this.getRow()] = 7;
            isOn = true;
        } else {
            TileManager.mapTileNum[this.getCol()][this.getRow()] = 5;
            isOn = false;
        }
    }

}
