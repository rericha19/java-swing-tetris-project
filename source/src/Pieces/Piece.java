
package Pieces;

import Field.Field;
import semestralny.Main;

/**
 *
 * @author Samo
 */

// representation of a piece, instead of storing all xy of all tiles, instead
// one xy is stored and each tile is represented using offset of the tile
// i think it makes game logic handling and piece definition a bit easier and
// less piece dependent
public class Piece {
    
    protected int x = 0;                        // current x
    protected int y = 0;                        // current y
    protected int start_x;                      // spawn x
    protected int start_y;                      // spawn y
    protected int tilecount;                    // how many 'squares' its made of
    protected int rotationcount;                // how many possible rotations there are
    protected int current_state = 0;            // which rotation its currently on
    protected int start_state;                  // which rotation it spawns at
    protected PieceState[] states;              // all states
    protected int piece_id;
     
    public void pieceSetStates() {};
    
    // each piece has its own constructor
    // and sets its own spawn state, coords, tilecount, rotationcount etc
    // each piece also has its own overriden pieceSetStates method which defines the rotations
    public Piece() {}
    
    // needed for cloning
    private Piece(int x, int y, int tilecount, int rotationcount, int curr_state, PieceState[] states, int start_x, int start_y, int piece_id) {
        this.x = x;
        this.y = y;
        this.tilecount = tilecount;
        this.rotationcount = rotationcount;
        this.current_state = curr_state;
        this.states = states;
        this.start_x = start_x;
        this.start_y = start_y;
        this.piece_id = piece_id;
    }
    
    // cloning is used for ghost piece, which is a clone copy of the current piece just dropped down
    @Override
    public Piece clone() {
        return new Piece(x, y, tilecount, rotationcount, current_state, states, start_x, start_y, piece_id);
    }
    
    // getter for current state
    public int getState() {
        return this.current_state;
    }
            
    // inits piece states, calls piece-specific method pieceSetStates
    protected void initPieceStates() {
        this.states = new PieceState[rotationcount];
        for (int i = 0; i < rotationcount; i++) 
            this.states[i] = new PieceState(tilecount);
        this.pieceSetStates();
    }
    
    // method returning true if one of its tiles is on said xy coordinate
    public boolean isAt(int x, int y) {
        for (int i = 0; i < tilecount; i++)
            if ((this.x + states[current_state].getTiles()[i][0]) == x && 
                (this.y + states[current_state].getTiles()[i][1]) == y)
                return true;
        
        return false;
    }
        
    // checks whether the piece can fall
    public boolean tryFallUtil(Field f) {
        for (int i = 0; i < tilecount; i++) {
            int x0 = states[current_state].getTiles()[i][0] + x;
            int y0 = 1 + states[current_state].getTiles()[i][1] + y;
            
            if (y0 < 0)
                continue;
            
            if (y0 >= f.getHeight()) 
                return false;
            
            if (x0 < 0 || x0 >= f.getWidth())
                return false;
            
            if (f.getTiles()[x0][y0] >= 0) 
                return false;                           
        }
        
        return true;
    }
    
    // checks whether the piece can fall
    // if not, piece dropped all the way down and makes the field update board etc
    public boolean tryFall(Field f) {
        boolean can_fall = tryFallUtil(f);
                       
        if (can_fall)
            y += 1;
        else {
            for (int i = 0; i < tilecount; i++) {
                int x0 = states[current_state].getTiles()[i][0] + x;
                int y0 = states[current_state].getTiles()[i][1] + y;
                
                
                if (y0 < 0)
                    ;
                else 
                    f.getTiles()[x0][y0] = piece_id;
            }
            f.checkFilled();
            f.nextPiece();
            f.incCounter(piece_id);
        }
        return can_fall;
    }
    
    // tries to rotate the piece left 
    public void tryRotateLeft(Field f) {
        int next_rot = (current_state + rotationcount - 1) % rotationcount;
        if (tryRotateUtil(f, next_rot)) {
            
            current_state = next_rot;
        }
    }
    
    // tries to rotate the piece right
    public void tryRotateRight(Field f) {        
        int next_rot = (current_state + 1) % rotationcount;
        if (tryRotateUtil(f, next_rot)) {
            
            current_state = next_rot;    
        }
    }
    
    // checks whether resulting rotation is valid
    private boolean tryRotateUtil(Field f, int next_rot) {
        for (int i = 0; i < tilecount; i++) {
            int x0 = states[next_rot].getTiles()[i][0] + x;
            int y0 = states[next_rot].getTiles()[i][1] + y;
            
            if (x0 < 0 || x0 >= f.getWidth()) 
                return false;
            
            if (y0 >= f.getHeight()) 
                return false;
            
            if (y0 < 0)
                continue;
            
            if (f.getTiles()[x0][y0] >= 0)
                return false;
        }
        return true;
    }
    
    // tries to move right
    public boolean tryMoveRight(Field f) {
        boolean can_move = tryMoveUtil(f, +1);
        if (can_move) {
            x += 1;    
        }
        
        return can_move;
    }
    
    // tries to move left
    public boolean tryMoveLeft(Field f) {
        boolean can_move = tryMoveUtil(f, -1);
        if (can_move) {
            x -= 1;
        }
        
        return can_move;
    }
    
    // checks whether resulting move is valid
    private boolean tryMoveUtil(Field f, int x_move) {
        
        for (int i = 0; i < tilecount; i++) {
            int x0 = states[current_state].getTiles()[i][0] + x + x_move;            
            int y0 = states[current_state].getTiles()[i][1] + y;
            
            if (x0 < 0 || x0 >= f.getWidth()) 
                return false; 
            if (y0 <= 0)
                continue;
            if (f.getTiles()[x0][y0] >= 0)
                return false;            
        }       
        return true;
    }
    
    // makes the piece drop instantly, returns score increase
    public int hardDrop(Field f) {
        int level = f.getLevel();
        int rows_dropped = 0;
        while (tryFall(f) == true) {
            rows_dropped++;
        }
        
        return (rows_dropped * (level + 1));
    }
    
    // sets coords to spawn ones
    public void resetCoords() {
        this.x = start_x;
        this.y = start_y;
    }
    
    // resets state back to the spawning one (needed when swapping held and current)
    public void resetState() {
        this.current_state = this.start_state;
    }
    
    // zero coords (previews expect the piece to be at 0;0)
    public void zeroCoords() {
        this.x = 0;
        this.y = 0;
    }
    
    // field does this so it needs to be a public method
    public void incY() {
        this.y++;
    }
    
    public int getPieceId() {
        return this.piece_id;
    }
}
