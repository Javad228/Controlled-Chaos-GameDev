package main;

import ai.Pathfinding;
import character.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

import character.Enemy;
import character.PlayerCharacter;
import loot.*;
import save.SaveData;
import save.SimpleCharacter;
import save.SimpleWeapon;
import tile.TileManager;
import tile.TrapTile;

public class GamePanel extends JPanel implements Runnable{
	final static int trapDamage = 5;
	final int originalTileSizes = 16;							//16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSizes * scale;		//48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 13;
	public final int screenWidth = tileSize * maxScreenCol; 	//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	//624 pixels

	public boolean paused = false;
	public Pathfinding pFinder = new Pathfinding(this);
	public int gameState;
	public static Graphics2D g2;

	private int fps = 60;
//TODO MERGE CHECK
	private Time startRunTime;		// Measure first time from start/resumption of run.
									// End time is not kept as a variable.
	private Time currentRunTime;	// Measure elapsed time

	private int currentRoomNum = 1;

	public CollisionChecker checker = new CollisionChecker(this);
	public KeyHandler keyH = new KeyHandler(this);
	transient Thread gameThread;
	public PlayerCharacter player = new PlayerCharacter(this, keyH); // was having issues with the player character being defined multiple times
	public TileManager tileM = new TileManager(this);
	public ArrayList<Projectile> projectileList = new ArrayList<>();

	private ArrayList<Room> rooms; // list of rooms. the index of the room is its room number

	public AssetSetter assetSetter = new AssetSetter(this);
	public SaveData saveData = new SaveData(this);
	public DeathPanel deathPanel = new DeathPanel(this);
	public Inventory inventory = new Inventory(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.currentRunTime = new Time(0);

		initializeRooms();
		tileM.update();
	}

	private void initializeRooms() {
		rooms = new ArrayList<>();
		rooms.add(new Room(0, keyH, this));
		rooms.add(new Room(1, keyH, this));
    	rooms.add(new Room(2, keyH, this));
		rooms.add(new Room(3, keyH, this));
		rooms.add(new Room(4, keyH, this));
		rooms.add(new Room(5, keyH, this));

		// First run will set enemy coordinates
		if (rooms.get(currentRoomNum).getEnemies() != null){
			// assuming this is to set the position of enemies after starting a new game. probably needs to change
			for (int i = 0; i < rooms.get(currentRoomNum).getEnemies().size(); i++) {
				Enemy enemy = rooms.get(currentRoomNum).getEnemies().get(i);
				enemy.setxCoord(100);
				enemy.setyCoord(100);
			}
		}
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

	public void newGame(boolean shouldStartGame) {
		this.currentRunTime = new Time(0);	// Reset game timer to 0
		this.setPlayer(new PlayerCharacter(this, keyH));
		initializeRooms();
		this.currentRoomNum = 1;
		tileM.update();

		if (shouldStartGame) {
			newGameHelper();
		}
	}

	public void newGame(SimpleCharacter sc, Time t, ArrayList<Room> rooms, int currentRoomNum, boolean shouldStartGame) {
		this.currentRunTime = t;
		this.setPlayer(new PlayerCharacter(sc, this, keyH));
		this.rooms = rooms;
		if (this.currentRoomNum != currentRoomNum) {
			this.currentRoomNum = currentRoomNum;
			tileM.update();
		} else {
			this.currentRoomNum = currentRoomNum;
		}

		if (shouldStartGame) {
			newGameHelper();
		}
	}

	private void newGameHelper() {
		Audio.stopWalking();
		Audio.stopMusic();
		Audio.openingMusic();
		Main.view.getSettingsPanel().hideSettingsPanel();
		deathPanel.hideDeathPanel();
		this.setFocusable(true);
		this.requestFocusInWindow();
		if (gameThread == null) {
			this.gameThread = new Thread(this);
			startGameThread();
		}

		if (rooms.get(currentRoomNum).getEnemies() != null){
			// assuming this is to set the position of enemies after starting a new game. probably needs to change
			for (int i = 0; i < rooms.get(currentRoomNum).getEnemies().size(); i++) {
				Enemy enemy = rooms.get(currentRoomNum).getEnemies().get(i);
				/*enemy.setxCoord(100);
				enemy.setyCoord(100);
				 */
			}
		}

		this.resumeThread();
	}

	public void pauseThread() {
		synchronized (this) {
			currentRunTime = new Time(currentRunTime.getTime() + (System.nanoTime() - startRunTime.getTime()));
			this.paused = true;
		}
	}

	public void resumeThread() {
		synchronized (this) {
			startRunTime = new Time(System.nanoTime());
			this.paused = false;
		}
	}

	public boolean readThreadState() {
		synchronized (this) {
			return this.paused;
		}
	}

	/**
	 * terminateGameThread() - Method used for testing the GameThread.
	 * This method finalizes test execution by terminating the gameThread.
	 */
	public void terminateGameThread() {
		this.gameThread = null;
	}

	@Override
	public void run() {
		double drawInterval;					//converts from nanoseconds to seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		startRunTime = new Time(System.nanoTime());
		
		while(gameThread != null){

			delta = 0;
			timer = 0;
			lastTime = System.nanoTime();

			String currentTimeStr;

			while (!readThreadState()) {
				currentTime = System.nanoTime();
				drawInterval = 1000000000. / fps;

				currentTimeStr = Long.toString(currentTime);

				delta += (currentTime - lastTime) / drawInterval;
				timer += (currentTime - lastTime);


				lastTime = currentTime;

				if (delta >= 1) {
					update();
					repaint();
					delta--;
					drawCount++;

					if (player.getCurrentTile().isDamageTile()) {
						player.damagePlayerInt(trapDamage);
					}
				}

				if (timer >= 1000000000) {
					Main.view.getWindow().setTitle("Controlled Chaos");
					//System.out.println("FPS:" + drawCount);
					drawCount = 0;
					timer = 0;
				}

				if (player.getHealth() <= 0 && !player.isAlive) {
					Audio.stopWalking();
					Audio.stopMusic();
					player.kill();
					player.setDefaultValues();
					keyH.reset();
					player.setKeyHandler(null);
					deathPanel.showDeathPanel();
					//this.pauseThread();
				}

				if (currentRoomNum == 4) { // room.TRAPROOM no longer exists. plz change
					if (currentTime % 1000000000 == 0) {
						for (int i = 0; i < maxScreenRow; i++) {
							rooms.get(4).getTrapTiles().get(maxScreenRow + i).toggleTrap(i, TrapTile.map1TrapCol2);
						}
					}
				}
			}
		}
	}

	public void update(){
		//System.out.println(player.name);
		player.update();
		if (rooms.get(currentRoomNum).getEnemies() != null){
			for (int i = 0; i < rooms.get(currentRoomNum).getEnemies().size(); i++) {
				//Enemy enemy = rooms.get(currentRoomNum).getEnemies().get(i);
				//enemy.update(this);

				// Above was producing unintended behavior
				rooms.get(currentRoomNum).getEnemies().get(i).update(this);
			}
		}

		if (rooms.get(currentRoomNum).getItems() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getItems().size(); i++) {
				Item item = rooms.get(currentRoomNum).getItems().get(i);
				item.update();
			}
		}

		if (rooms.get(currentRoomNum).getNPCs() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getNPCs().size(); i++) {
				NonPlayableCharacter npc = rooms.get(currentRoomNum).getNPCs().get(i);
				npc.update(this);
			}
		}


