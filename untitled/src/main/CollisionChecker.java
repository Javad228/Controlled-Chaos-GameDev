package main;

import character.Character;
import character.Enemy;
import character.PlayerCharacter;
import loot.Loot;
import tile.NextLevelTile;
import tile.Tile;
import tile.TileManager;

public class CollisionChecker {
    static final int trapDamage = 2;
    static int freezeCounter = 0;

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

    public boolean checkLootApproach(Character entity, Loot target) {
        if (target == null) return false;

        entity.collisionOn = false;
        entity.solidArea.x = entity.xCoord;
        entity.solidArea.y = entity.yCoord;

        int entityDefaultWidth = entity.solidArea.width;
        int entityDefaultHeight = entity.solidArea.height;

        entity.solidArea.width = 2 * gp.tileSize;
        entity.solidArea.height = 2 * gp.tileSize;

        target.solidArea.x = target.getxCoord();
        target.solidArea.y = target.getyCoord();

        int targetDefaultWidth = target.solidArea.width;
        int targetDefaultHeight = target.solidArea.height;

        target.solidArea.width = 2 * gp.tileSize;
        target.solidArea.height = 2 * gp.tileSize;

        if (entity.solidArea.intersects(target.solidArea)) {
            entity.collisionOn = true;
        }

        entity.solidArea.y = entity.collisionAreaDefaultY;
        entity.solidArea.x = entity.collisionAreaDefaultX;
        entity.solidArea.width = entityDefaultWidth;
        entity.solidArea.height = entityDefaultHeight;

        target.solidArea.x = target.collisionAreaDefaultX;
        target.solidArea.y = target.collisionAreaDefaultY;
        target.solidArea.width = targetDefaultWidth;
        target.solidArea.height = targetDefaultHeight;

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
                checkTileCollisionsAndRoom(character, tileNum1, tileNum2);
                checkNextLevelTileExistence(character, characterLeftCol, characterTopRow);
                checkNextLevelTileExistence(character, characterRightCol, characterTopRow);
                /*if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                    character.collisionOn = true;
                    if (gp.tileM.tile[tileNum1].getTileType() == Tile.DOOR2 || gp.tileM.tile[tileNum2].getTileType() == Tile.DOOR2) {
                        System.out.println("just collided with door!");
                    }
                }*/
            }
            case "down" -> {
                characterBottomRow = (characterBottom + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                checkTileCollisionsAndRoom(character, tileNum1, tileNum2);
                checkNextLevelTileExistence(character, characterLeftCol, characterBottomRow);
                checkNextLevelTileExistence(character, characterRightCol, characterBottomRow);
                /*if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                    character.collisionOn = true;
                    if (gp.tileM.tile[tileNum1].getTileType() == Tile.DOOR2 || gp.tileM.tile[tileNum2].getTileType() == Tile.DOOR2) {
                        System.out.println("just collided with door!");
                    }
                }*/
            }
            case "left" -> {
                characterLeftCol = (characterLeft - character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                checkTileCollisionsAndRoom(character, tileNum1, tileNum2);
                checkNextLevelTileExistence(character, characterLeftCol, characterTopRow);
                checkNextLevelTileExistence(character, characterLeftCol, characterBottomRow);
                /*if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                    character.collisionOn = true;
                    if (gp.tileM.tile[tileNum1].getTileType() == Tile.DOOR2 || gp.tileM.tile[tileNum2].getTileType() == Tile.DOOR2) {
                        System.out.println("just collided with door!");
                    }
                }*/
            }
            case "right" -> {
                characterRightCol = (characterRight + character.getMovementSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                checkTileCollisionsAndRoom(character, tileNum1, tileNum2);
                checkNextLevelTileExistence(character, characterRightCol, characterTopRow);
                checkNextLevelTileExistence(character, characterRightCol, characterBottomRow);
                /*if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                    character.collisionOn = true;
                    if (gp.tileM.tile[tileNum1].getTileType() == Tile.DOOR2 || gp.tileM.tile[tileNum2].getTileType() == Tile.DOOR2) {
                        System.out.println("just collided with door!");
                    }
                }*/
            } default -> {
                System.out.println("Error with block collision.");
                return;
            }
        }

        //checkTileCollisionsAndRoom(character, tileNum1, tileNum2);
        /*if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            character.collisionOn = true;
        }*/
//        System.out.println(character.collisionOn);
    }

    public void checkTileCollisionsAndRoom(Character character, int tileNum1, int tileNum2) {
        // check if we should stop given collisions with barriers or if we have hit a door
        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            character.collisionOn = true;
            // check if character is not enemy before checking room... otherwise enemies can get in other rooms...
            if (!(character instanceof Enemy)
                    && gp.tileM.tile[tileNum1].getTileType() == Tile.DOOR2
                    || gp.tileM.tile[tileNum2].getTileType() == Tile.DOOR2) {
                System.out.println("just collided with door!");
                // as a rule, doors to go back should be on the left side of the screen
                if (character.getxCoord() < gp.maxScreenCol * gp.tileSize / 2) {
                    // we want to go back!
                    if (gp.getCurrentRoomNum() == 8) {
                        gp.setCurrentRoomNum(4);
                    } else {
                        gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                    }
                    System.out.println(gp.getCurrentRoomNum());
                    gp.tileM.backward = true;
                    gp.tileM.update();
                } else {
                    // we want to go forward!
                    if (gp.getCurrentRoomNum() == 8) {
                        gp.setCurrentRoomNum(5);
                    //} else if (gp.getCurrentRoomNum() == 6) {
                        //go back to main menu?
                    } else {
                        gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                    }
                    System.out.println(gp.getCurrentRoomNum());
                    gp.tileM.backward = false;
                    gp.tileM.update();
                }
            }

            /*
            if (character.getxCoord() < gp.maxScreenCol * gp.tileSize / 2) {
                // we want to go back!
                gp.tileM.backward = true;
                if (gp.getCurrentRoomNum() == 7) {
                    //take us to room 4 from the hidden room
                    gp.setCurrentRoomNum(4);
                } else {
                    gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                    System.out.println(gp.getCurrentRoomNum());
                }
            } else {
                // we want to go forward!
                gp.tileM.backward = false;
                if (gp.getCurrentRoomNum() == 7) {
                    //take us to room 5 from the hidden room
                    gp.setCurrentRoomNum(5);
                } else {
                    gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                    System.out.println(gp.getCurrentRoomNum());
                }
            }
            gp.tileM.update();

            */
        } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.GRASSROOM && (gp.tileM.tile[tileNum1].getTileType() == Tile.ENVIRONMENT || gp.tileM.tile[tileNum2].getTileType() == Tile.ENVIRONMENT)) {
            character.movementSpeed = character.maxSpeed / 2;
        } else if (gp.getRooms().get(gp.getCurrentRoomNum()).getRoomType() == Room.ICEROOM && (gp.tileM.tile[tileNum1].getTileType() == Tile.ENVIRONMENT || gp.tileM.tile[tileNum2].getTileType() == Tile.ENVIRONMENT)) {
            if (freezeCounter <= gp.getFps()) { // should freeze character for a second
                freezeCounter++;
                character.movementSpeed = 0;
            } else { // after a second, reset the counter
                freezeCounter = 0;
                character.movementSpeed = character.maxSpeed;
            }
        } else {
            character.movementSpeed = character.maxSpeed;
        }
    }

    public void checkNextLevelTileExistence(Character character, int tileX, int tileY) {
        if (!(character instanceof Enemy
                && Main.view.getGamePanel().levelComplete)
                && (TileManager.tile[TileManager.mapTileNum[tileX][tileY]].getTileType() == Tile.NEXTLEVEL)) {
            int candidate = ((NextLevelTile) TileManager.tile[1]).findTile(tileX, tileY);
            switch (candidate) {
                case NextLevelTile.NEXTLEVEL_REG -> {
                    gp.nextLevel();
                }
                case NextLevelTile.NEXTLEVEL_DIFFICULT -> {
                    gp.getPlayer().incrementDifficulty();
                    gp.nextLevel();
                }
                case NextLevelTile.NEXTLEVEL_INVALID -> {/*Do nothing*/}
                default -> System.out.println("CollisionChecker: NextLevel findTile() produced bad output!");
            }
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

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 1) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() + 1;
            }
        }

        if (characterLeft > 55 && characterLeft < 75 && gp.getCurrentRoomNum() == 2) {
            if (characterBottom > 70 && characterBottom < 100) {
                //System.out.println("it's in");
                gp.tileM.backward = true;
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() - 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 2) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() + 1;
            }
        }

        if (characterLeft > 55 && characterLeft < 75 && gp.getCurrentRoomNum() == 3) {
            if (characterBottom > 70 && characterBottom < 100) {
                //System.out.println("it's in");
                gp.tileM.backward = true;
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() - 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 3) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() + 1;
            }
        }

        if (characterLeft > 55 && characterLeft < 75 && gp.getCurrentRoomNum() == 4) {
            if (characterBottom > 70 && characterBottom < 100) {
                //System.out.println("it's in");
                gp.tileM.backward = true;
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() - 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 4) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() + 1;
            }
        }

        if (characterLeft > 616 && characterLeft < 672 && gp.getCurrentRoomNum() == 5) {
            if (characterBottom > 376 && characterBottom < 406) {
                //System.out.println("it's in");
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() + 1);
                gp.tileM.update();
                return gp.getCurrentRoomNum() + 1;
            }
        }


        if (characterLeft > 55 && characterLeft < 75 && gp.getCurrentRoomNum() == 6) {
            if (characterBottom > 70 && characterBottom < 100) {
                //System.out.println("it's in");
                gp.tileM.backward = true;
                gp.setCurrentRoomNum(gp.getCurrentRoomNum() - 1);
                //gp.tileM.update();
                return gp.getCurrentRoomNum() - 1;
            }
        }
        return gp.getCurrentRoomNum();
    }
}
