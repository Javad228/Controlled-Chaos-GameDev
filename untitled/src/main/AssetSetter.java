package main;
import enemy.Slime;
public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }

    public void setNPC() {
        //TODO: had to comment this out because of the room class edition. not sure what this will break...
        /*
        gp.enemy = new Slime();
        gp.enemy.xCoord = 100;
        gp.enemy.yCoord = 100;
        */
    }
}
