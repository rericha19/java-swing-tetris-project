/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;
import semestralny.GameRules;
import semestralny.GameRules.HighScore;
import semestralny.GameSettings;
import semestralny.Main;
import static semestralny.Main.getWord;

import static semestralny.Main.settings;
import semestralny.XML_Handler;

/**
 *
 * @author Samo
 */
public class TitleScreen extends javax.swing.JFrame {

    // https://cdn.hipwallpaper.com/i/27/84/4GgiCz.jpg
    private Image img2;
    private Image img2_temp;
    TitlePanel panel = null;
    private GameSettings temp_settings;
    private int currently_changed;
    ArrayList<GameRules> loadedRules;
    private int current_game_rule_index;
    MediaPlayer music_player;
    private int prev_sfx_value;

    private static final Logger LOGGER = Logger.getLogger(TitleScreen.class.getName());
    
    @Override
    public void dispose() {
        int ans = JOptionPane.showConfirmDialog(rootPane, 
                getWord(temp_settings, "close_app?"), 
                getWord(temp_settings, "exit?"), 
                JOptionPane.OK_OPTION);
        if (ans == 0) 
            System.exit(0);
    }
          
    public GameSettings getSettings() {
        return temp_settings;
    }
    
    private class TitlePanel extends JPanel {
        
        private TitleScreen parent;
        private int prev_width = 0;
        private int prev_height = 0;
        
        public TitlePanel(TitleScreen par) {
            this.parent = par;
                        
            try {
                Font customFont = Font.createFont(
                        Font.TRUETYPE_FONT, new File("resources\\retro_font.ttf"));
                customFont = customFont.deriveFont(23f);
                customFont = customFont.deriveFont(Font.BOLD);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);                               
                
                button_new_game.setFont(customFont); // NOI18N                
                button_settings.setFont(customFont);
                button_high_scores.setFont(customFont);
                button_quit.setFont(customFont);
                high_scores_back.setFont(customFont);
                new_game_start.setFont(customFont.deriveFont(AffineTransform.getScaleInstance(0.9f, 1f)));
                new_game_back.setFont(customFont);                
                settings_back.setFont(customFont);
                settings_save.setFont(customFont);
                tetris_text.setFont(customFont);
                settings_text.setFont(customFont);
                new_game_text.setFont(customFont);
                high_scores_text.setFont(customFont);
                
                button_arrow_left.setFont(customFont);
                button_arrow_right.setFont(customFont);                                
                
                slider_show_ghost_text.setFont(customFont);
                slider_volume_text.setFont(customFont);
                slider_sfx_text.setFont(customFont);
                slider_after_first_move_delay_text.setFont(customFont);
                slider_after_hard_drop_delay_text.setFont(customFont);
                slider_side_scroll_delay_text.setFont(customFont);
                language_select_text.setFont(customFont);
                
                bind_left.setFont(customFont);
                bind_right.setFont(customFont);
                bind_down.setFont(customFont);
                bind_rotate_1.setFont(customFont);
                bind_rotate_2.setFont(customFont);
                bind_hard_drop.setFont(customFont);
                bind_swap_held.setFont(customFont);
                bind_pause.setFont(customFont);
                
                Font customFontSmaller = customFont.deriveFont(8f);
                customFontSmaller = customFontSmaller.deriveFont(Font.BOLD, AffineTransform.getScaleInstance(0.9f, 1.2f));
                change1.setFont(customFontSmaller);
                change2.setFont(customFontSmaller);
                change3.setFont(customFontSmaller);
                change4.setFont(customFontSmaller);
                change5.setFont(customFontSmaller);
                change6.setFont(customFontSmaller);
                change7.setFont(customFontSmaller);
                change8.setFont(customFontSmaller);
                                                    
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Font could not be loaded: " + e);
            }
                            
            button_new_game.setBackground(Color.white);
            button_settings.setBackground(Color.white);
            button_high_scores.setBackground(Color.white);
            button_quit.setBackground(Color.white);
            new_game_back.setBackground(Color.white);
            settings_back.setBackground(Color.white);
            settings_save.setBackground(Color.white);
            high_scores_back.setBackground(Color.white);
            new_game_start.setBackground(Color.white);
            
            button_arrow_left.setBackground(Color.white);
            button_arrow_right.setBackground(Color.white);
            
            change1.setBackground(Color.white);
            change2.setBackground(Color.white);
            change3.setBackground(Color.white);
            change4.setBackground(Color.white);
            change5.setBackground(Color.white);
            change6.setBackground(Color.white);
            change7.setBackground(Color.white);
            change8.setBackground(Color.white);
                        
            tetris_text.setForeground(Color.white);
            settings_text.setForeground(Color.black);
            new_game_text.setForeground(Color.black);
            high_scores_text.setForeground(Color.black);   
            
            game_rule_name.setForeground(Color.white);
            game_rule_board_width.setForeground(Color.white);
            game_rule_funny_pieces.setForeground(Color.white);
            game_rule_hold.setForeground(Color.white);
            game_rule_piece_generation.setForeground(Color.white);
            game_rule_scores.setForeground(Color.white);
            game_rule_hard_drop.setForeground(Color.white);
            new_game_start_level.setForeground(Color.white);
        }
        
