package character;

import loot.Item;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * character.Inventory Class - Class which represents an inventory for a Character to collect and store items and coins
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class Inventory {
    GamePanel gp;
    Graphics2D g2;
    private int numberOfSlots;                  // Number of slots to store items
    private ArrayList<Item> listOfItems;        // List of Items in this character.Inventory Object
    private int coinAmount;                     // Amount of Coins in this character.Inventory Object
    public int slotCol = 0;
    public int slotRow = 0;
    /**
     * Default constructor for a generic character.Inventory Object
     */
    public Inventory(GamePanel gp) {
        this.gp = gp;
        this.numberOfSlots = 10;
        this.listOfItems = new ArrayList<>();
        this.coinAmount = 0;
    }

    public Inventory(int numberOfSlots, ArrayList<Item> listOfItems, int coinAmount) {
        this.numberOfSlots = numberOfSlots;
        this.listOfItems = listOfItems;
        this.coinAmount = coinAmount;
    }


    public void draw(Graphics2D g2){

        this.g2 = g2;
        if (gp.gameState == 4){
            drawInventory();
        }
    }

    private void drawInventory() {
        final int frameX = gp.tileSize*8;
        final int frameY = gp.tileSize*2;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
        System.out.println(slotCol);
        System.out.println(slotRow);
        //Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //Draw Player's Items
        for(int i = 0; i < gp.player.getInventory().listOfItems.size(); i++){
            g2.drawImage(gp.player.getInventory().listOfItems.get(i).getLootImages()[0],slotX,slotY,null);

            //Display amount
            if(gp.player.getInventory().listOfItems.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(32F));
                int amountX;
                int amountY;

                String s = "" + gp.player.getInventory().listOfItems.get(i).amount;
                amountX = getXforAlignToRightText(s,slotX + 44);
                amountY = slotY + gp.tileSize;

                //number
                g2.setColor(Color.white);
                g2.drawString(s,amountX-3,amountY-3);
            }
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }

            int dFrameX = frameX - frameWidth-10;
            int dFrameY = frameY + frameHeight;
            int d1FrameHeight = gp.tileSize*3;

            int dFrameHeight = frameHeight+10;
            int textX =dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            int itemIndex = slotCol + (slotRow*5);
            if(itemIndex < gp.player.getInventory().listOfItems.size()) {
                drawSubWindow(dFrameX, frameY, frameWidth, dFrameHeight);
                g2.drawImage(gp.player.getInventory().listOfItems.get(i).getLootImages()[0],dFrameX+50,frameY+20,200,200,null);
            }
            if(itemIndex < gp.player.getInventory().listOfItems.size()) {
                drawSubWindow(dFrameX,dFrameY, frameWidth,d1FrameHeight);
                for(String line: gp.player.getInventory().listOfItems.get(itemIndex).getDescription().split("\n")){
                    g2.drawString(line,textX,textY);
                    textY += 32;
                }
            }
        }

        //Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //Description Frame


    }
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return tailX - length;
    }
    private void drawSubWindow(int x,int y,int width,int height) {
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
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
