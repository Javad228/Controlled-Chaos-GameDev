package main;

import ai.Pathfinding;
import character.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import character.Enemy;
import character.PlayerCharacter;
import loot.*;
import save.SaveData;
import save.SimpleCharacter;
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
	public boolean levelComplete = false;
	public Pathfinding pFinder = new Pathfinding(this);
	public int gameState;
	public static Graphics2D g2;

	private int fps = 60;

	private Time startRunTime;		// Measure first time from start/resumption of run.
									// End time is not kept as a variable.
	private Time currentRunTime;	// Measure elapsed time


	public static final long SECOND_L = 1000000000L;
	public static final long MINUTE_L = 60 * SECOND_L;
	public static final Time defaultTime1 = new Time(2 * MINUTE_L);                 // 2:00
	public static final Time defaultTime2 = new Time(2 * MINUTE_L + 30 * SECOND_L); // 2:30
	public static final Time defaultTime3 = new Time(3 * MINUTE_L);            		// 3:00

	private Time startLevelTime;
	private Time currentLevelTime;
	private Time time1;
	private Time time2;
	private Time time3;


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

		//initializeRooms();
		//initializeLevelClocks();
		//tileM.update();
	}

	public void initializeRooms() {
		rooms = new ArrayList<>();
		rooms.add(new Room(0, keyH, this));
		rooms.add(new Room(1, keyH, this));
		rooms.add(new Room(2, keyH, this));
		rooms.add(new Room(3, keyH, this));
		rooms.add(new Room(4, keyH, this));
		rooms.add(new Room(5, keyH, this));
		rooms.add(new Room(6, keyH, this));

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

	/**
	 * initializeLevelClocks() - Calculate the time a player
	 * has to complete a level.
	 * <p>
	 * This method takes player difficulty into consideration
	 * and creates time based on the player's difficulty
	 */
	public void initializeLevelClocks() {
		// Calculate time to complete level
		if (player.getGameDifficulty() < PlayerCharacter.MID) {
			time1 = defaultTime1;                                       // 1:00
			time2 = defaultTime2;                                       // 1:30
			time3 = defaultTime3;                                       // 2:00
		} else {
			int diff = (player.getGameDifficulty() - PlayerCharacter.EASY_ADVANCED) * 5;

			time1 = new Time(defaultTime1.getTime() - diff * SECOND_L); // From 0:55 to 0:25 (difficulty: DEMON)
			time2 = new Time(defaultTime2.getTime() - diff * SECOND_L); // From 1:25 to 0:55 (difficulty: DEMON)
			time3 = new Time(defaultTime3.getTime() - diff * SECOND_L); // From 1:55 to 1:25 (difficulty: DEMON)
		}
	}

	public void setupGame() {
		assetSetter.setNPC();
	}

	public void startGameThread() {
		//Audio.stopMusic();
		Audio.openingMusic();
		gameThread = new Thread(this);
		gameThread.setName("Game Thread");
		gameThread.start();
	}

	public void newGame(boolean shouldStartGame) {
		this.currentRunTime = this.currentLevelTime = new Time(0);	// Reset game timers to 0
		this.setPlayer(new PlayerCharacter(this, keyH));
		initializeRooms();
		this.currentRoomNum = 1;
		tileM.update();

		if (shouldStartGame) {
			newGameHelper();
		}
	}

	public void newGame(SimpleCharacter sc, Time run_t, Time level_t, ArrayList<Room> rooms, int currentRoomNum, boolean shouldStartGame) {
		this.currentRunTime = run_t;
		this.currentLevelTime = level_t;
		this.setPlayer(new PlayerCharacter(sc, this, keyH));
		this.player.setItemsUnlocked(saveData.restorePermanentUnlocks());
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
			long time = System.nanoTime();
			currentRunTime = new Time(currentRunTime.getTime() + (time - startRunTime.getTime()));
			currentLevelTime = new Time(currentLevelTime.getTime() + (time - startLevelTime.getTime()));
			this.paused = true;
		}
	}

	public void resumeThread() {
		synchronized (this) {
			long time = System.nanoTime();
			startRunTime = new Time(time);
			startLevelTime = new Time(time);
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

	public boolean readLevelComplete() {
		synchronized (this) {
			return this.levelComplete;
		}
	}

	public void setLevelComplete(boolean levelComplete) {
		synchronized (this) {
			this.levelComplete = levelComplete;
		}
	}

	@Override
	public void run() {
		double drawInterval;					//converts from nanoseconds to seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		long t = System.nanoTime();
		startRunTime = new Time(t);
		startLevelTime = new Time(t);
		
		while(gameThread != null){

			delta = 0;
			timer = 0;
			lastTime = System.nanoTime();

			String currentTimeStr;

			while (!readThreadState() && !readLevelComplete()) {
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

					if (levelComplete)	{
						showCompleteLevel();
						//while (levelComplete);
					}
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

		if (rooms.get(currentRoomNum).getChests() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getChests().size(); i++) {
				Chest chest = rooms.get(currentRoomNum).getChests().get(i);
				chest.update();
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
		if (rooms.get(currentRoomNum).getChests() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getChests().size(); i++) {
				Chest chest = rooms.get(currentRoomNum).getChests().get(i);
				chest.draw(g2, this);
			}
		}

		if (rooms.get(currentRoomNum).getSigns() != null) {
			for (int i = 0; i < rooms.get(currentRoomNum).getSigns().size(); i++) {
				Sign sign = rooms.get(currentRoomNum).getSigns().get(i);
				try {
					sign.draw(g2, this);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
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
		this.tileM.update();
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

	public Time getCurrentLevelTime() {
		return this.currentLevelTime;
	}

	public void setCurrentRunTime(Time currentRunTime) {
		this.currentRunTime = currentRunTime;
	}

	public Time getTime1() {
		return this.time1;
	}

	public Time getTime2() {
		return this.time2;
	}

	public Time getTime3() {
		return this.time3;
	}

	public void showCompleteLevel() {
		// Measure time, determine if player was damaged

		long levelTime_l;							// Keeps total time the player took to beat the level

		int levelTimeRewardTier = 3;				// Measures what tier player rewards will be awarded to the player
													// given the current level time
		int playerHealthRewardTier = 3;				// Measures what tier player rewards will be awarded to the player
													// given if the player has taken damage

		Room currRoom = rooms.get(currentRoomNum);


		pauseThread();					// Call pauseThread() to get current time
										// (symmetric call to resumeThread will be made)

		levelTime_l = currentLevelTime.getTime() +
				(System.nanoTime() - startLevelTime.getTime());	// Measure time

		if (levelTime_l < time3.getTime()) {
			if (levelTime_l < time2.getTime()) {
				if (levelTime_l < time1.getTime()) {
					levelTimeRewardTier--;
				}
				levelTimeRewardTier--;
			}
			levelTimeRewardTier--;
		}


		for (int i = 0; i < 3 - levelTimeRewardTier; i++) {

			// Get random x and y coordinates relative to player and parameterized by screen size
			int x =
				Math.max(
					Math.min(
							(int)(Math.random() * 120 - 60) + player.getxCoord()
							,
							getMaximumSize().width)
					,
					0
				);
			int y =
				Math.max(
					Math.min(
							(int)(Math.random() * 120 - 60) + player.getyCoord()
							,
							getMaximumSize().height)
					,
					0
				);

			// Get a random item type and place it relative to the player
			// coordinate plus the random offset
			int loot = (int)(Math.random() * 2);
			switch (loot) {
				case 0 -> {
					if (currRoom.getItems() == null)	currRoom.setItems(new ArrayList<>());
					currRoom.getItems().add(new Sword(Sword.DEFAULT_SWORD_IMAGEPATHS, this, x, y));
				}
				case 1 -> {
					if (currRoom.getCoins() == null)	currRoom.setCoins(new ArrayList<>());
					currRoom.getCoins().add(new Coin(Coin.DEFAULT_FRAMES_TO_WAIT, Coin.COIN_IMAGES, x, y, Coin.DEFAULT_VALUE));
				}
				default -> {
				}
			}
		}

		// Player health
		if (!player.isDamaged()) {

		}

		// Show panel
		LevelCompleteDialog panel = LevelCompleteDialog.getLevelCompleteDialog(this, levelTimeRewardTier + playerHealthRewardTier);
		panel.showLevelCompleteDialog();

		resumeThread();				// Call to resumeThread() ensures that GameThread
									// will not be paused and time is reset for next level
	}

	public void hideLevelComplete() {
		setLevelComplete(false);
		this.repaint();
		this.resumeThread();
	}
}