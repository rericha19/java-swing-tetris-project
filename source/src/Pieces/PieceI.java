
package Pieces;

import semestralny.GameConsts;


/**
 *
 * @author Samo
 */
public class PieceI extends Piece {
    
    public PieceI (int board_w) {
        this.tilecount = 4;
        this.rotationcount = 2;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 1;
        this.piece_id = GameConsts.ID_PIECE_I;
        initPieceStates();        
    }
    
    @Override
    public void pieceSetStates() {
        //                     (x, y, x , y, x , y, x, y)
        this.states[0].setTiles(0, 0, 0, -2, 0, -1, 0, 1);
        this.states[1].setTiles(0, 0, -2, 0, -1, 0, 1, 0);
    }    
}
