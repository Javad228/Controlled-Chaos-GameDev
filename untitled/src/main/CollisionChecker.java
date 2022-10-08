package main;

import character.Character;
import character.NonPlayableCharacter;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean checkEntity(Character entity, Character target){
        if(target != null){

            entity.solidArea.x = entity.xCoord + entity.solidArea.x;
            entity.solidArea.y = entity.yCoord + entity.solidArea.y;

            target.solidArea.x = target.xCoord + target.solidArea.x;
            target.solidArea.y = target.yCoord + target.solidArea.y;

            entityCollisionDirection(entity);
            if (entity.solidArea.intersects(target.solidArea)) {
                if(target != entity){
                    entity.collisionOn = true;
                }
            }

            entity.solidArea.y = entity.collisionAreaDefaultY;
            entity.solidArea.x = entity.collisionAreaDefaultX;
            target.solidArea.x = target.collisionAreaDefaultX;
            target.solidArea.y = target.collisionAreaDefaultY;
            return entity.collisionOn;
        }
        return false;
    }

    private void entityCollisionDirection(Character entity) {
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.movementSpeed;
            case "down" -> entity.solidArea.y += entity.movementSpeed;
            case "left" -> entity.solidArea.x -= entity.movementSpeed;
            case "right" -> entity.solidArea.x += entity.movementSpeed;
        }
    }
}
