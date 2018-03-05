package jump61;


import static jump61.Color.*;
import java.util.Stack;

/** A Jump61 board state.
 *  @author Darwin Li
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _N = N;
        _moves = 0;
        _numBlue = 0;
        _numRed = 0;
        winningColor = WHITE;
        _undoHistory = new Stack<Board>();
        xy = new String[_N][_N][2];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                xy[i][j][0] = "white";
                xy[i][j][1] = "0";
            }
        }
    }

    /** A board whose initial contents are copied from BOARD0. Clears the
     *  undo history. */
    MutableBoard(Board board0) {
        _N = board0.size();
        _moves = 0;
        _numBlue = 0;
        _numRed = 0;
        winningColor = WHITE;
        xy = new String[_N][_N][2];
        copy(board0);
    }

    @Override
    void clear(int N) {
        _N = N;
        _numBlue = 0;
        _numRed = 0;
        winningColor = WHITE;
        xy = new String[_N][_N][2];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                xy[i][j][0] = "white";
                xy[i][j][1] = "0";
            }
        }
        _moves = 0;
        _undoHistory = new Stack<Board>();
    }

    @Override
    void copy(Board board) {
        _N = board.size();
        _moves = board.numMoves();
        _numBlue = board.getNumBlue();
        _numRed = board.getNumRed();
        winningColor = board.winningColor;
        for (int i = 0; i < _N; i += 1) {
            for (int j = 0; j < _N; j += 1) {
                xy[i][j][0] = board.getXy()[i][j][0];
                xy[i][j][1] = board.getXy()[i][j][1];
            }
        }
    }

    @Override
    int size() {
        return _N;
    }

    @Override
    int spots(int r, int c) {
        return Integer.parseInt(xy[r - 1][c - 1][1]);
    }

    @Override
    int spots(int n) {
        return spots(n % _N + 1, ((int) n / _N) + 1);
    }

    @Override
    Color color(int r, int c) {
        return Color.parseColor(xy[r - 1][c - 1][0]);
    }

    @Override
    Color color(int n) {
        return color(n % _N + 1, ((int) n / _N) + 1);
    }

    @Override
    int numMoves() {
        return _moves;
    }

    @Override
    int numOfColor(Color color) {
        switch (color) {
        case BLUE:
            return _numBlue;
        case RED:
            return _numRed;
        default:
            return _N * _N - _numBlue - _numRed;
        }
    }

    @Override
    void makeMove(Color player, int r, int c) {
        addUndoList(this);
        addSpot(player, r, c);
        _moves += 1;
    }

    @Override
    void addSpot(Color player, int r, int c) {
        int[] neighb = neighborhood(sqNum(r, c));
        if (spots(r, c) == neighb.length) {
            set(r, c, 1, player);
            if (winningColor != WHITE) {
                return;
            }
            jump(sqNum(r, c), player, neighb);
        } else {
            set(r, c, spots(r, c) + 1, player);
            if (winningColor != WHITE) {
                return;
            }
        }
    }

    @Override
    void addSpot(Color player, int n) {
        int[] neighb = neighborhood(n);
        if (spots(row(n), col(n)) == neighb.length) {
            set(row(n), col(n), 1, player);
            if (winningColor != WHITE) {
                return;
            }
            jump(n, player, neighb);
        } else {
            set(row(n), col(n), spots(row(n), col(n)) + 1, player);
            if (winningColor != WHITE) {
                return;
            }
        }
    }

    @Override
    void set(int r, int c, int num, Color player) {
        if (color(r, c) != player) {
            if (color(r, c) == WHITE) {
                setPlayerSq(player, playerSq(player) + 1);
            } else {
                setPlayerSq(player, playerSq(player) + 1);
                setOppSq(player, oppSq(player) - 1);
            }
        }
        if (playerSq(player) == _N * _N) {
            winningColor = player;
            return;
        }
        xy[r - 1][c - 1][0] = player.toString();
        xy[r - 1][c - 1][1] = Integer.toString(num);
    }

    @Override
    void set(int n, int num, Color player) {
        set(row(n), col(n), num, player);
    }

    @Override
    void setMoves(int num) {
        assert num > 0;
        _moves = num;
    }

    @Override
    void undo() {
        copy(_undoHistory.pop());
    }

    @Override
    void setWhite(int r, int c) {
        if (color(r, c) == RED) {
            _numRed -= 1;
        }
        if (color(r, c) == BLUE) {
            _numBlue -= 1;
        }
        set(r, c, 0, WHITE);
    }

    /** Returns the number of squares of color PLAYER.*/
    int playerSq(Color player) {
        if (player == BLUE) {
            return _numBlue;
        } else {
            return _numRed;
        }
    }
    /** Sets the number of squares of color PLAYER to NUM.*/
    void setPlayerSq(Color player, int num) {
        if (player == BLUE) {
            _numBlue = num;
        } else {
            _numRed = num;
        }
    }
    /** Sets the number of squares of color PLAYER to NUM.*/
    void setOppSq(Color player, int num) {
        if (player == BLUE) {
            _numRed = num;
        } else {
            _numBlue = num;
        }
    }
    /** Returns the number of squares of PLAYER opponent.*/
    int oppSq(Color player) {
        if (player == BLUE) {
            return _numRed;
        } else {
            return _numBlue;
        }
    }


    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full and is the color PLAYER in the
     *  neighborhood HOOD.*/
    private void jump(int S, Color player, int[] hood) {
        int[] neighb = neighborhood(S);
        for (int i = 0; i < neighb.length; i += 1) {
            if (winningColor != WHITE) {
                break;
            }
            addSpot(player, neighb[i]);
        }
    }

}
