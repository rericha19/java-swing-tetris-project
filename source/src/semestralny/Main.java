/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralny;

import Screens.TitleScreen;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static semestralny.XML_Handler.*;

/**
 *
 * @author Samo
 */
public class Main {

    public static GameSettings settings;     
    private static MediaPlayer sound_back;
    private static MediaPlayer sound_burn;
    private static MediaPlayer sound_drop;
    private static MediaPlayer sound_game_over;
    private static MediaPlayer sound_generic;
    private static MediaPlayer sound_move;
    private static MediaPlayer sound_next_level;
    private static MediaPlayer sound_tetris;
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static String getWord(GameSettings settings, String key) {
        Locale loc_to_use;
        if ("sk".equals(settings.getLanguage()))
            loc_to_use = new Locale("sk", "SK");
        else
            loc_to_use = Locale.ENGLISH;
        
        ResourceBundle words = ResourceBundle.getBundle("internationalisation/strings", loc_to_use);
        
        return words.getString(key);
    }
    
    private static class SoundPlayer implements Runnable {
        
        MediaPlayer snd;
        
        SoundPlayer(MediaPlayer sound, double volume) {
            snd = sound;
            snd.seek(snd.getStartTime());
            snd.setVolume(volume);
            snd.play();
        }
        
        SoundPlayer(MediaPlayer sound) {
            // snd = new MediaPlayer(sound.getMedia());
            snd = sound;
            snd.seek(snd.getStartTime());
            snd.setVolume(((double) settings.getSfx_volume())/100);
            snd.play();
        }
        
        @Override
        public void run() {
            
            
        }        
    }
    
    public static void play_sound(String name, int volume) {
        MediaPlayer sound = null;
        switch(name) {
            case "back":
                sound = sound_back;
                break;
            case "burn":
                sound = sound_burn;                
                break;
            case "drop":
                sound = sound_drop;
                break;
            case "game_over":
                sound = sound_game_over;
                break;
            case "generic":
                sound = sound_generic;
                break;
            case "move":
                sound = sound_move;
                break;
            case "next_level":
                sound = sound_next_level;
                break;
            case "tetris":
                sound = sound_tetris;
            default:
                break;
        }
        
        if (sound != null) {            
            new SoundPlayer(sound, ((double) volume)/100);
        }
    }
    
    public static void play_sound(String name) {
        
        MediaPlayer sound = null;
        switch(name) {
            case "back":
                sound = sound_back;
                break;
            case "burn":
                sound = sound_burn;                
                break;
            case "drop":
                sound = sound_drop;
                break;
            case "game_over":
                sound = sound_game_over;
                break;
            case "generic":
                sound = sound_generic;
                break;
            case "move":
                sound = sound_move;
                break;
            case "next_level":
                sound = sound_next_level;
                break;
            case "tetris":
                sound = sound_tetris;
            default:
                break;
        }
        
        if (sound != null)
            new SoundPlayer(sound);
    }
    
    private static void init_sounds() {
        
        // needs to be initiated otherwise the sounds dont play
        final JFXPanel fxPanel = new JFXPanel();
        
        try {            
            String sound_path = "resources\\sound_back.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_back = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_back = null;
        }
        
        try {            
            String sound_path = "resources\\sound_burn.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_burn = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_burn = null;
        }
        
        try {            
            String sound_path = "resources\\sound_drop.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_drop = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_drop = null;
        }
        
        try {            
            String sound_path = "resources\\sound_game_over.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_game_over = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_game_over = null;
        }
        
        try {            
            String sound_path = "resources\\sound_generic.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_generic = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_generic = null;
        }
        
        try {            
            String sound_path = "resources\\sound_move.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_move = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_move = null;
        }
        
        try {            
            String sound_path = "resources\\sound_next_level.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_next_level = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_next_level = null;
        }
        
        try {            
            String sound_path = "resources\\sound_tetris.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_tetris = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_tetris = null;
        }
        
        try {            
            String sound_path = "resources\\sound_back.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_back = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_back = null;
        }
        
        try {            
            String sound_path = "resources\\sound_back.mp3";
            File file = new File(sound_path);
            Media sound = new Media(file.toURI().toString());
            sound_back = new MediaPlayer(sound);            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when reading sound:" + e);
            sound_back = null;
        }
    }
    
    public static void main(String[] args) {
        try {
            settings = getSettings();
            init_sounds();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Fatal exception when attempting to load settings" + e);;
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(Main::close));
        new TitleScreen();
    }
    
    
    private static void close() {
        try {            
            saveSettings(settings);
            LOGGER.log(Level.INFO, "Saved settings on exit");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save settigns on exit");
        }        
    }
    
}
