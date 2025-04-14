package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Font maruMonica, purisaB;
    BufferedImage keyImage;
    public Boolean messageOn;
    public String message = "";
    Graphics2D g2;
    public String currentDialogue;
    public int commandNum;
    public int titleScreenState = 0;
    private BufferedImage heart_full, heart_half, heart_blank;

    public UI(GamePanel gp){
        this.gp = gp;
        try{
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException q){
            q.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image1;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        currentDialogue = "";
        commandNum = 0;

    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        else if(gp.gameState == gp.pauseState){
            drawPauseScreen();

        } else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        } else if (gp.gameState == gp. titleState) {
            drawTitleScreen();
        }
    }

    private void drawPlayerLife() {
       
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank, x, y, null);
            i ++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i ++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i ++;
            x += gp.tileSize;
        }
    }

    private void drawTitleScreen() {
        if(titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenLength, gp.screenWidth);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = " B  Y  O  W ";
            int x = getXforCenterdText(text);
            int y = gp.tileSize * 3;

            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // Player Image Show
            x = gp.screenLength / 2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // Menu Show
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getXforCenterdText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "LOAD GAME";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "QUIT";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenLength, gp.screenWidth);

            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Select your class!";
            int x = getXforCenterdText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Thief";
            x = getXforCenterdText(text);
            y += gp.tileSize ;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Sorcerer";
            x = getXforCenterdText(text);
            y += gp.tileSize ;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "back";
            x = getXforCenterdText(text);
            y += gp.tileSize * 2 ;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    private void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2, y = gp.tileSize / 2, length = gp.screenLength - gp.tileSize * 4, width = gp.tileSize * 4;
        drawSubWindow(x, y, length, width);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += gp.tileSize;
        }
    }

    public void drawSubWindow(int x, int y, int length, int width){
        Color c = new Color(0, 0, 0, 120);
        g2.setColor(c);
        g2.fillRoundRect(x, y, length, width, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, length - 10, width - 10, 25, 25);
    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x;
        int y = gp.screenWidth / 2;
        x = getXforCenterdText(text);
        g2.drawString(text, x, y);


    }
    public int getXforCenterdText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenLength / 2 - length / 2;
    }





}
