package tile;

public class DoorTile extends Tile {
    public static final int map1Room4DoorRow = 13;
    public static final int map1Room4DoorCol = 7;

    private boolean locked;

    public DoorTile() {
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void toggleDoor(int row, int col) {
        if (this.locked) {
            TileManager.mapTileNum[col][row] = 2;
            locked = false;
        } else {
            TileManager.mapTileNum[col][row] = 8;
            locked = true;
        }
    }
}
