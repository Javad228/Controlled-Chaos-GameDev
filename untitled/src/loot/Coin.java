package loot;

import main.KeyHandler;

public class Coin extends Loot {
    private int value;

    public Coin(KeyHandler keyH, int framesToWait, String[] imagePaths, int xCoord, int yCoord, int value) {
        super(keyH, framesToWait, imagePaths, xCoord, yCoord);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
