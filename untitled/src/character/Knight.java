package character;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Knight extends Friendly{
    public static final int room4Row = 4;
    public static final int room4Col = 10;

    public Knight(int xCoord, int yCoord) {
        super();
        this.name = "Knight";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 25;

        //TODO: add more dialog
        //create knight dialog pool (not currently implemented)
        String currentDialog = "Excellent work " + getGp().getPlayer().name + "!";
        this.dialogPool.add(currentDialog);
        currentDialog = "Careful up ahead, " + getGp().getPlayer().name + ".";
        this.dialogPool.add(currentDialog);



        try {
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown3(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown4(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown5(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));
            this.setDown6(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/knight/tile000.png"))));

        } catch (
                IOException exception) {
            exception.printStackTrace();
        }
    }
}
