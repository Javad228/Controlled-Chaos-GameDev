package character;

import loot.Item;

import java.util.ArrayList;

/**
 * character.Inventory Class - Class which represents an inventory for a Character to collect and store items and coins
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class Inventory {
    private int numberOfSlots;                  // Number of slots to store items
    private ArrayList<Item> listOfItems;        // List of Items in this character.Inventory Object
    private int coinAmount;                     // Amount of Coins in this character.Inventory Object

    /**
     * Default constructor for a generic character.Inventory Object
     */
    public Inventory() {
        this.numberOfSlots = 10;
        this.listOfItems = new ArrayList<>();
        this.coinAmount = 0;
    }

    public Inventory(int numberOfSlots, ArrayList<Item> listOfItems, int coinAmount) {
        this.numberOfSlots = numberOfSlots;
        this.listOfItems = listOfItems;
        this.coinAmount = coinAmount;
    }

    public void addItem(Item item) {
        this.listOfItems.add(item);
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;

        Inventory inventory = (Inventory) o;

        if (this.numberOfSlots != inventory.getNumberOfSlots()) return false;
        return this.coinAmount == inventory.getCoinAmount();
    }

}
