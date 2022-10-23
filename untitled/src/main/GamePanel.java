package main;

import ai.Pathfinding;
import character.NonPlayableCharacter;
import character.PlayerCharacter;
import enemy.Skeleton;
import enemy.Slime;
import loot.Consumable;
import loot.Effect;
import loot.Weapon;
import save.SaveData;
import save.SimpleCharacter;
import save.SimpleWeapon;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSizes = 16;							//16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSizes * scale;		//48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; 	//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	//576 pixels

	public boolean paused = false;
	public Pathfinding pFinder = new Pathfinding(this);

	private int fps = 60;
	public CollisionChecker checker = new CollisionChecker(this);

	public TileManager tileM = new TileManager(this);

	private String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
	private String[] weaponImages = {"/weapons/wooden_sword.png"};
	private String[] appleImages = {"/consumables/apple.png"};

	public KeyHandler keyH = new KeyHandler();

	Thread gameThread;
	public PlayerCharacter player = new PlayerCharacter(this, keyH);

	public Weapon weapon = new Weapon(keyH, weaponImages);
	public Effect effect = new Effect(keyH, effectImages);

	public AssetSetter assetSetter = new AssetSetter(this);
	public SaveData saveData = new SaveData(this);
	public DeathPanel deathPanel = new DeathPanel(this);

	//Methods to alter player:
	/*
 	int playerX = player.getxCoord();
	int playerY = player.getyCoord();
	double playerSpeed = player.getMovementSpeed();

	player.setxCoord(1); */
	public NonPlayableCharacter[] enemies = new NonPlayableCharacter[12];
//	public Slime enemy = new Slime();
	public Consumable apple = new Consumable(this, appleImages);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

	}
	public void setupGame() {
		assetSetter.setNPC();
	}
	public void startGameThread() {
		//Audio.stopMusic();
		Audio.openingMusic();
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newGame() {

		this.setPlayer(new PlayerCharacter(this, keyH));
		this.setWeapon(new Weapon(keyH, weaponImages));
		newGameHelper();
	}

	public void newGame(SimpleCharacter sc, SimpleWeapon w) {
		this.setPlayer(new PlayerCharacter(sc, this, keyH));
		this.setWeapon(new Weapon(w, keyH));
		newGameHelper();
	}

	private void newGameHelper() {
		Audio.stopWalking();
		Audio.stopMusic();
		Audio.openingMusic();
		Main.view.getSettingsPage().hideSettingsPanel();
		deathPanel.hideDeathPanel();
		this.setFocusable(true);
		this.requestFocusInWindow();
		if (gameThread == null) {
			this.gameThread = new Thread(this);
			startGameThread();
		}
		enemies[0].setxCoord(50);
		enemies[0].setyCoord(50);
		enemies[1].setxCoord(150);
		enemies[1].setyCoord(150);
		this.resumeThread();
	}

	public void pauseThread() {
		synchronized (this) {
			this.paused = true;
		}
	}

	public void resumeThread() {
		synchronized (this) {
			this.paused = false;
		}
	}

	public boolean readThreadState() {
		synchronized (this) {
			return this.paused;
		}
	}

	@Override
	public void run() {
		Slime enemy = new Slime();
		Skeleton enemy1 = new Skeleton();
		enemies[0] = enemy;
		enemies[1] = enemy1;

		double drawInterval;					//converts from nanoseconds to seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null){

			delta = 0;
			timer = 0;
			lastTime = System.nanoTime();

			while (!readThreadState()) {
				currentTime = System.nanoTime();
				drawInterval = 1000000000. / fps;

				delta += (currentTime - lastTime) / drawInterval;
				timer += (currentTime - lastTime);


				lastTime = currentTime;

				if (delta >= 1) {
					update();
					repaint();
					delta--;
					drawCount++;
				}

				if (timer >= 1000000000) {
					Main.window.setTitle("Controlled Chaos");
					System.out.println("FPS:" + drawCount);
					drawCount = 0;
					timer = 0;
				}

				if (player.getHealth() <= 0) {
					Audio.stopWalking();
					Audio.stopMusic();
					player.kill();
					player.setDefaultValues();
					keyH.reset();
					player.setKeyHandler(null);
					deathPanel.showDeathPanel();
					//Main.view.getWindow().set
					this.pauseThread();
				}
			}
		}
	}

	public void update(){
		player.update();
		for(int i = 0; i<2; i++){
			enemies[i].update(this);
		}
		weapon.update();
		apple.update();
		effect.update();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		apple.draw(g2, this);
		for(int i = 0; i<2; i++){
			enemies[i].draw(g2, this);
		}
		player.draw(g2);
		weapon.draw(g2, this);
		effect.draw(g2, this);


		g2.dispose();
	}

	public int getFps() {
		return fps;
	}
	public void setFps(int newFrameRate) {
		fps = newFrameRate;
	}

	public synchronized PlayerCharacter getPlayer() {
		return this.player;
	}

	public synchronized void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	public synchronized Weapon getWeapon() {
		return this.weapon;
	}

	public synchronized void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}