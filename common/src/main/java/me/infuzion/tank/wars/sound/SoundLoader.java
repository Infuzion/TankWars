package me.infuzion.tank.wars.sound;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundLoader {

    public SoundLoader()
        throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream stream = AudioSystem
            .getAudioInputStream(getClass().getResourceAsStream("/sound/bg_music.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(stream);
        clip.start();
        FloatControl control = (FloatControl) clip.getControl(Type.MASTER_GAIN);
        control.setValue(-20);
        clip.loop(Integer.MAX_VALUE);
    }

    public static void main(String[] args)
        throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        new SoundLoader();
        while (true) {
            Thread.sleep(100);
        }
    }
}
