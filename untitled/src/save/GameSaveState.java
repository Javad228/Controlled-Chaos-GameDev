package save;

import main.Room;

import java.sql.Time;
import java.util.ArrayList;

/**
 * GameSaveState - Private class which structures the save file format
 *
 * @author Cameron Hofbauer
 * @version October 9, 2022
 */
public class GameSaveState {
    public SimpleCharacter player;
    public ArrayList<SimpleRoom> rooms;
    public int currentRoomNum;
    public SimpleWeapon weapon;
    public long currentRunTimeNS;
    public String currentRunTimeS;

    @Deprecated
    public GameSaveState(SimpleCharacter player, SimpleWeapon weapon) {
        this.player = player;
        this.weapon = weapon;
    }

    // 10/22 - put this in temporarily to get rid of an error within SaveData.java. Currently trying to decouple the
    // presence of items in the room from the GamePanel class. ~KA
    public GameSaveState(SimpleCharacter player, Time currentRunTime, ArrayList<Room> rooms, int currentRoomNum) {
        this.player = player;
        this.currentRunTimeNS = currentRunTime.getTime();
        this.currentRunTimeS = this.currentRunTimeNS / 1000000000 + "." + (this.currentRunTimeNS / 1000000) % 1000;
        this.rooms = new ArrayList<>();

        for (Room r : rooms) {
            this.rooms.add(new SimpleRoom(r));
        }

        this.currentRoomNum = currentRoomNum;
    }
}
