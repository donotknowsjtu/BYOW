package entity;

import core.KeyHandler;
import core.MouseHandler;
import core.UtilityTool;
import gameContext.AudioContext;
import gameContext.CoreContext;
import gameContext.InputContext;
import lombok.Setter;
import object.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static jdk.vm.ci.hotspot.riscv64.RISCV64HotSpotRegisterConfig.gp;

@Component("player")
public class Player extends Entity{
    public int screenX , screenY ;
    public int worldCol, worldRow, screenCol, screenRow;
    private int tempScreenX, tempScreenY;
    public boolean attackCanceled;
    public ArrayList<Entity> inventory;
    public final int maxInventorySize;
    private BufferedImage swordUp1, swordUp2, swordDown1, swordDown2, swordLeft1, swordLeft2, swordRight1, swordRight2,
            axeUp1, axeUp2, axeDown1, axeDown2, axeLeft1, axeLeft2, axeRight1, axeRight2;
    public boolean weaponChanged;
    // 武器
    private Point leftHandLocation, rightHandLocation;
    private Point rotationCenter;
    private BufferedImage weaponImage;
    private float mouseAngle;
    // context
    @Setter
    private InputContext inputContext;
    @Setter
    private AudioContext audioContext;
    // uTool
    @Autowired
    private UtilityTool uTool;

    // handler
    @Autowired
    private KeyHandler keyHandler;
    @Autowired
    private MouseHandler mouseHandler;

