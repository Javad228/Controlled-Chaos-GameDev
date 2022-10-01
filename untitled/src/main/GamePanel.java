package main;

import javax.swing.*;

import character.Character;
import character.PlayerCharacter;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSizes = 16;							//16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSizes * scale;		//48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; 	//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	//576 pixels

	int fps = 60;

	KeyHandler keyH = new KeyHandler();

	Thread gameThread;
	public PlayerCharacter player = new PlayerCharacter();

	//Default Player Position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	//at 13:26 in video

	//Methods to alter player:
/* 	int playerX = player.getxCoord();
	int playerY = player.getyCoord();
	double playerSpeed = player.getMovementSpeed();

	player.setxCoord(1); */

	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {

		//First way to construct the game loop, led to inconsistent FPS.

	/* 	double drawInterval = 1000000000/fps;					//converts from nanoseconds to seconds 
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		long timer = 0;
		long drawCount = 0;
		
		while(gameThread != null){
			long currentTime = System.nanoTime();
			
			timer += (nextDrawTime - currentTime);
			
			update();
			repaint();
			drawCount++;
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime = remainingTime / 1000000;		//converts from nanoseconds to milliseconds
				
				if (remainingTime < 0){
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(timer >= 1000000000){
				Main.window.setTitle("Controlled Chaos");
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		*/

		double drawInterval = 1000000000/fps;					//converts from nanoseconds to seconds 
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null){
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if(timer >= 1000000000){
				Main.window.setTitle("Controlled Chaos");
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
					
		}
		
	}
	
	public void update(){
		if(keyH.upPressed == true && playerY > 0) {
			playerY -= playerSpeed;
		}
		if(keyH.downPressed == true && playerY < (screenHeight - tileSize)) {
			playerY += playerSpeed;
		}
		if(keyH.leftPressed == true && playerX > 0) {
			playerX -= playerSpeed;
		}
		if(keyH.rightPressed == true && playerX < (screenWidth - tileSize)) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.white);

		g2.fillRect(playerX, playerY, tileSize, tileSize);

		//player.Draw(g2);
		 
		g2.dispose();
	}
	

}