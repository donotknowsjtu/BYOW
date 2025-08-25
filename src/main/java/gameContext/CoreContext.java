package gameContext;

import core.UtilityTool;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public interface CoreContext {
    // static
    public final int screenColumn = 20;
    public final int screenRow = 15;
    public final int pixel = 48 ;
    public final int tileSize = pixel ;
    public final int screenLength = screenColumn * tileSize;
    public final int screenWidth = screenRow * tileSize;

    public final int maxWorldCol = 50, maxWorldRow = 50;
    public final int maxWorldLength = maxWorldCol * tileSize;
    public final int maxWorldWidth = maxWorldRow * tileSize;


}
