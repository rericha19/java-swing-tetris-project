
package PieceFactory;
import Pieces.*;
import semestralny.GameConsts;

/**
 *
 * @author Samo
 */

// abstract class, in runtime replaced by one of the nextpiece generators
public abstract class PieceFactory {        
        
    abstract public Piece getPiece();      
    
    public static Piece newPiece(int num, int board_w) {
        switch (num) {
            case GameConsts.ID_PIECE_I:
                return new PieceI(board_w);
            case GameConsts.ID_PIECE_J:
                return new PieceJ(board_w);
            case GameConsts.ID_PIECE_L:
                return new PieceL(board_w);
            case GameConsts.ID_PIECE_O:
                return new PieceO(board_w);
            case GameConsts.ID_PIECE_S:
                return new PieceS(board_w);
            case GameConsts.ID_PIECE_T:
                return new PieceT(board_w);
            case GameConsts.ID_PIECE_Z:
                return new PieceZ(board_w);
            case GameConsts.ID_PIECE_FUNNY_U:
                return new PieceFunnyU(board_w);
            case GameConsts.ID_PIECE_FUNNY_T:
                return new PieceFunnyT(board_w);
            case GameConsts.ID_PIECE_FUNNY_CROSS:
                return new PieceFunnyCross(board_w);
            case GameConsts.ID_PIECE_FUNNY_L1:
                return new PieceFunnyL1(board_w);
            case GameConsts.ID_PIECE_FUNNY_J1:
                return new PieceFunnyJ1(board_w);
            case GameConsts.ID_PIECE_FUNNY_L2:
                return new PieceFunnyL2(board_w);
            case GameConsts.ID_PIECE_FUNNY_J2:
                return new PieceFunnyJ2(board_w);
            default:
                return new PieceO(board_w);
        }
    }
}
