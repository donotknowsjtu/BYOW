package gameContext;

import org.springframework.stereotype.Component;

@Component("stateContext")
public interface StateContext {
    public final int titleState = 0;
    public final int playState = 1;

    public final int pauseState = 2;
    public final int dialogueState = 3;

    int getGameState();

    void setGameState(int state);
}
