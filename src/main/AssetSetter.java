package main;

import entity.NPC_Soldier;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {

    private GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;


    }


    public void setObject(){
        int i = 0;
        gp.objects[i] = new OBJ_Door(gp);
        gp.objects[i].worldX = gp.tileSize * 21;
        gp.objects[i].worldY = gp.tileSize * 22;
        i ++;
        gp.objects[i] = new OBJ_Door(gp);
        gp.objects[i].worldX = gp.tileSize * 23;
        gp.objects[i].worldY = gp.tileSize * 25;
        i ++;
        gp.objects[i] = new OBJ_Key(gp);
        gp.objects[i].worldX = gp.tileSize * 19;
        gp.objects[i].worldY = gp.tileSize * 22;
        i ++;
        gp.objects[i] = new OBJ_Key(gp);
        gp.objects[i].worldX = gp.tileSize * 19;
        gp.objects[i].worldY = gp.tileSize * 23;
        i ++;
        gp.objects[i] = new OBJ_Key(gp);
        gp.objects[i].worldX = gp.tileSize * 19;
        gp.objects[i].worldY = gp.tileSize * 24;
        i ++;
        gp.objects[i] = new OBJ_Axe(gp);
        gp.objects[i].worldX = gp.tileSize * 18;
        gp.objects[i].worldY = gp.tileSize * 24;
        i ++;
        gp.objects[i] = new OBJ_Potion(gp, 2);
        gp.objects[i].worldX = gp.tileSize * 17;
        gp.objects[i].worldY = gp.tileSize * 24;
    }

    public void setNPC(){
        gp.npcs[0] = new NPC_Soldier(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;

    }

    public void setMonster(){
        gp.monsters[0] = new MON_GreenSlime(gp);
        gp.monsters[0].worldX = gp.tileSize * 23;
        gp.monsters[0].worldY = gp.tileSize * 22;

        gp.monsters[1] = new MON_GreenSlime(gp);
        gp.monsters[1].worldX = gp.tileSize * 23;
        gp.monsters[1].worldY = gp.tileSize * 39;
    }

}
