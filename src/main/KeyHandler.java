package main;




import net.sf.saxon.trans.SymbolicName;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_C;

public class KeyHandler implements KeyListener {
    public boolean W_pressed, S_pressed, A_pressed, D_pressed, Enter_pressed;
    public boolean debugMode;
    public boolean reloadMap;
    private GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
        this.Enter_pressed = false;
        this.debugMode = false;
        this.reloadMap = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState){titleState(code);}
        else if(gp.gameState == gp.playState) {playState(code, e);}
        else if (gp.gameState == gp.pauseState) {pauseState(code);}
        else if (gp.gameState == gp.dialogueState) {dialogueState(code);}
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
                    gp.playMusic(0);
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
            gp.gameState = gp.pauseState;
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

    }
    private void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
        if(code == VK_C){
            gp.ui.showCharacterUI = !gp.ui.showCharacterUI;
        }
    }
    private void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
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
    }
}
