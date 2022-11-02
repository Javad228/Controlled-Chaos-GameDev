package tile;

import character.Inventory;
import main.GamePanel;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class TrapTile extends Tile {
    private boolean isOn;

    public TrapTile() {
        isOn = true;
    }

    public boolean getOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void toggleTrap() {
        try {

            if (this.isOn) {
                //tileManager.drawTile(Inventory.g2, TileManager.tile[6], this.get);
                this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_down.png"))));
                TileManager.draw(GamePanel.g2);
            } else {
                this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_up.png"))));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("couldn't find trap image when toggling");
        }
    }
}
