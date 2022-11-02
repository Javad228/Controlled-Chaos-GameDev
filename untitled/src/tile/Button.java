package tile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Button extends Tile{
    private boolean isOn;
    private ArrayList<TrapTile> trapTiles;

    public Button() {
        isOn = false;
        trapTiles = new ArrayList<TrapTile>();
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public ArrayList<TrapTile> getTrapTiles() {
        return this.trapTiles;
    }

    public void addTile(TrapTile trapTile) {
        trapTiles.add(trapTile);
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void toggle() {
        if (trapTiles.size() == 0) {
            return;
        }

        if (isOn) {
            for (int i = 0; i < trapTiles.size(); i++) {
                Tile tile = trapTiles.get(i);
                try {
                    this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_down.png"))));
                    TileManager.tile[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_up.png"))));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("image reading failed when toggling switch off");
                }
                tile.setDamageTile(false);
            }
            isOn = false;
        } else {
            for (int i = 0; i < trapTiles.size(); i++) {
                Tile tile = trapTiles.get(i);
                try {
                    this.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/button_on_grass_up.png"))));
                    TileManager.tile[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/spike_on_grass_down.png"))));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("image reading failed when toggling switch on");
                }
                tile.setDamageTile(true);
            }
            isOn = true;
        }
    }

}
