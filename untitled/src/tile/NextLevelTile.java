package tile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NextLevelTile extends Tile {

    public static final int NEXTLEVEL_REG = 0;
    public static final int NEXTLEVEL_DIFFICULT = 1;

    public static final int defaultCol = 13;
    public static final int defaultRow1 = 4;
    public static final int defaultRow2 = 7;

    private boolean isVisible;
    private int doorType;

    public NextLevelTile(int flag) {

        switch (flag) {
            case NEXTLEVEL_REG -> {

            }
            case NEXTLEVEL_DIFFICULT -> {

            }
            default -> {

            }
        }
        isVisible = false;

        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/red_door.png"))));
        } catch (IOException e) {
            System.out.println("Image IO error: " + this.getClass());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Path to image does not exist: " + this.getClass());
            e.printStackTrace();
        }
    }
}
