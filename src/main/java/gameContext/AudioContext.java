package gameContext;

import org.springframework.stereotype.Component;

@Component("audioContext")
public interface AudioContext {
    void playMusic(int i);
    void stopMusic();
    void playSE(int i);
}
