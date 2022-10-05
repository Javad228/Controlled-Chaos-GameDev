package main;

import javax.swing.*;

import character.PlayerCharacter;
import loot.Weapon;

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
	public PlayerCharacter player = new PlayerCharacter(this, keyH);
	public Weapon weapon = new Weapon(this, keyH);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		Audio.openingMusic();
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {

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
		player.update();
		weapon.update();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		player.draw(g2);
		weapon.draw(g2);
		 
		g2.dispose();
	}
	

}