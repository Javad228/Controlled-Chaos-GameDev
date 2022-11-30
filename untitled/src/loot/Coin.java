package loot;

import main.KeyHandler;

public class Coin extends Loot {

    public static transient final int DEFAULT_FRAMES_TO_WAIT = 7;
    public static transient final String[] COIN_IMAGES = new String[]{"/items/coin.png"};
    public static transient final int DEFAULT_VALUE = 1;

    private int value;

    public Coin(int framesToWait, String[] imagePaths, int xCoord, int yCoord, int value) {
        super(framesToWait, imagePaths, xCoord, yCoord);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
