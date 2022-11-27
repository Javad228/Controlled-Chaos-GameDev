package loot;

import character.Character;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Sign extends Character {
    private GamePanel gp;
    private int signNum;

    public Sign(int xCoord, int yCoord, GamePanel gp, int signNum) {
        super();
        this.signNum = signNum;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = 50;
        this.width = 50;
        this.gp = gp;

    }

    public int getRow() {
        return this.getyCoord()/TileManager.gp.tileSize;
    }

    public int getCol() {return this.getxCoord()/TileManager.gp.tileSize;}

    public void draw(Graphics2D g2, GamePanel gamePanel) throws IOException {
        if(signNum==0){
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/shopSign/1$.png"))));
        } else if (signNum == 1) {
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/shopSign/2$.png"))));
        }else{
            this.setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/shopSign/4$.png"))));
        }
        g2.drawImage(this.getDown1(), this.getxCoord(), this.getyCoord(), gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void update() {

    }
}
