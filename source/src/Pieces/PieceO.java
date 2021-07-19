
package Pieces;

import semestralny.GameConsts;

/**
 *
 * @author Samo
 */
public class PieceO extends Piece {

    public PieceO(int board_w) {
        this.tilecount = 4;
        this.rotationcount = 1;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 0;
        this.piece_id = GameConsts.ID_PIECE_O;
        initPieceStates();
    }

    @Override
    public void pieceSetStates() {
        this.states[0].setTiles(0, 0, -1, 0, -1, 1, 0, 1);
    }

    
    
}
