package main;

import entity.Direction;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    Boolean canTouchEvent;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col ++;

            if(col == gp.maxWorldCol){
                col = 0;
                row ++;
            }
        }
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = true;
    }
    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent) {
            if (hit(23, 23, Direction.RIGHT)) {
                damagePit(23, 23, gp.dialogueState);
            }
            if (hit(23, 24, Direction.RIGHT)) {
                healingPool(23, 24, gp.dialogueState);
            }
            if (hit(23, 25, Direction.RIGHT)) {
                teleport(23, 25, gp.dialogueState);
            }
        }
    }
    public boolean hit(int eventCol, int eventRow, int reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[eventCol][eventRow].x = eventCol * gp.tileSize + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow * gp.tileSize + eventRect[eventCol][eventRow].y;

        if(gp.player.solidArea.intersects(eventRect[eventCol][eventRow]) && !eventRect[eventCol][eventRow].eventDone){
//            if(gp.player.direction == reqDirection || reqDirection == -1){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
//            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;
        return hit;
    }
    private void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall in a pit!";
        gp.playSE(6);
//        eventRect[col][row].eventDone = true;
        gp.player.life -= 1;
        if(gp.player.life < 0){
            gp.player.life = 0;
        }
        canTouchEvent = false;

    }
    private void healingPool(int col, int row, int gameState){
        if(gp.keyHandler.Enter_pressed){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            gp.ui.currentDialogue = "You drink the water. \nYour life has been recovered.";
//            eventRect[col][row].eventDone = true;
            gp.player.life += 1;
        }
        canTouchEvent = false;

    }
    public void teleport(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
//        eventRect[col][row].eventDone = true;
        gp.player.worldX = 19 * gp.tileSize;
        gp.player.worldY = 19 * gp.tileSize;
        canTouchEvent = false;



    }
}
