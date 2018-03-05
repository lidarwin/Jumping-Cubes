package jump61;

import java.util.ArrayList;
import java.util.Collections;
import static jump61.Color.*;

/** An automated Player.
 *  @author Darwin Li
 */
class AI extends Player {

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    AI(Game game, Color color) {
        super(game, color);
        _board = _game.getBoard();
    }

    @Override
    void makeMove() {
        if (!_game.isPlaying()) {
            return;
        } else {
            ArrayList<Integer> theList = generateMoves();
            Collections.shuffle(theList);
            int score = Integer.MIN_VALUE;
            int mov = theList.get(0);
            for (int i = 0; i < theList.size(); i += 1) {
                _board.makeMove(_color,
                    _board.row(theList.get(i)), _board.col(theList.get(i)));
                int alph = alphabeta(_color, 4,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (alph > score) {
                    score = alph;
                    mov = theList.get(i);
                }
                _board.undo();
            }
            if (!_board.isLegal(_color, _board.row(mov), _board.col(mov))) {
                System.out.println("AI Messed up legal moves, tried to do "
                    + mov);
            } else {
                _game.makeMove(_board.row(mov), _board.col(mov));
            }
        }
    }

    /** Alpha beta returns heuristic val that works for the player to moves
     *  C and will operate only to depth D. ALPHA and BETA are passed.*/
    private int alphabeta(Color c, int d, int alpha, int beta) {
        int a = alpha;
        int b = beta;
        if (_board.getWinner() == c.opposite()) {
            return Integer.MIN_VALUE;
        } else if (_board.getWinner() == c) {
            return Integer.MAX_VALUE;
        } else if (d == 0) {
            return staticEval(c, _board);
        } else if (_board.whoseMove() == c) {
            for (int i = 0; i < _board.size() * _board.size(); i += 1) {
                if (_board.isLegal(c, _board.row(i), _board.col(i))) {
                    _board.makeMove(c, _board.row(i), _board.col(i));
                    a = Math.max(a, alphabeta(c, d - 1, a, b));
                    _board.undo();
                    if (b <= a) {
                        break;
                    }
                }
            }
            return a;
        } else {
            for (int i = 0; i < _board.size() * _board.size(); i += 1) {
                if (_board.isLegal(c.opposite(),
                    _board.row(i), _board.col(i))) {
                    _board.makeMove(c.opposite(), _board.row(i), _board.col(i));
                    b = Math.min(b, alphabeta(c, d - 1, a, b));
                    _board.undo();
                    if (b <= a) {
                        break;
                    }
                }
            }
            return b;
        }
    }



    /** Method that returns all the legal moves for the board.*/
    private ArrayList<Integer> generateMoves() {
        ArrayList<Integer> theList = new ArrayList<Integer>();
        for (int i = 0; i < _board.size() * _board.size(); i += 1) {
            if (_board.isLegal(_board.whoseMove(),
                _board.row(i), _board.col(i))) {
                theList.add(i);
            }
        }
        return theList;
    }





    /** Returns heuristic value of board B for player C.
     *  Higher is better for P. */
    private int staticEval(Color c, Board b) {
        String[][][] xyy = b.getXy();
        if (b.getWinner() == c) {
            return Integer.MAX_VALUE;
        } else if (b.getWinner() != WHITE) {
            return Integer.MIN_VALUE;
        } else if (c == BLUE) {
            return b.getNumBlue();
        } else {
            return b.getNumRed();
        }
    }

}


