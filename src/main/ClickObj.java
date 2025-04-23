package main;

import java.awt.*;

public class ClickObj {
    public Rectangle obj;
    public boolean touched;
    public boolean clicked;
    public ClickObj(int x, int y, int length, int height){
        touched = false;
        clicked = false;
        obj = new Rectangle(x, y, length, height);
    }
}
