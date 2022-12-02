package tile;

import main.GamePanel;
import main.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    public static GamePanel gp;
    public static Tile[] tile;
    public static int[][] mapTileNum;
    private Object[] loot;
    public boolean backward = false;
    private ArrayList<int[][]> doorLocations;
    private ArrayList<int[][]> jackOLanternLocations;

    public TileManager(GamePanel gp1) {
        gp = gp1;
        tile = new Tile[12];
        mapTileNum = new int[gp1.maxScreenCol+1][gp1.maxScreenRow+1];
        //this.roomNum = 0;   // might need to change based on saved progress
        doorLocations = new ArrayList<>();
        jackOLanternLocations = new ArrayList<>();
    }

    public void update() {
        int roomNum = gp.getCurrentRoomNum();
        System.out.println(roomNum);
//        System.out.println(roomNum);
        doorLocations.clear();
        jackOLanternLocations.clear();
        loadMap("/maps/mapset" + gp.player.roomSetNum + "/room" + roomNum + ".txt");
        if (backward) {
            for (int i = 0; i < doorLocations.size(); i++) {
                int doorLocationCol = doorLocations.get(i)[0][0];
                int doorLocationRow = doorLocations.get(i)[0][1];
                if (doorLocationCol > gp.maxScreenCol / 2) { // we found our door
                    gp.player.setxCoord(doorLocationCol * gp.tileSize - gp.tileSize);
                    gp.player.setyCoord(doorLocationRow * gp.tileSize);
                    break;
                }
            }
            backward = false;
        } else {
            for (int i = 0; i < doorLocations.size(); i++) {
                int doorLocationCol = doorLocations.get(i)[0][0];
                int doorLocationRow = doorLocations.get(i)[0][1];
                if (doorLocationCol < gp.maxScreenCol / 2) { // we found our door!
                    gp.player.setxCoord(doorLocationCol * gp.tileSize + gp.tileSize);
                    gp.player.setyCoord(doorLocationRow * gp.tileSize);
                    break;
                }
            }
        }

        getTileImage();
    }

    public void getTileImage() {
        tile[0] = new Tile();
        tile[2] = new Tile();
        //tile[2].collision = true;
        tile[2].setCollision(true);
        tile[2].setTileType(Tile.DOOR2);
        tile[3] = new Tile();
        tile[3].setCollision(true);

        try {
            System.out.println("room type = " + Integer.toString(gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType()));
            System.out.println("room set number = " + Integer.toString(gp.player.roomSetNum));
            if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.VOLCANOROOM) {
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/black.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_black.png"))));
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/lava.png"))));
            } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.GRASSROOM) {
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_grass.png"))));
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png"))));
                tile[10] = new Tile();
                tile[10].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/mud.png"))));
                tile[10].setTileType(Tile.ENVIRONMENT);
            } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.SPOOKYROOM) {
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/black.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_black.png"))));
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/cobweb.png"))));
                tile[10] = new Tile();
                tile[10].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/jack_o_lantern.png"))));
                tile[10].setTileType(Tile.ENVIRONMENT);
                tile[10].setCollision(true);
            } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.ICEROOM) {
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/snow.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_snow.png"))));
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/ice_mountain.png"))));
                tile[10] = new Tile();
                tile[10].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png"))));
                tile[10].setTileType(Tile.ENVIRONMENT);
            } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.SPACEROOM) {
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/space.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_black.png"))));
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/space_rock.png"))));
            } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == 6) { // all 6th rooms will be shop rooms
                tile[0] = new Tile();
                tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/black.png"))));
                tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door_black.png"))));
                tile[3] = new Tile();
                tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/cobweb.png"))));
            } else {
                System.out.println("Received bad room type. Update of tile images not executed.");
            }


            /* no need for 2 doors... perhaps use this for the red door instead?
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png"))));
            tile[1].setCollision(true);
            */

            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_general_up.png"))));
            tile[4].setDamageTile(true);
            //tile[4].setTileType(4);

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_up.png"))));
            tile[5].setCollision(false);
            tile[5].setTileType(Tile.BUTTON);

            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_general_down.png"))));
            tile[6].setDamageTile(false);

            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_down.png"))));
            tile[7].setCollision(false);
            tile[7].setTileType(Tile.BUTTON);

            tile[8] = new Tile();
            tile[8].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/locked_door.png"))));
            tile[8].setCollision(true);

            tile[9] = new Tile();
            tile[9].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hint_block.png"))));

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

                    if (num == Tile.DOOR2) {
                        int[][] doorLocation = new int[1][2];
                        doorLocation[0][0] = col;
                        doorLocation[0][1] = row;
                        doorLocations.add(doorLocation);
                    } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.SPOOKYROOM && num == Tile.ENVIRONMENT) {
                        int[][] jackOLanternLocation = new int[1][2];
                        jackOLanternLocation[0][0] = col;
                        jackOLanternLocation[0][1] = row;
                        jackOLanternLocations.add(jackOLanternLocation);
                    }

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

    public ArrayList<int[][]> getJackOLanternLocations() {
        return jackOLanternLocations;
    }

    public void setJackOLanternLocations(ArrayList<int[][]> jackOLanternLocations) {
        this.jackOLanternLocations = jackOLanternLocations;
    }
}
