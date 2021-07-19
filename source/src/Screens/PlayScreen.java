
package Screens;

import semestralny.GameRules;
import PieceFactory.*;
import Field.Field;
import Pieces.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import semestralny.GameConsts;
import semestralny.GameRules.HighScore;
import semestralny.Main;

import static semestralny.Main.settings;
import static semestralny.Main.getWord;
import static semestralny.Main.settings;

/**
 *
 * @author Samo
 */
public class PlayScreen extends javax.swing.JFrame {
    
    private Image bg, bg_temp;
    private ArrayList keys;    
    private Field field;
    private FieldPanel panel;
    private GameRules game_rules;
    private TitleScreen parent;
    private Boolean paused;
    private int prev_height = 0;
    private int prev_width = 0;
    private Image[][] tile_images;
    private MediaPlayer music_player;
    private boolean game_over = false;
    private int starting_level;
    
    int piece_count;
    private Piece[] counter_pieces;
    
    private static final Logger LOGGER = Logger.getLogger(TitleScreen.class.getName());
    
    public GameRules screenGetRules() {
        return this.game_rules;
    }
    
    @Override
    public void dispose() {
        if (game_over)
            return;
        
        if (!paused)
            gamePause();
        else
            realClose();
    }
    
    public void realClose() {        
        this.processWindowEvent(
             new WindowEvent(
                   this, WindowEvent.WINDOW_CLOSED));
        field.stop();        
        parent.hide_new_game();
        parent.title_screen_resume_music();
        parent.setVisible(true);
        parent.setSize(this.getWidth(), this.getHeight());
        parent.repaint();
        super.dispose();
    }
    
    public void gameOver() {
        HighScore scores[] = game_rules.getScores();
        game_over = true;
        field.gameOver();
        if (music_player != null)
            music_player.pause();   
        blur_panel_pause.setVisible(true);
        if (field.getScore() > scores[9].getScore()) {
            high_score_name.setText("aaaaa");
            high_score_name.requestFocus();
            high_score_text.setVisible(true);
            high_score_name.setVisible(true);
            high_score_button.setVisible(true);
        }
        else {
            button_try_again.setVisible(true);
            button_quit.setVisible(true);
        }
    }
    
    public void gamePause() {
        Main.play_sound("generic");
        if (music_player != null)
            music_player.pause();
        blur_panel_pause.setVisible(true);
        field.fieldPause();
        this.paused = true;
        this.setTitle(getWord(settings, "tetris_paused"));                
        
        text_paused.setVisible(true);
        button_continue.setVisible(true);
        button_quit.setVisible(true);
    }
    
    public void gameUnpause() {
        Main.play_sound("generic");
        if (music_player != null)
            music_player.play();
        blur_panel_pause.setVisible(false);
        field.fieldUnpause();
        this.paused = false;
        this.requestFocus();
        this.setTitle(getWord(settings, "tetris"));
        
        text_paused.setVisible(false);
        button_continue.setVisible(false);
        button_quit.setVisible(false);
    }
    
    public ArrayList getKeys() {
        return this.keys;
    }
    
    public Field getField() {
        return this.field;
    }
        
    class FieldPanel extends JPanel {
        
        int width;
        int height;
        PlayScreen parent;
        
        public FieldPanel(int w, int h, Field field, PlayScreen parent) {
            width = w;
            height = h;
            this.parent = parent;            
        }
        
        private Image scaleImage(int curr_width, int curr_height) {                     
            
            if (curr_width != prev_width || curr_height != prev_height) {
                prev_height = curr_height;
                prev_width = curr_width;                
                return bg.getScaledInstance(curr_width, curr_height, Image.SCALE_DEFAULT);
            }
            
            return bg_temp;
        }
        
        private void paintBg(Graphics g) {
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            if (bg != null) {
                bg_temp = scaleImage(curr_width, curr_height);
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                ((Graphics2D) g).setComposite(ac);
                g.drawImage(bg_temp, 0, 0, this);
                 ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
                ((Graphics2D) g).setComposite(ac);
            } 
        }
        
