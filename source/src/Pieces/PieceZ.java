
package Pieces;

import semestralny.GameConsts;

/**
 *
 * @author Samo
 */
public class PieceZ extends Piece {
    
    public PieceZ (int board_w) {
        this.rotationcount = 2;
        this.tilecount = 4;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 0;
        this.piece_id = GameConsts.ID_PIECE_Z;
        initPieceStates();
    }


    @Override
    public void pieceSetStates() {
        this.states[0].setTiles(0, 0, -1, 0, 0, 1, 1, 1);
        this.states[1].setTiles(0, 0, 0, 1, 1, 0, 1, -1);
    }
}
