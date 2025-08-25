package core;




import entity.Entity;
import gameContext.AudioContext;
import gameContext.StateContext;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_C;

@Component("keyHandler")
public class KeyHandler implements KeyListener {
    public boolean W_pressed, S_pressed, A_pressed, D_pressed, Enter_pressed;
    public boolean E_pressed;
    public boolean debugMode;
    public boolean reloadMap;
    public boolean mute;
    @Setter
    private StateContext stateContext;
    @Setter
    private AudioContext audioContext;

    public KeyHandler(){
        this.Enter_pressed = false;
        this.debugMode = false;
        this.reloadMap = false;
        this.mute = true;
        this.E_pressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_M){mute = !mute;}
        if(stateContext.getGameState() == StateContext.titleState){titleState(code);}
        else if(stateContext.getGameState() == StateContext.playState) {playState(code, e);}
        else if (stateContext.getGameState() == StateContext.pauseState) {pauseState(code);}
        else if (stateContext.getGameState() == StateContext.dialogueState) {dialogueState(code);}
    }

    private void titleState(int code){
        if(gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W ) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            } else if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            } else if (code == KeyEvent.VK_ENTER ) {
                if (gp.ui.commandNum == 0) {
                    gp.ui. titleScreenState = 1;
                    audioContext.playMusic(0);
                } else if (gp.ui.commandNum == 1) {

                } else if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }


            }
        } else if(gp.ui.titleScreenState == 1)  {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            } else if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            } else if (code == KeyEvent.VK_ENTER ) {
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
    }
    private void playState(int code, KeyEvent e){
        if (code == KeyEvent.VK_W) {
            W_pressed = true;
        }
        if (code == KeyEvent.VK_S) {
            S_pressed = true;
        }
        if (code == KeyEvent.VK_A) {
            A_pressed = true;
        }
        if (code == KeyEvent.VK_D) {
            D_pressed = true;
        }
        if (code == KeyEvent.VK_P) {
            stateContext.setGameState(StateContext.pauseState);
        }
        if(code == VK_C){
            gp.ui.showCharacterUI = !gp.ui.showCharacterUI;
        }
        if(code == KeyEvent.VK_ENTER){
            Enter_pressed = true;
        }
        // debug mode使用反斜杠\调用
        if(code == KeyEvent.VK_BACK_SLASH){
            debugMode = !debugMode;
        }
        // reloadMap使用正斜杠/调用
        if(code == KeyEvent.VK_SLASH){
            reloadMap = true;
        }
        if(code == KeyEvent.VK_CAPS_LOCK){
            gp.ui.showInventoryUI = !gp.ui.showInventoryUI;
        }
        if(code == KeyEvent.VK_E){
            if(gp.ui.showInventoryUI) {inventoryUI();}
            E_pressed = true;
        }
    }
    private void inventoryUI(){
        if(gp.mouseHandler.touchedObjNum < gp.player.inventory.size()) {
            gp.player.selectObject(gp.mouseHandler.touchedObjNum);
            if (gp.player.inventory.get(gp.mouseHandler.touchedObjNum).type == Entity.objectType && gp.player.inventory.get(gp.mouseHandler.touchedObjNum).toUse) {
                gp.player.inventory.get(gp.mouseHandler.touchedObjNum).use();
                if (gp.player.inventory.get(gp.mouseHandler.touchedObjNum).disappear) {
                    gp.player.inventory.remove(gp.mouseHandler.touchedObjNum);
                }
            }
        }
    }
    private void pauseState(int code){
        if(code == KeyEvent.VK_P){
            stateContext.setGameState(StateContext.playState);
        }
        if(code == VK_C){
            gp.ui.showCharacterUI = !gp.ui.showCharacterUI;
        }
    }
    private void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            stateContext.setGameState(StateContext.playState);
        }
        if(code == VK_C){
            gp.ui.showCharacterUI = !gp.ui.showCharacterUI;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            W_pressed = false;
        }
        if(code == KeyEvent.VK_S){
            S_pressed = false;
        }
        if(code == KeyEvent.VK_A){
            A_pressed = false;
        }
        if(code == KeyEvent.VK_D){
            D_pressed = false;
        }
        if(code == KeyEvent.VK_SLASH){
            reloadMap = false;
        }
        if(code == KeyEvent.VK_E){
            E_pressed = false;
        }
    }
}
