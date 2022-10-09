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
        gp.enemy = new Slime(gp);
        gp.enemy.xCoord = 100;
        gp.enemy.yCoord = 100;

    }
}
