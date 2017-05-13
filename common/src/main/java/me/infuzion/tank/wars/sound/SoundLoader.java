package me.infuzion.tank.wars.sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SoundLoader {

    public SoundLoader()
        throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream stream = AudioSystem
            .getAudioInputStream(
                new BufferedInputStream(getClass().getResourceAsStream("/sound/bg_music.wav")));
        Clip clip = AudioSystem.getClip();
//        clip.open(stream);
//        clip.start();
//        FloatControl control = (FloatControl) clip.getControl(Type.MASTER_GAIN);
//        control.setValue(-20);
//        clip.loop(Integer.MAX_VALUE);
    }

    public static void main(String[] args)
        throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        new SoundLoader();
        while (true) {
            Thread.sleep(100);
        }
    }
}
