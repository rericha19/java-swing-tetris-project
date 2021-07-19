/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralny;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameRules {
    private String name;
    private Boolean use_hold;
    private Boolean use_funny_pieces;
    private Boolean use_hard_drop;
    private Boolean use_new_piece_generation;    
    private int board_width;
    private HighScore[] scores; 
    
    private static final Logger LOGGER = Logger.getLogger(GameRules.class.getName());

    public void addNewScore(HighScore highScore) {
        int new_index = 10;
        for (int i = 0; i < 10; i++) {
            if (highScore.getScore() > this.scores[i].getScore()) {
                new_index = i;
                break;
            }
        }
        
        for (int i = 9; i > new_index; i--)
            this.scores[i] = this.scores[i - 1];
        this.scores[new_index] = highScore;
            
        try {
            XML_Handler.saveGameRules(this);
            LOGGER.log(Level.INFO, "Updated game rule's scoreboard");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save game rules on new score update: " + e);           
        }
    }
    
    public static class HighScore {
        private int score;
        private int starting_level;
        private String name;
        
        public HighScore() {
            this.score = 0;
            this.starting_level = 0;
            this.name = "-----";
        }
        
        public HighScore(int score, int start_level, String name) {
            this.score = score;
            this.starting_level = start_level;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public int getStarting_level() {
            return starting_level;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setStarting_level(int starting_level) {
            this.starting_level = starting_level;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    public GameRules() {
        
    }
        
    public GameRules(String name, Boolean use_h, Boolean use_f_p, Boolean use_7bag, Boolean use_hard_drop, int board_w) {
        
        this.name = name;
        this.use_hold = use_h;
        this.use_funny_pieces = use_f_p;
        this.use_new_piece_generation = use_7bag;
        this.board_width = board_w;
        this.use_hard_drop = use_hard_drop;
        this.scores = new HighScore[10];
        for (int i = 0; i < 10; i++) {
            this.scores[i] = new HighScore();
        }
    }
     
    
    public static GameRules getDefaultRulesClassic() {
        GameRules rules = new GameRules();
        rules.name = "Classic";
        rules.use_hold = false;
        rules.use_funny_pieces = false;
        rules.use_new_piece_generation = false;
        rules.board_width = 10;
        rules.setUse_hard_drop((Boolean) false);
        rules.scores = new HighScore[10];
        for (int i = 0; i < 10; i++)
            rules.scores[i] = new HighScore();
        
        return rules;
    }
    
    public static GameRules getDefaultRulesModern() {
        GameRules rules = new GameRules();
        
        rules.name = "Modern";
        rules.use_hold = true;
        rules.use_funny_pieces = false;
        rules.use_new_piece_generation = true;
        rules.board_width = 10;
        rules.use_hard_drop = true;        
        rules.scores = new HighScore[10];
        for (int i = 0; i < 10; i++)
            rules.scores[i] = new HighScore();
        
        return rules;
    }
    
    public static GameRules getDefaultRulesFunny() {
        GameRules rules = new GameRules();
        
        rules.name = "Wonky";
        rules.use_hold = true;
        rules.use_funny_pieces = true;
        rules.use_new_piece_generation = true;
        rules.setUse_hard_drop((Boolean) true);
        rules.board_width = 20;
        rules.scores = new HighScore[10];
        for (int i = 0; i < 10; i++)
            rules.scores[i] = new HighScore();
        
        return rules;
    }

    // @XmlElement(name = "board_width")
    public int getBoard_width() {
        return board_width;
    }

    // @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    //@XmlElementWrapper(name = "scores")
    //@XmlElement(name = "score")
    public HighScore[] getScores() {
        return scores;
    }

    //@XmlElement(name = "use_funny_pieces")
    public Boolean getUse_funny_pieces() {
        return use_funny_pieces;
    }

    // @XmlElement(name = "use_hold")
    public Boolean getUse_hold() {
        return use_hold;
    }

    //@XmlElement(name = "use_new_piece_generation")
    public Boolean getUse_new_piece_generation() {
        return use_new_piece_generation;
    }

    @XmlElement(name = "board_width")
    public void setBoard_width(int board_width) {
        this.board_width = board_width;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "scores")
    @XmlElement(name = "score")
    public void setScores(HighScore[] scores) {
        this.scores = scores;
    }

    @XmlElement(name = "use_funny_pieces")
    public void setUse_funny_pieces(Boolean use_funny_pieces) {
        this.use_funny_pieces = use_funny_pieces;
    }

    @XmlElement(name = "use_hold")
    public void setUse_hold(Boolean use_hold) {
        this.use_hold = use_hold;
    }

    @XmlElement(name = "use_new_piece_generation")
    public void setUse_new_piece_generation(Boolean use_new_piece_generation) {
        this.use_new_piece_generation = use_new_piece_generation;
    }

    public Boolean getUse_hard_drop() {
        return use_hard_drop;
    }

    @XmlElement(name = "use_hard_drop")
    public void setUse_hard_drop(Boolean use_hard_drop) {
        this.use_hard_drop = use_hard_drop;
    }             
}