        private void paintStrings(Graphics g, int tile_size) {
            
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            
            try {
                Font customFont = Font.createFont(
                        Font.TRUETYPE_FONT, new File("resources\\retro_font.ttf"));
                customFont = customFont.deriveFont(20f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                g.setFont(customFont);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Font could not be loaded: " + e);
            }
                        
            
            g.setColor(Color.black);            
            g.drawString(getWord(settings, "next"), curr_width - 9 * tile_size, curr_height - 9 * tile_size);
            
            if (game_rules.getUse_hold())
                g.drawString(getWord(settings, "held"), curr_width - 9 * tile_size, 5 * tile_size);
            
            String lines_string = getWord(settings, "lines") + ": " + parent.getField().getLineCount();
            String level_string = getWord(settings, "level") + ": " + parent.getField().getLevel();
            String score_string = getWord(settings, "score") + ": " + parent.getField().getScore(); 
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString(lines_string, curr_width - 9 * tile_size, 14 * tile_size);
            g.drawString(level_string, curr_width - 9 * tile_size, 12 * tile_size);
            g.drawString(score_string, curr_width / 2 - ((width/3) * tile_size), curr_height - 3 * tile_size);
        }
        
        private void paintHold(Graphics g, int tile_size) {
            
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            
            for (int i = -2; i <= 2; i++)
                for (int j = -2; j <= 2; j++) {
                    int curr_x = curr_width - 7 * tile_size + i * tile_size;
                    int curr_y = (7 + j) * tile_size;
                    
                    Piece held_piece = parent.getField().getHeldPiece();
                    
                    if (held_piece == null) {                        
                        paintTile(g, tile_size, curr_x, curr_y, -1, false);
                        continue;
                    }
                    
                    if (held_piece.isAt(i, j))                                            
                        paintTile(g, tile_size, curr_x, curr_y, held_piece.getPieceId(), false);
                    else
                        paintTile(g, tile_size, curr_x, curr_y, -1, false);

                }
        }
        
        private void paintNext(Graphics g, int tile_size) {
            
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            
             for (int i = -2; i <= 2; i++) 
                for (int j = -2; j <= 2; j++) {
                    int curr_x = curr_width - 7 * tile_size + i * tile_size;
                    int curr_y = curr_height - 7 * tile_size + j * tile_size;
                    
                    Piece next_piece = parent.getField().getNextPiece();
                    boolean is_at = next_piece.isAt(i, j);
                    if (is_at)
                        paintTile(g, tile_size, curr_x, curr_y, next_piece.getPieceId(), false);
                    else
                        paintTile(g, tile_size, curr_x, curr_y, -1, false);                    
                }
        }
        
        private int getPieceColorValue(int value) {
            return value % 3;
        }
        
        private void paintTile(Graphics g, int tile_size, int x, int y, int tile_value, boolean is_ghost) {
            AlphaComposite ac;
            int curr_level = field.getLevel();
            int piece_color = getPieceColorValue(tile_value);
                            
            // empty tile
            if (piece_color == -1) {
                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
                ((Graphics2D) g).setComposite(ac);
                g.setColor(Color.black);
                g.fillRect(x, y, tile_size, tile_size);
                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
                ((Graphics2D) g).setComposite(ac);
                return;
            }
                        
            Image img = tile_images[curr_level % 10][piece_color];
            
            // ghost has a lower opacity
            if (is_ghost) {
                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
                ((Graphics2D) g).setComposite(ac);
            }  
                     
            // tile with a thing
            if (img != null) {
                g.drawImage(img, x, y, tile_size - 1, tile_size - 1, this);                
            } 
            else {
                g.setColor(Color.darkGray);
                g.fillRect(x, y, tile_size - 1, tile_size - 1);
            }
            
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
            ((Graphics2D) g).setComposite(ac);
        }
        
        private void paintField(Graphics g, int tile_size, int x_board) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++) {
                    Piece curr_piece = field.getCurrPiece();
                    Piece ghost_piece = field.getGhostPiece();
                    boolean pieceThere = curr_piece.isAt(i, j);
                    boolean ghostThere = ghost_piece.isAt(i, j);     
                    
                    int curr_x = x_board + i * tile_size;
                    int curr_y = (j + 1) * tile_size;
                
                    if ((parent.getField().getTiles()[i][j] >= 0)) {
                        paintTile(g, tile_size, curr_x, curr_y, parent.getField().getTiles()[i][j], false);                        
                    }
                    else if (pieceThere) {
                        paintTile(g, tile_size, curr_x, curr_y, curr_piece.getPieceId(), false);
                    }
                    else if (ghostThere && settings.getShow_ghost()) {
                        paintTile(g, tile_size, curr_x, curr_y, ghost_piece.getPieceId(), true);                        
                    }
                    else {
                        paintTile(g, tile_size, curr_x, curr_y, -1, true);                        
                    }
                }
        }
        
