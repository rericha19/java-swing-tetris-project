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
public class PieceFunnyL2 extends Piece {
    
      public PieceFunnyL2 (int board_w) {
        
        this.tilecount = 5;
        this.rotationcount = 4;
        this.start_x = board_w/2;
        this.start_y = 0;
        this.current_state = this.start_state = 1;
        this.piece_id = GameConsts.ID_PIECE_FUNNY_L2;
        initPieceStates();
    }
    
    @Override
    public void pieceSetStates() {
        //                     (x, y, x, y , x , y , x ,  y)
        this.states[0].setTiles(0, 0, 0, -1,  0,  1,  1,  1, 0, -2);
        this.states[1].setTiles(0, 0, 1,  0, -1,  0, -1,  1, 2, 0);
        this.states[2].setTiles(0, 0, 0,  1,  0, -1, -1, -1, 0, 2);
        this.states[3].setTiles(0, 0, -1, 0,  1,  0,  1, -1, -2, 0);
    }
    
}
