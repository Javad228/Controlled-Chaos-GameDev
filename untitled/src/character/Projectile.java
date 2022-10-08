package character;

public class Projectile extends Entity {

    public Projectile() {
        super();
    }

    public void set(int xCoord, int yCoord, String direction, int movementSpeed) {  //CombatType type
                                                                //boolean isInvincible
                                                                //Entity user (whether it damages the player or enemies)
        this.setxCoord(xCoord);
        this.setyCoord(yCoord);
        this.setDirection(direction);
        this.setMovementSpeed(movementSpeed);
        //this.type = RANGED;
        //this.user = user;
    }

    public void update() {
        switch(this.getDirection()) {
            case "up": this.setyCoord(this.getyCoord() - this.getMovementSpeed()); break;
            case "down": this.setyCoord(this.getyCoord() + this.getMovementSpeed()); break;
            case "left": this.setxCoord(this.getxCoord() - this.getMovementSpeed()); break;
            case "right": this.setxCoord(this.getxCoord() + this.getMovementSpeed()); break;
        }

        /* life--;
        if (life <= 0) {
            alive = false;
        } */

        this.setSpriteCounter(this.getSpriteCounter() + 1);
        if (this.getSpriteCounter() > 1) {
            if (this.getSpriteNum() == 1) {
                this.setSpriteNum(2);
            }
            else if (this.getSpriteNum() == 2) {
                this.setSpriteNum(1);
            }
            this.setSpriteCounter(0);
        }


    }

}