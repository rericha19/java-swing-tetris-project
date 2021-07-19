/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import semestralny.GameConsts;

public class PieceFunnyCross extends Piece {
        
    public PieceFunnyCross (int board_w) {
        this.tilecount = 5;
        this.rotationcount = 1;
        this.start_x = board_w / 2;
        this.start_y = 0;
        this.current_state = this.start_state = 0;
        this.piece_id = GameConsts.ID_PIECE_FUNNY_CROSS;
        initPieceStates();        
    }
    
    @Override
    public void pieceSetStates() {
        //                     (x, y, x , y, x , y, x, y)
        this.states[0].setTiles(0, 0, 0, 1, 0, -1, 1, 0, -1, 0);
    }   
}
