package core.WorldPackage;


import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Road {
    private List<Point> room_center_points;
    private TETile[][] tiles;
    private Random RandomSeed;
    private int LENGTH, WIDTH;

    public Road(WorldTiles worldTiles){
        this.tiles = worldTiles.tiles;
        this.RandomSeed = worldTiles.RandomSeed;
        this.LENGTH = worldTiles.LENGTH;
        this.WIDTH = worldTiles.WIDTH;
        this.room_center_points = new ArrayList<>();
        // 将rooms列表中的room的中心点全部读取并分别存储到room_center_points列表里
        for(Room room : worldTiles.rooms){

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
            if(RandomSeed.nextInt(100) < 50){
                if(direction[0] != 0){
                    newPoint1.x += direction[0];
                    tiles[newPoint1.x][newPoint1.y] = Tileset.FLOOR;
                    ConstructWALLThroughLENGTH(direction[0], newPoint1.x, newPoint1.y);
                }
            }else{
                if(direction[1] != 0){
                    newPoint1.y += direction[1];
                    tiles[newPoint1.x][newPoint1.y] = Tileset.FLOOR;
                    ConstructWALLThroughWIDTH(direction[1], newPoint1.x, newPoint1.y);
                }
            }
            direction[0] = (int) Math.signum(point2.x - newPoint1.x);
            direction[1] = (int) Math.signum(point2.y - newPoint1.y);
        }
    }

    /**
     * 左右移动
     * @param direction
     * @param x
     * @param y
     */
    private void ConstructWALLThroughLENGTH(int direction, int x, int y) {
        if(direction < 0){
            // 向左建墙
            if(x > 0 && tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            // 向上建墙
            if(y < WIDTH - 1 && tiles[x][y + 1] != Tileset.FLOOR){tiles[x][y + 1] = Tileset.WALL;}
            // 向下建墙
            if(y > 0 && tiles[x][y - 1] != Tileset.FLOOR){tiles[x][y - 1] = Tileset.WALL;}
        }else{
            // 向右建墙
            if(x < LENGTH - 1 && tiles[x + 1][y] != Tileset.FLOOR){tiles[x + 1][y] = Tileset.WALL;}
            // 向上建墙
            if(y < WIDTH - 1 && tiles[x][y + 1] != Tileset.FLOOR){tiles[x][y + 1] = Tileset.WALL;}
            // 向下建墙
            if(y > 0 && tiles[x][y - 1] != Tileset.FLOOR){tiles[x][y - 1] = Tileset.WALL;}
        }
    }

    /**
     * 上下移动
     * @param direction
     * @param x
     * @param y
     */
    private void ConstructWALLThroughWIDTH(int direction, int x, int y){
        if(direction < 0){
            // 向下建墙
            if(y > 0 && tiles[x][y - 1] != Tileset.FLOOR){tiles[x][y - 1] = Tileset.WALL;}
            // 向左建墙
            if(x > 0 && tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            // 向右建墙
            if(x < LENGTH - 1 && tiles[x + 1][y] != Tileset.FLOOR){tiles[x + 1][y] = Tileset.WALL;}
        }else{
            // 向上建墙
            if(y < WIDTH - 1 && tiles[x][y + 1] != Tileset.FLOOR){tiles[x][y + 1] = Tileset.WALL;}
            // 左侧建墙
            if(x > 0 && tiles[x - 1][y] != Tileset.FLOOR){tiles[x - 1][y] = Tileset.WALL;}
            // 右侧建墙
            if(x < LENGTH - 1 && tiles[x + 1][y] != Tileset.FLOOR){tiles[x + 1][y] = Tileset.WALL;}
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