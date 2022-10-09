package main;
import character.Inventory;
import character.PlayerCharacter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import loot.*;
import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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

    private final GamePanel gp;
    private static final String file = "untitled/src/main/temp_storage.txt";
    private final String separator = "\n-=-=-=-=-=-=-\n";
    private GsonBuilder gb;
    private Gson g;

    public SaveData(GamePanel gp) {
        this.gp = gp;
        initializeSaveGameButton();
        initializeRestoreGameButton();
        this.gb = new GsonBuilder();
        gb.setPrettyPrinting();
        g = gb.create();
    }

    private void initializeSaveGameButton() {
        saveGameButton = new JButton("Save");
        saveGameButton.setFocusable(true);
        saveGameButton.setToolTipText("Save the current Game State");

        saveGameButton.addActionListener((a) -> {
            if (saveGameState()) {
                System.out.println("Save Failed");
            } else {
                System.out.println("Save Success");
                JOptionPane.showMessageDialog(saveGameButton, "Save Success");
            }
        });
    }

    private void initializeRestoreGameButton() {
        restoreGameButton = new JButton("Load from Save");
        restoreGameButton.setFocusable(true);
        restoreGameButton.setToolTipText("Load a saved instance of the game from file");

        restoreGameButton.addActionListener((a) -> {});
    }

    public boolean saveGameState() {
        try {
            FileWriter f = new FileWriter(file);
            //f.write(g.toJson(new GameSaveState(gp.player, gp.weapon), GameSaveState.class));
            //f.write(new GameSaveState(gp.player, gp.weapon).write(g.newJsonWriter()))
            //new GameSaveState(gp.player, gp.weapon).write(g.newJsonWriter(f));
            f.write(g.toJson(deallocateCharacter(new PlayerCharacter(gp.player))));
            f.write(separator);
            f.write(g.toJson(deallocateWeapon(new Weapon(gp.weapon))));
            f.close();
            return false;
        } catch (IOException e) {
            System.out.println("ERROR: Save to File Failed!");
            e.printStackTrace();
        }
        return true;
    }

    //TODO: Polish Load from File method
    public GameSaveState restoreGameState() {
        try {
            StringBuilder input = new StringBuilder();
            BufferedReader r = new BufferedReader(new FileReader(file));
            while (r.ready()) {
                input.append(r.readLine());
            }
            return g.fromJson(input.toString(), GameSaveState.class);
        } catch (IOException e) {
            System.out.println("ERROR: Reading from Save Failed");
            e.printStackTrace();
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("ERROR: Syntax Parsing Failure!");
            return null;
        }
        return null;
    }

    private static PlayerCharacter deallocateCharacter(PlayerCharacter c) {
        c.setGamePanel(null);
        c.setKeyHandler(null);
        c.setUp1(null);
        c.setUp2(null);
        c.setDown1(null);
        c.setDown2(null);
        c.setLeft1(null);
        c.setLeft2(null);
        c.setRight1(null);
        c.setRight2(null);
        return c;
    }

    private static Weapon deallocateWeapon(Weapon c) {
        c.setGp(null);
        c.setKeyH(null);
        c.setWoodenSword(null);
        return c;
    }

    //TODO Temporary to familiarize with GSON
    public static void main(String[] args) {
        //SaveGameData s = new SaveGameData();

        //String file = "untitled/src/main/storage.txt";

        System.out.print("Enter character name: ");
        Scanner s = new Scanner(System.in);

        PlayerCharacter character = new PlayerCharacter(null, null);
        character.setName(s.next());
        character.setInventory(new Inventory(20, 20));
        deallocateCharacter(character);
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        Gson g = b.create();
        String str = g.toJson(character);
        System.out.printf("%s\n", str);

        StringBuilder input = new StringBuilder();

        try {
            FileWriter f = new FileWriter(file);
            f.write("");
            f.write(str);
            f.close();
            BufferedReader r = new BufferedReader(new FileReader(file));
            while (r.ready()) {
                input.append(r.readLine());
            }
            System.out.printf("%s\n", input);

        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerCharacter newChar = g.fromJson(input.toString(), PlayerCharacter.class);
        System.out.printf("%s\n", g.toJson(newChar));
        if (!newChar.equals(character)) throw new AssertionError();
        else System.out.println("Yay!");

    }

}

/**
 * GameSaveState - Private class which structures the save file format
 *
 * @author Cameron Hofbauer
 * @version October 9, 2022
 */
class GameSaveState {
    public PlayerCharacter player;
    //TODO: Add list of npc
    //TODO: Add list of enemies
    public Weapon weapon;
    //TODO: Add list of loot

    public GameSaveState(PlayerCharacter player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;
    }

    public void write(JsonWriter writer) throws IOException {
        TypeAdapter<GameSaveState> typeAdapter = new Gson().getAdapter(GameSaveState.class);
        typeAdapter.write(writer, this);
    }

    public GameSaveState read(JsonReader r) throws IOException {
        r.beginObject();
//        PlayerCharacter player = new PlayerCharacter(null, null);
//        player.setCharacterType(CharacterType.getCharacterType(r.nextString()));
//        player.setInventory(new Inventory(r.nextInt(), r.nextInt()));
//        player.setName(r.nextString())

        //this.player = r.();

        r.endObject();

        return this;
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

}