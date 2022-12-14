package save;
import character.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import enemy.*;
import com.google.gson.*;
import loot.Item;
import main.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * SaveData - Class which implements functionality to save and restore game states
 * from a given file into JSON format. This functionality allows for one file save
 *
 * @author Cameron Hofbauer
 * @version October 9, 2022
 */
public class SaveData {

    public JButton saveGameButton;
    public JButton restoreGameButton;
    public JButton resetGameProgressButton;

    private static GamePanel gp;
    private static final String gameSaveFilePath = "untitled/src/main/game.save";
    private static final String playerUnlockFilePath = "untitled/src/main/player_unlocks.save";
//  private final String separator = "\n-=-=-=-=-=-=-\n";
    private final GsonBuilder gb;
    private final Gson g;

    //
    //
    public SaveData(GamePanel gp) {
        SaveData.gp = gp;
        initializeSaveGameButton();
        initializeRestoreGameButton();
        initializeResetGameProgressButton();
        this.gb = new GsonBuilder();
        //gb.registerTypeAdapter(BufferedImage.class, new BufferedImageAdapter());
        gb.setPrettyPrinting();
        g = gb.create();
    }

    private void initializeSaveGameButton() {
        saveGameButton = new JButton("Save");
        saveGameButton.setFocusable(true);
        saveGameButton.setToolTipText("Save the current Game State");

        saveGameButton.addActionListener((a) -> {
            if (gp.deathPanel.isShowing()) return;
            if (saveGameState()) {
                System.out.println("Save Failed");
            } else {
                System.out.println("Save Success");
                JOptionPane.showMessageDialog(saveGameButton, "Save Succeeded");
            }

            if (Main.view.getSettingsPanel().getPriorPage().equals("Main Menu")) {
                Main.view.showMainMenuPanel();
            } else if (Main.view.getSettingsPanel().getPriorPage().equals("Game Panel")) {
                Main.view.showGamePanel();
                Main.view.getGamePanel().resumeThread();
                Audio.stopMusic();
                Audio.openingMusic();
            }
        });
    }

    private void initializeRestoreGameButton() {
        restoreGameButton = new JButton("Load from Save");
        restoreGameButton.setFocusable(true);
        restoreGameButton.setToolTipText("Load a saved instance of the game from file");

        restoreGameButton.addActionListener((a) -> {
            if (gp.deathPanel.isShowing()) return;

            if (Main.view.getSettingsPanel().isShowing()) {
                if (Main.view.getSettingsPanel().getPriorPage().equals("Main Menu")) {
                    Main.view.showMainMenuPanel();
                } else if (Main.view.getSettingsPanel().getPriorPage().equals("Game Panel")) {
                    Main.view.showGamePanel();
                    //Main.view.getGamePanel().resumeThread();
                    Audio.stopMusic();
                    Audio.openingMusic();
                }
            }
            if (restoreSave()) JOptionPane.showMessageDialog(restoreGameButton, "Game Restore Succeeded");
            else JOptionPane.showMessageDialog(restoreGameButton, "Game Restore Failed\nRestoring to Default Save");
        });
    }

    private void initializeResetGameProgressButton() {
        resetGameProgressButton = new JButton("Reset Saved Progress");
        resetGameProgressButton.setFocusable(true);
        resetGameProgressButton.setToolTipText("Clear any saved progress from file");

        resetGameProgressButton.addActionListener((a) -> {
            if (JOptionPane.showConfirmDialog(resetGameProgressButton,
                    "Resetting game progress will erase all progress made\nAre you sure?", "Confirm Reset",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                resetSavedProgress();
                //gp.newGame();
                System.out.println("Saved Progress Reset");
                if (Main.view.getSettingsPanel().getPriorPage().equals("Main Menu")) {
                    Main.view.showMainMenuPanel();
                } else if (Main.view.getSettingsPanel().getPriorPage().equals("Game Panel")) {
                    Main.view.showGamePanel();
                    Main.view.getGamePanel().newGame(true);
                    //Main.view.getGamePanel().resumeThread();
                    Audio.stopMusic();
                    Audio.openingMusic();
                }
                JOptionPane.showMessageDialog(resetGameProgressButton, "Game Progress Reset",
                        "Reset Game Progress", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    /**
     * saveGameState -
     *
     * @return false - no error occurs
     *                  true - error occurs
     */
    public boolean saveGameState() {

        FileWriter f = null;

        try {
            f = new FileWriter(gameSaveFilePath);
            f.write(g.toJson(
                    new GameSaveState(
                            new SimpleCharacter(gp.getPlayer()),
                            gp.getCurrentRunTime(),
                            gp.getCurrentLevelTime(),
                            gp.getRooms(),
                            gp.getCurrentRoomNum(),
                            gp.getPlayer().roomSetNum
                    )));
            //f.write(g.toJson(new GameSaveState(new SimpleCharacter(gp.getPlayer()), new SimpleWeapon(gp.getWeapon()))));
            f.close();
        } catch (IOException e) {
            System.out.println("ERROR: Save player progress to File Failed!");
            e.printStackTrace();
            return true;
        }

        try {
            f = new FileWriter(playerUnlockFilePath);
            f.write(g.toJson(gp.player.getItemsUnlocked()));
            f.close();
        } catch (IOException e) {
            System.out.println("ERROR: Save permanent unlocks to File Failed!");
            e.printStackTrace();
            return true;
        }

        return false;

    }

    public boolean restoreSave() {
        GameSaveState gs = restoreGameState();

        if (gs == null) {
            gp.newGame(true);
            if (gp.readThreadState()) System.out.println("Game restore Failed\nUsing starting values");
            else System.out.println("Game restore Failed");
            return false;
        }

        gs = new GameSaveState(gs, this);

        Item.setUpItemListImages(gs.player.inventory.getListOfItems());
        gp.newGame(
                gs.player,
                new Time(gs.currentRunTimeNS),
                new Time(gs.currentLevelTimeNS),
                initializeRooms(gs.rooms, gs.player),
                gs.currentRoomNum,
                gs.currentLevelNum,
                true);
        System.out.println("Game restore Succeeded");
        return true;
    }

    public GameSaveState restoreGameState() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(gameSaveFilePath));
            StringBuilder input = new StringBuilder();

            // Read from file
            while (r.ready()) input.append(r.readLine());

            return g.fromJson(input.toString(), GameSaveState.class);
        } catch (IOException e) {
            System.out.println("ERROR: Reading from Save Failed");
            e.printStackTrace();
            return null;
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("ERROR: Syntax Parsing Failure!");
            return null;
        }
    }

    public boolean[] restorePermanentUnlocks() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(playerUnlockFilePath));
            StringBuilder input = new StringBuilder();

            // Read from file
            while (r.ready())   input.append(r.readLine());

            return g.fromJson(input.toString(), boolean[].class);
        } catch (IOException e) {
            System.out.println("ERROR: Reading from Save Failed");
            return null;
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("ERROR: Syntax Parsing Failure!");
            return null;
        }
    }

