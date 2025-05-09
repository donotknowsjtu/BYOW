package main;

import entity.Entity;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MouseHandler extends MouseInputAdapter {
    private GamePanel gp;
    private Point mousePoint;
    private BufferedImage image;
    public int touchedObjNum;
    public int clickedObjNum;

    public MouseHandler(GamePanel gp){
        this.gp = gp;
        this.mousePoint = new Point(0, 0);
        this.image = getMouseImage();
        touchedObjNum = 0;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // 处理鼠标按下
        mousePoint = e.getPoint();
        // 处理鼠标触碰

        for (int i = 0; i < gp.ui.toBeClickedObj.size(); i++) {
            if (gp.ui.toBeClickedObj.get(i).obj.contains(mousePoint)) {

                clickedObjNum = i;
                gp.ui.commandNum = i;
                if(gp.gameState == gp.titleState){titleState();}
                if(gp.gameState == gp.playState){gameState();}
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // 处理鼠标移动
        mousePoint = e.getPoint();
        // 处理鼠标触碰

        for(int i = 0; i < gp.ui.toBeClickedObj.size(); i ++){
            if(gp.ui.toBeClickedObj.get(i).obj.contains(mousePoint)){


                if(touchedObjNum != i){
                    gp.playSE(8);
                }
                touchedObjNum = i;
                gp.ui.commandNum = i;
                gameState();
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 处理鼠标拖动
        mousePoint = e.getPoint();

    }


    private BufferedImage getMouseImage(){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/mouse/mouse.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        return uTool.scaleImage(image, gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2) {
        if (mousePoint != null) {

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));

             if (image != null) {
                 g2.drawImage(image, mousePoint.x, mousePoint.y, null);
             }

        }
    }

    public Cursor createInvisibleCursor() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        return Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "invisibleCursor");
    }

    // 不同游戏模式
    private void titleState() {
        if (gp.ui.titleScreenState == 0) {
            if (gp.ui.commandNum == 0) {
                gp.ui.titleScreenState = 1;
                gp.playMusic(0);
            } else if (gp.ui.commandNum == 1) {

            } else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        } else if (gp.ui.titleScreenState == 1) {


            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.playState;
            } else if (gp.ui.commandNum == 2) {
                gp.gameState = gp.playState;
            } else if (gp.ui.commandNum == 3) {
                gp.ui.titleScreenState = 0;
                gp.stopMusic();
                gp.ui.commandNum = 0;
            }
        }
    }

    private void gameState(){

    }
}
