package tile;

import main.GamePanel;
import main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    public static GamePanel gp;
    public static Tile[] tile;
    public static int[][] mapTileNum;
    //private int roomNum;
    private Object[] loot;

    public TileManager(GamePanel gp1) {
        gp = gp1;
        tile = new Tile[10];
        mapTileNum = new int[gp1.maxScreenCol+1][gp1.maxScreenRow+1];
        //this.roomNum = 0;   // might need to change based on saved progress
        getTileImage();
        update();
    }

    public void update() {
        int roomNum = gp.getCurrentRoomNum();
//        System.out.println(roomNum);
        if (roomNum == 2) {
            //loadMap("maps/mapset" + gp.player.roomsetNub + "/map3.txt");
            loadMap("/maps/mapset2/map3.txt");
        }
        if (roomNum == 1) {
            System.out.println("loaded");
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/map1.txt");
            System.out.println("loaded success");
        }
        if (roomNum == 0) {
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/map2.txt");
        }

        /*
        if (gp.tileM != null){
            gp.updateLootInRoom();
        }
         */
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png"))));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png"))));
            tile[1].setCollision(true);

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png"))));
            //tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png"))));
            tile[3].setCollision(true);

            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_up.png"))));
            tile[4].setDamageTile(true);
            //tile[4].setTileType(4);

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_up.png"))));
            tile[5].setCollision(false);
            tile[5].setTileType(Tile.BUTTON);

            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_down.png"))));
            tile[6].setDamageTile(false);

            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_down.png"))));
            tile[7].setCollision(false);
            tile[7].setTileType(Tile.BUTTON);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.out.println("Problem when loading map: input stream returned null");
                System.exit(1);
            }
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
            e.printStackTrace();
        }
    }

    public static void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            drawTile(g2, tileNum, x, y);
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

    public static void drawTile(Graphics2D g2, int tileNum, int x, int y) {
        g2.drawImage(tile[tileNum].getImage(), x, y, gp.tileSize, gp.tileSize, null);
    }

    public Object[] getLoot() {
        return loot;
    }

    public void setLoot(Object[] loot) {
        this.loot = loot;
    }
}
