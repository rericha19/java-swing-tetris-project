/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import semestralny.GameConsts;

/**
 *
 * @author Samo
 */
public class PieceFunnyL1 extends Piece {
    
      public PieceFunnyL1 (int board_w) {
        this.rotationcount = 4;
        this.tilecount = 5;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 1;
        this.piece_id = GameConsts.ID_PIECE_FUNNY_L1;
        initPieceStates();
    }
    
    @Override
    public void pieceSetStates() {
        //                     (x, y, x, y , x , y , x ,  y)
        this.states[0].setTiles(-1, 0, -1, -1,  -1,  1,  0,  1, 1, 1);
        this.states[1].setTiles(0, -1, 1,  -1, -1,  -1, -1,  0, -1, 1);
        this.states[2].setTiles(1, 0, 1,  1,  1, -1, 0, -1, -1, -1);
        this.states[3].setTiles(0, 1, -1, 1,  1,  1,  1, 0, 1, -1);
    }
    
}
