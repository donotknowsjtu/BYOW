package core.FindPath;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStart {
    private static class Node implements Comparable<Node>{
        final int x;
        final int y;
        Node parent;
        int gCOST;
        int hCOST;

        Node(int x, int y){
            this.x = x;
            this.y = y;
        }
        int Cost(){
            return gCOST + hCOST;
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.Cost(), other.Cost());
        }
        @Override
        public boolean equals(Object o){
            if (this == o){
                return true;
            }
            if (o == null || getClass() != o.getClass()){
                return false;
            }
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
    }

    public interface NodeChecker{
        boolean isWalkable(int x , int y);
    }

    public static List<Point> find_path(int start_x, int start_y, int target_x, int target_y, NodeChecker nodeChecker){
        Node startNode = new Node(start_x,start_y);
        Node targetNode = new Node(target_x,target_y);

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Map<Node, Node> allNode = new HashMap<>();

        openList.add(startNode);
        allNode.put(startNode, startNode);

        while (!openList.isEmpty()){
            Node currentNode = openList.poll();
            if(currentNode.equals(targetNode)){
                return reconstructPath(currentNode);
            }
            //搜索附近节点
            for (int dx = -1; dx <= 1; dx++){
                for (int dy = -1; dy <= 1; dy++){
                    if(dx == 0 && dy ==0){
                        continue;
                    }
                    int neighborX = currentNode.x + dx;
                    int neighborY = currentNode.y + dy;
                    if(!nodeChecker.isWalkable(neighborX, neighborY)){
                        continue;
                    }
                    Node neighborNode = new Node(neighborX , neighborY);
                    int TryNodeCost = currentNode.gCOST + ((dx == 0 || dy == 0)? 10 : 14);

                    if(!allNode.containsKey(neighborNode) || TryNodeCost < allNode.get(neighborNode).gCOST){
                        neighborNode.parent = currentNode;
                        neighborNode.gCOST = TryNodeCost;
                        neighborNode.hCOST = heuristic(neighborNode,targetNode);

                        if(!allNode.containsKey(neighborNode)){
                            openList.add(neighborNode);
                            allNode.put(neighborNode,neighborNode);
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private static int heuristic(Node n1 , Node n2){
        return (Math.abs(n1.x - n2.x) + Math.abs(n1.y - n2.y)) * 10;
    }

    private static List<Point> reconstructPath(Node endNode){
        List<Point> path = new ArrayList<>();
        Node current = endNode;
        while (current != null){
            path.add(0, new Point(current.x , current.y));
            current = current.parent;
        }
        return path;
    }
}
