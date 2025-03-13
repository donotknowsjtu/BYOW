package core.Collisionchecker;

import core.EntityPackage.Entity;
import core.EntityPackage.MapTile;
import core.GUI.MazeModeJFrame.JPanel.GamePanel;
import core.WorldPackage.WorldGeneration;

public class CollisionChecker {
    public GamePanel gp; // 游戏面板对象，用于获取游戏界面的相关信息
    public WorldGeneration gg; // 游戏生成器对象，用于获取地图数据

    // 构造函数，初始化碰撞检测器
    public CollisionChecker(GamePanel gp, WorldGeneration gg){
        this.gp = gp;
        this.gg = gg;
    }

    // 碰撞检测方法，检测实体是否与地图中的障碍物发生碰撞
    public void checker(Entity entity){
        // 计算实体的碰撞矩形边界坐标
        int EntityLeft_x = entity.x + entity.CollisionRect.x; // 实体碰撞矩形的左边界x坐标
        int EntityRight_x = entity.x + entity.CollisionRect.x + entity.CollisionRect.width; // 实体碰撞矩形的右边界x坐标
        int EntityUP_y = entity.y + entity.CollisionRect.y; // 实体碰撞矩形的上边界y坐标
        int EntityDown_y = entity.y + entity.CollisionRect.y + entity.CollisionRect.height; // 实体碰撞矩形的下边界y坐标

        // 计算实体当前所在的行和列（基于地图的网格系统）
        int EntityLeftCol = EntityLeft_x / gp.tileSize; // 实体左边界所在的列
        int EntityRightCol = EntityRight_x / gp.tileSize; // 实体右边界所在的列
        int EntityUpRow = EntityUP_y / gp.tileSize; // 实体上边界所在的行
        int EntityDownRow = EntityDown_y / gp.tileSize; // 实体下边界所在的行

        MapTile Tile1, Tile2; // 用于存储实体可能碰撞的两个地图块

        // 根据实体的移动方向进行碰撞检测
        switch (entity.direction){
            case "up": // 如果实体向上移动
                EntityUpRow = (EntityUP_y - entity.speed) / gp.tileSize; // 计算实体移动后的上边界所在的行

                // 获取实体左上角和右上角可能碰撞的地图块
                Tile1 = gg.MT[EntityLeftCol][EntityUpRow];
                Tile2 = gg.MT[EntityRightCol][EntityUpRow];
                // 如果任一地图块有碰撞属性，则设置实体的碰撞状态为true
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "down": // 如果实体向下移动
                EntityDownRow = (EntityDown_y + entity.speed) / gp.tileSize; // 计算实体移动后的下边界所在的行

                // 获取实体左下角和右下角可能碰撞的地图块
                Tile1 = gg.MT[EntityLeftCol][EntityDownRow];
                Tile2 = gg.MT[EntityRightCol][EntityDownRow];
                // 如果任一地图块有碰撞属性，则设置实体的碰撞状态为true
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "left": // 如果实体向左移动
                EntityLeftCol = (EntityLeft_x - entity.speed) / gp.tileSize; // 计算实体移动后的左边界所在的列

                // 获取实体左上角和左下角可能碰撞的地图块
                Tile1 = gg.MT[EntityLeftCol][EntityUpRow];
                Tile2 = gg.MT[EntityLeftCol][EntityDownRow];
                // 如果任一地图块有碰撞属性，则设置实体的碰撞状态为true
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
            case "right": // 如果实体向右移动
                EntityRightCol = (EntityRight_x + entity.speed) / gp.tileSize; // 计算实体移动后的右边界所在的列

                // 获取实体右上角和右下角可能碰撞的地图块
                Tile1 = gg.MT[EntityRightCol][EntityUpRow];
                Tile2 = gg.MT[EntityRightCol][EntityDownRow];
                // 如果任一地图块有碰撞属性，则设置实体的碰撞状态为true
                if (Tile1.collision || Tile2.collision) {
                    entity.isCollision = true;
                }
                break;
        }
    }
}