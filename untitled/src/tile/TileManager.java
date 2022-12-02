package tile;

import main.GamePanel;
import main.Main;
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
    public static ArrayList<int[]> grassTiles;

    private Object[] loot;
    public boolean backward = false;
//TODO: <<<<<<< Cameron-NextLevels
    private static ArrayList<int[][]> doorLocations = null;
//=======
    //private ArrayList<int[][]> doorLocations;
    private ArrayList<int[][]> jackOLanternLocations;
//>>>>>>> Cameron-MergeNextLevels

    public TileManager(GamePanel gp1) {
        gp = gp1;
        tile = new Tile[12];
        mapTileNum = new int[gp1.maxScreenCol+1][gp1.maxScreenRow+1];
        //this.roomNum = 0;   // might need to change based on saved progress
        doorLocations = new ArrayList<>();
        grassTiles = new ArrayList<>();

        jackOLanternLocations = new ArrayList<>();
    }

    public void update() {
        int roomNum = gp.getCurrentRoomNum();
        int totalRooms = gp.getRooms().size();

        // Map roomNum to adjusted roomNum for Levels beyond default level
        //switch (totalRooms - roomNum) {
        //    case 0 -> roomNum = 6;
        //    case 1 -> roomNum = 5;
        //    //default -> roomNum = (((roomNum) % 5));
        //    default -> roomNum = (roomNum-1)%4 + 1;
        //}
        System.out.println("Room Number: " + roomNum + " out of: " + Room.numOfRooms);
//        System.out.println(roomNum);
        doorLocations.clear();
        jackOLanternLocations.clear();
        loadMap(getMapFilePath(roomNum));

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

    public static String getMapFilePath(int roomNum) {

        String prefix = "/maps/mapset" + gp.player.roomSetNum + "/";
        String file;

        switch (Room.numOfRooms - roomNum) {
            case 0 -> {
                file = prefix + "boss.txt";
            }
            case 1 -> {
                file = prefix + "shop.txt";
            }
            default -> {
                file = prefix + "room" + roomNum + ".txt";
            }
        }

        System.out.println("File Path: " + file);
        return file;
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
//TODO: <<<<<<< Cameron-NextLevels

//=======
/*          System.out.println("room type = " + Integer.toString(gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType()));
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
*/
//>>>>>>> Cameron-MergeNextLevels

            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(getFloorTilePath()))));

            // Next Level door (Floor or Green/Red door)
            NextLevelTile nextLevelTile = new NextLevelTile(this);
            tile[1] = nextLevelTile;
            tile[1].setCollision(true);
            tile[1].setTileType(Tile.NEXTLEVEL);


            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/door.png"))));
            //tile[2].collision = true;
            tile[2].setCollision(true);
            tile[2].setTileType(Tile.DOOR2);

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(getWallTilePath()))));
            tile[3].setCollision(true);

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

    public String getFloorTilePath() {
        switch (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType()) {
            case Room.VOLCANOROOM:
            case Room.SPOOKYROOM:
            case 5:
                return "/tiles/black.png";
            case Room.GRASSROOM:
                return "/tiles/grass.png";
            case Room.ICEROOM:
                return "/tiles/snow.png";
            case Room.SPACEROOM:
                return "/tiles/space.png";
            default:
                System.out.println("Received bad room type. Update of tile images not executed.");
                break;
        }
        return "";
    }

    public String getWallTilePath() throws IOException {
        switch (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType()) {
            case Room.VOLCANOROOM:
                return "/tiles/lava.png";
            case Room.GRASSROOM:
                return "/tiles/tree.png";
            case Room.SPOOKYROOM:
                return "/tiles/cobweb.png";
            case Room.ICEROOM:
                return "/tiles/ice_mountain.png";
            case Room.SPACEROOM:
                return "/tiles/space_rock.png";
            case 5:
                return "/tiles/cobweb.png";
            default:
                System.out.println("Received bad room type. Update of tile images not executed.");
                break;
        }
        return "";
    }
    
    public String getEnvironmentTilePath() throws IOException {
    
    }

    // getMap() will always update the grassPanel
    public static int[][] getMap(String filePath) {
        int[][] map = new int[gp.maxScreenCol+1][gp.maxScreenCol+1];
        grassTiles = new ArrayList<>();

        try {
            InputStream is = TileManager.class.getResourceAsStream(filePath);
            if (is == null) {
                System.out.println("Problem when loading map: input stream returned null\n" + filePath);
                System.exit(1);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;


            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {

                    String numbers[] = line.split(" ");

// TODO: <<<<<<< Cameron-NextLevels
                    int num;

                    switch (num = Integer.parseInt(numbers[col])) {
                        case Tile.GRASS -> grassTiles.add(new int[]{col, row});
                        case Tile.NEXTLEVEL -> ((NextLevelTile)tile[1]).addTile(col, row);
                        case Tile.DOOR2 -> {
                            int[][] doorLocation = new int[1][2];
                            doorLocation[0][0] = col;
                            doorLocation[0][1] = row;
                            doorLocations.add(doorLocation);
                        }
//=======
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
//>>>>>>> Cameron-MergeNextLevels
                    }

                    map[col][row] = num;

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

        return map;
    }

    private void loadMap(String filePath) {
        mapTileNum = getMap(filePath);
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
        if (tileNum == 1) {
            ((NextLevelTile)tile[tileNum]).setNextLevelTileImage(x/gp.tileSize, y/gp.tileSize);
        }
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
