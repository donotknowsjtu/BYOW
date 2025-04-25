package main;

import entity.Direction;
import entity.Entity;
import entity.Player;

public class CollisionChecker {

    public GamePanel gp;
    private int pickUpCounter;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
        this.pickUpCounter = 0;

    }

    public void checkTile(Entity entity) {
        int leftX = entity.worldX + entity.solidArea.x;
        int rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int topY = entity.worldY + entity.solidArea.y;
        int bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = leftX / gp.tileSize;
        int entityRightCol = rightX / gp.tileSize;
        int entityTopRow = topY / gp.tileSize;
        int entityBottomRow = bottomY / gp.tileSize;


        int tileNum1, tileNum2;

        switch (entity.direction) {
            case Direction.UP:
                entityTopRow = (topY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManage.map[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManage.map[entityRightCol][entityTopRow];
                entity.collisionOn = gp.tileManage.tileType[tileNum1].collisonOn || gp.tileManage.tileType[tileNum2].collisonOn;
                break;
            case Direction.DOWN:
                entityBottomRow = (bottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManage.map[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManage.map[entityRightCol][entityBottomRow];
                entity.collisionOn = gp.tileManage.tileType[tileNum1].collisonOn || gp.tileManage.tileType[tileNum2].collisonOn;
                break;
            case Direction.LEFT:
                entityLeftCol = (leftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManage.map[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManage.map[entityLeftCol][entityBottomRow];
                entity.collisionOn = gp.tileManage.tileType[tileNum1].collisonOn || gp.tileManage.tileType[tileNum2].collisonOn;
                break;
            case Direction.RIGHT:
                entityRightCol = (rightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManage.map[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManage.map[entityRightCol][entityBottomRow];
                entity.collisionOn = gp.tileManage.tileType[tileNum1].collisonOn || gp.tileManage.tileType[tileNum2].collisonOn;
                break;
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;
        for (int i = 0; i < gp.objects.length; i++) {
            if (gp.objects[i] != null) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.objects[i].solidArea.x = gp.objects[i].worldX + gp.objects[i].solidArea.x;
                gp.objects[i].solidArea.y = gp.objects[i].worldY + gp.objects[i].solidArea.y;
                switch (entity.direction) {
                    case Direction.UP:
                        entity.solidArea.y -= entity.speed; break;
                    case Direction.DOWN:
                        entity.solidArea.y += entity.speed; break;
                    case Direction.LEFT:
                        entity.solidArea.x -= entity.speed; break;
                    case Direction.RIGHT:
                        entity.solidArea.x += entity.speed; break;
                }
                if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                    if (gp.objects[i].collisionOn) {
                        entity.collisionOn = true;
                    }
                    if (isPlayer) {
                        index = i;
                    }
                    pickUpCounter++;
                    if (pickUpCounter == 120) {
                        gp.ui.addMessage("按E拾取");
                        pickUpCounter = 0;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
                gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
                switch (entity.direction) {
                    case Direction.UP:
                        entity.solidArea.y -= entity.speed;
                        break;
                    case Direction.DOWN:
                        entity.solidArea.y += entity.speed;
                        break;
                    case Direction.LEFT:
                        entity.solidArea.x -= entity.speed;
                        break;
                    case Direction.RIGHT:
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[i].solidArea) && target[i] != entity) {
                    entity.collisionOn = true;
                    index = i;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;

            }
        }
        return index;
    }



    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        switch (entity.direction) {
            case Direction.UP:
                entity.solidArea.y -= entity.speed; break;
            case Direction.DOWN:
                entity.solidArea.y += entity.speed; break;

            case Direction.LEFT:
                entity.solidArea.x -= entity.speed; break;

            case Direction.RIGHT:
                entity.solidArea.x += entity.speed; break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }


}