package loot;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Loot {
    private String name;
    private LootType lootType;
    private int xCoord;                         // x-position in a room
    private int yCoord;                         // y-position in a room
    //transient KeyHandler keyH;
    private String[] imagePaths;
    private transient BufferedImage[] lootImages;
    private int framesToWait;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX = solidArea.x;
    public int collisionAreaDefaultY = solidArea.y;
    // the following variables are used for bobbing motion
    private int spritePosition = 0; // gives current position of weapon in its movement
    private boolean isBobbingUp = true;
    private int frameCounter = 0;
    private final int bobIncrement = 1;
    private final int maxSpritePosition = 6;
    // the following variables are used for flipping through sprites
    private int spriteCounter = 0;
    private boolean isExpanding = true;

    public Loot(int framesToWait, String[] imagePaths) {
        this.name = "";
        this.lootType = LootType.DEFAULT;
        this.xCoord = 0;
        this.yCoord = 0;
        this.framesToWait = framesToWait;
        this.imagePaths = imagePaths;
        this.getImage(imagePaths);
    }

    public Loot(int framesToWait, String[] imagePaths, int xCoord, int yCoord) {
        this.name = "";
        this.lootType = LootType.DEFAULT;
        this.xCoord = 0;
        this.yCoord = 0;
        this.framesToWait = framesToWait;
        this.imagePaths = imagePaths;
        this.getImage(imagePaths);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Loot(String name, LootType lootType, int xCoord, int yCoord) {
        this.name = name;
        this.lootType = lootType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void getImage(String[] imagePaths) {
        lootImages = new BufferedImage[imagePaths.length];

        try {
            for (int i = 0; i < imagePaths.length; i++) {
                lootImages[i] = ImageIO.read(getClass().getResourceAsStream(imagePaths[i]));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // update position of loot
    public void update() {
        if (frameCounter == 0) { // only update the animation once every ___ frames (depending on if statement after this)
            if (lootImages.length == 1) { // loot is bobbing up and down
                if (spritePosition == 0) { // at the bottom, need to bob up
                    isBobbingUp = true;
                    spritePosition++;
                    setyCoord(getyCoord() + bobIncrement);
                } else if (spritePosition < maxSpritePosition && isBobbingUp) { // in the middle of bobbing up
                    spritePosition++;
                    setyCoord(getyCoord() + bobIncrement);
                } else if (spritePosition == maxSpritePosition) { // reached the top, need to go down
                    isBobbingUp = false;
                    spritePosition--;
                    setyCoord(getyCoord() - bobIncrement);
                } else if (spritePosition < maxSpritePosition && !isBobbingUp) { // in the middle of bobbing down
                    spritePosition--;
                    setyCoord(getyCoord() - bobIncrement);
                } else {
                    System.out.format("Received bad sprite counter value (%d)/\n", spritePosition);
                }
            } else {
                if (spriteCounter == 0) { // at shrunken state, needs to expand
                    isExpanding = true;
                    spriteCounter++;
                } else if (spriteCounter < lootImages.length - 1 && isExpanding) { // in the middle of expanding
                    spriteCounter++;
                } else if (spriteCounter == lootImages.length - 1) { // at expanded state, needs to shrink
                    isExpanding = false;
                    spriteCounter--;
                } else if (spriteCounter < lootImages.length - 1 && !isExpanding) { // in the middle of shrinking
                    spriteCounter--;
                } else {
                    System.out.format("Received bad sprite counter value (%d)/\n", spritePosition);
                }
            }
        }

        frameCounter++;

        // reset the frame counter when we reach ___ frames
        if (frameCounter == framesToWait) {
            frameCounter = 0;
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        /*
        if (lootImages.length == 1) { // if there is only one image then we just want to bob up and down.
            g2.drawImage(lootImages[0], this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
        } else { // if not, flip through images
            g2.drawImage(lootImages[spriteCounter], this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
        }
         */
        g2.drawImage(lootImages[spriteCounter], this.getxCoord(), this.getyCoord(), gp.tileSize, gp.tileSize, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LootType getLootType() {
        return lootType;
    }

    public void setLootType(LootType lootType) {
        this.lootType = lootType;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public BufferedImage[] getLootImages() {
        return lootImages;
    }

    public void setLootImages(BufferedImage[] lootImages) {
        this.lootImages = lootImages;
    }

    /*
    public KeyHandler getkeyH() {
        return keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }


     */
    public String[] getImagePaths() {
        return this.imagePaths;
    }

    public void setImagePaths(String[] imagePaths) {
        this.imagePaths = imagePaths;
    }

    public int getFrameCounter() {
        return this.frameCounter;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }
}
