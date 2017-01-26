package me.infuzion.tank.wars.client;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import me.infuzion.tank.wars.sound.SoundLoader;

public class Main {

    public static void main(String[] string)
        throws IOException, LineUnavailableException, UnsupportedAudioFileException,
        ClassNotFoundException, UnsupportedLookAndFeelException,
        InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        TankWars tankWars = new TankWars();
        new SoundLoader();
        tankWars.start();
    }
}
