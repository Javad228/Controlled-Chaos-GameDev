package character;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Satyr extends Friendly {

    public Satyr(int xCoord, int yCoord) {
        super();
        this.name = "Satyr";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 25;

        try {
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown3(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown4(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown5(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));
            this.setDown6(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/satyr/tile000.png"))));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
