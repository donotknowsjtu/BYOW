package core;


import org.junit.jupiter.api.Test;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.HashMap;
import java.util.*;
import java.util.List;

public class Road {
    private List<Point> room_center_points;
    private TETile[][] tiles;
    private Random RandomSeed;

    public Road(WorldGeneration worldGeneration){
        this.tiles = worldGeneration.tiles;
        this.RandomSeed = worldGeneration.RandomSeed;
        this.room_center_points = new ArrayList<>();
        // 将rooms列表中的room的中心点全部读取并分别存储到room_center_points列表里
        for(Room room : worldGeneration.rooms){

            this.room_center_points.add(room.centerPoint);
        }

        // 对列表进行排序
        sortByManhattanDistance(room_center_points);

    }

    /**
     * 计算并重组列表 @room_center_points，使用了优先队列优化了算法，使得时间复杂度由n的平方降低到了对数级
     */
    private void sortByManhattanDistance(List<Point> points) {
        if (points == null || points.size() <= 1) {
            return;
        }

        int n = points.size();
        boolean[] visited = new boolean[n];
        visited[0] = true;

        for (int i = 0; i < n - 1; i++) {
            Point currentPoint = points.get(i);
            PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(
                    p -> calculateManhattanDistance(currentPoint, p)
            ));

            // 将所有未访问的点加入优先队列
            for (int j = i + 1; j < n; j++) {
                if (!visited[j]) {
                    pq.add(points.get(j));
                }
            }

            // 找到最小距离的点并进行调换位置
            if (!pq.isEmpty()) {
                Point minPoint = pq.poll();
                int minIndex = points.indexOf(minPoint);
                if (minIndex != i + 1) {
                    swap(points, i + 1, minIndex);
                }
                visited[i + 1] = true;
            }
        }
    }
    /** 计算两个中心点之间的曼哈顿距离
     * **/
    private int calculateManhattanDistance(Point point1, Point point2){
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y -  point2.y);
    }
    /**
     * 调换point列表中指定两个位置i, j处的点
     * @param points
     * @param i
     * @param j
     */
    private void swap(List<Point> points, int i, int j) {
        Point temp = points.get(i);
        points.set(i, points.get(j));
        points.set(j, temp);
    }


    /**
     * 将两个点连接起来
     */
    private void linkPoints(Point point1, Point point2 ){
        Point newPoint1 = point1;
        int[] direction = new int[]{(int) Math.signum(point2.x - newPoint1.x), (int) Math.signum(point2.y - newPoint1.y)};
        while(newPoint1.x != point2.x || newPoint1.y != point2.y){
            // 通过seed决定左右还是上下移动，概率五五开
            if(RandomSeed.nextInt(100) < 50){
                // 当newPoint1运动到point2的正下方或者正上方时停止左右运动，防止出现回环的路,同时也避免了撞墙bug
                if(direction[0] == 0){
                    continue;
                }
                // 左右移动
                newPoint1.x += direction[0];
                // 将新点铺上地板
                tiles[newPoint1.x][newPoint1.y] = Tileset.FLOOR;
                // 修改tiles, 添加或删除墙体
                ConstructWALLThroughWidth(direction[0], newPoint1.x, newPoint1.y);
            }else{
                // 当newPoint1运动到point2的正左方或者正右方时停止上下运动，防止出现回环的路，同时也避免了撞墙bug
                if(direction[1] == 0){
                    continue;
                }
                // 左右移动
                newPoint1.x += direction[1];
                // 修改tiles, 添加或删除墙体
                ConstructWALLThroughLength(direction[1], newPoint1.x, newPoint1.y);
            }
        }
    }

    /**
     * 左右移动
     * @param direction
     * @param x
     * @param y
     */
    private void ConstructWALLThroughWidth(int direction, int x, int y) {
        if(direction < 0){
            if(tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(y < tiles[0].length - 1 && tiles[x][y + 1] != Tileset.FLOOR){tiles[x][y + 1] = Tileset.WALL;}
            if(y > 0 && tiles[x][y - 1] != Tileset.FLOOR){tiles[x][y - 1] = Tileset.WALL;}
        }else{
            if(tiles[x + 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(y < tiles[0].length - 1 && tiles[x][y + 1] != Tileset.FLOOR){tiles[x][y + 1] = Tileset.WALL;}
            if(y > 0 && tiles[x][y - 1] != Tileset.FLOOR){tiles[x][y - 1] = Tileset.WALL;}
        }
    }

    /**
     * 上下移动
     * @param direction
     * @param x
     * @param y
     */
    private void ConstructWALLThroughLength(int direction, int x, int y){
        if(direction < 0){
            if(tiles[x][y - 1] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(x > 0 && tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(x < tiles.length && tiles[x + 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
        }else{
            if(tiles[x][y + 1] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(x > 0 && tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            if(x < tiles.length && tiles[x + 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
    }
}

    /**
     * 将距离最近的房间的中心点连接起来
     */
    public void RoadGenerate(Random randomSeed) {
        for (int i = 0; i < room_center_points.size() - 1; i++) {
            linkPoints(room_center_points.get(i), room_center_points.get(i + 1));
        }
    }
}
