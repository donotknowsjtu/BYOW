package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public Player(GamePanel gp){
        super(gp);
        this.type = playerType;

        this.screenX = (gp.screenLength - gp.tileSize) / 2;
        this.screenY = (gp.screenWidth - gp.tileSize) / 2;
        this.worldCol = this.worldX / gp.tileSize;
        this.worldRow = this.worldY / gp.tileSize;
        this.screenCol = screenX / gp.tileSize;
        this.screenRow = screenY / gp.tileSize;
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
        this.worldX = 22 * gp.tileSize;
        this.worldY = 22 * gp.tileSize;
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

    private void loadImage(){
        up1 = setup("/player/_up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/_up2.png", gp.tileSize, gp.tileSize);
        up3 = setup("/player/_up3.png", gp.tileSize, gp.tileSize);
        up4 = setup("/player/_up4.png", gp.tileSize, gp.tileSize);
        down1 = setup("/player/_down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/_down2.png", gp.tileSize, gp.tileSize);
        down3 = setup("/player/_down3.png", gp.tileSize, gp.tileSize);
        down4 = setup("/player/_down4.png", gp.tileSize, gp.tileSize);
        left1 = setup("/player/_left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/_left2.png", gp.tileSize, gp.tileSize);
        left3 = setup("/player/_left3.png", gp.tileSize, gp.tileSize);
        left4 = setup("/player/_left4.png", gp.tileSize, gp.tileSize);
        right1 = setup("/player/_right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/_right2.png", gp.tileSize, gp.tileSize);
        right3 = setup("/player/_right3.png", gp.tileSize, gp.tileSize);
        right4 = setup("/player/_right4.png", gp.tileSize, gp.tileSize);
    }

    private void loadPlayerAttackImage(){
        // sword
        swordUp1 = setup("/player/attacking/boy_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
        swordUp2 = setup("/player/attacking/boy_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
        swordDown1 = setup("/player/attacking/boy_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
        swordDown2 = setup("/player/attacking/boy_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
        swordLeft1 = setup("/player/attacking/boy_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
        swordLeft2 = setup("/player/attacking/boy_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        swordRight1 = setup("/player/attacking/boy_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        swordRight2 = setup("/player/attacking/boy_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
        // axe
        axeUp1 = setup("/player/attacking/boy_axe_up_1.png", gp.tileSize, gp.tileSize * 2);
        axeUp2 = setup("/player/attacking/boy_axe_up_2.png", gp.tileSize, gp.tileSize * 2);
        axeDown1 = setup("/player/attacking/boy_axe_down_1.png", gp.tileSize, gp.tileSize * 2);
        axeDown2 = setup("/player/attacking/boy_axe_down_2.png", gp.tileSize, gp.tileSize * 2);
        axeLeft1 = setup("/player/attacking/boy_axe_left_1.png", gp.tileSize * 2, gp.tileSize);
        axeLeft2 = setup("/player/attacking/boy_axe_left_2.png", gp.tileSize * 2, gp.tileSize);
        axeRight1 = setup("/player/attacking/boy_axe_right_1.png", gp.tileSize * 2, gp.tileSize);
        axeRight2 = setup("/player/attacking/boy_axe_right_2.png", gp.tileSize * 2, gp.tileSize);

    }

    @Override
    public void update(){
        tempScreenX = screenX;
        tempScreenY = screenY;
        if(attacking){
            attacking();
        }
        else if(gp.keyHandler.W_pressed || gp.keyHandler.D_pressed || gp.keyHandler.S_pressed || gp.keyHandler.A_pressed || gp.keyHandler.Enter_pressed ) {
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
            if (gp.keyHandler.Enter_pressed){

            }
            if(gp.keyHandler.Enter_pressed && !attackCanceled){
                gp.playSE(5);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            collisionOn = true;
            // 检测瓦片碰撞
            gp.cChecker.checkTile(this);
            // 检测物品碰撞
            int objIndex = gp.cChecker.checkObject(this, true);
            if(gp.keyHandler.E_pressed) {pickUpObject(objIndex);}
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
        image = getImage();
        worldCol = this.worldX / gp.tileSize;
        worldRow = this.worldY / gp.tileSize;
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
               gp.playSE(5);
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
            gp.playSE(7);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "your are level " + level + "now!\n" + "you feel stronger";
        }
    }

    private void interactNpc(int npcIndex) {
        if(gp.keyHandler.Enter_pressed){
            if(npcIndex != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npcs[npcIndex].speak();
            }
        }
    }

    private void contactMonster(int monsterIndex) {
        if(monsterIndex != 999){
            if(!invincible){
                gp.playSE(6);
                int damage = gp.monsters[monsterIndex].attack - this.defense;
                if(damage < 0){damage = 0;}
                life -= damage;
                invincible = true;
            }
        }
    }

    public void pickUpObject(int objIndex){
        if(objIndex != 999){
            String text;
            if(inventory.size() < maxInventorySize){
                inventory.add(gp.objects[objIndex]);
                gp.playSE(1);
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
                    tempScreenY = screenY - gp.tileSize;
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
                    tempScreenX = screenX - gp.tileSize;
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

    @Override
    public void draw(Graphics2D g2, int type){


        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void selectObject(int objNum){
        Entity selectObject = inventory.get(objNum);
        // weapon
        if(selectObject.getClass() == OBJ_Sword_Normal.class || selectObject.getClass() == OBJ_Axe.class){
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
