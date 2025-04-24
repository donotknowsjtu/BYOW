package main;

import entity.Entity;
import object.OBJ_Heart;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Font maruMonica, purisaB, yaHei, xiaWu;
    BufferedImage keyImage;
//    public boolean messageOn;
//    public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    Graphics2D g2;
    public String currentDialogue;
    public int commandNum;
    public int titleScreenState = 0;
    private BufferedImage heart_full, heart_half, heart_blank;
    public boolean showCharacterUI;
    public boolean showInventoryUI;
    public int slotCol = 0;
    public int slotRow = 0;
    public ArrayList<ClickObj> toBeClickedObj;



    public UI(GamePanel gp){
        this.gp = gp;
        try{
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/LXGWWenKai-Regular.ttf");
            xiaWu = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException q){
            q.printStackTrace();
        }
        yaHei = new Font("Microsoft YaHei", Font.PLAIN, 20);

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image1;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        currentDialogue = "";
        commandNum = 0;
        showCharacterUI = false;
        showInventoryUI = false;
        toBeClickedObj = new ArrayList<>();


    }
    public void addMessage(String text){
      message.add(text);
      messageCounter.add(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(xiaWu);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        else if(gp.gameState == gp.pauseState){
            drawPauseScreen();

        } else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();

        } else if (gp.gameState == gp. titleState) {
            drawTitleScreen();
        }
        if(showCharacterUI){
            drawCharacterScreen();
        }
        if(gp.keyHandler.debugMode){
            drawDebugScreen();
        }
        if(showInventoryUI){
            drawInventoryScreen();
        }
    }

    private void drawInventoryScreen() {
        int frameX = gp.screenLength - gp.tileSize * 5 - 90;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6 + 20;
        int frameHeight = gp.tileSize * 5 + 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));

        toBeClickedObj.clear();
        // 绘制5X4的矩形网格
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                drawCursorRect(slotXstart, slotYstart, col, row);
                toBeClickedObj.add(new ClickObj(slotXstart + (gp.tileSize + 5) * col, slotYstart + (gp.tileSize + 5) * row, gp.tileSize + 5, gp.tileSize + 5));
            }
        }

        // 绘制背包物品图片
        for(int i = 0; i < gp.player.inventory.size(); i ++){
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += gp.tileSize + 5;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        int row = gp.mouseHandler.touchedObjNum / 5; // 行数
        int col = gp.mouseHandler.touchedObjNum % 5; // 列数
        // 绘制强调的圆角矩形背景
        int highlightX = slotXstart + (gp.tileSize + 5) * col;
        int highlightY = slotYstart + (gp.tileSize + 5) * row;
        g2.setColor(new Color(255, 255, 255, 100)); // 设置透明度
        g2.fillRoundRect(highlightX, highlightY, gp.tileSize + 5, gp.tileSize + 5, 10, 10);

        // 描述页面
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize / 2 + 5;

        g2.setFont(g2.getFont().deriveFont(20F));
        if(commandNum < gp.player.inventory.size()){
            String description = gp.player.inventory.get(commandNum).description;
            if(description != null) {
                for (String line : description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += gp.tileSize / 2;
                }
            }
        }
    }



    private void drawCursorRect(int slotXstart, int slotYstart, int slotCol, int slotRow){
        g2.drawRoundRect(slotXstart + (gp.tileSize + 5) * slotCol, slotYstart + (gp.tileSize + 5) * slotRow, gp.tileSize + 5, gp.tileSize + 5, 10, 10);
    }

    private void drawDebugScreen() {
        int messageX = gp.screenLength - gp.tileSize * 5;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.setColor(Color.white);
        int worldCol = gp.player.worldCol + 1;
        int worldRow = gp.player.worldRow + 1;
        g2.drawString("worldCol: " + worldCol , messageX, messageY);
        messageY += 40;
        g2.drawString("worldRow: " + worldRow, messageX, messageY);
    }

    private void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for(int i = 0; i < message.size(); i ++){
            if(message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 30;
                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawCharacterScreen() {
        final int frameX = gp.tileSize ;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 25;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Shield", textX, textY);

        int tailX = frameX + frameWidth - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = gp.player.life + "/" + gp.player.maxLife;
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.strength);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.dexterity);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.attack);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.defense);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.exp);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.nextLevelExp);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.coin);
        textY += lineHeight;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);

        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
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
            toBeClickedObj.clear();

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
            y += gp.tileSize * 7/2;
            drawTextWithRoundedRect(text, x, y, 20);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "LOAD GAME";
            x = getXforCenterdText(text);
            y += gp.tileSize * 2;
            drawTextWithRoundedRect(text, x, y, 20);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "QUIT";
            x = getXforCenterdText(text);
            y += gp.tileSize * 2;
            drawTextWithRoundedRect(text, x, y, 20);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            toBeClickedObj.clear();

            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenLength, gp.screenWidth);

            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Select your class!";
            int x = getXforCenterdText(text);
            int y = gp.tileSize * 5;
            g2.drawString(text, x, y);


            text = "Fighter";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            drawTextWithRoundedRect(text, x, y, 20);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Thief";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            drawTextWithRoundedRect(text, x, y, 20);

            if(commandNum == 1 ){
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Sorcerer";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            drawTextWithRoundedRect(text, x, y, 20);

            if(commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);

            }
            text = "back";
            x = getXforCenterdText(text);
            y += gp.tileSize * 3 / 2;
            drawTextWithRoundedRect(text, x, y, 20);

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

    private void drawSubWindow(int x, int y, int length, int width){
        Color c = new Color(0, 0, 0, 120);
        g2.setColor(c);
        g2.fillRoundRect(x, y, length, width, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, length - 10, width - 10, 25, 25);
    }

    private void drawPauseScreen(){
        String text = "PAUSED";
        int x;
        int y = gp.screenWidth / 2;
        x = getXforCenterdText(text);
        g2.drawString(text, x, y);


    }

    private int getXforCenterdText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenLength / 2 - length / 2;
    }

    private int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    private void drawTextWithRoundedRect(String text, int x, int y, int paddingX) {
        // Calculate text dimensions
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

        // Calculate rectangle dimensions
        int rectX = x - paddingX;
        int rectY = y - textHeight - 3;
        int rectWidth = textWidth + paddingX * 2;
        int rectHeight = textHeight + 5 * 3;

        // Draw rounded rectangle
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 25, 25);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 25, 25);

        // Draw text
        g2.drawString(text, x, y);

        // Create and add ClickObj // 可优化
        ClickObj clickObj = new ClickObj(rectX, rectY, rectWidth, rectHeight);
        toBeClickedObj.add(clickObj);
    }




}
