/**
 * GamePanel Class: contains game loop and other functionality to repaint the window given user input.
 * Made with the help of this tutorial: https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 *
 * @author Karina Abraham
 * @version September 30, 2022
 */

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale;                              // scaling our tiles to 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;                            // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;                           // 576 pixels

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));        // set size of main panel
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);                                           // improves rendering performance; drawing performed offscreen
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Contains game loop
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.016666 seconds per frame?
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            // update information such as character position
            update();

            // draw screen with updated information
            repaint();                                                               // calls paintComponent()

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000; // find how much time remaining

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;                                         // change g to Graphics2D class

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();                                                           // save memory
    }
}
