package main;

import character.PlayerCharacter;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import character.SimpleCharacter;
import loot.SimpleWeapon;
import loot.Weapon;

import java.io.IOException;

/**
 * GameSaveState - Private class which structures the save file format
 *
 * @author Cameron Hofbauer
 * @version October 9, 2022
 */
public class GameSaveState {
    public SimpleCharacter player;
    //TODO: Add list of npc
    //TODO: Add list of enemies
    public SimpleWeapon weapon;
    //TODO: Add list of loot

    public GameSaveState(SimpleCharacter player, SimpleWeapon weapon) {
        this.player = player;
        this.weapon = weapon;
    }
}
