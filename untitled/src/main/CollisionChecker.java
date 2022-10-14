package main;

import character.Character;
import loot.Consumable;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean checkConsumableCollision(Character entity, Consumable target) {
        if (target == null) return false;

        entity.collisionOn = false;
        entity.solidArea.x = entity.xCoord + entity.solidArea.x;
        entity.solidArea.y = entity.yCoord + entity.solidArea.y;

        target.solidArea.x = target.getxCoord() + target.solidArea.x;
        target.solidArea.y = target.getyCoord() + target.solidArea.y;

        if (entity.solidArea.intersects(target.solidArea)) {
            entity.collisionOn = true;
        }

        entity.solidArea.y = entity.collisionAreaDefaultY;
        entity.solidArea.x = entity.collisionAreaDefaultX;
        target.solidArea.x = target.collisionAreaDefaultX;
        target.solidArea.y = target.collisionAreaDefaultY;

        return entity.collisionOn;
    }

    public boolean checkEntityCollision(Character entity, Character target) {
        if (target == null) return false;

        entity.collisionOn = false;
        entity.solidArea.x = entity.xCoord + entity.solidArea.x;
        entity.solidArea.y = entity.yCoord + entity.solidArea.y;

        target.solidArea.x = target.xCoord + target.solidArea.x;
        target.solidArea.y = target.yCoord + target.solidArea.y;

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

    public boolean checkEntityAttack(Character entity, Character target){
        if(target != null){
            entity.collisionOn = false;
//            System.out.println(entity.solidArea.x);
            entity.solidArea.x = entity.xCoord + entity.solidArea.x;
            entity.solidArea.y = entity.yCoord + entity.solidArea.y;

            target.solidArea.x = target.xCoord + target.solidArea.x;
            target.solidArea.y = target.yCoord + target.solidArea.y;

            entityCollisionDirection(entity);
//            System.out.println(entity.name);
//            System.out.println(entity.solidArea);
//            System.out.println(target.name);
//            System.out.println(target.solidArea);
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
