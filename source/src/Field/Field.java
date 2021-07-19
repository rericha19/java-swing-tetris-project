package Field;
import Pieces.Piece;
import PieceFactory.PieceFactory;
import PieceFactory.PieceFactoryRetro;
import Screens.PlayScreen;
import semestralny.GameConsts;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import semestralny.GameRules;
import semestralny.GameSettings;
import semestralny.Main;


import static semestralny.Main.settings;
/**
 *
 * @author Samo
 */
public class Field extends Thread {
    private int [][] tile_matrix;           // representation of the playfield
    private int width;                      // dimensions of the playfield
    private int height;
    private Piece current_piece;            // piece currently in the field (falling)
    private Piece next_piece;               // next piece (is shown in the next piece preview)
    private Piece held_piece;               // currently held piece, can be swapped for the current piece
    private int current_level;              // speed level the game's currently on, defines piece's falling speed
    private int start_level;                // starting level, used when saving games
    private int score;                      // current score, used for saving games
    private int line_count;                 // lines cleared, used for level progression and as a statistic
    private PieceFactory piece_gen;         // object responsible for serving next piece, can be pseudorandom, 7bag ...
    private PlayScreen parent;              // parent JFrame, provides key inputs and does drawing of the game field
    private boolean can_swap_held;          
    
    private boolean can_pause;
    private boolean can_soft_drop;          // used for piece movement code to make it comfortable and
    private boolean can_hard_drop;          // responsive 
    private boolean can_rotate_left;        // so it doesnt spin like crazy when u hold rotate, u need to let go to make it go off again
    private boolean can_rotate_right;
    private boolean has_moved = false;      // needed to make tucks convenient (otherwise you would have to time it very precisely)
    private int move_timer = 0;             // makes sure it doesnt go turbo fast horizonally when u hold left/right
    private int hard_drop_timer = 0;        // used to make sure you dont accidentally hard drop right after a piece spawned
    private int move_timer2 = 0;            // makes sure you dont move twice when u tap a key for a bit too long
    private int fall_timer = 0;             // so it only drops the piece every couple ticks when u hold down instead of every tick
    
    private int soft_drop_points = 0;       // when soft dropping a piece, you get points based on how much u dropped it
    private Boolean paused;
    private Boolean game_ended;
    
    int counters[];
    int piece_count;
    
    private static final Logger LOGGER = Logger.getLogger(Field.class.getName());
    
