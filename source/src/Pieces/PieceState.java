
package Pieces;

// set of tiles, 2D array containing the possible rotations
public class PieceState {
    private int [][] tiles;
        
    public PieceState (int tilecount) {
        tiles = new int[tilecount][2];            
    }
    
    // called by individual pieces    
    public void setTiles(int ... values) {
        int x = 0;
        for (int a : values) {
            tiles[x / 2][x % 2] = a;
            x++;
        }
    }
    
    public int[][] getTiles() {
        return this.tiles;
    }
}