    public boolean resetSavedProgress() {
        try {
            FileWriter f = new FileWriter(gameSaveFilePath);
            f.write("");
            f.close();
            gp.setCurrentRunTime(new Time(0));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Room> initializeRooms(ArrayList<SimpleRoom> rooms, SimpleCharacter p) {
        ArrayList<Room> returnableRoomList = new ArrayList<>();
        int i = 0;

        for (SimpleRoom savedRoom : rooms) {
            Room thisRoom = new Room(i, gp.keyH, gp);

            thisRoom.setItems(savedRoom.items);
            Item.setUpItemListImages(thisRoom.getItems());

            ArrayList<Enemy> enemies = new ArrayList<>();

            if (savedRoom.enemies != null) {

                for (SimpleEnemy enemy : savedRoom.enemies) {
                    if (enemy.classification.equals(SimpleEnemyClassification.SKELETON)) {
                        enemies.add(new Skeleton(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.SLIME)) {
                        enemies.add(new Slime(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.WIZARD)) {
                        enemies.add(new Wizard(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.BARREL)) {
                        enemies.add(new Barrel(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.BIGSLONCH)) {
                        enemies.add(new BigSlonch(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.BIGSKULL)) {
                        enemies.add(new BigSkull(enemy));
                    } else if (enemy.classification.equals(SimpleEnemyClassification.BIGWIZARD)) {
                        enemies.add(new BigWizard(enemy));
                        /*Add more classifications for future enemies*/
                    } else {
                        System.out.println("Generic enemy encountered!");
                    }
                }
            }

            thisRoom.setEnemies(enemies);
            thisRoom.setCoins(savedRoom.coins);

            returnableRoomList.add(thisRoom);
            i++;
        }

        return returnableRoomList;
    }
}

class BufferedImageAdapter extends TypeAdapter<BufferedImage> {
    @Override
    public BufferedImage read(JsonReader reader) throws IOException {
        reader.beginObject();
        reader.endObject();
        return null;
    }

    @Override
    public void write(JsonWriter writer, BufferedImage image) throws IOException {
        writer.beginObject();
        writer.endObject();
    }
}

/**
 * TestSaveData - Private Class which tests the functionality of SaveData and its
 * relative components
 *
 * @author Cameron Hofbauer
 * @version October 9, 2022
 */
class TestSaveData {
    //TODO Replace with test
    public static void main(String[] args) {
        //SaveGameData s = new SaveGameData();

        //String file = "untitled/src/main/storage.txt";

        System.out.print("Enter character name: ");
        Scanner s = new Scanner(System.in);

        PlayerCharacter character = new PlayerCharacter(null, null);
        character.setName(s.next());
        character.setInventory(new Inventory(20, new ArrayList<>(),20));
        //deallocateCharacter(character);
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        Gson g = b.create();
        String str = g.toJson(character);
        System.out.printf("%s\n", str);

        //StringBuilder input = new StringBuilder();

        //try {
        //    FileWriter f = new FileWriter(file);
        //    f.write("");
        //    f.write(str);
        //    f.close();
        //    BufferedReader r = new BufferedReader(new FileReader(file));
        //    while (r.ready()) {
        //        input.append(r.readLine());
        //    }
        //    System.out.printf("%s\n", input);

        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //PlayerCharacter newChar = g.fromJson(input.toString(), PlayerCharacter.class);
        //System.out.printf("%s\n", g.toJson(newChar));
        //if (!newChar.equals(character)) throw new AssertionError();
        //else System.out.println("Yay!");

    }
}