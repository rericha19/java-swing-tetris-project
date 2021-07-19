/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralny;

import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Samo
 */
@XmlRootElement
public class GameSettings {
   
    private int key_left;
    private int key_right;
    private int key_down;
    private int key_rotate1;
    private int key_rotate2;
    private int key_hard_drop;
    private int key_swap_held;
    private int key_pause;

    private Boolean show_ghost;
    private int side_scroll_speed;
    private int after_first_move_delay;
    private int after_hard_drop_delay;

    private int mus_volume;
    private int sfx_volume;
    
    private String language;    
    private String bg_image;
    
    public GameSettings clone() {
        GameSettings new_s = new GameSettings();
        
        new_s.key_left = this.key_left;
        new_s.key_right = this.key_right;
        new_s.key_down = this.key_down;
        new_s.key_rotate1 = this.key_rotate1;
        new_s.key_rotate2 = this.key_rotate2;
        new_s.key_hard_drop = this.key_hard_drop;
        new_s.key_swap_held = this.key_swap_held;
        new_s.show_ghost = this.show_ghost;
        new_s.side_scroll_speed = this.side_scroll_speed;
        new_s.after_first_move_delay = this.after_first_move_delay;        
        new_s.after_hard_drop_delay = this.after_hard_drop_delay;
        new_s.setKey_pause(this.getKey_pause());
        
        new_s.mus_volume = this.mus_volume;
        new_s.sfx_volume = this.sfx_volume;
        new_s.language = new String(this.language);
        new_s.bg_image = new String(this.bg_image);
        
        return new_s;
    }

    public static GameSettings getDefaultSettings() {
        GameSettings settings = new GameSettings();
        settings.setKey_left(KeyEvent.VK_A);
        settings.setKey_right(KeyEvent.VK_D);
        settings.setKey_down(KeyEvent.VK_S);
        settings.setKey_rotate1(KeyEvent.VK_J);
        settings.setKey_rotate2(KeyEvent.VK_L);
        settings.setKey_hard_drop(KeyEvent.VK_SPACE);
        settings.setKey_swap_held(KeyEvent.VK_T);
        settings.setKey_pause(KeyEvent.VK_ESCAPE);

        settings.setShow_ghost((Boolean) true);
        settings.setSide_scroll_speed(6);
        settings.setAfter_first_move_delay(15);
        settings.setAfter_hard_drop_delay(50);

        settings.setMus_volume(35);
        settings.setSfx_volume(35);
                
        settings.setBg_image("path");
        settings.setLanguage("en");
        
        return settings;
    }    
    
    @XmlElement(name = "after_first_move_delay")
    public void setAfter_first_move_delay(int after_first_move_delay) {
        this.after_first_move_delay = after_first_move_delay;
    }

    @XmlElement(name = "after_hard_drop_delay")
    public void setAfter_hard_drop_delay(int after_hard_drop_delay) {
        this.after_hard_drop_delay = after_hard_drop_delay;
    }

    @XmlElement(name = "key_down")
    public void setKey_down(int key_down) {
        this.key_down = key_down;
    }

    @XmlElement(name = "key_hard_drop")
    public void setKey_hard_drop(int key_hard_drop) {
        this.key_hard_drop = key_hard_drop;
    }

    @XmlElement(name = "key_left")
    public void setKey_left(int key_left) {
        this.key_left = key_left;
    }

    @XmlElement(name = "key_right")
    public void setKey_right(int key_right) {
        this.key_right = key_right;
    }

    @XmlElement(name = "key_rotate1")
    public void setKey_rotate1(int key_rotate1) {
        this.key_rotate1 = key_rotate1;
    }

    @XmlElement(name = "key_rotate2")
    public void setKey_rotate2(int key_rotate2) {
        this.key_rotate2 = key_rotate2;
    }

    @XmlElement(name = "key_swap_held")
    public void setKey_swap_held(int key_swap_held) {
        this.key_swap_held = key_swap_held;
    }

    @XmlElement(name = "mus_volume")
    public void setMus_volume(int mus_volume) {
        this.mus_volume = mus_volume;
    }

    @XmlElement(name = "sfx_volume")
    public void setSfx_volume(int sfx_volume) {
        this.sfx_volume = sfx_volume;
    }

    @XmlElement(name = "show_ghost")
    public void setShow_ghost(Boolean show_ghost) {
        this.show_ghost = show_ghost;
    }

    @XmlElement(name = "side_scroll_speed")
    public void setSide_scroll_speed(int side_scroll_speed) {
        this.side_scroll_speed = side_scroll_speed;
    }

    public int getAfter_first_move_delay() {
        return after_first_move_delay;
    }

    public int getAfter_hard_drop_delay() {
        return after_hard_drop_delay;
    }

    public int getKey_down() {
        return key_down;
    }

    public int getKey_hard_drop() {
        return key_hard_drop;
    }

    public int getKey_left() {
        return key_left;
    }

    public int getKey_right() {
        return key_right;
    }

    public int getKey_rotate1() {
        return key_rotate1;
    }

    public int getKey_rotate2() {
        return key_rotate2;
    }

    public int getKey_swap_held() {
        return key_swap_held;
    }

    public int getMus_volume() {
        return mus_volume;
    }

    public int getSfx_volume() {
        return sfx_volume;
    }

    public Boolean getShow_ghost() {
        return show_ghost;
    }

    public int getSide_scroll_speed() {
        return side_scroll_speed;
    }

    // @XmlElement(name = "bg_image")
    public String getBg_image() {
        return bg_image;
    }

    @XmlElement(name = "bg_image")
    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public String getLanguage() {
        return language;
    }

    @XmlElement(name = "language")
    public void setLanguage(String language) {
        this.language = language;
    }       

    public int getKey_pause() {
        return key_pause;
    }

    @XmlElement(name = "key_pause")
    public void setKey_pause(int key_pause) {
        this.key_pause = key_pause;
    }
    
    
}