		for (int i = 0; i < projectileList.size(); i++) {
			if (projectileList.get(i) != null) {
				if (projectileList.get(i).isAlive) {
					projectileList.get(i).update(this);
				}
				if (!projectileList.get(i).isAlive) {
					projectileList.remove(i);
				}
			}
		}

		if (rooms.get(currentRoomNum).getCoins() != null) {
			for (int j = 0; j < rooms.get(currentRoomNum).getCoins().size(); j++) {
				Coin coin = rooms.get(currentRoomNum).getCoins().get(j);
				coin.update();
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g2 = (Graphics2D)g;

		tileM.draw(g2);
		player.draw(g2);

		if (rooms.get(currentRoomNum).getEnemies() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getEnemies().size(); i++) {
				Enemy enemy = rooms.get(currentRoomNum).getEnemies().get(i);

				if (enemy != null) {
					enemy.draw(g2, this);
				}
			}
		}

		if (rooms.get(currentRoomNum).getItems() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getItems().size(); i++) {
				Item item = rooms.get(currentRoomNum).getItems().get(i);
				item.draw(g2, this);
			}
		}

		if (rooms.get(currentRoomNum).getNPCs() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getNPCs().size(); i++) {
				NonPlayableCharacter npc = rooms.get(currentRoomNum).getNPCs().get(i);
				npc.draw(g2, this);
			}
		}

		for (int i = 0; i < projectileList.size(); i++) {
			if (projectileList.get(i) != null) {
				if (projectileList.get(i).isAlive) {
					projectileList.get(i).draw(g2);
				}
			}
		}

		if (rooms.get(currentRoomNum).getCoins() != null) {
			for (int k = 0; k < rooms.get(currentRoomNum).getCoins().size(); k++) {
				Coin coin = rooms.get(currentRoomNum).getCoins().get(k);
				coin.draw(g2, this);
			}
		}

		inventory.draw(g2);

		Main.view.updateCoinLabel(g2);

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

	public int getCurrentRoomNum() {
		return currentRoomNum;
	}

	public void setCurrentRoomNum(int currentRoomNum) {
		this.currentRoomNum = currentRoomNum;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public Time getCurrentRunTime() {
		return this.currentRunTime;
	}

	public void setCurrentRunTime(Time currentRunTime) {
		this.currentRunTime = currentRunTime;
	}
}