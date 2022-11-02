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
    public Tile[] tile;
    public int[][] mapTileNum;
    private Object[] loot;
    public boolean backward = false;

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
            System.out.println("loaded");
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/starting1.txt");
            gp.player.setxCoord(20);
            gp.player.setyCoord(50);
            if (backward) {
                gp.player.setxCoord(600);
                gp.player.setyCoord(380);
                backward = false;
            }
            System.out.println("loaded success");
        }
        if (roomNum == 2) {
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/enemy2.txt");
            gp.player.setxCoord(40);
            gp.player.setyCoord(80);
            if (backward) {
                gp.player.setxCoord(600);
                gp.player.setyCoord(380);
                backward = false;
            }
        }
        if (roomNum == 3) {
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/item3.txt");
            gp.player.setxCoord(40);
            gp.player.setyCoord(80);
            if (backward) {
                gp.player.setxCoord(600);
                gp.player.setyCoord(380);
                backward = false;
            }
        }
        if (roomNum == 4) {
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/traps4.txt");
            gp.player.setxCoord(40);
            gp.player.setyCoord(80);
            if (backward) {
                gp.player.setxCoord(600);
                gp.player.setyCoord(380);
                backward = false;
            }
        }
        if (roomNum == 5) {
            loadMap("/maps/mapset" + gp.player.roomsetNub + "/boss5.txt");
            gp.player.setxCoord(40);
            gp.player.setyCoord(80);
            if (backward) {
                gp.player.setxCoord(600);
                gp.player.setyCoord(380);
                backward = false;
            }
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
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png")));
            //tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[3].collision = true;
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
