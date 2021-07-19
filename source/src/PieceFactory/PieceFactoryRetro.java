
package PieceFactory;

import Pieces.*;
import semestralny.GameConsts;

/**
 *
 * @author Samo
 */

// pseudorandom piece factory, similar to the one used in older versions
// repeating pieces are allowed as well as 'droughts', but they are partially mitigated
// by decrementing the chance to get a piece every subsequent time its picked
public class PieceFactoryRetro extends PieceFactory {
       
    private int width;
    private int[] chances;
    private final int BASE_CHANCE = 160;
    private final int CHANCE_DEC = 32;
    private int piece_count;
    
    
    public PieceFactoryRetro(Boolean uses_funny_pieces, int board_width) {
        if (uses_funny_pieces)
            piece_count = GameConsts.PIECE_COUNT_FUNNY;
        else
            piece_count = GameConsts.PIECE_COUNT_NORMAL;
        
        width = board_width;
        chances = new int[piece_count];
        for (int i = 0; i < piece_count; i++) {
            chances[i] = BASE_CHANCE;
        }
    }
    
    @Override
    public Piece getPiece() {
        
        int [] sums = new int[piece_count];
        sums[0] = chances[0];
        
        for (int i = 1; i < piece_count; i++)
            sums[i] = sums[i - 1] + chances[i];
        
        int x = (int) (Math.random() * sums[piece_count - 1]);
        
        for (int i = 0; i < piece_count; i++) {
            if (x < sums[i]) {
                if (chances[i] > CHANCE_DEC)
                    chances[i] -= CHANCE_DEC;
                return newPiece(i, width);
            }
            else
                chances[i] = BASE_CHANCE;
        }
                                    
        return new PieceO(width);
    }  
    
}
