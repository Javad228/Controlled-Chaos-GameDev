/**
 * Inventory Class - Class which represents an inventory for a Character to collect and store items and coins
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class Inventory {
    private int numberOfSlots;                  // Number of slots to store items
    // private ArrayList<Item> listOfItems;     // List of Items in this Inventory Object
                                                // TODO: Create Item Class
    private int coinAmount;                     // Amount of Coins in this Inventory Object

    /**
     * Default constructor for a generic Inventory Object
     */
    public Inventory() {
        this.numberOfSlots = 10;
        this.coinAmount = 0;
    }

    // TODO add listOfItems after Item Class has been implemented
    public Inventory(int numberOfSlots, int coinAmount) {
        this.numberOfSlots = numberOfSlots;
        this.coinAmount = coinAmount;
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
}
