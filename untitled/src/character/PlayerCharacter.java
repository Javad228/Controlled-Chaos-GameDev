package character;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * PlayerCharacter - A class which models a user-controlled character and contains attributes for a Character.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class PlayerCharacter extends Character {
    private CharacterType characterType;    // Player Character Type
    // private Item startingItem            // Player Starting Item
                                            // TODO: Implement Item class
    private Inventory inventory;            // Player character.Inventory
    GamePanel gp;
    KeyHandler keyH;

    public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
        super();
        this.characterType = CharacterType.DEFAULT;
        this.inventory = new Inventory();
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        this.setxCoord(100);
        this.setyCoord(100);
        this.setMovementSpeed(4);
        this.setDirection("down");
    }

    public void getPlayerImage() {
        try {
            this.setUp1(ImageIO.read(getClass().getResourceAsStream("/player_character/up_1.png")));
            this.setUp2(ImageIO.read(getClass().getResourceAsStream("/player_character/up_2.png")));
            this.setDown1(ImageIO.read(getClass().getResourceAsStream("/player_character/down_1.png")));
            this.setDown2(ImageIO.read(getClass().getResourceAsStream("/player_character/down_2.png")));
            this.setLeft1(ImageIO.read(getClass().getResourceAsStream("/player_character/left_1.png")));
            this.setLeft2(ImageIO.read(getClass().getResourceAsStream("/player_character/left_2.png")));
            this.setRight1(ImageIO.read(getClass().getResourceAsStream("/player_character/right_1.png")));
            this.setRight2(ImageIO.read(getClass().getResourceAsStream("/player_character/right_2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            int currentX = this.getxCoord();
            int currentY = this.getyCoord();
            int speed = this.getMovementSpeed();

            if (keyH.upPressed && currentY > 0) {
                this.setDirection("up");
                this.setyCoord(currentY - speed);
            }
            if (keyH.downPressed && currentY < (gp.screenHeight - gp.tileSize)) {
                this.setDirection("down");
                this.setyCoord(currentY + speed);
            }
            if (keyH.leftPressed && currentX > 0) {
                this.setDirection("left");
                this.setxCoord(currentX - speed);
            }
            if (keyH.rightPressed && currentX < (gp.screenWidth - gp.tileSize)) {
                this.setDirection("right");
                this.setxCoord(currentX + speed);
            }

            this.setSpriteCounter(this.getSpriteCounter() + 1);
            if (this.getSpriteCounter() > 12) {
                if (this.getSpriteNum() == 1) {
                    this.setSpriteNum(2);
                } else if (this.getSpriteNum() == 2) {
                    this.setSpriteNum(1);
                }
                this.setSpriteCounter(0);
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(this.getDirection()) {
            case "up":
                if (this.getSpriteNum() == 1) {
                    image = this.getUp1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getUp2();
                }
                break;
            case "down":
                if (this.getSpriteNum() == 1) {
                    image = this.getDown1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getDown2();
                }
                break;
            case "left":
                if (this.getSpriteNum() == 1) {
                    image = this.getLeft1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getLeft2();
                }
                break;
            case "right":
                if (this.getSpriteNum() == 1) {
                    image = this.getRight1();
                }
                if (this.getSpriteNum() == 2) {
                    image = this.getRight2();
                }
                break;
        }

        g2.drawImage(image, this.getxCoord(), this.getyCoord(), 18, 46, null);
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // TODO create getter and setter method for startingItem
}
