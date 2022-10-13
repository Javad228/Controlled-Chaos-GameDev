package main;

import javax.swing.*;

import character.PlayerCharacter;
import character.SimpleCharacter;
import enemy.Slime;
import loot.SimpleWeapon;
import loot.Effect;
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

	public boolean paused = false;

	private int fps = 60;
	public CollisionChecker checker = new CollisionChecker(this);

	private String[] effectImages = {"/effects/invincibility_1.png", "/effects/invincibility_2.png", "/effects/invincibility_3.png"};
	private String[] weaponImages = {"/weapons/wooden_sword.png"};

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;

	public PlayerCharacter player = new PlayerCharacter(this, keyH);
	public Weapon weapon = new Weapon(this, keyH, weaponImages);
	public Effect effect = new Effect(this, keyH, effectImages);

	public AssetSetter assetSetter = new AssetSetter(this);
	public SaveData saveData = new SaveData(this);
	public DeathPanel deathPanel = new DeathPanel(this);

	//Methods to alter player:
	/*
 	int playerX = player.getxCoord();
	int playerY = player.getyCoord();
	double playerSpeed = player.getMovementSpeed();

	player.setxCoord(1); */

	public Slime enemy = new Slime(this);

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
		Audio.stopMusic();
		Audio.openingMusic();
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newGame() {
		this.setPlayer(new PlayerCharacter(this, keyH));
		this.setWeapon(new Weapon(this, keyH, weaponImages));
		newGameHelper();
	}

	public void newGame(SimpleCharacter sc, SimpleWeapon w) {
		this.setPlayer(new PlayerCharacter(sc, this, keyH));
		this.setWeapon(new Weapon(w, this, keyH));
		newGameHelper();
	}

	private void newGameHelper() {
		Audio.stopWalking();
		Audio.stopMusic();
		Audio.openingMusic();
		this.setFocusable(true);
		this.requestFocusInWindow();
		if (gameThread == null) {
			this.gameThread = new Thread(this);
			startGameThread();
		}
	}

	private void pauseThread() {
		this.paused = true;
	}

	public void resumeThread() {
		this.paused = false;
	}

	@Override
	public void run() {

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

			while (!paused) {
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
					this.player.setHealth(this.player.getHealth()-1);						//TODO: Debug HealthBar
					drawCount = 0;
					timer = 0;
				}

				if (player.getHealth() == 0 ||
						(player.getxCoord() == 752 && player.getyCoord() == 532)) {        //TODO: Debug DeathPanel
					player.setHealth(0);
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
		enemy.update();
		weapon.update();
		effect.update();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		enemy.draw(g2);
		player.draw(g2);
		weapon.draw(g2);
		effect.draw(g2);

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