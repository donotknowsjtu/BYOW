package main;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    // 音乐和音效使用不同的Clip池
    private Clip musicClip;
    private final Map<Integer, Clip> soundClips = new HashMap<>();
    private final URL[] soundURLs = new URL[30];

    // 当前播放的音乐ID
    private int currentMusicId = -1;

    public Sound() {
        // 初始化所有音效资源路径
        soundURLs[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURLs[1] = getClass().getResource("/sound/coin.wav");
        soundURLs[2] = getClass().getResource("/sound/powerup.wav");
        soundURLs[3] = getClass().getResource("/sound/unlock.wav");
        soundURLs[4] = getClass().getResource("/sound/fanfare.wav");
        soundURLs[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURLs[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURLs[7] = getClass().getResource("/sound/levelup.wav");
        soundURLs[8] = getClass().getResource("/sound/cursor.wav");

        // 预加载所有音效(可选，也可以在第一次播放时懒加载)
        preloadAllSounds();
    }

    /**
     * 预加载所有音效
     */
    private void preloadAllSounds() {
        for (int i = 0; i < soundURLs.length; i++) {
            if (soundURLs[i] != null) {
                loadSound(i);
            }
        }
    }

    /**
     * 加载单个音效
     */
    private void loadSound(int soundId) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURLs[soundId]);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            soundClips.put(soundId, clip);
        } catch (Exception e) {
            System.err.println("Failed to load sound " + soundId);
            e.printStackTrace();
        }
    }

    /**
     * 播放背景音乐
     */
    public void playMusic(int musicId) {
        // 如果已经在播放相同的音乐，则不做任何操作
        if (currentMusicId == musicId && musicClip != null && musicClip.isActive()) {
            return;
        }

        // 停止当前音乐
        stopMusic();

        try {
            // 加载并播放新音乐
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURLs[musicId]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentMusicId = musicId;
        } catch (Exception e) {
            System.err.println("Failed to play music " + musicId);
            e.printStackTrace();
        }
    }

    /**
     * 停止背景音乐
     */
    public void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }
        currentMusicId = -1;
    }

   /**
     * 播放音效(短声音效果)
     */
    public void playSE(int soundId) {
        Clip clip = soundClips.get(soundId);

        // 如果音效未加载，则立即加载
        if (clip == null) {
            loadSound(soundId);
            clip = soundClips.get(soundId);
        }

        // 播放音效
        if (clip != null) {
            // 设置音量为 75%
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = volumeControl.getMinimum() + range * 0.75f; // 75%
            volumeControl.setValue(gain);

            // 重置到开头并播放
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * 检查是否有音乐正在播放
     */
    public boolean isPlaying() {
        return musicClip != null && musicClip.isActive();
    }
}