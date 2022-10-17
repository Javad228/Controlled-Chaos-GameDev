package main;

import character.Character;
import character.NonPlayableCharacter;
import character.PlayerCharacter;

public class CollisionChecker {
    GamePanel gamePanel;
    GamePanel gp;

    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public boolean checkEntity(Character entity, Character target){
        if(target != null){

            entity.collisionOn = false;
//            System.out.println(entity.solidArea.x);
            entity.solidArea_2.x = entity.xCoord + entity.solidArea_2.x;
            entity.solidArea_2.y = entity.yCoord + entity.solidArea_2.y;

            target.solidArea_2.x = target.xCoord + target.solidArea_2.x;
            target.solidArea_2.y = target.yCoord + target.solidArea_2.y;

            entityCollisionDirection(entity);
            System.out.println("Player");
            System.out.println(entity.solidArea);
            System.out.println(target.name);
            System.out.println(target.solidArea);
            if (entity.solidArea_2.intersects(target.solidArea_2)) {
                if(target != entity){
                    entity.collisionOn = true;
                }
            }

            entity.solidArea_2.y = entity.collisionAreaDefaultY;
            entity.solidArea_2.x = entity.collisionAreaDefaultX;
            target.solidArea_2.x = target.collisionAreaDefaultX;
            target.solidArea_2.y = target.collisionAreaDefaultY;
            return entity.collisionOn;
        }
        return false;
    }

    private void entityCollisionDirection(Character entity) {
        switch (entity.direction) {
            case "up" -> entity.solidArea_2.y -= entity.movementSpeed;
            case "down" -> entity.solidArea_2.y += entity.movementSpeed;
            case "left" -> entity.solidArea_2.x -= entity.movementSpeed;
            case "right" -> entity.solidArea_2.x += entity.movementSpeed;
        }
    }

    public void checkTile (PlayerCharacter character) {

        int characterLeft = character.getxCoord() + character.solidArea.x;
        int characterRight = character.getxCoord() + character.solidArea.x + character.solidArea.width;
        int characterTop = character.getyCoord() + character.solidArea.y;
        int characterBottom = character.getyCoord() + character.solidArea.y + character.solidArea.height;
        System.out.println(gp.tileSize);
        int characterLeftCol = characterLeft/gp.tileSize;
        int characterRightCol = characterRight/gp.tileSize;
        int characterTopRow = characterTop/gp.tileSize;
        int characterBottomRow = characterBottom/gp.tileSize;

        int tileNum1, tileNum2;

        switch (character.getDirection()) {
            case "up" -> {
                characterTopRow = (characterTop - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.collisionOn = true;
                }
            }
            case "down" -> {
                characterBottomRow = (characterBottom + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.collisionOn = true;
                }
            }
            case "left" -> {
                characterLeftCol = (characterLeft - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.collisionOn = true;
                }
            }
            case "right" -> {
                characterRightCol = (characterRight + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.collisionOn = true;
                }
            }
        }
    }

    public int checkRoom (PlayerCharacter character) {
        int characterLeft = character.getxCoord() + character.solidArea_2.x;
        int characterRight = character.getxCoord() + character.solidArea_2.x + character.solidArea_2.width;
        int characterTop = character.getyCoord() + character.solidArea_2.y;
        int characterBottom = character.getyCoord() + character.solidArea_2.y + character.solidArea_2.height;

        if (characterLeft > 616 && characterLeft < 672 && character.roomNub == 0) {
            if (characterBottom > 226 && characterBottom < 254) {
                System.out.println("it's in");
                character.roomNub = 1;
                return 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && character.roomNub == 1) {
            if (characterBottom > 376 && characterBottom < 406) {
                System.out.println("it's in");
                character.roomNub = 0;
                return 0;
            }
        }
        return character.roomNub;
    }
}
