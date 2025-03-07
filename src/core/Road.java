package core;


import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Road {
    List<Point> room_center_points;
//    HashMap<Point, Integer> not_linked_points;

    public Road(WorldGeneration worldGeneration){
        // 将rooms列表中的room的中心点全部读取并分别存储到room_center_points列表里
        for(Room room : worldGeneration.rooms){
            room_center_points.add(room.centerPoint);
//            not_linked_points.add(room.centerPoint, );
        }

    }
    // 计算每个

    // 将每一个房间的中心点都连接在一起，已经连接的房间
    public void RoadGenerae(){

    }



}
