package core;

import gameContext.AudioContext;
import gameContext.CoreContext;
import gameContext.StateContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component("mouserHandler")
public class MouseHandler extends MouseInputAdapter {
    @Getter
    private Point mousePoint;
    private BufferedImage image;
    public int touchedObjNum;
    public int clickedObjNum;
    // 鼠标与面板中心点的连线与水平向右方向的夹角，逆时针为正，范围为-pi~pi
    @Getter
    private double mouseAngle;
    @Autowired
    private UtilityTool uTool;

    @Setter
    private StateContext stateContext;
    @Setter
    private AudioContext audioContext;

    public MouseHandler(){
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
                if(stateContext.getGameState() == StateContext.titleState){titleState();}
                if(stateContext.getGameState() == StateContext.playState){gameState();}
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
                    audioContext.playSE(8);
                }
                touchedObjNum = i;
                gp.ui.commandNum = i;
                gameState();
            }
        }

        mouseAngle = uTool.calculateAngle(gp.getPanelCenter(), mousePoint);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 处理鼠标拖动
        mousePoint = e.getPoint();

    }


    private BufferedImage getMouseImage(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/mouse/mouse.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        return uTool.scaleImage(image, CoreContext.tileSize, CoreContext.tileSize);
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
                audioContext.playMusic(0);
            } else if (gp.ui.commandNum == 1) {

            } else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        } else if (gp.ui.titleScreenState == 1) {


            if (gp.ui.commandNum == 0) {
                stateContext.setGameState(StateContext.playState);
            } else if (gp.ui.commandNum == 1) {
                stateContext.setGameState(StateContext.playState);
            } else if (gp.ui.commandNum == 2) {
                stateContext.setGameState(StateContext.playState);
            } else if (gp.ui.commandNum == 3) {
                gp.ui.titleScreenState = 0;
                audioContext.stopMusic();
                gp.ui.commandNum = 0;
            }
        }
    }

    private void gameState(){

    }
}
