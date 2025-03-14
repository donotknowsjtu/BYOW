package core.WorldPackage;

import java.awt.*;

public class Room {
    public int r_x, r_y, r_width, r_height;
    public Point centerPoint;

    public Room(int r_x, int r_y, int r_width, int r_height){
        this.r_width = r_width;
        this.r_height = r_height;
        this.r_x = r_x;
        this.r_y = r_y;
        // 添加了获取中心点的部分
        this.centerPoint = get_center();
    }

    /*判断房间是否重叠*/
    public boolean is_intersect(Room room){
        return r_x < room.r_x + room.r_width &&
                r_y < room.r_y + room.r_height &&
                r_x + r_width > room.r_x &&
                r_y + r_height >room.r_y;
    }

    /*获取房间中心*/
    public Point get_center(){
        return new Point((int)Math.floor(r_x + r_width / 2.0), (int)Math.floor(r_y + r_height / 2.0));
    }
}
