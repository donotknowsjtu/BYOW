package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage image;
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public int direction;
    public Rectangle solidArea;
    public Rectangle attackArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public String dialogues[];
    public int dialogueIndex;
    public Random random;
    // STATE
        // 无敌效果和无敌时间
        // 针对slam和玩家触碰时，玩家会每隔1/60掉血一次，这个时间间隔太短，需要增加时间间隔，故引入无敌时间
    public boolean invincible;
    public boolean attacking;
    public boolean alive;
    public boolean dying;
    public boolean hpBarOn;
    // Counter
    public int invincibleCounter;
    public int spriteCounter;
    public int spriteNum;
    public int actionLockCounter;
    public int dyingCounter;
    public int hpBarCounter;
    // life
    public int maxLife;
    public int life;

    // Object
    public BufferedImage image1, image2, image3;
    public String name;
    public boolean collisionOn;

    // type用来区分player = 0， npc = 1， monster = 2
    public int type;
    public final int playerType = 0;
    public final int npcType = 1;
    public final int monsterType = 2;


    public Entity(GamePanel gp){
        this.gp = gp;
        this.spriteCounter = 1;
        actionLockCounter = 0;
        this.collisionOn = true;
        this.dialogues = new String[10];
        this.dialogueIndex = 0;
        this.direction = Direction.UP;
        this.solidArea = new Rectangle();
        this.attackArea = new Rectangle(0, 0, 0, 0);
        this.random = new Random(1234);
        this.invincible = false;
        this.invincibleCounter = 0;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.attacking = false;
        this.alive = true;
        this.dying = false;
        this.dyingCounter = 0;
        this.hpBarOn = false;
        this.hpBarCounter = 0;
    }
    // 这个方法是npc专用
    public void draw(Graphics2D g2){
        int screenX = this.worldX - gp.player.worldX + gp.player.screenX;
        int screenY = this.worldY - gp.player.worldY + gp.player.screenY;

        if(direction == Direction.UP && spriteCounter == 1){
            image = up1;
        }else if(direction == Direction.UP && spriteCounter == 2){
            image = up2;
        }else if(direction == Direction.UP && spriteCounter == 3){
            image = up3;
        }else if(direction == Direction.UP && spriteCounter == 4){
            image = up4;
        }else if(direction == Direction.DOWN && spriteCounter == 1){
            image = down1;
        }else if(direction == Direction.DOWN && spriteCounter == 2){
            image = down2;
        }else if(direction == Direction.DOWN && spriteCounter == 3){
            image = down3;
        }else if(direction == Direction.DOWN && spriteCounter == 4){
            image = down4;
        } else if(direction == Direction.LEFT && spriteCounter == 1){
            image = left1;
        }else if(direction == Direction.LEFT && spriteCounter == 2){
            image = left3;
        }else if(direction == Direction.LEFT && spriteCounter == 3){
            image = left3;
        }else if(direction == Direction.LEFT && spriteCounter == 4){
            image = left4;
        }else if(direction == Direction.RIGHT && spriteCounter == 1){
            image = right1;
        }else if(direction == Direction.RIGHT && spriteCounter == 2){
            image = right3;
        }else if(direction == Direction.RIGHT && spriteCounter == 3){
            image = right3;
        }else if(direction == Direction.RIGHT && spriteCounter == 4){
            image = right4;
        }

        // Monster HP bar
        if(type == 2 && hpBarOn) {
            double oneScale = (double) gp.tileSize / maxLife;
            double hpBarValue = oneScale * life;

            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX - 1, screenY - 16,gp.tileSize + 2, 12);
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

            hpBarCounter ++;
            if(hpBarCounter >= 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }
        // 被攻击，画笔改为半透明
        if(invincible){
            hpBarOn = true;
            changeAlpha(g2, 0.3f);
        }

        if(dying){
            dyingAnimation(g2);
        }

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }

        // 恢复原画笔
        changeAlpha(g2, 1f);
    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i){changeAlpha(g2, 0f);}
        else if (dyingCounter <= i * 2) {changeAlpha(g2, 1f);}
        else if (dyingCounter <= i * 3) {changeAlpha(g2, 0f);}
        else if (dyingCounter <= i * 4) {changeAlpha(g2, 1f);}
        else if (dyingCounter <= i * 5) {changeAlpha(g2, 0f);}
        else if (dyingCounter <= i * 6) {changeAlpha(g2, 1f);}
        else if (dyingCounter <= i * 7) {changeAlpha(g2, 0f);}
        else if (dyingCounter <= i * 8) {changeAlpha(g2, 1f);}
        else if (dyingCounter <= i * 9) {changeAlpha(g2, 0f);}
        else{
            dying = false;
            alive = false;
            dyingCounter = 0;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
    public BufferedImage setup(String imagePath, int width, int length){
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            scaledImage = uTool.scaleImage(scaledImage, width, length);
        } catch (IOException e){
            e.printStackTrace();
        }
        return scaledImage;
    }
    // 这个方法npc专用
    public BufferedImage getImage(){
        if(direction == Direction.UP && spriteCounter == 1){
            image = up1;
        }else if(direction == Direction.UP && spriteCounter == 2){
            image = up2;
        }else if(direction == Direction.UP && spriteCounter == 3){
            image = up3;
        }else if(direction == Direction.UP && spriteCounter == 4){
            image = up4;
        }else if(direction == Direction.DOWN && spriteCounter == 1){
            image = down1;
        }else if(direction == Direction.DOWN && spriteCounter == 2){
            image = down2;
        }else if(direction == Direction.DOWN && spriteCounter == 3){
            image = down3;
        }else if(direction == Direction.DOWN && spriteCounter == 4){
            image = down4;
        } else if(direction == Direction.LEFT && spriteCounter == 1){
            image = left1;
        }else if(direction == Direction.LEFT && spriteCounter == 2){
            image = left3;
        }else if(direction == Direction.LEFT && spriteCounter == 3){
            image = left3;
        }else if(direction == Direction.LEFT && spriteCounter == 4){
            image = left4;
        }else if(direction == Direction.RIGHT && spriteCounter == 1){
            image = right1;
        }else if(direction == Direction.RIGHT && spriteCounter == 2){
            image = right3;
        }else if(direction == Direction.RIGHT && spriteCounter == 3){
            image = right3;
        }else if(direction == Direction.RIGHT && spriteCounter == 4){
            image = right4;
        }
        return image;
    }

    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter == 120) {
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = Direction.UP;
            } else if (i <= 50) {
                direction = Direction.DOWN;
            } else if (i <= 75) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            actionLockCounter = 0;
        }
    }

    // 这个方法默认给NPC_Soldier使用
    public void update(){
        setAction();
        image = getImage();
        collisionOn = true;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npcs);
        gp.cChecker.checkEntity(this, gp.monsters);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        // 对于monster触发contact player事件进行处理
        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
                gp.playSE(6);
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
        if (!collisionOn) {
            switch (direction) {
                    case Direction.UP:
                        worldY -= speed;
                        stepCount();
                        break;
                    case Direction.DOWN:
                        worldY += speed;
                        stepCount();
                        break;
                    case Direction.LEFT:
                        worldX -= speed;
                        stepCount();
                        break;
                    case Direction.RIGHT:
                        worldX += speed;
                        stepCount();
                        break;
            }
        }
        if(invincible){
            invincibleCounter ++;
            if(invincibleCounter == 30){
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }
    public void stepCount(){
        spriteCounter++;
        if (spriteCounter == 5) {
            spriteCounter = 1;
        }
    }
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex ++;
        switch (gp.player.direction){
            case  Direction.UP: direction = Direction.DOWN;break;
            case Direction.DOWN: direction = Direction.UP;break;
            case Direction.LEFT: direction = Direction.RIGHT;break;
            case Direction.RIGHT: direction = Direction.LEFT;break;
        }
    }
    public void damageReaction(){

    }
}
