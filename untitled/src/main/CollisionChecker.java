package main;

import character.Character;
import character.PlayerCharacter;
import loot.Loot;
import tile.Tile;

public class CollisionChecker {
    static final int trapDamage = 2;

    GamePanel gp;

    public CollisionChecker(GamePanel gamePanel){
        this.gp = gamePanel;
    }

    public boolean checkLootCollision(Character entity, Loot target) {
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

    public void checkTile (Character character) {
        int characterLeft = character.getxCoord() + character.solidArea.x;
        int characterRight = character.getxCoord() + character.solidArea.x + character.solidArea.width;
        int characterTop = character.getyCoord() + character.solidArea.y;
        int characterBottom = character.getyCoord() + character.solidArea.y + character.solidArea.height;
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
            }
            case "down" -> {
                characterBottomRow = (characterBottom + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
            }
            case "left" -> {
                characterLeftCol = (characterLeft - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
            }
            case "right" -> {
                characterRightCol = (characterRight + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
            } default -> {
                System.out.println("Error with block collision.");
                return;
            }
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            character.collisionOn = true;
        }

    }

    public void checkPath (Character character,int[][] maze) {
        int characterLeft = character.getxCoord() + character.solidArea.x;
        int characterRight = character.getxCoord() + character.solidArea.x + character.solidArea.width;
        int characterTop = character.getyCoord() + character.solidArea.y;
        int characterBottom = character.getyCoord() + character.solidArea.y + character.solidArea.height;
        int characterLeftCol = characterLeft/gp.tileSize;
        int characterRightCol = characterRight/gp.tileSize;
        int characterTopRow = characterTop/gp.tileSize;
        int characterBottomRow = characterBottom/gp.tileSize;

        int tileNum1, tileNum2;
        int characterTopRow1 = (characterTop - character.getMovementSpeed()) / gp.tileSize;
        int characterBottomRow1 = (characterBottom + character.getMovementSpeed()) / gp.tileSize;
        int characterLeftCol1 = (characterLeft - character.getMovementSpeed()) / gp.tileSize;
        int characterRightCol1 = (characterRight + character.getMovementSpeed()) / gp.tileSize;
        if(maze[characterLeftCol][characterTopRow1]==5 || maze[characterLeftCol][characterTopRow1]==9 ){
            if(characterTopRow1!=0) {
                if (maze[characterLeftCol][characterTopRow1 - 1] == 5){
//                    maze[characterLeftCol][characterTopRow1 - 1] = 9;
//                    maze[characterLeftCol][characterTopRow1] = 9;
                    character.setDirection("up");
//                    System.out.println("up");
                }
            }
        }
        if(maze[characterLeftCol][characterBottomRow1]==5 || maze[characterLeftCol][characterBottomRow1]==9){
            if(maze[characterLeftCol][characterBottomRow1+1]==5){
//                maze[characterLeftCol][characterBottomRow1+1] = 9;
//                maze[characterLeftCol][characterBottomRow1] = 9;
                character.setDirection("down");
//                System.out.println("down");
            }
        }
        if(maze[characterLeftCol1][characterBottomRow]==5 || maze[characterLeftCol1][characterBottomRow]==9){
            if(maze[characterLeftCol1-1][characterBottomRow]==5){
//                maze[characterLeftCol1-1][characterBottomRow]=9;
//                maze[characterLeftCol1][characterBottomRow]=9;
                character.setDirection("left");
//                System.out.println("left");
            }

        }
        if(maze[characterRightCol1][characterBottomRow]==5 || maze[characterRightCol1][characterBottomRow]==9){
            if(maze[characterRightCol1+1][characterBottomRow]==5){
//                maze[characterRightCol1+1][characterBottomRow]=9;
//                maze[characterRightCol1][characterBottomRow]=9;
                character.setDirection("right");
//                System.out.println("right");
            }

        }
        StringBuilder result = new StringBuilder("\n");
        for(int row = 0; row< maze.length;row++){
            for(int column =0; column<maze[row].length;column++){
                result.append(maze[row][column]).append(" ");
            }
            result.append("\n");
        }
//        System.out.println(result);
        switch (character.getDirection()) {
            case "up" -> {
                characterTopRow = (characterTop - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = maze[characterLeftCol][characterTopRow];
                tileNum2 = maze[characterRightCol][characterTopRow];
                if (tileNum1 != 5 || tileNum2 != 5) {
                    character.collisionOn = true;
                }
            }
            case "down" -> {
                characterBottomRow = (characterBottom + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = maze[characterLeftCol][characterBottomRow];
                tileNum2 = maze[characterRightCol][characterBottomRow];
                if (tileNum1 != 5 || tileNum2 != 5) {
                    character.collisionOn = true;
                }
//                System.out.println(characterTopRow);
//                System.out.println(characterBottomRow);
            }
            case "left" -> {
                characterLeftCol = (characterLeft - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = maze[characterLeftCol][characterTopRow];
                tileNum2 = maze[characterLeftCol][characterBottomRow];
                if (tileNum1 != 5 || tileNum2 != 5) {
                    character.collisionOn = true;
                }
            }
            case "right" -> {
                characterRightCol = (characterRight + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = maze[characterRightCol][characterTopRow];
                tileNum2 = maze[characterRightCol][characterBottomRow];
                if (tileNum1 != 5 || tileNum2 != 5) {
                    character.collisionOn = true;
                }
            }
        }
//        System.out.println(character.collisionOn);
    }
    public int checkRoom (PlayerCharacter character) {
        int characterLeft = character.getxCoord() + character.solidArea.x;
        int characterRight = character.getxCoord() + character.solidArea.x + character.solidArea.width;
        int characterTop = character.getyCoord() + character.solidArea.y;
        int characterBottom = character.getyCoord() + character.solidArea.y + character.solidArea.height;

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 0) {
            if (characterBottom > 226 && characterBottom < 254) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(1);
                gp.tileM.update();
                return 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 1) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(0);
                gp.tileM.update();
                return 0;
            }
        }
        return gp.getCurrentRoomNum();
    }
}
