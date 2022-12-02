package etc;

import main.Main;
import tile.Tile;
import tile.TileManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CoordinateWizard {

    static int x = -1;
    static int y = -1;
    static int relX = -1;
    static int relY = -1;
    static int count = 1;
    static int relCount = -1;
    static int roomNo = -1;
    static int playerX = -1;
    static int playerY = -1;
    static ArrayList<int[]> grassTiles = new ArrayList<>();
    static ArrayList<int[]> relativeTiles = new ArrayList<>();

    public static int getX(int roomNum) {
        if (--count <= 0)   generateValidCoordinates(roomNum);
        return x;
    }

    public static int getY(int roomNum) {
        if (--count <= 0)   generateValidCoordinates(roomNum);
        return y;
    }

    /**
     * Get an x-coordinate relative to the player
     * @param roomNum
     * @return
     */
    public static int getRelativeX(int roomNum) {
        if (Main.view.getGamePanel().getPlayer().xCoord != playerX ||
                relativeTiles.isEmpty())
            generateValidRelativeCoordinates(roomNum);

        if (--relCount < 0)  getRandomRelativeCoordinate();
        return relX;
    }

    /**
     * Get a y-coordinate relative to the player
     * @param roomNum
     * @return
     */
    public static int getRelativeY(int roomNum) {
        if (Main.view.getGamePanel().getPlayer().yCoord != playerY ||
                relativeTiles.isEmpty())
            generateValidRelativeCoordinates(roomNum);

        if (--relCount < 0)  getRandomRelativeCoordinate();
        return relY;
    }

    private static void generateValidCoordinates(int roomNum) {
        int tileSize = Main.view.getGamePanel().tileSize;

        // Fetch new grass tiles when the inputted room number
        // changes
        if (roomNum != roomNo) {
            TileManager.getMap(TileManager.getMapFilePath(roomNum));
            grassTiles = TileManager.grassTiles;
            roomNo = roomNum;
        }

        // Find empty tile

        // Get random grass tile from the map and set as pseudo-random coordinates
        int[] coordinates = grassTiles.get((int)(Math.random() * grassTiles.size()));
        x = coordinates[0] * tileSize;
        y = coordinates[1] * tileSize;

        count = 2;
    }

    private static void generateValidRelativeCoordinates(int roomNum) {
        int tileSize = Main.view.getGamePanel().tileSize;
        playerX = Main.view.getGamePanel().getPlayer().xCoord;
        playerY = Main.view.getGamePanel().getPlayer().yCoord;

        int playerTileX = playerX/tileSize;
        int playerTileY = playerY/tileSize;


        // Fetch new grass tiles when the inputted room number
        // changes
        if (roomNum != roomNo) {
            TileManager.getMap(TileManager.getMapFilePath(roomNum));
            grassTiles = TileManager.grassTiles;
            roomNo = roomNum;
        }


        // Find any grass tiles near the player
        for (int[] coordinate : grassTiles) {
            if (playerTileX-2 < coordinate[0] && coordinate[0] < playerTileX+2) {
                if (playerTileY-2 < coordinate[1] && coordinate[1] < playerTileY+2) {
                    relativeTiles.add(new int[]{coordinate[0]*tileSize, coordinate[1]*tileSize});
                }
            }
        }

        relCount = 2;
    }

    private static void getRandomRelativeCoordinate() {
        if (relativeTiles.isEmpty()) {
            relX = -1;
            relY = -1;
        } else {
            int[] randCoord = relativeTiles.remove((int)(Math.random() * relativeTiles.size()));
            relX = randCoord[0];
            relY = randCoord[1];
        }
    }
}