    public Field(int w, int h, int start_level, PieceFactory piecegen, PlayScreen parent) {
        this.start_level = start_level;
        current_level = start_level;
        score = 0;
        line_count = 0;
        width = w;
        height = h;
        held_piece = piecegen.getPiece();
        can_swap_held = true;
        game_ended = false;
        this.parent = parent;
        piece_gen = piecegen;
        tile_matrix = new int[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                tile_matrix[i][j] = -1;
        
        if (piece_gen instanceof PieceFactoryRetro)
            piece_count = GameConsts.PIECE_COUNT_NORMAL;
        else
            piece_count = GameConsts.PIECE_COUNT_FUNNY;
        
        counters = new int[piece_count];
        for (int i = 0; i < piece_count; i++)
            counters[i] = 0;
        
        if (settings == null) {
            settings = GameSettings.getDefaultSettings();
            LOGGER.log(Level.INFO, "field was provided no settings, picking default");            
        }
        
        LOGGER.log(Level.INFO, "field succesfully created");
    }
    
    public int[][] getTiles() {
        return this.tile_matrix;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    // checks filled rows, updates score and lines
    // called when a piece is dropped and new one is spawned
    public void checkFilled() {
        int lines_filled = 0;
        for (int i = height - 1; i > 0; i--) {
            boolean is_full = true;
            for (int j = 0; j < width; j++)
                if (tile_matrix[j][i] == -1) {
                    is_full = false;
                    break;
                }
            
            if (is_full) {
                lines_filled++;
                for (int j = i; j > 0; j--)
                    for (int k = 0; k < width; k++) {
                        tile_matrix[k][j] = tile_matrix[k][j - 1];
                        tile_matrix[k][j - 1] = -1;
                    }
                i = height;
            }
        }
        
        updateLines(lines_filled);
        score += scoreInc(lines_filled);
        
        if (lines_filled == 4)
            Main.play_sound("tetris");
        else if (lines_filled > 0)
            Main.play_sound("burn");
    }
    
    // increases score based on number of lines cleared and level
    // same as the NES tetris scoring system
    private int scoreInc(int lines) {
         return GameConsts.LINE_SCORE_MULT[lines] * (current_level + 1);
    }
    
    // updates current level and current line count
    private void updateLines(int lines) {
        
        int line_count_prev = line_count;
        for (int i = 0; i < lines; i++) {
            line_count++;
            if (line_count % GameConsts.LINES_PER_LEVEL == 0) {
                int prev = current_level;
                current_level = Math.max(current_level, 
                                        line_count / GameConsts.LINES_PER_LEVEL);
                int curr = current_level;
                if (curr > prev) {
                    Main.play_sound("next_level");
                    LOGGER.log(Level.FINE, "curr level: " + current_level);
                }
            }
        }
        
        if (line_count != line_count_prev)
            LOGGER.log(Level.FINE, "curr lines: " + line_count);
    }
    
    // resets move ti a adddadadadamer 
    private void resetMoveTimer() {
        move_timer = settings.getSide_scroll_speed();     
    }
    
    // decrements move timer
    private void decMoveTimer() {
        if (move_timer > 0)
            move_timer--;
    }
    
    // resets fall timer
    private void resetFallTimer() {
        fall_timer = GameConsts.FALL_DELAY;   
    }
    
    // decrements faa aall timer
    private void decFallTimer() {
        if (fall_timer > 0)
            fall_timer--;
    }
    
    private void resetHardDropTimer() {
        hard_drop_timer = settings.getAfter_hard_drop_delay();  
    }
    
    private void decHardDropTimer() {
        if (hard_drop_timer > 0)
            hard_drop_timer--;
    }
    
    public int[] getPieceCounts() {
        return this.counters;
    }
    
    // main loop
    public void run() {
        int tick = 0;
            
            current_piece = piece_gen.getPiece();
            current_piece.resetCoords();
            next_piece = piece_gen.getPiece();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Field thread new game could not sleep: " + e);
            };
            
            paused = false;
            can_pause = true;
            
            while (true) {                
                
                try {
                    Thread.sleep(10);
                    
                    if (!paused) {
                        tick += 1;                    
                        if (tick % (100 / (Math.min(current_level/2, 99) + 1)) == 0)
                        if (current_piece.tryFall(this) == false) {
                            Thread.sleep(200);
                        }
                    
                        decHardDropTimer();
                        decMoveTimer();
                        decFallTimer();                                                                                       
                    }
                    
                    checkInput(parent.getKeys());                    
                    parent.repaint();
                    Thread.yield();
                } catch (Exception e) {
                   LOGGER.log(Level.WARNING, "Field thread tick sleep exception: " + e);
                };
            }
    }
    
    public void incCounter(int id) {
        counters[id]++;
    }
    
    // getter for current piece
    public Piece getCurrPiece() {
        return this.current_piece;
    }  
    
    
    private Boolean check_new_piece_end_of_game(Piece curr_piece, Field field) {
        for (int i = 0; i < field.width; i++) 
            for (int j = 0; j < field.height; j++) {
                if (field.getTiles()[i][j] >= 0 && curr_piece.isAt(i, j))
                    return true;
            }
        
        return false;
    }
    
    public void gameOver() {
        this.fieldPause();
        game_ended = true;
        
    }
    
    // spawns next piece, resets necessary input variables
    public void nextPiece() {
        
        current_piece = next_piece;
        next_piece = piece_gen.getPiece();
        current_piece.resetCoords();
        if (check_new_piece_end_of_game(current_piece, this)) {
            LOGGER.log(Level.INFO, "game ended");
            Main.play_sound("game_over");
            parent.gameOver();
        }
        can_swap_held = true;
        score += soft_drop_points;
        soft_drop_points = 0;
        can_soft_drop = false;   
        resetHardDropTimer();
        Main.play_sound("move");
    }
    
    // getter for next piece
    public Piece getNextPiece() {
        return next_piece;
    }
    
    // getter for held piece
    public Piece getHeldPiece() {
        return held_piece;
    }
    
    // tries to swap held piece with current, if cannot swap doesnt
    public void trySwapHeld() {
        if (can_swap_held == false) 
            return;
        
        can_swap_held = false;
        if (held_piece == null) {
            held_piece = current_piece;
            held_piece.zeroCoords();     
            held_piece.resetState();
            nextPiece();
        } else {
            Piece temp = held_piece;
            held_piece = current_piece;
            current_piece = temp;
            held_piece.zeroCoords();
            held_piece.resetState();
            current_piece.resetCoords();
        }
    }
    
    // creates and returns ghost piece based on the current piece
    public Piece getGhostPiece() {
        Piece temp = current_piece.clone();
        while (temp.tryFallUtil(this))
            temp.incY();
        
        return temp;
    }
    
    // getter for line count
    public int getLineCount() {
        return this.line_count;
    }
    
    // getter for current level
    public int getLevel() {
        return this.current_level;
    }
    
    // getter for score
    public int getScore() {
        return this.score;
    }
    
    // input management code, in parent component keys are stored in an arraylist
    // and updated using keyPressed and keyReleased, which alone arent good for
    // managing actual movement
    void checkInput(ArrayList keys) {                       
        
        if (game_ended)
            return;
        
        if (keys.contains(settings.getKey_pause())) {
            if (can_pause) {
                if (!paused)                
                    parent.gamePause();
                else
                    parent.gameUnpause();
            }
            can_pause = false;
        } 
        else 
            can_pause = true;
        
        if (paused)
            return;
        
        // move left
        if (keys.contains(settings.getKey_left())) {
            if ((move_timer == 0 && (move_timer2 == 0 || move_timer2 > settings.getAfter_first_move_delay()))
                    || !has_moved) {
                if (current_piece.tryMoveLeft(this)) {                   
                    resetMoveTimer();
                    has_moved = true;
                }
            }           
        }
        
        // move right
        if (keys.contains(settings.getKey_right())) {
            if ((move_timer == 0 && (move_timer2 == 0 || move_timer2 > settings.getAfter_first_move_delay())) || !has_moved) {
                if (current_piece.tryMoveRight(this)) {
                    has_moved = true;
                    resetMoveTimer();
                }
            }            
        } 
        
        // has moved
        if (keys.contains(settings.getKey_left()) || keys.contains(settings.getKey_right()))
            move_timer2++;
        else {
            move_timer2 = 0;
            has_moved = false;
        }
        
        // soft drop (fall faster)
        if (keys.contains(settings.getKey_down())) {
            if (can_soft_drop && fall_timer == 0) {
                current_piece.tryFall(this);
                resetFallTimer(); 
                int temp = (int) ((Math.random() * 100 % 3) * (current_level + 1));
                // System.out.println(temp);
                soft_drop_points += temp;
            }
        } else {
            soft_drop_points = 0;
            can_soft_drop = true;
        }
        
        // hard drop
        if (parent.screenGetRules().getUse_hard_drop()) {
            if (keys.contains(settings.getKey_hard_drop())) {
                if (can_hard_drop && hard_drop_timer == 0) {
                    int score_inc = current_piece.hardDrop(this);
                    score = score + score_inc;
                    can_hard_drop = false;
                }
            } else {
                can_hard_drop = true;
            }
        }
        
        // swap held
        if (parent.screenGetRules().getUse_hold()) {
            if (keys.contains(settings.getKey_swap_held())) {           
                trySwapHeld();
            }
        }
        // rotate left
        if (keys.contains(settings.getKey_rotate1())) {
            if (can_rotate_left) {
                current_piece.tryRotateLeft(this);
                can_rotate_left = false;
            }
        } else {
            can_rotate_left = true;
        }
        
        // rotate right
        if (keys.contains(settings.getKey_rotate2())) {
            if (can_rotate_right) {
                current_piece.tryRotateRight(this);
                can_rotate_right = false;
            }
        } else {
            can_rotate_right = true;
        }
    }
    
    public void fieldPause() {
        this.paused = true;
    }
    
    public void fieldUnpause() {
        this.paused = false;
    }
}