        private void paintPieceCounts(Graphics g, int tile_size) {
            int counts[] = field.getPieceCounts();            
            Font customFont = null;
            
            try {
                customFont = Font.createFont(
                        Font.TRUETYPE_FONT, new File("resources\\retro_font.ttf"));
                customFont = customFont.deriveFont(35f);                
            } catch (Exception e) {
                customFont = null;
                LOGGER.log(Level.WARNING, "Font could not be loaded: " + e);
            }  
            
            g.setFont(customFont);
            for (int i = 0; i < GameConsts.PIECE_COUNT_NORMAL; i++) {
                g.drawString(Integer.toString(counts[i]), (int) (tile_size * 5.5f), (3 + i * 3) * tile_size);
                for (int j = -2; j <= 2; j++) {
                    for (int k = -1; k <= 1; k++) {
                        int curr_x = tile_size * 3 + j * tile_size;
                        int curr_y = (2 + i * 3) * tile_size + k * tile_size;
                        
                        if (counter_pieces[i].isAt(j, k))
                            paintTile(g, tile_size, curr_x, curr_y, i, false);                        
                    }
                }
            }
            
            if (counter_pieces.length > GameConsts.PIECE_COUNT_NORMAL)
            for (int i = GameConsts.PIECE_COUNT_NORMAL; i < GameConsts.PIECE_COUNT_FUNNY; i++) {
                g.drawString(Integer.toString(counts[i]), tile_size * 11, (3 + (i - 7) * 3) * tile_size);
                
                for (int j = -2; j <= 2; j++) {
                    for (int k = -1; k <= 1; k++) {
                        int curr_x = tile_size * 8 + j * tile_size;
                        int curr_y = (2 + (i - 7) * 3) * tile_size + k * tile_size;
                                                
                        if (counter_pieces[i].isAt(j, k)) {
                            if (i == 13)
                                paintTile(g, tile_size, curr_x + tile_size, curr_y, i, false);
                            else
                                paintTile(g, tile_size, curr_x, curr_y, i, false);                        
                        }                        
                    }
                }
            }
        }
        
