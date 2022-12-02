package main;

import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MiniMap extends JPanel{
    transient GamePanel gp;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;

    public int exploredRoomNub = 1;
    String[] note = new String[20];

    public MiniMap(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if (gp.gameMapState == 4){
            if (gp.getCurrentRoomNum() > exploredRoomNub) {
                exploredRoomNub++;
            }
            drawMiniMap();

        }
    }



    private void drawMiniMap() {
        final int frameX = gp.tileSize*8;
        final int frameY = gp.tileSize*2;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
        drawQM();
        drawExploredMap();
        drawPlayer();
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        int itemIndex = slotCol + (slotRow*5);
//        System.out.println(slotCol);
//        System.out.println(slotRow);
        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        int dFrameX = frameX - frameWidth-10;
        int dFrameHeight = frameHeight+10;
        drawSubWindow(dFrameX, frameY, frameWidth, dFrameHeight);
        if (itemIndex < exploredRoomNub) {
            drawMiniMap(itemIndex + 1);
        }

        dFrameX = frameX - frameWidth-10;
        int dFrameY = frameY + frameHeight;
        int d1FrameHeight = gp.tileSize*3;
        int textX =dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        drawSubWindow(dFrameX,dFrameY, frameWidth * 2,d1FrameHeight);
        for (int i = 0; i < note.length; i++) {
            if (note[i] == null) {
                note[i] = "write you note about this map (press esc)";
            }
        }
        g2.drawString(note[itemIndex],textX,textY);
        if (gp.getCurrentRoomNum() == itemIndex + 1) {
            drawPlayerPosition();
        }

        //System.out.println("ksejbglkjsbgklsjbkb   " + note[gp.getCurrentRoomNum()]);
    }

    public void setText(String text) {
        note[gp.getCurrentRoomNum() - 1] = text;
    }

    private void drawSubWindow(int x,int y,int width,int height) {
        Color c = new Color(0,0,0,250);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    private void drawQM() {
        for(int i = 0; i < 5; i++) {
            for(int k = 0; k < 4; k++) {
                TileManager.drawTile(g2, 11, gp.tileSize*8 + 20 + i * 51,gp.tileSize*2 + 20 + k * 51);
            }
        }
    }

    private void drawExploredMap() {
        int counter = 0;
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 5; k++) {
                counter++;
                if (counter > exploredRoomNub) {
                    return;
                }
                TileManager.drawTile(g2, 102, gp.tileSize*8 + 20 + k * 51,gp.tileSize*2 + 20 + i * 51);
            }
        }
    }

    private void drawPlayer() {
        int counter = 0;
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 5; k++) {
                counter++;
                if (counter == gp.getCurrentRoomNum()) {
                    TileManager.drawTile(g2, 100, gp.tileSize*8 + 20 + k * 51,gp.tileSize*2 + 20 + i * 51);
                    return;
                }
            }
        }
    }

    private void drawPlayerPosition() {
        int playery = gp.player.getRow();
        int playerx = gp.player.getCol();

        for(int i = 0; i < 17; i++) {
            for(int k = 0; k < 17; k++) {
                if (playery == i && playerx == k) {
                    TileManager.drawminiTile(g2, 100, gp.tileSize * 2 + k * 15 + 5,gp.tileSize * 2 + i * 18 + 10 + 8);
                    return;
                }
            }
        }
        //System.out.println("the position is " + playerx + "  " + playery);
    }

    private void drawMiniMap(int exploredRoomNub) {
        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = TileManager.minimapTileNum[exploredRoomNub][col][row];
            TileManager.drawminiTile(g2, tileNum, gp.tileSize + 60 + col * 15,gp.tileSize*2 + 20 + row * 15);
            col++;

            if (col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }

}