    @Autowired
    public Player() {
        super();
        this.type = playerType;

        // 初始化位置
        this.screenX = (CoreContext.screenLength - CoreContext.tileSize) / 2;
        this.screenY = (CoreContext.screenWidth - CoreContext.tileSize) / 2;
        this.worldCol = this.worldX / CoreContext.tileSize;
        this.worldRow = this.worldY / CoreContext.tileSize;
        this.screenCol = screenX / CoreContext.tileSize;
        this.screenRow = screenY / CoreContext.tileSize;
        this.direction = Direction.UP;
        this.spriteCounter = 1;
        this.solidArea = new Rectangle(8, 12, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;

        loadImage();
        loadPlayerAttackImage();
        this.image = getImage();

        this.tempScreenX = screenX;
        this.tempScreenY = screenY;

        this.attackCanceled = false;
        setDefaultValues();

        this.inventory = new ArrayList<>();
        maxInventorySize = 20;
        setItems();

        this.weaponChanged = true;

        // 武器初始化
        leftHandLocation.x = this.screenX + this.image.getWidth() / 2 + 5;
        leftHandLocation.y = this.screenY + this.image.getHeight() / 2;
        rightHandLocation.x = this.screenX + this.image.getHeight() / 2 - 5;
        rightHandLocation.y = this.screenY + this.image.getHeight() / 2;
        rotationCenter = rightHandLocation;
        weaponImage = getWeaponImage();
        mouseAngle = 0;

    }



    // 设置背包
    private void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));

    }

    // default value
    private void setDefaultValues(){
        this.speed = 4;
        this.worldX = 22 * CoreContext.tileSize;
        this.worldY = 22 * CoreContext.tileSize;
        // LIFE VALUE
        this.maxLife = 6;
        life = maxLife;
        // Player state
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();

    }

    private int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    private int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void loadImage(){
        up1 = setup(uTool, "/player/_up1.png", CoreContext.tileSize, CoreContext.tileSize);
        up2 = setup(uTool, "/player/_up2.png", CoreContext.tileSize, CoreContext.tileSize);
        up3 = setup(uTool, "/player/_up3.png", CoreContext.tileSize, CoreContext.tileSize);
        up4 = setup(uTool, "/player/_up4.png", CoreContext.tileSize, CoreContext.tileSize);
        down1 = setup(uTool, "/player/_down1.png", CoreContext.tileSize, CoreContext.tileSize);
        down2 = setup(uTool, "/player/_down2.png", CoreContext.tileSize, CoreContext.tileSize);
        down3 = setup(uTool, "/player/_down3.png", CoreContext.tileSize, CoreContext.tileSize);
        down4 = setup(uTool, "/player/_down4.png", CoreContext.tileSize, CoreContext.tileSize);
        left1 = setup(uTool, "/player/_left1.png", CoreContext.tileSize, CoreContext.tileSize);
        left2 = setup(uTool, "/player/_left2.png", CoreContext.tileSize, CoreContext.tileSize);
        left3 = setup(uTool, "/player/_left3.png", CoreContext.tileSize, CoreContext.tileSize);
        left4 = setup(uTool, "/player/_left4.png", CoreContext.tileSize, CoreContext.tileSize);
        right1 = setup(uTool, "/player/_right1.png", CoreContext.tileSize, CoreContext.tileSize);
        right2 = setup(uTool, "/player/_right2.png", CoreContext.tileSize, CoreContext.tileSize);
        right3 = setup(uTool, "/player/_right3.png", CoreContext.tileSize, CoreContext.tileSize);
        right4 = setup(uTool, "/player/_right4.png", CoreContext.tileSize, CoreContext.tileSize);
    }

    private void loadPlayerAttackImage(){
        // sword
        swordUp1 = setup(uTool, "/player/attacking/boy_attack_up_1.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        swordUp2 = setup(uTool, "/player/attacking/boy_attack_up_2.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        swordDown1 = setup(uTool, "/player/attacking/boy_attack_down_1.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        swordDown2 = setup(uTool, "/player/attacking/boy_attack_down_2.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        swordLeft1 = setup(uTool, "/player/attacking/boy_attack_left_1.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        swordLeft2 = setup(uTool, "/player/attacking/boy_attack_left_2.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        swordRight1 = setup(uTool, "/player/attacking/boy_attack_right_1.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        swordRight2 = setup(uTool, "/player/attacking/boy_attack_right_2.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        // axe
        axeUp1 = setup(uTool, "/player/attacking/boy_axe_up_1.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        axeUp2 = setup(uTool, "/player/attacking/boy_axe_up_2.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        axeDown1 = setup(uTool, "/player/attacking/boy_axe_down_1.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        axeDown2 = setup(uTool, "/player/attacking/boy_axe_down_2.png", CoreContext.tileSize, CoreContext.tileSize * 2);
        axeLeft1 = setup(uTool, "/player/attacking/boy_axe_left_1.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        axeLeft2 = setup(uTool, "/player/attacking/boy_axe_left_2.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        axeRight1 = setup(uTool, "/player/attacking/boy_axe_right_1.png", CoreContext.tileSize * 2, CoreContext.tileSize);
        axeRight2 = setup(uTool, "/player/attacking/boy_axe_right_2.png", CoreContext.tileSize * 2, CoreContext.tileSize);

    }

    @Override
    public void update(){
        tempScreenX = screenX;
        tempScreenY = screenY;
        if(attacking){
            attacking();
        }
        else if(keyHandler.W_pressed || keyHandler.D_pressed || keyHandler.S_pressed || keyHandler.A_pressed || keyHandler.Enter_pressed ) {
            if (keyHandler.W_pressed) {
                direction = Direction.UP;
                image = getImage();
                stepCount();
            }
            if (keyHandler.S_pressed) {
                direction = Direction.DOWN;
                image = getImage();
                stepCount();
            }
            if (keyHandler.A_pressed) {
                direction = Direction.LEFT;
                image = getImage();
                stepCount();
            }
            if (keyHandler.D_pressed) {
                direction = Direction.RIGHT;
                image = getImage();
                stepCount();
            }
            if (keyHandler.Enter_pressed){

            }
            if(keyHandler.Enter_pressed && !attackCanceled){
                audioContext.playSE(5);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            collisionOn = true;
            // 检测瓦片碰撞
            gp.cChecker.checkTile(this);
            // 检测物品碰撞
            int objIndex = gp.cChecker.checkObject(this, true);
            if(keyHandler.E_pressed) {pickUpObject(objIndex);}
            // 检查npc碰撞
            int npcIndex = gp.cChecker.checkEntity(this, gp.npcs);
            interactNpc(npcIndex);
            // 检查怪物碰撞
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            contactMonster(monsterIndex);
            // 检查事件触发
            gp.eHandler.checkEvent();



            if (!collisionOn && !keyHandler.Enter_pressed) {
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
            keyHandler.Enter_pressed = false;
        }
        if(invincible){
            invincibleCounter ++;
            if(invincibleCounter == 30){
                invincibleCounter = 0;
                invincible = false;
            }
        }
        image = getImage();
        worldCol = this.worldX / CoreContext.tileSize;
        worldRow = this.worldY / CoreContext.tileSize;
    }

    private void attacking() {
        spriteCounter ++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        } else if (spriteCounter <= 25) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            switch (direction){
                case Direction.UP : worldY -= attackArea.height; break;
                case Direction.DOWN: worldY += attackArea.height; break;
                case Direction.LEFT: worldX -= attackArea.width; break;
                case Direction.RIGHT: worldX += attackArea.width; break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            damageMonster(monsterIndex);
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else {
            spriteNum = 1;
            spriteCounter = 1;
            attacking = false;
        }
    }

    private void damageMonster(int monsterIndex) {
        if(monsterIndex != 999){
           if(!gp.monsters[monsterIndex].invincible){
               audioContext.playSE(5);
               int damage = attack - gp.monsters[monsterIndex].defense;
               if(damage < 0){damage = 0;}
               gp.monsters[monsterIndex].life -= damage;
               gp.ui.addMessage(damage + "damage!");
               gp.monsters[monsterIndex].invincible = true;
               gp.monsters[monsterIndex].damageReaction();
               if(gp.monsters[monsterIndex].life <= 0){
                   gp.monsters[monsterIndex].dying = true;
                   gp.ui.addMessage("Killed the " + gp.monsters[monsterIndex].name + "!");
                   gp.ui.addMessage("EXP + " + gp.monsters[monsterIndex].exp + "!");
                   exp += gp.monsters[monsterIndex].exp;
                   checkLevelUp();
               }
           }
        }
    }

    private void checkLevelUp(){
        if(exp >= nextLevelExp){
            level ++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength ++;
            dexterity ++;
            attack = getAttack();
            defense = getDefense();
            audioContext.playSE(7);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "your are level " + level + "now!\n" + "you feel stronger";
        }
    }

    private void interactNpc(int npcIndex) {
        if(keyHandler.Enter_pressed){
            if(npcIndex != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npcs[npcIndex].speak();
            }
        }
    }

    private void contactMonster(int monsterIndex) {
        if(monsterIndex != 999){
            if(!invincible && !gp.monsters[monsterIndex].dying){
                audioContext.playSE(6);
                int damage = gp.monsters[monsterIndex].attack - this.defense;
                if(damage < 0){damage = 0;}
                this.life -= damage;
                invincible = true;
            }
        }
    }

    public void pickUpObject(int objIndex){
        if(objIndex != 999){
            String text;
            if(inventory.size() < maxInventorySize){
                inventory.add(gp.objects[objIndex]);
                audioContext.playSE(1);
                text = gp.objects[objIndex].name + "+1";
                gp.objects[objIndex] = null;
                gp.ui.addMessage(text);
            }else {
                text = "the bag is full!";
                gp.ui.addMessage(text);
            }
        }

    }

    @Override
    public BufferedImage getImage(){
        if(weaponChanged){
            if(currentWeapon.getClass() == OBJ_Sword_Normal.class){
                attackUp1 = swordUp1;
                attackUp2 = swordUp2;
                attackDown1 = swordDown1;
                attackDown2 = swordDown2;
                attackLeft1 = swordLeft1;
                attackLeft2 = swordLeft2;
                attackRight1 = swordRight1;
                attackRight2 = swordRight2;
            } else if (currentWeapon.getClass() == OBJ_Axe.class) {
                attackUp1 = axeUp1;
                attackUp2 = axeUp2;
                attackDown1 = axeDown1;
                attackDown2 = axeDown2;
                attackLeft1 = axeLeft1;
                attackLeft2 = axeLeft2;
                attackRight1 = axeRight1;
                attackRight2 = axeRight2;
            }
            weaponChanged = false;
        }
        switch(direction){
            case Direction.UP:
                if(!attacking){
                    if(spriteCounter == 1){image = up1;}
                    if(spriteCounter == 2){image = up2;}
                    if(spriteCounter == 3){image = up3;}
                    if(spriteCounter == 4){image = up4;}
                }else {
                    tempScreenY = screenY - CoreContext.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                }
                break;
            case Direction.DOWN:
                if(!attacking){
                    if(spriteCounter == 1){image = down1;}
                    if(spriteCounter == 2){image = down2;}
                    if(spriteCounter == 3){image = down3;}
                    if(spriteCounter == 4){image = down4;}
                }else {
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                }
                break;
            case Direction.LEFT:
                if(!attacking){
                    if(spriteCounter == 1){image = left1;}
                    if(spriteCounter == 2){image = left2;}
                    if(spriteCounter == 3){image = left3;}
                    if(spriteCounter == 4){image = left4;}
                }else {
                    tempScreenX = screenX - CoreContext.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                }
                break;
            case Direction.RIGHT:
                if(!attacking){
                    if(spriteCounter == 1){image = right1;}
                    if(spriteCounter == 2){image = right2;}
                    if(spriteCounter == 3){image = right3;}
                    if(spriteCounter == 4){image = right4;}
                }else {
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                }
                break;
        }
        return image;
    }

    // 获取武器图片
    private BufferedImage getWeaponImage() {
        return currentWeapon.image;
    }
    // 绘制武器图片
    private void drawWeaponImage(Graphics2D g2, float angle){
        // 设置抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 保存原始变换
        AffineTransform originalTransform = g2.getTransform();

        // 1. 平移画布，使旋转中心与图片上的点重合
        // 计算需要移动的距离：旋转中心坐标减去图片上点的坐标
        Point imagePoint = new Point(0, 40);
        int dx = rotationCenter.x - imagePoint.x;
        int dy = rotationCenter.y - imagePoint.y;
        g2.translate(dx, dy);

        // 2. 旋转画布
        // 旋转中心现在是(0,0)，因为我们已经平移了画布
        g2.rotate(angle, imagePoint.x, imagePoint.y);

        // 3. 绘制图像
        g2.drawImage(weaponImage, 0, 0, null);

        // 恢复原始变换
        g2.setTransform(originalTransform);
    }

    @Override
    public void draw(Graphics2D g2, int type){


        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        // 绘制武器图片
        drawWeaponImage(g2, mouseAngle);
        // 绘制人物图片
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void selectObject(int objNum){
        Entity selectObject = inventory.get(objNum);
        // weapon
        if(selectObject.getClass() == OBJ_Sword_Normal.class || selectObject.getClass() == OBJ_Axe.class || selectObject.getClass() == OBJ_Gun.class){
            currentWeapon = selectObject;
            weaponChanged = true;
        }
        // shield
        else if (selectObject.getClass() == OBJ_Shield_Wood.class) {
            currentShield = selectObject;
        }
        // others
    }
}
