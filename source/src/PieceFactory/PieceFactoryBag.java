
package PieceFactory;

import Pieces.*;
import java.util.ArrayList;
import java.util.Collections;
import semestralny.GameConsts;

/**
 *
 * @author Samo
 */

// 7bag piece factory, based on modern versions
// when a piece is needed it picks it from current bag (and removes it)
// when bag is empty a new one is created and shuffled
// makes sure you dont get lengthy periods without a certain piece or
// bursted by 5 of the same piece on a row (which isnt too uncommon in pseudorandom generation)
public class PieceFactoryBag extends PieceFactory {

    private int width;
    private ArrayList bag;
    private int piece_count;    
    
    private ArrayList refillBag() {
        ArrayList temp = new ArrayList();
        for (int i = 0; i < piece_count; i++)
            temp.add(i);
        Collections.shuffle(temp);
        return temp;
    }
    
    public PieceFactoryBag(Boolean use_funny_pieces, int board_width) {
        if (use_funny_pieces)
            piece_count = GameConsts.PIECE_COUNT_FUNNY;
        else
            piece_count = GameConsts.PIECE_COUNT_NORMAL;
        
        width = board_width;
        bag = refillBag();
    }
    
    @Override
    public Piece getPiece() {
        if (bag.isEmpty())
            bag = refillBag();
        
        int value = (int) bag.get(0);
        bag.remove(0);
        
        return newPiece(value, width);
    }  
}
