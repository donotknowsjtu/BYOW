package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    private KeyHandler keyH;
    private int stepCount;
    public int screenX , screenY ;
    public int worldCol, worldRow, screenCol, screenRow;


    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.speed = 4;
        this.worldX = 22 * gp.tileSize;
        this.worldY = 22 * gp.tileSize;
        this.screenX = (gp.screenLength - gp.tileSize) / 2;
        this.screenY = (gp.screenWidth - gp.tileSize) / 2;
        this.worldCol = this.worldX / gp.tileSize;
        this.worldRow = this.worldY / gp.tileSize;
        this.screenCol = screenX / gp.tileSize;
        this.screenRow = screenY / gp.tileSize;
        this.direction = Direction.UP;
        this.stepCount = 1;

        this.solidArea = new Rectangle(8, 12, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;

        loadImage();
        this.image = getImage();

        // LIFE VALUE
        this.maxLife = 6;
        life = maxLife;
    }

    private void loadImage(){
        up1 = setup("/player/_up1.png");
        up2 = setup("/player/_up2.png");
        up3 = setup("/player/_up3.png");
        up4 = setup("/player/_up4.png");
        down1 = setup("/player/_down1.png");
        down2 = setup("/player/_down2.png");
        down3 = setup("/player/_down3.png");
        down4 = setup("/player/_down4.png");
        left1 = setup("/player/_left1.png");
        left2 = setup("/player/_left2.png");
        left3 = setup("/player/_left3.png");
        left4 = setup("/player/_left4.png");
        right1 = setup("/player/_right1.png");
        right2 = setup("/player/_right2.png");
        right3 = setup("/player/_right3.png");
        right4 = setup("/player/_right4.png");
    }


    @Override
    public void update(){
        if(gp.keyHandler.W_pressed || gp.keyHandler.D_pressed || gp.keyHandler.S_pressed || gp.keyHandler.A_pressed || gp.keyHandler.Enter_pressed) {
            if (gp.keyHandler.W_pressed) {
                direction = Direction.UP;
                image = getImage();
                stepCount();
            }
            if (gp.keyHandler.S_pressed) {
                direction = Direction.DOWN;
                image = getImage();
                stepCount();
            }
            if (gp.keyHandler.A_pressed) {
                direction = Direction.LEFT;
                image = getImage();
                stepCount();
            }
            if (gp.keyHandler.D_pressed) {
                direction = Direction.RIGHT;
                image = getImage();
                stepCount();
            }

            collisionOn = true;
            // 检测瓦片碰撞
            gp.cChecker.checkTile(this);
            // 检测物品碰撞
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // 检查npc碰撞
            int npcIndex = gp.cChecker.checkEntity(this, gp.npcs);
            interactNpc(npcIndex);
            // 检查怪物碰撞
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            contactMonster(monsterIndex);
            // 检查事件触发
            gp.eHandler.checkEvent();



            if (!collisionOn && !gp.keyHandler.Enter_pressed) {
                switch (direction) {
                    case Direction.UP:
                        worldY -= speed;
                        break;
                    case Direction.DOWN:
                        worldY += speed;
                        break;
                    case Direction.LEFT:
                        worldX -= speed;
                        break;
                    case Direction.RIGHT:
                        worldX += speed;
                        break;
                }
            }
            gp.keyHandler.Enter_pressed = false;
        }
        if(invincible){
            invincibleCounter ++;
            if(invincibleCounter == 30){
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }



    private void interactNpc(int npcIndex) {
        if(npcIndex != 999){
            if(gp.keyHandler.Enter_pressed) {
                gp.gameState = gp.dialogueState;
                gp.npcs[npcIndex].speak();
            }
        }
    }

    private void contactMonster(int monsterIndex) {
        if(monsterIndex != 999){
            if(!invincible){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void pickUpObject(int objIndex){
        if(objIndex != 999){

        }

    }
    @Override
    public void draw(Graphics2D g2){
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}
