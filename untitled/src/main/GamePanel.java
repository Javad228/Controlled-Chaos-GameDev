package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSizes = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSizes * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	int fps = 60;

	KeyHandler keyH = new KeyHandler();

	Thread gameThread;

//	public PlayerCharacter player = new PlayerCharacter(this, keyH);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.WHITE);
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
		double drawInterval = 1000000000/fps;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		long timer = 0;

		
		while(gameThread != null){
			long currentTime = System.nanoTime();
			
			timer += (nextDrawTime - currentTime);
			
			Update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0){
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(timer >= 1000000000){
				Main.window.setTitle("Controlled Chaos");
				timer = 0;
			}
		}
	}
	
	public void Update(){
//		player.Update();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

//		player.Draw(g2);
		
		g2.dispose();
	}
	

}