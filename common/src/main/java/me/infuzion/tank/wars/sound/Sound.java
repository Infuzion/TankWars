package me.infuzion.tank.wars.sound;

import me.infuzion.tank.wars.sprite.SpriteDescriptor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Sound {

    private final SpriteDescriptor descriptor;

    public Sound(SpriteDescriptor descriptor, AudioInputStream sound, Clip clip)
        throws LineUnavailableException, IOException {
        this.descriptor = descriptor;
        clip.open(sound);
        clip.start();
        FloatControl control = (FloatControl) clip.getControl(Type.MASTER_GAIN);
        control.setValue(-20);
        clip.loop(Integer.MAX_VALUE);
    }

    public SpriteDescriptor getDescriptor() {
        return descriptor;
    }
}
