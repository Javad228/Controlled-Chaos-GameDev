package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public static Tile[] tile;
    public static int[][] mapTileNum;
    //private int roomNum;
    private Object[] loot;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol+1][gp.maxScreenRow+1];
        //this.roomNum = 0;   // might need to change based on saved progress
        getTileImage();
        update();
    }

    public void update() {
        int roomNum = gp.getCurrentRoomNum();
//        System.out.println(roomNum);
        if (roomNum == 1) {
            //System.out.println("loaded");
            loadMap("/maps/map1.txt");
            //System.out.println("loaded success");
        } else if (roomNum == 0) {
            loadMap("/maps/map0.txt");
        } else if (roomNum == 2) {
            loadMap("/maps/map2.txt");
        }

        /*
        if (gp.tileM != null){
            gp.updateLootInRoom();
        }
         */
    }

    public void getTileImage() {
        try {
            tile[Tile.GRASS] = new Tile();
            tile[Tile.GRASS].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
            tile[Tile.GRASS].tileType = Tile.GRASS;

            tile[Tile.STONE] = new Tile();
            tile[Tile.STONE].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stone.png")));
            tile[Tile.STONE].collision = true;
            tile[Tile.STONE].tileType = Tile.STONE;

            tile[Tile.DOOR] = new Tile();
            tile[Tile.DOOR].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png")));
            tile[Tile.DOOR].tileType = Tile.DOOR;

            tile[Tile.TRAP] = new Tile();
            tile[Tile.TRAP].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_up.png")));
            tile[Tile.TRAP].damageTile = true;
            tile[Tile.TRAP].tileType = Tile.TRAP;

            tile[Tile.BUTTON] = new Tile();
            tile[Tile.BUTTON].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_up.png")));
            tile[Tile.BUTTON].collision = false;
            tile[Tile.BUTTON].tileType = Tile.BUTTON;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;

                    col++;
                }

                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }


            br.close();
        } catch(Exception e) {

        }
    }
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }

    public Object[] getLoot() {
        return loot;
    }

    public void setLoot(Object[] loot) {
        this.loot = loot;
    }
}