        private Image scaleImage(int curr_width, int curr_height) {                     
            
            if (curr_width != prev_width || curr_height != prev_height) {
                prev_height = curr_height;
                prev_width = curr_width;                
                return img2.getScaledInstance(curr_width, curr_height, Image.SCALE_DEFAULT);
            }
            
            return img2_temp;
        }
        
        
        public void paint(Graphics g) {
            
            temp_settings.setMus_volume(slider_volume.getValue());
            temp_settings.setSfx_volume(slider_sfx.getValue());
            if (music_player != null)
                music_player.setVolume(((double) temp_settings.getMus_volume()) / 100);
            
            if (slider_sfx.getValue() != prev_sfx_value)
                Main.play_sound("generic", temp_settings.getSfx_volume());
            prev_sfx_value = slider_sfx.getValue();
            
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            
            button_new_game.setText(getWord(temp_settings, "new_game"));
            button_settings.setText(getWord(temp_settings, "settings"));
            button_high_scores.setText(getWord(temp_settings, "high_scores"));
            button_quit.setText(getWord(temp_settings, "quit"));
            high_scores_back.setText(getWord(temp_settings, "back"));
            new_game_back.setText(getWord(temp_settings, "back"));
            settings_back.setText(getWord(temp_settings, "back"));
            settings_save.setText(getWord(temp_settings, "save"));
            tetris_text.setText(getWord(temp_settings, "tetris_caps"));
            settings_text.setText(getWord(temp_settings, "settings_caps"));
            new_game_text.setText(getWord(temp_settings, "new_game_caps"));
            high_scores_text.setText(getWord(temp_settings, "high_scores_caps"));
            new_game_start.setText(getWord(temp_settings, "start"));
            
            button_arrow_left.setText("←");
            button_arrow_right.setText("→");
            
            change1.setText(getWord(temp_settings, "change"));
            change2.setText(getWord(temp_settings, "change"));
            change3.setText(getWord(temp_settings, "change"));
            change4.setText(getWord(temp_settings, "change"));
            change5.setText(getWord(temp_settings, "change"));
            change6.setText(getWord(temp_settings, "change"));
            change7.setText(getWord(temp_settings, "change"));
            change8.setText(getWord(temp_settings, "change"));
            
            GameRules curr_rule = loadedRules.get(current_game_rule_index);
            
            game_rule_name.                 setText(getWord(temp_settings, "mode") + ": " + curr_rule.getName());
            game_rule_board_width.          setText(getWord(temp_settings, "board_width") + curr_rule.getBoard_width());
            if (curr_rule.getUse_funny_pieces())
                game_rule_funny_pieces.     setText(getWord(temp_settings, "uses_funny_pieces_yes"));
            else
               game_rule_funny_pieces.      setText(getWord(temp_settings, "uses_funny_pieces_no"));
               
            if (curr_rule.getUse_hold())
                game_rule_hold.             setText(getWord(temp_settings, "uses_hold_yes"));
            else
                game_rule_hold.             setText(getWord(temp_settings, "uses_hold_no"));
            
            if (curr_rule.getUse_new_piece_generation())
                game_rule_piece_generation. setText(getWord(temp_settings, "piece_gen_new"));
            else
                game_rule_piece_generation. setText(getWord(temp_settings, "piece_gen_old"));
            
            if (curr_rule.getUse_hard_drop())
                game_rule_hard_drop.        setText(getWord(temp_settings, "uses_hard_drop_yes"));
            else
                game_rule_hard_drop.        setText(getWord(temp_settings, "uses_hard_drop_no"));
                
            new_game_start_level.           setText(getWord(temp_settings, "starting_level") + slider_starting_level.getValue());
            slider_starting_level.setBounds(curr_width * 3 / 5,  curr_height * 21 / 40, curr_width / 8, slider_starting_level.getHeight());
            
            String scores_string = "<html>Scores:<br><br>Name&#160&#160&nbsp;&nbsp;&nbsp;&nbsp;Start&#160&#160&nbsp;&nbsp;Score<br>";
            for (HighScore score: curr_rule.getScores()) {
                String string_temp = String.format("%5s&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%02d", 
                        score.getName(), score.getStarting_level());
                String string_temp2 = String.format("%07d", score.getScore());
                scores_string = scores_string.concat(string_temp + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + string_temp2 + "<br>");
            }
            scores_string = scores_string.concat("</html>");
            game_rule_scores.setText(scores_string);
                       
            game_rule_name.setBounds(curr_width * 7 / 10 - game_rule_name.getWidth() * 2 / 5, curr_height / 5 + game_rule_name.getHeight() * 2 /5, game_rule_name.getWidth(), game_rule_name.getHeight());
            game_rule_piece_generation.setBounds(curr_width * 3 / 5, curr_height * 5 / 20, game_rule_piece_generation.getWidth(), game_rule_piece_generation.getHeight());
            game_rule_hold.setBounds(curr_width * 3 / 5, curr_height * 6 / 20, game_rule_hold.getWidth(), game_rule_hold.getHeight());
            game_rule_funny_pieces.setBounds(curr_width * 3 / 5, curr_height * 8 / 20, game_rule_funny_pieces.getWidth(), game_rule_funny_pieces.getHeight());
            game_rule_board_width.setBounds(curr_width * 3 / 5, curr_height * 9 / 20, game_rule_board_width.getWidth(), game_rule_board_width.getHeight());
            game_rule_hard_drop.setBounds(curr_width * 3 / 5, curr_height * 7 / 20, game_rule_hard_drop.getWidth(), game_rule_hard_drop.getHeight());
            new_game_start_level.setBounds(curr_width * 3 / 5, curr_height * 10 / 20, new_game_start_level.getWidth(), new_game_start_level.getHeight());            
            game_rule_scores.setBounds(curr_width * 3 / 5, curr_width / 15, curr_width, curr_height);
            
            slider_volume_text.setText(getWord(temp_settings, "music_vol") + slider_volume.getValue());
            slider_sfx_text.setText(getWord(temp_settings, "sfx_vol") + slider_sfx.getValue());
            slider_after_first_move_delay_text.setText(getWord(temp_settings, "first_move_delay") + slider_after_first_move_delay.getValue());
            slider_side_scroll_delay_text.setText(getWord(temp_settings, "side_scroll_delay") + slider_side_scroll_delay.getValue());
            slider_after_hard_drop_delay_text.setText(getWord(temp_settings, "hard_drop_delay") + slider_after_hard_drop_delay.getValue());
            if (slider_show_ghost.getValue() == 1)
                slider_show_ghost_text.setText(getWord(temp_settings, "show_ghost_yes"));
            else
                slider_show_ghost_text.setText(getWord(temp_settings, "show_ghost_no"));
            if (slider_language.getValue() == 1)
                temp_settings.setLanguage("sk");
            else
                temp_settings.setLanguage("en");
            
            language_select_text.setText(getWord(temp_settings, "language") + temp_settings.getLanguage());
            
            bind_left.      setText(getWord(temp_settings, "move_left") + KeyEvent.getKeyText(temp_settings.getKey_left()));
            bind_right.     setText(getWord(temp_settings, "move_right") + KeyEvent.getKeyText(temp_settings.getKey_right()));
            bind_down.      setText(getWord(temp_settings, "move_down") + KeyEvent.getKeyText(temp_settings.getKey_down()));
            bind_rotate_1.  setText(getWord(temp_settings, "rotate_1") + KeyEvent.getKeyText(temp_settings.getKey_rotate1()));
            bind_rotate_2.  setText(getWord(temp_settings, "rotate_2") + KeyEvent.getKeyText(temp_settings.getKey_rotate2()));
            bind_hard_drop. setText(getWord(temp_settings, "insta_drop") + KeyEvent.getKeyText(temp_settings.getKey_hard_drop()));
            bind_swap_held. setText(getWord(temp_settings, "swap_held") + KeyEvent.getKeyText(temp_settings.getKey_swap_held()));
            bind_pause.     setText(getWord(temp_settings, "pause") + KeyEvent.getKeyText(temp_settings.getKey_pause()));
            
            bind_left.setBounds(curr_width / 2 - 30, curr_height * 6 / 33 - 30, bind_left.getWidth(), bind_left.getHeight());
            bind_right.setBounds(curr_width / 2 - 30, curr_height * 8 / 33 - 30, bind_right.getWidth(), bind_right.getHeight());
            bind_down.setBounds(curr_width / 2 - 30, curr_height * 10 / 33 - 30, bind_down.getWidth(), bind_down.getHeight());
            bind_rotate_1.setBounds(curr_width / 2 - 30, curr_height * 12 / 33 - 30, bind_rotate_1.getWidth(), bind_rotate_1.getHeight());
            bind_rotate_2.setBounds(curr_width / 2 - 30, curr_height * 14 / 33 - 30, bind_rotate_2.getWidth(), bind_rotate_2.getHeight());
            bind_hard_drop.setBounds(curr_width / 2 - 30, curr_height * 16 / 33 - 30, bind_hard_drop.getWidth(), bind_hard_drop.getHeight());
            bind_swap_held.setBounds(curr_width / 2 - 30, curr_height * 18 / 33 - 30, bind_swap_held.getWidth(), bind_swap_held.getHeight());
            bind_pause.setBounds(curr_width / 2 - 30, curr_height * 20 / 33 - 30, bind_pause.getWidth(), bind_pause.getHeight());
              
            change1.setBounds(curr_width * 2 / 3 + 10, curr_height *  6 / 33 - 35, 80, 30);  
            change2.setBounds(curr_width * 2 / 3 + 10, curr_height *  8 / 33 - 35, 80, 30);
            change3.setBounds(curr_width * 2 / 3 + 10, curr_height * 10 / 33 - 35, 80, 30);
            change4.setBounds(curr_width * 2 / 3 + 10, curr_height * 12 / 33 - 35, 80, 30);
            change5.setBounds(curr_width * 2 / 3 + 10, curr_height * 14 / 33 - 35, 80, 30);
            change6.setBounds(curr_width * 2 / 3 + 10, curr_height * 16 / 33 - 35, 80, 30);
            change7.setBounds(curr_width * 2 / 3 + 10, curr_height * 18 / 33 - 35, 80, 30);
            change8.setBounds(curr_width * 2 / 3 + 10, curr_height * 20 / 33 - 35, 80, 30);
            
            button_arrow_left.setBounds(curr_width * 3 / 5, curr_height / 5, 80, 30);
            button_arrow_right.setBounds(curr_width * 4 / 5, curr_height / 5, 80, 30);
            
            button_new_game.setBounds(curr_width - 450, curr_height * 1 / 6, 300,  curr_height * 1 / 9);
            button_settings.setBounds(curr_width - 450, curr_height * 2 / 6, 300,  curr_height * 1 / 9);
            button_high_scores.setBounds(curr_width - 450, curr_height * 3 / 6, 300,  curr_height * 1 / 9);
            button_quit.setBounds(curr_width - 450, curr_height * 4 / 6, 300,  curr_height * 1 / 9);
            high_scores_back.setBounds(curr_width - 300, 50, 150, curr_height / 18);
            settings_back.setBounds(curr_width - 300, 50, 150, curr_height / 18);
            new_game_back.setBounds(curr_width - 300, 50, 150, curr_height / 18);
            settings_save.setBounds(curr_width - 300, curr_height * 8 / 10, 150, curr_height / 18);
            new_game_start.setBounds(curr_width - 300, curr_height * 8 / 10, 150, curr_height / 18);

            slider_volume_text.setBounds(curr_width * 3 / 4, curr_height * 2 / 12 - 30, slider_volume_text.getWidth(), slider_volume_text.getHeight());
            slider_volume.setBounds(curr_width * 3 / 4, curr_height * 2 / 12, curr_width / 8, slider_volume.getHeight());
            
            slider_sfx_text.setBounds(curr_width * 3 / 4, curr_height * 3 / 12 - 30, slider_sfx_text.getWidth(), slider_sfx_text.getHeight());
            slider_sfx.setBounds(curr_width * 3 / 4, curr_height * 3 / 12, curr_width / 8, slider_sfx.getHeight());
            
            slider_show_ghost_text.setBounds(curr_width * 3 / 4, curr_height * 4 / 12 - 30, slider_show_ghost_text.getWidth(), slider_show_ghost_text.getHeight());
            slider_show_ghost.setBounds(curr_width * 3 / 4, curr_height * 4 / 12, curr_width / 8, slider_show_ghost.getHeight());
            
            slider_side_scroll_delay_text.setBounds(curr_width * 3 / 4, curr_height * 5 / 12 - 30,
                    slider_side_scroll_delay_text.getWidth(), slider_side_scroll_delay_text.getHeight());
            slider_side_scroll_delay.setBounds(curr_width * 3 / 4, curr_height * 5 / 12, curr_width / 8, slider_side_scroll_delay.getHeight());
            
            slider_after_first_move_delay_text.setBounds(curr_width * 3 / 4, curr_height * 6 / 12 - 30,
                    slider_after_first_move_delay_text.getWidth(), slider_after_first_move_delay_text.getHeight());
            slider_after_first_move_delay.setBounds(curr_width * 3 / 4, curr_height * 6 / 12, curr_width / 8, slider_after_first_move_delay.getHeight());
            
            slider_after_hard_drop_delay_text.setBounds(curr_width * 3 / 4, curr_height * 7 / 12 - 30,
                    slider_after_hard_drop_delay_text.getWidth(), slider_after_hard_drop_delay_text.getHeight());
            slider_after_hard_drop_delay.setBounds(curr_width * 3 / 4, curr_height * 7 / 12, curr_width / 8, slider_after_hard_drop_delay.getHeight());
            
            language_select_text.setBounds(curr_width * 3 / 4, curr_height * 8 / 12 - 30, language_select_text.getWidth(), language_select_text.getHeight());
            slider_language.setBounds(curr_width * 3 / 4, curr_height * 8 /12, curr_width / 8, slider_language.getHeight());
            
            tetris_text.setBounds(25, (int) (curr_height / 3.5f), curr_width, curr_height);
            settings_text.setBounds(75, (int ) (- curr_height / 2.5f), curr_width, curr_height);
            new_game_text.setBounds(75, (int ) (- curr_height / 2.5f), curr_width, curr_height);
            high_scores_text.setBounds(75, (int ) (- curr_height / 2.5f), curr_width, curr_height);
            
            Font font = tetris_text.getFont().deriveFont((float) curr_width / 11);            
            tetris_text.setFont(font);
            
            Font font2 = settings_text.getFont().deriveFont((float) curr_width / 20);            
            settings_text.setFont(font2);
            new_game_text.setFont(font2);
            high_scores_text.setFont(font2);
            
            Font font3 = slider_volume_text.getFont().deriveFont((float) curr_width / 100);
            slider_volume_text.setFont(font3);
            slider_sfx_text.setFont(font3);
            slider_show_ghost_text.setFont(font3);
            slider_side_scroll_delay_text.setFont(font3);
            slider_after_first_move_delay_text.setFont(font3);
            slider_after_hard_drop_delay_text.setFont(font3);
            language_select_text.setFont(font3);
            new_game_start_level.setFont(font3);
            
            game_rule_name.setFont(font3);
            game_rule_board_width.setFont(font3);
            game_rule_funny_pieces.setFont(font3);
            game_rule_hold.setFont(font3);
            game_rule_piece_generation.setFont(font3);
            game_rule_hard_drop.setFont(font3);
            game_rule_scores.setFont(font3);
            
            bind_left.setFont(font3);
            bind_right.setFont(font3);
            bind_down.setFont(font3);
            bind_rotate_1.setFont(font3);
            bind_rotate_2.setFont(font3);
            bind_hard_drop.setFont(font3);
            bind_swap_held.setFont(font3);
            bind_pause.setFont(font3);
            
            if (img2 != null) {
                img2_temp = scaleImage(curr_width, curr_height);
                g.drawImage(img2_temp, 0, 0, this);
            }                          
        }
    }
    
    private void readImgs() {       
        
        try {
            File pathToFile = new File("resources\\titlescreen2.jpg");
            img2 = ImageIO.read(pathToFile);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Titlescreen bg could not be loaded: " + e);
            img2 = null;
        } 
    }
    
    private void reset_slider_values() {
        slider_volume.setValue(temp_settings.getMus_volume());
        slider_sfx.setValue(temp_settings.getSfx_volume());
        Boolean curr_ghost = temp_settings.getShow_ghost();
        if (curr_ghost)
            slider_show_ghost.setValue(1);
        else
            slider_show_ghost.setValue(0);  
        slider_side_scroll_delay.setValue(temp_settings.getSide_scroll_speed());
        slider_after_first_move_delay.setValue(temp_settings.getAfter_first_move_delay());
        slider_after_hard_drop_delay.setValue(temp_settings.getAfter_hard_drop_delay());
        
    }
    
    private void init_components_manual() {
        high_scores_back.setVisible(false);
        new_game_back.setVisible(false);
        settings_back.setVisible(false);
        settings_save.setVisible(false);
        settings_text.setVisible(false);
        new_game_text.setVisible(false);
        high_scores_text.setVisible(false);
                
        slider_volume_text.setVisible(false);
        slider_volume.setVisible(false); 
        slider_volume.setMinimum(0);       
        slider_volume.setMaximum(100);
        
        slider_sfx_text.setVisible(false);
        slider_sfx.setVisible(false);
        slider_sfx.setMinimum(0);       
        slider_sfx.setMaximum(100);
        prev_sfx_value = slider_sfx.getValue();
         
        slider_show_ghost_text.setVisible(false);
        slider_show_ghost.setVisible(false);
        slider_show_ghost.setMinimum(0);
        slider_show_ghost.setMaximum(1);        
        
        slider_side_scroll_delay_text.setVisible(false);
        slider_side_scroll_delay.setVisible(false);
        slider_side_scroll_delay.setMinimum(2);
        slider_side_scroll_delay.setMaximum(20);        
          
        slider_after_first_move_delay_text.setVisible(false);
        slider_after_first_move_delay.setVisible(false);
        slider_after_first_move_delay.setMinimum(0);
        slider_after_first_move_delay.setMaximum(20);        
        
        slider_after_hard_drop_delay_text.setVisible(false);        
        slider_after_hard_drop_delay.setVisible(false);
        slider_after_hard_drop_delay.setMinimum(0);
        slider_after_hard_drop_delay.setMaximum(50);
        
        slider_language.setMinimum(0);
        slider_language.setMaximum(1);
        if (temp_settings.getLanguage().equals("sk"))
            slider_language.setValue(1);
        else
            slider_language.setValue(0);
        
        bind_left.setVisible(false);
        bind_right.setVisible(false);
        bind_down.setVisible(false);
        bind_rotate_1.setVisible(false);
        bind_rotate_2.setVisible(false);
        bind_hard_drop.setVisible(false);
        bind_swap_held.setVisible(false);
        bind_pause.setVisible(false);
        
        change1.setVisible(false);
        change2.setVisible(false);
        change3.setVisible(false);
        change4.setVisible(false);
        change5.setVisible(false);
        change6.setVisible(false);
        change7.setVisible(false);
        change8.setVisible(false);
        
        button_arrow_left.setVisible(false);
        button_arrow_right.setVisible(false);
        
        game_rule_board_width.setVisible(false);
        game_rule_funny_pieces.setVisible(false);
        game_rule_hold.setVisible(false);
        game_rule_name.setVisible(false);
        game_rule_piece_generation.setVisible(false);
        game_rule_hard_drop.setVisible(false);
        game_rule_scores.setVisible(false);  
        
        new_game_start_level.setVisible(false);
        slider_starting_level.setVisible(false);
        slider_starting_level.setMinimum(0);
        slider_starting_level.setMaximum(40);
        slider_starting_level.setValue(0);
        
        language_select_text.setVisible(false);
        slider_language.setVisible(false);
        new_game_start.setVisible(false);
        
        cosmeticPanel.setVisible(false);
        cosmeticPanel.setBackground(new Color(255, 255, 255, 128));
    }
            
    private void loadGameRules() {
        
        loadedRules = XML_Handler.loadGameRules();
        if (loadedRules.isEmpty()) {
            try {
                XML_Handler.saveGameRules(GameRules.getDefaultRulesClassic());
                XML_Handler.saveGameRules(GameRules.getDefaultRulesModern());
                XML_Handler.saveGameRules(GameRules.getDefaultRulesFunny());
                loadedRules = XML_Handler.loadGameRules();
            } catch (JAXBException e) {
                LOGGER.log(Level.SEVERE, "Exception when trying to save newly made default game rules: " + e);
                System.exit(1);
            }
        }
    }
    
    private class MusicHandle implements Runnable {
              
        private MusicHandle() {
            final JFXPanel fxPanel = new JFXPanel();
            try {            
                String sound_path = "resources\\music_title.mp3";
                File file = new File(sound_path);
                Media sound = new Media(file.toURI().toString());
                music_player = new MediaPlayer(sound);
                music_player.setAutoPlay(true);
                music_player.setVolume(((double) settings.getMus_volume())/100);
                music_player.setCycleCount(MediaPlayer.INDEFINITE);
                music_player.play();

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Title screen music loading exception: " + e);
                music_player = null;
            }
        }

        @Override
        public void run() {
            
        }              
    }
        
    public TitleScreen() {
               
        temp_settings = settings.clone();
        
        initComponents();  
        init_components_manual();
                       
        slider_volume.setValue(settings.getMus_volume());
        
        readImgs();
        loadGameRules();
        current_game_rule_index = 0;
        // System.out.println(loadedRules.size());
        
        panel = new TitlePanel(this);
        this.add(panel);
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\icon.png"));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "icon.png could not be loaded: " + e);
        }
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();       
        this.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        
        new MusicHandle();                
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_new_game = new javax.swing.JButton();
        button_settings = new javax.swing.JButton();
        button_high_scores = new javax.swing.JButton();
        button_quit = new javax.swing.JButton();
        tetris_text = new javax.swing.JLabel();
        settings_text = new javax.swing.JLabel();
        new_game_text = new javax.swing.JLabel();
        high_scores_text = new javax.swing.JLabel();
        high_scores_back = new javax.swing.JButton();
        new_game_back = new javax.swing.JButton();
        settings_back = new javax.swing.JButton();
        settings_save = new javax.swing.JButton();
        slider_volume_text = new javax.swing.JLabel();
        slider_volume = new javax.swing.JSlider();
        slider_sfx_text = new javax.swing.JLabel();
        slider_sfx = new javax.swing.JSlider();
        slider_show_ghost_text = new javax.swing.JLabel();
        slider_show_ghost = new javax.swing.JSlider();
        slider_side_scroll_delay_text = new javax.swing.JLabel();
        slider_side_scroll_delay = new javax.swing.JSlider();
        slider_after_first_move_delay_text = new javax.swing.JLabel();
        slider_after_first_move_delay = new javax.swing.JSlider();
        slider_after_hard_drop_delay_text = new javax.swing.JLabel();
        slider_after_hard_drop_delay = new javax.swing.JSlider();
        language_select_text = new javax.swing.JLabel();
        slider_language = new javax.swing.JSlider();
        bind_left = new javax.swing.JLabel();
        bind_right = new javax.swing.JLabel();
        bind_down = new javax.swing.JLabel();
        bind_rotate_1 = new javax.swing.JLabel();
        bind_rotate_2 = new javax.swing.JLabel();
        bind_hard_drop = new javax.swing.JLabel();
        bind_swap_held = new javax.swing.JLabel();
        bind_pause = new javax.swing.JLabel();
        change1 = new javax.swing.JButton();
        change2 = new javax.swing.JButton();
        change3 = new javax.swing.JButton();
        change4 = new javax.swing.JButton();
        change5 = new javax.swing.JButton();
        change6 = new javax.swing.JButton();
        change7 = new javax.swing.JButton();
        change8 = new javax.swing.JButton();
        button_arrow_left = new javax.swing.JButton();
        button_arrow_right = new javax.swing.JButton();
        game_rule_name = new javax.swing.JLabel();
        game_rule_hold = new javax.swing.JLabel();
        game_rule_funny_pieces = new javax.swing.JLabel();
        game_rule_piece_generation = new javax.swing.JLabel();
        game_rule_board_width = new javax.swing.JLabel();
        game_rule_scores = new javax.swing.JLabel();
        new_game_start_level = new javax.swing.JLabel();
        slider_starting_level = new javax.swing.JSlider();
        new_game_start = new javax.swing.JButton();
        game_rule_hard_drop = new javax.swing.JLabel();
        cosmeticPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tetris");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        button_new_game.setText("jButton1");
        button_new_game.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_new_gameMousePressed(evt);
            }
        });
        getContentPane().add(button_new_game);

        button_settings.setText("jButton2");
        button_settings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_settingsMousePressed(evt);
            }
        });
        getContentPane().add(button_settings);

        button_high_scores.setText("jButton3");
        button_high_scores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_high_scoresMousePressed(evt);
            }
        });
        getContentPane().add(button_high_scores);

        button_quit.setText("jButton4");
        button_quit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button_quitMouseReleased(evt);
            }
        });
        getContentPane().add(button_quit);

        tetris_text.setText("jLabel1");
        getContentPane().add(tetris_text);

        settings_text.setText("jLabel1");
        getContentPane().add(settings_text);

        new_game_text.setText("jLabel1");
        getContentPane().add(new_game_text);

        high_scores_text.setText("jLabel2");
        getContentPane().add(high_scores_text);

        high_scores_back.setText("jButton5");
        high_scores_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                high_scores_backMousePressed(evt);
            }
        });
        getContentPane().add(high_scores_back);

        new_game_back.setText("jButton1");
        new_game_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                new_game_backMousePressed(evt);
            }
        });
        getContentPane().add(new_game_back);

        settings_back.setText("jButton2");
        settings_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                settings_backMousePressed(evt);
            }
        });
        getContentPane().add(settings_back);

        settings_save.setText("jButton1");
        settings_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                settings_saveMousePressed(evt);
            }
        });
        getContentPane().add(settings_save);

        slider_volume_text.setText("jLabel1");
        getContentPane().add(slider_volume_text);
        getContentPane().add(slider_volume);

        slider_sfx_text.setText("jLabel2");
        getContentPane().add(slider_sfx_text);
        getContentPane().add(slider_sfx);

        slider_show_ghost_text.setText("jLabel3");
        getContentPane().add(slider_show_ghost_text);
        getContentPane().add(slider_show_ghost);

        slider_side_scroll_delay_text.setText("jLabel4");
        getContentPane().add(slider_side_scroll_delay_text);
        getContentPane().add(slider_side_scroll_delay);

        slider_after_first_move_delay_text.setText("jLabel5");
        getContentPane().add(slider_after_first_move_delay_text);
        getContentPane().add(slider_after_first_move_delay);

        slider_after_hard_drop_delay_text.setText("jLabel6");
        getContentPane().add(slider_after_hard_drop_delay_text);
        getContentPane().add(slider_after_hard_drop_delay);

        language_select_text.setText("jLabel7");
        getContentPane().add(language_select_text);
        getContentPane().add(slider_language);

        bind_left.setText("jLabel1");
        getContentPane().add(bind_left);

        bind_right.setText("jLabel2");
        getContentPane().add(bind_right);

        bind_down.setText("jLabel3");
        getContentPane().add(bind_down);

        bind_rotate_1.setText("jLabel4");
        getContentPane().add(bind_rotate_1);

        bind_rotate_2.setText("jLabel5");
        getContentPane().add(bind_rotate_2);

        bind_hard_drop.setText("jLabel6");
        getContentPane().add(bind_hard_drop);

        bind_swap_held.setText("jLabel7");
        getContentPane().add(bind_swap_held);

        bind_pause.setText("jLabel1");
        getContentPane().add(bind_pause);

        change1.setText("jButton1");
        change1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change1MousePressed(evt);
            }
        });
        getContentPane().add(change1);

        change2.setText("jButton2");
        change2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change2MousePressed(evt);
            }
        });
        getContentPane().add(change2);

        change3.setText("jButton3");
        change3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change3MousePressed(evt);
            }
        });
        getContentPane().add(change3);

        change4.setText("jButton4");
        change4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change4MousePressed(evt);
            }
        });
        getContentPane().add(change4);

        change5.setText("jButton5");
        change5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change5MousePressed(evt);
            }
        });
        getContentPane().add(change5);

        change6.setText("jButton6");
        change6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change6MousePressed(evt);
            }
        });
        getContentPane().add(change6);

        change7.setText("jButton7");
        change7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change7MousePressed(evt);
            }
        });
        getContentPane().add(change7);

        change8.setText("jButton1");
        change8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                change8MousePressed(evt);
            }
        });
        getContentPane().add(change8);

        button_arrow_left.setText("jButton1");
        button_arrow_left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_arrow_leftMousePressed(evt);
            }
        });
        getContentPane().add(button_arrow_left);

        button_arrow_right.setText("jButton2");
        button_arrow_right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_arrow_rightMousePressed(evt);
            }
        });
        getContentPane().add(button_arrow_right);

        game_rule_name.setText("jLabel1");
        getContentPane().add(game_rule_name);

        game_rule_hold.setText("jLabel1");
        getContentPane().add(game_rule_hold);

        game_rule_funny_pieces.setText("jLabel2");
        getContentPane().add(game_rule_funny_pieces);

        game_rule_piece_generation.setText("jLabel3");
        getContentPane().add(game_rule_piece_generation);

        game_rule_board_width.setText("jLabel4");
        getContentPane().add(game_rule_board_width);

        game_rule_scores.setText("jLabel5");
        getContentPane().add(game_rule_scores);

        new_game_start_level.setText("jLabel1");
        getContentPane().add(new_game_start_level);
        getContentPane().add(slider_starting_level);

        new_game_start.setText("jButton1");
        new_game_start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                new_game_startMousePressed(evt);
            }
        });
        getContentPane().add(new_game_start);

        game_rule_hard_drop.setText("jLabel1");
        getContentPane().add(game_rule_hard_drop);
        getContentPane().add(cosmeticPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_quitMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_quitMouseReleased
        Main.play_sound("generic");
        dispose();
    }//GEN-LAST:event_button_quitMouseReleased

    private void button_new_gameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_new_gameMousePressed
        
        Main.play_sound("generic");
        this.setTitle(getWord(temp_settings, "tetris_new_game"));
        button_new_game.setVisible(false);
        button_settings.setVisible(false);
        button_high_scores.setVisible(false);
        button_quit.setVisible(false);
        
        button_arrow_left.setVisible(true);
        button_arrow_right.setVisible(true);
        
        game_rule_board_width.setVisible(true);
        game_rule_funny_pieces.setVisible(true);
        game_rule_hold.setVisible(true);
        game_rule_name.setVisible(true);
        game_rule_hard_drop.setVisible(true);
        game_rule_piece_generation.setVisible(true);
        
        new_game_back.setVisible(true);
        new_game_text.setVisible(true);
        
        cosmeticPanel.setVisible(true);
        
        new_game_start.setVisible(true);
        new_game_start_level.setVisible(true);
        slider_starting_level.setVisible(true);
                
    }//GEN-LAST:event_button_new_gameMousePressed

    private void button_high_scoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_high_scoresMousePressed
        
        Main.play_sound("generic");
        this.setTitle(getWord(temp_settings, "tetris_high_scores"));
        button_new_game.setVisible(false);
        button_settings.setVisible(false);
        button_high_scores.setVisible(false);
        button_quit.setVisible(false);
        
        button_arrow_left.setVisible(true);
        button_arrow_right.setVisible(true);
        high_scores_back.setVisible(true);
        high_scores_text.setVisible(true);
        
        game_rule_board_width.setVisible(true);
        game_rule_funny_pieces.setVisible(true);
        game_rule_hold.setVisible(true);
        game_rule_name.setVisible(true);
        game_rule_piece_generation.setVisible(true);
        game_rule_hard_drop.setVisible(true);
        game_rule_scores.setVisible(true);
        
        cosmeticPanel.setVisible(true);
    }//GEN-LAST:event_button_high_scoresMousePressed

    private void high_scores_backMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_high_scores_backMousePressed
        
        Main.play_sound("generic");
        this.setTitle(getWord(temp_settings, "tetris"));
        button_new_game.setVisible(true);
        button_settings.setVisible(true);
        button_high_scores.setVisible(true);
        button_quit.setVisible(true);
        
        button_arrow_left.setVisible(false);
        button_arrow_right.setVisible(false);
        high_scores_back.setVisible(false);
        high_scores_text.setVisible(false);    
        
        game_rule_board_width.setVisible(false);
        game_rule_funny_pieces.setVisible(false);
        game_rule_hold.setVisible(false);
        game_rule_name.setVisible(false);
        game_rule_piece_generation.setVisible(false);
        game_rule_hard_drop.setVisible(false);
        game_rule_scores.setVisible(false);
        
        current_game_rule_index = 0;
        cosmeticPanel.setVisible(false);
    }//GEN-LAST:event_high_scores_backMousePressed

    
    private void hide_settings() {
        
        temp_settings = settings.clone();
        if (temp_settings.getLanguage().equals("sk"))
            slider_language.setValue(1);
        else
            slider_language.setValue(0);
        this.setTitle(getWord(temp_settings, "tetris"));
        button_new_game.setVisible(true);
        button_settings.setVisible(true);
        button_high_scores.setVisible(true);
        button_quit.setVisible(true);
        settings_back.setVisible(false);
        settings_save.setVisible(false);
        settings_text.setVisible(false);
       
        slider_volume_text.setVisible(false);
        slider_volume.setVisible(false);
        
        slider_sfx_text.setVisible(false);
        slider_sfx.setVisible(false);
        
        slider_show_ghost_text.setVisible(false);
        slider_show_ghost.setVisible(false);
        
        slider_side_scroll_delay_text.setVisible(false);
        slider_side_scroll_delay.setVisible(false);
        
        slider_after_first_move_delay_text.setVisible(false);
        slider_after_first_move_delay.setVisible(false);
        
        slider_after_hard_drop_delay_text.setVisible(false);
        slider_after_hard_drop_delay.setVisible(false);
        
        bind_left.setVisible(false);
        bind_right.setVisible(false);
        bind_down.setVisible(false);
        bind_rotate_1.setVisible(false);
        bind_rotate_2.setVisible(false);
        bind_hard_drop.setVisible(false);
        bind_swap_held.setVisible(false);
        bind_pause.setVisible(false);
        
        change1.setVisible(false);
        change2.setVisible(false);
        change3.setVisible(false);
        change4.setVisible(false);
        change5.setVisible(false);
        change6.setVisible(false);
        change7.setVisible(false);
        change8.setVisible(false);
        
        slider_language.setVisible(false);
        language_select_text.setVisible(false);
        
        cosmeticPanel.setVisible(false);
    }
    
    private void settings_backMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settings_backMousePressed
        Main.play_sound("generic");
        temp_settings = settings.clone();
        slider_volume.setValue(temp_settings.getMus_volume());
        hide_settings();
    }//GEN-LAST:event_settings_backMousePressed

    private void button_settingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_settingsMousePressed
        
        Main.play_sound("generic");
        this.setTitle(getWord(temp_settings, "tetris_settings"));
        button_new_game.setVisible(false);
        button_settings.setVisible(false);
        button_high_scores.setVisible(false);
        button_quit.setVisible(false);
        settings_back.setVisible(true);
        settings_save.setVisible(true);
        settings_text.setVisible(true);
        
        slider_volume_text.setVisible(true);
        slider_volume.setVisible(true);
        
        slider_sfx_text.setVisible(true);
        slider_sfx.setVisible(true);
        
        slider_show_ghost_text.setVisible(true);
        slider_show_ghost.setVisible(true);
        
        slider_side_scroll_delay_text.setVisible(true);
        slider_side_scroll_delay.setVisible(true);
        
        slider_after_first_move_delay_text.setVisible(true);
        slider_after_first_move_delay.setVisible(true);
        
        slider_after_hard_drop_delay_text.setVisible(true);
        slider_after_hard_drop_delay.setVisible(true);
        
        slider_language.setVisible(true);
        language_select_text.setVisible(true);
        
        bind_left.setVisible(true);
        bind_right.setVisible(true);
        bind_down.setVisible(true);
        bind_rotate_1.setVisible(true);
        bind_rotate_2.setVisible(true);
        bind_hard_drop.setVisible(true);
        bind_swap_held.setVisible(true);
        bind_pause.setVisible(true);        
        
        change1.setVisible(true);
        change2.setVisible(true);
        change3.setVisible(true);
        change4.setVisible(true);
        change5.setVisible(true);
        change6.setVisible(true);
        change7.setVisible(true);
        change8.setVisible(true);
        
        temp_settings = settings.clone();    
        reset_slider_values();
        cosmeticPanel.setVisible(true);
    }//GEN-LAST:event_button_settingsMousePressed

    public void hide_new_game() {
        this.setTitle(getWord(temp_settings, "tetris"));
        button_new_game.setVisible(true);
        button_settings.setVisible(true);
        button_high_scores.setVisible(true);
        button_quit.setVisible(true);
        
        button_arrow_left.setVisible(false);
        button_arrow_right.setVisible(false);
        new_game_back.setVisible(false);
        new_game_text.setVisible(false);
        
        game_rule_board_width.setVisible(false);
        game_rule_funny_pieces.setVisible(false);
        game_rule_hold.setVisible(false);
        game_rule_name.setVisible(false);
        game_rule_piece_generation.setVisible(false);
        game_rule_hard_drop.setVisible(false);
        
        current_game_rule_index = 0;
        
        new_game_start.setVisible(false);
        new_game_start_level.setVisible(false);
        slider_starting_level.setVisible(false);
        slider_starting_level.setValue(0);
        
        cosmeticPanel.setVisible(false);
    }
    
    private void new_game_backMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_new_game_backMousePressed
        Main.play_sound("generic");
        hide_new_game();
    }//GEN-LAST:event_new_game_backMousePressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (panel != null)
            panel.repaint();
    }//GEN-LAST:event_formComponentResized

    private void settings_saveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settings_saveMousePressed
        Main.play_sound("generic", temp_settings.getSfx_volume());
        int ans = JOptionPane.showConfirmDialog(rootPane, 
                getWord(temp_settings, "wanna_save?"), 
                getWord(temp_settings, "save?"), 
                JOptionPane.OK_OPTION);
        if (ans == 1) 
            return;
        
        temp_settings.setMus_volume(slider_volume.getValue());
        temp_settings.setSfx_volume(slider_sfx.getValue());
        temp_settings.setSide_scroll_speed(slider_side_scroll_delay.getValue());
        temp_settings.setAfter_first_move_delay(slider_after_first_move_delay.getValue());
        temp_settings.setAfter_hard_drop_delay(slider_after_hard_drop_delay.getValue());        
        
        if (slider_show_ghost.getValue() == 1)
            temp_settings.setShow_ghost(true);
        else
            temp_settings.setShow_ghost(false);
        settings = temp_settings.clone();
        hide_settings();
    }//GEN-LAST:event_settings_saveMousePressed

    public void setKeybindVar(int keycode) {
        switch (currently_changed) {
            case 1:
                temp_settings.setKey_left(keycode);
                break;
            case 2:
                temp_settings.setKey_right(keycode);
                break;
            case 3:
                temp_settings.setKey_down(keycode);
                break;
            case 4:
                temp_settings.setKey_rotate1(keycode);
                break;
            case 5:
                temp_settings.setKey_rotate2(keycode);
                break;
            case 6:
                temp_settings.setKey_hard_drop(keycode);
                break;
            case 7:
                temp_settings.setKey_swap_held(keycode);
                break;
            case 8:
                temp_settings.setKey_pause(keycode);
                break;
            default: 
                break;
        }
    }
    
    private void change1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change1MousePressed
        currently_changed = 1;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change1MousePressed

    private void change2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change2MousePressed
        currently_changed = 2;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change2MousePressed

    private void change3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change3MousePressed
        currently_changed = 3;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change3MousePressed

    private void change4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change4MousePressed
        currently_changed = 4;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change4MousePressed

    private void change5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change5MousePressed
        currently_changed = 5;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change5MousePressed

    private void change6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change6MousePressed
        currently_changed = 6;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change6MousePressed

    private void change7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change7MousePressed
        currently_changed = 7;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change7MousePressed

    private void change8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change8MousePressed
        currently_changed = 8;
        Main.play_sound("generic", temp_settings.getSfx_volume());
        new ButtonChange(this);
    }//GEN-LAST:event_change8MousePressed

    private void button_arrow_leftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_arrow_leftMousePressed
        current_game_rule_index = (current_game_rule_index + (loadedRules.size()) - 1) % loadedRules.size();
        Main.play_sound("generic");
        repaint();
    }//GEN-LAST:event_button_arrow_leftMousePressed

    private void button_arrow_rightMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_arrow_rightMousePressed
        current_game_rule_index = (current_game_rule_index + 1) % loadedRules.size();
        Main.play_sound("generic");
        repaint();
    }//GEN-LAST:event_button_arrow_rightMousePressed

    private void new_game_startMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_new_game_startMousePressed
        Main.play_sound("generic");
        if (music_player != null)            
            music_player.pause();
        PlayScreen playscreen = new PlayScreen(loadedRules.get(current_game_rule_index), this, slider_starting_level.getValue());        
    }//GEN-LAST:event_new_game_startMousePressed

    
    public void title_screen_resume_music() {
        if (music_player != null)
            music_player.play();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TitleScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TitleScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TitleScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TitleScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TitleScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bind_down;
    private javax.swing.JLabel bind_hard_drop;
    private javax.swing.JLabel bind_left;
    private javax.swing.JLabel bind_pause;
    private javax.swing.JLabel bind_right;
    private javax.swing.JLabel bind_rotate_1;
    private javax.swing.JLabel bind_rotate_2;
    private javax.swing.JLabel bind_swap_held;
    private javax.swing.JButton button_arrow_left;
    private javax.swing.JButton button_arrow_right;
    private javax.swing.JButton button_high_scores;
    private javax.swing.JButton button_new_game;
    private javax.swing.JButton button_quit;
    private javax.swing.JButton button_settings;
    private javax.swing.JButton change1;
    private javax.swing.JButton change2;
    private javax.swing.JButton change3;
    private javax.swing.JButton change4;
    private javax.swing.JButton change5;
    private javax.swing.JButton change6;
    private javax.swing.JButton change7;
    private javax.swing.JButton change8;
    private javax.swing.JPanel cosmeticPanel;
    private javax.swing.JLabel game_rule_board_width;
    private javax.swing.JLabel game_rule_funny_pieces;
    private javax.swing.JLabel game_rule_hard_drop;
    private javax.swing.JLabel game_rule_hold;
    private javax.swing.JLabel game_rule_name;
    private javax.swing.JLabel game_rule_piece_generation;
    private javax.swing.JLabel game_rule_scores;
    private javax.swing.JButton high_scores_back;
    private javax.swing.JLabel high_scores_text;
    private javax.swing.JLabel language_select_text;
    private javax.swing.JButton new_game_back;
    private javax.swing.JButton new_game_start;
    private javax.swing.JLabel new_game_start_level;
    private javax.swing.JLabel new_game_text;
    private javax.swing.JButton settings_back;
    private javax.swing.JButton settings_save;
    private javax.swing.JLabel settings_text;
    private javax.swing.JSlider slider_after_first_move_delay;
    private javax.swing.JLabel slider_after_first_move_delay_text;
    private javax.swing.JSlider slider_after_hard_drop_delay;
    private javax.swing.JLabel slider_after_hard_drop_delay_text;
    private javax.swing.JSlider slider_language;
    private javax.swing.JSlider slider_sfx;
    private javax.swing.JLabel slider_sfx_text;
    private javax.swing.JSlider slider_show_ghost;
    private javax.swing.JLabel slider_show_ghost_text;
    private javax.swing.JSlider slider_side_scroll_delay;
    private javax.swing.JLabel slider_side_scroll_delay_text;
    private javax.swing.JSlider slider_starting_level;
    private javax.swing.JSlider slider_volume;
    private javax.swing.JLabel slider_volume_text;
    private javax.swing.JLabel tetris_text;
    // End of variables declaration//GEN-END:variables
}
