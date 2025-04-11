package main;


import entity.Entity;
import org.checkerframework.checker.units.qual.K;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean W_pressed, S_pressed, A_pressed, D_pressed, Enter_pressed;
    public boolean checkDrawTime = false;
    private GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
        this.Enter_pressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState){
            if(gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                } else if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                } else if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
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
                } else if (code == KeyEvent.VK_ENTER) {
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
        else if(gp.gameState == gp.playState) {
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
            if(code == KeyEvent.VK_ENTER){
                Enter_pressed = true;
            }
            //Debug
            if (code == KeyEvent.VK_O) {
                checkDrawTime = !checkDrawTime;
            }

        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }

        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
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

    }
}
