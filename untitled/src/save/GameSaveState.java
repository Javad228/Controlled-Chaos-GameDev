package save;

import save.SimpleCharacter;
import save.SimpleWeapon;

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

    // 10/22 - put this in temporarily to get rid of an error within SaveData.java. Currently trying to decouple the
    // presence of items in the room from the GamePanel class. ~KA
    public GameSaveState(SimpleCharacter player) {
        this.player = player;
    }
}
