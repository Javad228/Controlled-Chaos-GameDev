package save;

import character.Enemy;
import enemy.Skeleton;
import enemy.Slime;
import enemy.Wizard;
import loot.Item;
import main.GamePanel;
import main.Main;
import main.Room;

import java.sql.Time;
import java.util.ArrayList;

/**
 * GameSaveState - A class which structures the save file format
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

    public GameSaveState(SimpleCharacter player) {
        this.player = player;
    }

    public GameSaveState(SimpleCharacter player, Time currentRunTime, ArrayList<Room> rooms, int currentRoomNum) {
        this.player = player;
        this.currentRunTimeNS = currentRunTime.getTime();
        this.currentRunTimeS = formatRunTime(currentRunTimeNS);
        this.rooms = new ArrayList<>();

        for (Room r : rooms) {
            this.rooms.add(new SimpleRoom(r));
        }

        this.currentRoomNum = currentRoomNum;
    }

    public GameSaveState(GameSaveState gss, SaveData sd) {
        this(gss.player, new Time(gss.currentRunTimeNS), sd.initializeRooms(gss.rooms), gss.currentRoomNum);
        Item.setUpItemListImages(gss.player.inventory.getListOfItems());
    }

    private String formatRunTime(long currentRunTimeNS) {
        //return (currentRunTimeNS / (1000000000L * 60 * 60)) + ":" +     //Hours
        //        (currentRunTimeNS / (1000000000L *60)) % 60 + ":" +    //Minutes
        //        (currentRunTimeNS / 1000000000) % 60 + "." +           //Seconds
        //        (currentRunTimeNS / 1000000) % 1000;                   //Milliseconds
        return String.format("%01d", currentRunTimeNS / (1000000000L * 60 * 60)) + ":" +        //H
                String.format("%02d", (currentRunTimeNS / (1000000000L * 60)) % 60) + ":" +     //MM
                String.format("%02d", (currentRunTimeNS / 1000000000) % 60) + "." +             //SS
                String.format("%03d", (currentRunTimeNS / 1000000) % 1000);                     //msmsms
    }
}