        public void paint(Graphics g) {  
                                    
            int curr_width = parent.getWidth();
            int curr_height = parent.getHeight();
            
            int tile_size = curr_height / 25;
            int x_board = curr_width / 2 - (width/2 * tile_size);
            
            paintBg(g);
            paintStrings(g, tile_size);                                  
            paintField(g, tile_size, x_board);                        
            paintNext(g, tile_size);           
            paintPieceCounts(g, tile_size);
            
            if (game_rules.getUse_hold()) {
                paintHold(g, tile_size);
            }
                        
            high_score_text.setBounds(curr_width /2 - 300 , curr_height / 2 - 400, 600, 200);
            high_score_name.setBounds(curr_width /2 - 200 , curr_height / 2 - 200, 400, 100);            
            text_paused.setBounds(50, 25, 250, 75);
            button_continue.setBounds(curr_width /2 - 200 , curr_height / 2 - 200, 400, 150);
            button_try_again.setBounds(curr_width /2 - 200 , curr_height / 2 - 200, 400, 150);
            button_quit.setBounds(curr_width /2 - 200 , curr_height / 2 + 0, 400, 150);
            high_score_button.setBounds(curr_width /2 - 200 , curr_height / 2 + 0, 400, 150);
        }
    }
    
    private void init_components_manual_playscreen() {        
        blur_panel_pause.setVisible(false);
        blur_panel_pause.setBackground(new Color(128, 128, 128, 192));
        this.requestFocus();
        high_score_name.setVisible(false);
        high_score_text.setVisible(false);
        high_score_button.setVisible(false);
        
        text_paused.setVisible(false);
        button_continue.setVisible(false);
        button_try_again.setVisible(false);
        button_quit.setVisible(false);
        button_continue.setBackground(Color.white);
        button_quit.setBackground(Color.white);
        high_score_button.setBackground(Color.white);
        button_try_again.setBackground(Color.white);
        
        try {
            Font customFont = Font.createFont(
                    Font.TRUETYPE_FONT, new File("resources\\retro_font.ttf"));
            customFont = customFont.deriveFont(35f);
            high_score_name.setHorizontalAlignment(JTextField.CENTER);
            high_score_name.setFont(customFont);
            button_continue.setFont(customFont);
            button_try_again.setFont(customFont);
            button_quit.setFont(customFont);
            text_paused.setFont(customFont.deriveFont(20f));
            high_score_button.setFont(customFont);
            high_score_text.setFont(customFont);
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Font could not be loaded: " + e);
        }
        
        button_try_again.setText(getWord(settings, "try_again"));
        high_score_button.setText(getWord(settings, "save"));
        high_score_text.setText(getWord(settings, "new_hs"));
        button_continue.setText(getWord(settings, "continue"));
        button_quit.setText(getWord(settings, "quit"));
        text_paused.setText(getWord(settings, "paused"));
        
    }
    
    private void read_img() {
        try {
            File pathToFile = new File("resources\\playscreen_bg.jpg");
            bg = ImageIO.read(pathToFile);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "playscreen_bg.jpg loading exception: " + e);
            bg = null;
        } 
    }
    
    private Image[][] load_tile_images() {
        Image temp[][] = new Image[10][3];
       
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 3; j++) {
                try {
                    String path = String.format("resources\\tile%d%d.png", i, j);
                    File file = new File(path);
                    temp[i][j] = ImageIO.read(file);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, String.format("tile%d%d loading exception", i , j) + e);
                    temp[i][j] = null;
                }
            }
        return temp;
    }
     
    private class MusicHandlePlayScreen implements Runnable {

        
        private MusicHandlePlayScreen() {
            final JFXPanel fxPanel = new JFXPanel();
            try {            
                String sound_path = "resources\\music_play.mp3";
                File file = new File(sound_path);
                Media sound = new Media(file.toURI().toString());
                music_player = new MediaPlayer(sound);
                music_player.setAutoPlay(true);
                music_player.setVolume(((double) settings.getMus_volume())/100);
                music_player.setCycleCount(MediaPlayer.INDEFINITE);
                music_player.play();

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Music_play loading exception: " + e);
                music_player = null;
            }
        }
        
        @Override
        public void run() {
            
        }
        
    }
    
    public PlayScreen(GameRules g_rules, TitleScreen parent, int starting_level) {
        this.parent = parent;       
        this.starting_level = starting_level;
        this.game_rules = g_rules;
        this.paused = false;
        keys = new ArrayList<>();
        read_img();
        tile_images = load_tile_images();        
        new MusicHandlePlayScreen();
        
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\icon.png"));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "icon.png loading exception: " + e);
        }               
        
        initComponents(); 
        init_components_manual_playscreen();
        
        PieceFactory factory;
        if (g_rules.getUse_new_piece_generation())
            factory = new PieceFactoryBag(g_rules.getUse_funny_pieces(), g_rules.getBoard_width());
        else
            factory = new PieceFactoryRetro(g_rules.getUse_funny_pieces(), g_rules.getBoard_width());
        
        this.field = new Field(g_rules.getBoard_width(), 20, starting_level, factory, this);
        field.start();
        this.panel = new FieldPanel(g_rules.getBoard_width(), 20, field, this);
        add(panel);
                
        
        if(!g_rules.getUse_funny_pieces()) 
            piece_count = GameConsts.PIECE_COUNT_NORMAL;                  
        else
            piece_count = GameConsts.PIECE_COUNT_FUNNY;
        
        counter_pieces = new Piece[piece_count];
        for (int i = 0; i < piece_count; i++) {
            counter_pieces[i] = PieceFactory.newPiece(i, 0);
            counter_pieces[i].zeroCoords();
        }
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();       
        this.setBounds(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight());
        parent.setVisible(false);
        this.setVisible(true);
        LOGGER.log(Level.INFO, "New playscreen created successfully");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text_paused = new javax.swing.JLabel();
        button_continue = new javax.swing.JButton();
        button_quit = new javax.swing.JButton();
        high_score_text = new javax.swing.JLabel();
        high_score_button = new javax.swing.JButton();
        button_try_again = new javax.swing.JButton();
        high_score_name = new javax.swing.JTextField();
        blur_panel_pause = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tetris");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        text_paused.setText("jLabel1");
        getContentPane().add(text_paused);

        button_continue.setText("jButton1");
        button_continue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_continueMousePressed(evt);
            }
        });
        getContentPane().add(button_continue);

        button_quit.setText("jButton1");
        button_quit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_quitMousePressed(evt);
            }
        });
        getContentPane().add(button_quit);

        high_score_text.setText("jLabel1");
        getContentPane().add(high_score_text);

        high_score_button.setText("jButton1");
        high_score_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                high_score_buttonMousePressed(evt);
            }
        });
        getContentPane().add(high_score_button);

        button_try_again.setText("jButton1");
        button_try_again.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button_try_againMousePressed(evt);
            }
        });
        getContentPane().add(button_try_again);

        high_score_name.setText("jTextField1");
        getContentPane().add(high_score_name);
        getContentPane().add(blur_panel_pause);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(keys.contains(evt.getKeyCode())){
            keys.remove(keys.indexOf(evt.getKeyCode()));
            /*for (Object key :keys)
                System.out.println(KeyEvent.getKeyText((int) key));*/
        }
    }//GEN-LAST:event_formKeyReleased

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        if (!keys.contains(evt.getKeyCode())){
            keys.add(evt.getKeyCode());
            // System.out.println(keys);
        }
    }//GEN-LAST:event_formKeyPressed

    private void button_continueMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_continueMousePressed
        Main.play_sound("generic");
        gameUnpause();
    }//GEN-LAST:event_button_continueMousePressed

    private void button_quitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_quitMousePressed
        Main.play_sound("generic");
        int ans = JOptionPane.showConfirmDialog(rootPane, 
                getWord(settings, "wanna_quit?"), 
                getWord(settings, "quit?"),
                JOptionPane.OK_OPTION);
        if (ans == 0) 
            realClose();
    }//GEN-LAST:event_button_quitMousePressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // gamePause();
    }//GEN-LAST:event_formComponentResized

    private void high_score_buttonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_high_score_buttonMousePressed
        String new_name = high_score_name.getText();
        String regex = "^[a-zA-Z0-9]{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(new_name);
        boolean result = matcher.matches();
        
        if (!result) 
            JOptionPane.showMessageDialog(this, getWord(settings, "save_name_info"));
        else {
            game_rules.addNewScore(new HighScore(field.getScore(), starting_level, new_name));
            high_score_button.setVisible(false);
            high_score_text.setVisible(false);
            high_score_name.setVisible(false);
            button_try_again.setVisible(true);
            button_quit.setVisible(true);
        }
    }//GEN-LAST:event_high_score_buttonMousePressed

    private void button_try_againMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_try_againMousePressed
        paused = false;
        game_over = false;
        blur_panel_pause.setVisible(false);
        button_try_again.setVisible(false);
        button_quit.setVisible(false);
        
        PieceFactory factory;
        if (game_rules.getUse_new_piece_generation())
            factory = new PieceFactoryBag(game_rules.getUse_funny_pieces(), game_rules.getBoard_width());
        else
            factory = new PieceFactoryRetro(game_rules.getUse_funny_pieces(), game_rules.getBoard_width());
        
        this.field = new Field(game_rules.getBoard_width(), 20, starting_level, factory, this);
        field.start();
        
        this.requestFocus();
        if (music_player != null)
            music_player.play();
    }//GEN-LAST:event_button_try_againMousePressed

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
            java.util.logging.Logger.getLogger(PlayScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlayScreen(null, null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel blur_panel_pause;
    private javax.swing.JButton button_continue;
    private javax.swing.JButton button_quit;
    private javax.swing.JButton button_try_again;
    private javax.swing.JButton high_score_button;
    private javax.swing.JTextField high_score_name;
    private javax.swing.JLabel high_score_text;
    private javax.swing.JLabel text_paused;
    // End of variables declaration//GEN-END:variables
}
