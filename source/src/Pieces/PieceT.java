
package Pieces;

import semestralny.GameConsts;

/**
 *
 * @author Samo
 */
public class PieceT extends Piece {

    public PieceT(int board_w) {
        this.rotationcount = 4;
        this.tilecount = 4;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 2;
        this.piece_id = GameConsts.ID_PIECE_T;
        initPieceStates();
    }
    

    @Override
    public void pieceSetStates() {
        this.states[0].setTiles(0, 0, -1, 0, 0, -1, 1, 0);
        this.states[1].setTiles(0, 0, 0, -1, 1, 0, 0, 1);
        this.states[2].setTiles(0, 0, 1, 0, 0, 1, -1, 0);
        this.states[3].setTiles(0, 0, 0, 1, -1, 0, 0, -1);
    }
    
}
