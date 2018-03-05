package jump61;

import java.util.Stack;

import static jump61.Color.*;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered 0 - size()-1, in
 *  row 2 numbered size() - 2*size() - 1, etc.
 *  @author Darwin Li
 */
abstract class Board {

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        unsupported("clear");
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        unsupported("copy");
    }

    /** Return the number of rows and of columns of THIS. */
    abstract int size();

    /** Returns the number of spots in the square at row R, column C,
     *  1 <= R, C <= size (). */
    abstract int spots(int r, int c);

    /** Returns the number of spots in square #N. */
    abstract int spots(int n);

    /** Returns the color of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    abstract Color color(int n);

    /** Returns the color of the square at row R, column C,
     *  1 <= R, C <= size(). */
    abstract Color color(int r, int c);

    /** Returns the total number of moves made (red makes the odd moves,
     *  blue the even ones). */
    abstract int numMoves();

    /** Returns the Color of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Color whoseMove() {
        if (_moves % 2 == 0) {
            return RED;
        } else {
            return BLUE;
        }
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return ((int) (n / _N)) + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return n % _N + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return (r - 1) * _N + c - 1;
    }


    /** Returns true iff it would currently be legal for PLAYER to add a spot
        to square at row R, column C. */
    boolean isLegal(Color player, int r, int c) {
        if (!isLegal(player)) {
            return false;
        } else if (!exists(r, c)) {
            return false;
        } else if (player == WHITE || player == color(r, c)
            || color(r, c) == WHITE) {
            return true;
        }
        return false;
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Color player, int n) {
        return isLegal(player, row(n), col(n));
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Color player) {
        if (winningColor == WHITE) {
            return true;
        }
        return false;
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    Color getWinner() {
        return winningColor;
    }

    /** Return the number of squares of given COLOR. */
    abstract int numOfColor(Color color);

    /** Makes a move for PLAYER on R and C that will be saved in history.*/
    void makeMove(Color player, int r, int c) {
        unsupported("makeMove");
    }

    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Color player, int r, int c) {
        unsupported("addSpot");
    }

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Color player, int n) {
        unsupported("addSpot");
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Clear the undo
     *  history. */
    void set(int r, int c, int num, Color player) {
        unsupported("set");
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white).  Clear the undo history. */
    void set(int n, int num, Color player) {
        unsupported("set");
    }

    /** Makes the square given by row R and column C neutral.*/
    void setWhite(int r, int c) {
        unsupported("setWhite");
    }

    /** Set the current number of moves to N.  Clear the undo history. */
    void setMoves(int n) {
        unsupported("setMoves");
    }

    /** Adds a new MutableBoard ADDTHIS to the undo history.*/
    void addUndoList(Board addthis) {
        MutableBoard addHist = new MutableBoard(addthis);
        _undoHistory.push(addHist);
    }


    /** Returns the number of moves.*/
    int getMoves() {
        return _moves;
    }

    /** Undo the effects one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        unsupported("undo");
    }

    /** Returns my dumped representation. */
    @Override
    public String toString() {
        String dumped = "===" + "\n";
        dumped = dumped + toDisplayString();
        dumped = dumped + "\n" + "===";
        return dumped;
    }

    /** Returns an external rendition of me, suitable for
     *  human-readable textual display.  This is distinct from the dumped
     *  representation (returned by toString). */
    public String toDisplayString() {
        String dumped = "";
        for (int i = 0; i < _N; i += 1) {
            if (i != 0) {
                dumped = dumped + "\n";
            }
            dumped = dumped + "    ";
            for (int j = 0; j < _N; j += 1) {
                int num = Integer.parseInt(xy[i][j][1]);
                String str;
                if (xy[i][j][0].equals("white")) {
                    str = "--";
                    if (j == 0) {
                        dumped = dumped + str;
                    } else {
                        dumped = dumped + " " + str;
                    }
                } else if (xy[i][j][0].equals("red")) {
                    str = num + "r";
                    if (j == 0) {
                        dumped = dumped + str;
                    } else {
                        dumped = dumped + " " + str;
                    }
                } else {
                    str = num + "b";
                    if (j == 0) {
                        dumped = dumped + str;
                    } else {
                        dumped = dumped + " " + str;
                    }
                }
            }
        }
        return dumped;
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        return neighbors(sqNum(r, c));
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        int[] temp = neighborhood(n);
        return temp.length;
    }

    /** Returns the undoHistory of the board.*/
    Stack<Board> getUndoHistory() {
        return _undoHistory;
    }

    /** Returns the Array that holds all the information.*/
    String[][][] getXy() {
        return xy;
    }

    /** Returns the number of blue squares on the board.*/
    int getNumBlue() {
        return _numBlue;
    }
    /** Returns the number of red squares on the board.*/
    int getNumRed() {
        return _numRed;
    }

    /** Returns an integer list of all the legal neighbor's n values of S.*/
    int[] neighborhood(int s) {
        int[] neighborList;
        if (s == 0) {
            neighborList = new int[2];
            neighborList[0] = s + _N;
            neighborList[1] = s + 1;
        } else if (s == _N - 1) {
            neighborList = new int[2];
            neighborList[0] = s + _N;
            neighborList[1] = s - 1;
        } else if (s == _N * _N - 1) {
            neighborList = new int[2];
            neighborList[0] = s - _N;
            neighborList[1] = s - 1;
        } else if (s == _N * _N - _N) {
            neighborList = new int[2];
            neighborList[0] = s - _N;
            neighborList[1] = s + 1;
        } else if (row(s) == 1) {
            neighborList = new int[3];
            neighborList[0] = s + _N;
            neighborList[1] = s + 1;
            neighborList[2] = s - 1;
        } else if (row(s) == _N) {
            neighborList = new int[3];
            neighborList[0] = s + 1;
            neighborList[1] = s - 1;
            neighborList[2] = s - _N;
        } else if (col(s) == 1) {
            neighborList = new int[3];
            neighborList[0] = s + _N;
            neighborList[1] = s + 1;
            neighborList[2] = s - _N;
        } else if (col(s) == _N) {
            neighborList = new int[3];
            neighborList[0] = s - _N;
            neighborList[1] = s + _N;
            neighborList[2] = s - 1;
        } else {
            neighborList = new int[4];
            neighborList[0] = s - _N;
            neighborList[1] = s + 1;
            neighborList[2] = s - 1;
            neighborList[3] = s + _N;
        }
        return neighborList;
    }

    /** The winning color of the board.*/
    protected Color winningColor;

    /** Whether this game is over.*/
    protected boolean gameOver;

    /** Indicate fatal error: OP is unsupported operation. */
    private void unsupported(String op) {
        String msg = String.format("'%s' operation not supported", op);
        throw new UnsupportedOperationException(msg);
    }

    /** The List that holds the rows.*/
    protected String[][][] xy;
    /** The number of blue squares.*/
    protected int _numBlue;
    /** The number of red Squares.*/
    protected int _numRed;
    /** Convenience variable: size of board (squares along one edge). */
    protected int _N;
    /** The stack undo history.*/
    protected Stack<Board> _undoHistory;
    /** Total combined number of moves by both sides. */
    protected int _moves;

    /** The length of an end of line on this system. */
    private static final int NL_LENGTH =

        System.getProperty("line.separator").length();

}
