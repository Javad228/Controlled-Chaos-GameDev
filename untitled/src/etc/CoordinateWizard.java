package etc;

import main.Main;
import tile.Tile;
import tile.TileManager;

import java.util.ArrayList;

public class CoordinateWizard {

    static int x = -1;
    static int y = -1;
    static int count = 1;
    static int roomNo = -1;
    static ArrayList<int[]> grassTiles = new ArrayList<>();

    public static int getX(int roomNum) {
        if (--count <= 0)   generateValidCoordinates(roomNum);
        return x;
    }

    public static int getY(int roomNum) {
        if (--count <= 0)   generateValidCoordinates(roomNum);
        return y;
    }

    private static void generateValidCoordinates(int roomNum) {
        //x = -1;
        //y = -1;
        int tileSize = Main.view.getGamePanel().tileSize;
        //int maxX = Main.view.getGamePanel().maxScreenCol;
        //int maxY = Main.view.getGamePanel().maxScreenRow;

        //while ((x == -1 && y == -1) || TileManager.mapTileNum[x/tileSize][y/tileSize] == Tile.TREE) {
        //    x = (((int) (Math.random() * Main.view.getGamePanel().getWidth()))/tileSize)*tileSize;
        //    y = (((int) (Math.random() * Main.view.getGamePanel().getHeight()))/tileSize)*tileSize;
        //}

        //do {
        //    x = (int)(Math.random() * Main.view.getGamePanel().getWidth()) + tileSize/4;
        //    y = (int)(Math.random() * Main.view.getGamePanel().getHeight()) + tileSize/4;
        //} while(TileManager.mapTileNum[x/tileSize][y/tileSize] != Tile.GRASS &&
        //        TileManager.mapTileNum[x/tileSize][y/tileSize+1] != Tile.GRASS &&
        //        TileManager.mapTileNum[x/tileSize+1][y/tileSize] != Tile.GRASS &&
        //        TileManager.mapTileNum[x/tileSize+1][y/tileSize+1] != Tile.GRASS);


        int tileX;
        int tileY;

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
}
