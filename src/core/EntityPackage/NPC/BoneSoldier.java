package core.EntityPackage.NPC;

import core.EntityPackage.Entity;
import core.EntityPackage.Player;
import core.GUI.MazeModeJFrame.JPanel.GamePanel;
import java.util.List;
import core.FindPath.AStart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * npc实体：骷髅兵
 * 功能：
 * 1.在指定范围内射箭
 * 2.向玩家行走
 */
public class BoneSoldier extends Entity {
    /**
     * 指针，指向调用BoneSoldier的GamePanel
     */
    GamePanel gp;
    Player player;

    //状态枚举
    private enum State { PATROLLING, CHASING }
    private State currentState = State.PATROLLING;

    //新增感知范围
    private final int detection_range = 5 * gp.tileSize; //5格距离
    private final int lose_range = 8 * gp.tileSize;

    private List<Point> currentPath = new ArrayList<>();

    private static final Random random = new Random();
    int[] player_location = new int[2];


    // 巡逻系统
    private int patrolIndex = 0;
    private final Point[] patrolPoints = {
            new Point(54, 80),   // 初始位置
            new Point(84, 80),
            new Point(84, 100),
            new Point(54, 100)
    };


    public BoneSoldier(GamePanel gp){
        this.gp = gp;
        this.direction = "up";
        CollisionRect = new Rectangle(3,6,6,6);

        getBoneSoldierImage();
        setDefaultValue();
    }

    private Point worldToGrid(Point worldPos) {
        return new Point(worldPos.x / gp.tileSize, worldPos.y / gp.tileSize);
    }

    private Point gridToWorld(Point gridPos) {
        return new Point(gridPos.x * gp.tileSize + gp.tileSize/2, gridPos.y * gp.tileSize + gp.tileSize/2);
    }


    /**
     * 设置初始位置
     */
    private void setDefaultValue(){
        x = 54;
        y = 80;
        speed = 2;
    }

    private void getBoneSoldierImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/BoneSoldier.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private List<Point> findPath(Point targetWorld) {
        Point startGrid = worldToGrid(new Point(x, y));
        Point targetGrid = worldToGrid(targetWorld);

        List<Point> gridPath = AStart.find_path(
                startGrid.x, startGrid.y,
                targetGrid.x, targetGrid.y,
                (x, y) -> !gp.gg.MT[y][x].isCollision  // 碰撞检测
        );

        // 转换为世界坐标
        List<Point> worldPath = new ArrayList<>();
        for (Point p : gridPath) {
            worldPath.add(gridToWorld(p));
        }
        return smoothPath(worldPath);  // 路径平滑处理
    }

    private List<Point> smoothPath(List<Point> path) {
        if (path.size() < 3) return path;

        List<Point> newPath = new ArrayList<>();
        newPath.add(path.get(0));

        for (int i = 1; i < path.size()-1; i++) {
            Point prev = path.get(i-1);
            Point next = path.get(i+1);
            if (!hasDirectPath(prev, next)) {
                newPath.add(path.get(i));
            }
        }
        newPath.add(path.get(path.size()-1));
        return newPath;
    }

    // 判断直线可达性
    private boolean hasDirectPath(Point a, Point b) {
        int steps = Math.max(Math.abs(b.x - a.x), Math.abs(b.y - a.y));
        float dx = (b.x - a.x) / (float)steps;
        float dy = (b.y - a.y) / (float)steps;

        for (int i = 0; i <= steps; i++) {
            int checkX = (int)(a.x + dx * i);
            int checkY = (int)(a.y + dy * i);
            if (gp.gg.MT[checkY/gp.tileSize][checkX/gp.tileSize].isCollision) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据玩家的位置移动
     */
    public void update(Player player){
        updateState(player);

        switch (currentState) {
            case PATROLLING:
                handlePatrol();
                break;
            case CHASING:
                handleChasing(player);
                break;
        }
    }

    // 状态更新
    private void updateState(Player player) {
        double distance = Point.distance(x, y, player.x, player.y);

        if (currentState == State.PATROLLING && distance < detection_range) {
            currentState = State.CHASING;
            currentPath = findPath(new Point(player.x, player.y));
        } else if (currentState == State.CHASING && distance > lose_range) {
            currentState = State.PATROLLING;
            currentPath.clear();
        }
    }


    // 巡逻处理
    private void handlePatrol() {
        if (currentPath.isEmpty()) {
            Point target = patrolPoints[patrolIndex];
            currentPath = findPath(target);
            patrolIndex = (patrolIndex + 1) % patrolPoints.length;
        }
        followPath();
    }

    // 追击处理
    private void handleChasing(Player player) {
        currentPath = findPath(new Point(player.x, player.y));

        followPath();
    }

    // 路径跟随
    private void followPath() {
        if (!currentPath.isEmpty()) {
            Point target = currentPath.get(0);

            // 计算移动方向
            int dx = target.x - x;
            int dy = target.y - y;
            double distance = Math.hypot(dx, dy);

            // 到达当前路径点
            if (distance < speed) {
                currentPath.remove(0);
                return;
            }

            // 计算移动量
            double ratio = speed / distance;
            int moveX = (int)(dx * ratio);
            int moveY = (int)(dy * ratio);

            // 尝试移动
            int oldX = x, oldY = y;
            x += moveX;
            y += moveY;

            // 碰撞检测
            gp.CC.checker(this);
            if (isCollision) {
                x = oldX;
                y = oldY;
                currentPath.clear();  // 路径受阻，重新计算
            }

            // 更新方向
            updateDirection(dx, dy);
        }
    }


    // 更新面向方向
    private void updateDirection(int dx, int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            direction = dx > 0 ? "right" : "left";
        } else {
            direction = dy > 0 ? "down" : "up";
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(up1, x, y, gp.tileSize, gp.tileSize, null);
    }

}
