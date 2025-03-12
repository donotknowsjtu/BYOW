package core.Collisionchecker;

import core.EntityPackage.Entity;
import core.EntityPackage.MapTile;
import core.GUI.GamePanel;
import core.GameGeneration.gamegeneration;

public class CollisionChecker {
    public GamePanel gp;
    public gamegeneration gg;
    public CollisionChecker(GamePanel gp, gamegeneration gg){
        this.gp = gp;
        this.gg = gg;
    }
    public void checker(Entity entity){
        int EntityLeft_x = entity.x + entity.CollisionRect.x;
        int EntityRight_x = entity.x + entity.CollisionRect.x + entity.CollisionRect.width;
        int EntityUP_y = entity.y + entity.CollisionRect.y;
        int EntityDown_y = entity.y + entity.CollisionRect.y + entity.CollisionRect.height;

        int EntityLeftCol = EntityLeft_x / gp.tileSize;
        int EntityRightCol = EntityRight_x / gp.tileSize;
        int EntityUpRow = EntityUP_y / gp.tileSize;
        int EntityDownRow = EntityDown_y / gp.tileSize;

        MapTile Tile1, Tile2;
        switch (entity.direction){
            case "up":
                EntityUpRow = (EntityUP_y - entity.speed) / gp.tileSize;

                Tile1 = gg.MT[EntityLeftCol][EntityUpRow];
                Tile2 = gg.MT[EntityRightCol][EntityUpRow];
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "down":
                EntityDownRow = (EntityDown_y + entity.speed) / gp.tileSize;

                Tile1 = gg.MT[EntityLeftCol][EntityDownRow];
                Tile2 = gg.MT[EntityRightCol][EntityDownRow];
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "left":
                EntityLeftCol = (EntityLeft_x - entity.speed) / gp.tileSize;

                Tile1 = gg.MT[EntityLeftCol][EntityUpRow];
                Tile2 = gg.MT[EntityLeftCol][EntityDownRow];
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "right":
                EntityRightCol = (EntityRight_x + entity.speed) / gp.tileSize;

                Tile1 = gg.MT[EntityRightCol][EntityUpRow];
                Tile2 = gg.MT[EntityRightCol][EntityDownRow];
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
        }
    }
}
