package gameContext;

import core.KeyHandler;
import core.MouseHandler;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public interface InputContext {
    public KeyHandler getKeyHandler();
    public MouseHandler getMouseHandler();
}
