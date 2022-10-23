package save;
import character.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import loot.*;
import com.google.gson.*;
import main.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private static final String file = "untitled/src/main/temp_storage.txt";
//  private final String separator = "\n-=-=-=-=-=-=-\n";
    private GsonBuilder gb;
    private Gson g;

    public SaveData(GamePanel gp) {
        SaveData.gp = gp;
        initializeSaveGameButton();
        initializeRestoreGameButton();
        initializeResetGameProgressButton();
        this.gb = new GsonBuilder();
        gb.registerTypeAdapter(BufferedImage.class, new BufferedImageAdapter());
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
            Main.view.getSettingsPage().hideSettingsPanel();
            Main.view.getGamePanel().resumeThread();
            Audio.stopMusic();
            Audio.openingMusic();
        });
    }

    private void initializeRestoreGameButton() {
        restoreGameButton = new JButton("Load from Save");
        restoreGameButton.setFocusable(true);
        restoreGameButton.setToolTipText("Load a saved instance of the game from file");

        restoreGameButton.addActionListener((a) -> {
            if (gp.deathPanel.isShowing()) return;
            if (Main.view.getSettingsPage().isShowing()) Main.view.getSettingsPage().setVisible(false);
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
                System.out.println("Saved Progress Reset");
                JOptionPane.showMessageDialog(resetGameProgressButton, "Game Progress Reset",
                        "Reset Game Progress", JOptionPane.INFORMATION_MESSAGE);
                Main.view.getSettingsPage().hideSettingsPanel();
                Main.view.getGamePanel().resumeThread();
                Audio.stopMusic();
                Audio.openingMusic();
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
        try {
            FileWriter f = new FileWriter(file);
            f.write(g.toJson(new GameSaveState(new SimpleCharacter(gp.getPlayer()))));
            //f.write(g.toJson(new GameSaveState(new SimpleCharacter(gp.getPlayer()), new SimpleWeapon(gp.getWeapon()))));
            f.close();
            return false;
        } catch (IOException e) {
            System.out.println("ERROR: Save to File Failed!");
            e.printStackTrace();
        }
        return true;
    }

    public boolean restoreSave() {
        GameSaveState gs;

        if ((gs=restoreGameState()) == null) {
            gp.newGame();
            if (gp.paused) System.out.println("Game restore Failed\nUsing starting values");
            else System.out.println("Game restore Failed");
            return false;
        }

        gp.newGame(gs.player, gs.weapon);
        System.out.println("Game restore Succeeded");
        return true;
    }

    private GameSaveState restoreGameState() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
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

    public boolean resetSavedProgress() {
        try {
            FileWriter f = new FileWriter(file);
            f.write("");
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static PlayerCharacter deallocateCharacter(PlayerCharacter c) {
        c.setGamePanel(null);
        c.setKeyHandler(null);
        c.setUp1(null);
        c.setUp2(null);
        c.setUp3(null);
        c.setUp4(null);
        c.setUp5(null);
        c.setUp6(null);
        c.setDown1(null);
        c.setDown2(null);
        c.setDown3(null);
        c.setDown4(null);
        c.setDown5(null);
        c.setDown6(null);
        c.setLeft1(null);
        c.setLeft2(null);
        c.setLeft3(null);
        c.setLeft4(null);
        c.setLeft5(null);
        c.setLeft6(null);
        c.setRight1(null);
        c.setRight2(null);
        c.setRight3(null);
        c.setRight4(null);
        c.setRight5(null);
        c.setRight6(null);
        return c;
    }

    private static Weapon deallocateWeapon(Weapon c) {
        //c.setGp(null);
        c.setKeyH(null);
        c.setLootImages(null);
        return c;
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