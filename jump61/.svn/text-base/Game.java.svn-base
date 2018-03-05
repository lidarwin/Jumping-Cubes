package jump61;

import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;

import java.util.Scanner;
import java.util.Random;

import static jump61.Color.*;

/** Main logic for playing (a) game(s) of Jump61.
 *  @author Darwin Li
 */
class Game {

    /** Name of resource containing help message. */
    private static final String HELP = "jump61/Help.txt";

    /** A new Game that takes command/move input from INPUT, prints
     *  normal output on OUTPUT, prints prompts for input on PROMPTS,
     *  and prints error messages on ERROROUTPUT. The Game now "owns"
     *  INPUT, PROMPTS, OUTPUT, and ERROROUTPUT, and is responsible for
     *  closing them when its play method returns. */
    Game(Reader input, Writer prompts, Writer output, Writer errorOutput) {
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        _readonlyBoard = new ConstantBoard(_board);
        _prompter = new PrintWriter(prompts, true);
        _inp = new Scanner(input);
        _inp.useDelimiter("(?m)\\p{Blank}*$|^\\p{Blank}*|\\p{Blank}+");
        _out = new PrintWriter(output, true);
        _err = new PrintWriter(errorOutput, true);
        _humanRed = new HumanPlayer(this, RED);
        _humanBlue = new HumanPlayer(this, BLUE);
        _aiRed = new AI(this, RED);
        _aiBlue = new AI(this, BLUE);
        _playerRed = _humanRed;
        _playerBlue = _aiBlue;
        _playing = false;
        _notFinished = true;
    }

    /** Play a session of Jump61.  This may include multiple games,
     *  and proceeds until the user exits.  Returns an exit code: 0 is
     *  normal; any positive quantity indicates an error.  */
    int play() {
        _out.println("Welcome to " + Defaults.VERSION);
        _out.flush();
        while (_notFinished) {
            if (_playing) {
                if (_board.whoseMove() == RED) {
                    _playerRed.makeMove();
                } else {
                    _playerBlue.makeMove();
                }
            } else {
                readExecuteCommand();
            }
        }
        return 0;
    }

    /** Get a move from my input and place its row and column in
     *  MOVE.  Returns true if this is successful, false if game stops
     *  or ends first. */
    boolean getMove(int[] move) {
        while (_playing && _move[0] == 0) {
            readExecuteCommand();
        }
        if (_move[0] > 0) {
            move[0] = _move[0];
            move[1] = _move[1];
            _move[0] = 0;
            return true;
        } else {
            return false;
        }
    }

    /** Add a spot to R C, if legal to do so. */
    void makeMove(int r, int c) {
        Color player = _board.whoseMove();
        if (player == RED && _playerRed == _aiRed) {
            _out.println("Red moves " + r + " " + c + ".");
        } else if (player == BLUE && _playerBlue == _aiBlue) {
            _out.println("Blue moves " + r + " " + c + ".");
        }
        if (_board.isLegal(player, r, c)) {
            _board.makeMove(player, r, c);
            checkForWin();
        } else {
            reportError("bad command: '%s'", "Not a legal move");
        }
    }

    /** Add a spot to square #N, if legal to do so. */
    void makeMove(int n) {
        makeMove(_board.row(n), _board.col(n));
    }

    /** Return a random integer in the range [0 .. N), uniformly
     *  distributed.  Requires N > 0. */
    int randInt(int n) {
        return _random.nextInt(n);
    }

    /** Send a message to the user as determined by FORMAT and ARGS, which
     *  are interpreted as for String.format or PrintWriter.printf. */
    void message(String format, Object... args) {
        _out.printf(format, args);
    }

    /** Check whether we are playing and there is an unannounced winner.
     *  If so, announce and stop play. */
    private void checkForWin() {
        if (_board.winningColor != WHITE) {
            announceWinner();
            _playing = false;
        }
    }

    /** Send announcement of winner to my user output. */
    private void announceWinner() {
        String temp;
        if (_board.winningColor == BLUE) {
            temp = "Blue";
        } else {
            temp = "Red";
        }
        _out.println(temp + " wins.");
    }

    /** Make PLAYER an AI for subsequent moves. */
    private void setAuto(Color player) {
        if (player == BLUE) {
            _playerBlue = _aiBlue;
        } else {
            _playerRed = _aiRed;
        }
    }

    /** Make PLAYER take manual input from the user for subsequent moves. */
    private void setManual(Color player) {
        if (player == BLUE) {
            _playerBlue = _humanBlue;
        } else {
            _playerRed = _humanRed;
        }
    }

    /** Stop any current game and clear the board to its initial
     *  state. */
    private void clear() {
        _playing = false;
        _board.clear(_board.size());
    }

    /** Print the current board using standard board-dump format. */
    private void dump() {
        _out.println(_board.toString());
        _out.flush();
    }

    /** Print a help message. */
    private void help() {
        Main.printHelpResource(HELP, _out);
    }

    /** Stop any current game and set the move number to N. */
    private void setMoveNumber(int n) {
        _board.setMoves(n - 1);
    }

    /** Seed the random-number generator with SEED. */
    private void setSeed(long seed) {
        _random.setSeed(seed);
    }

    /** Place SPOTS spots on square R:C and color the square red or
     *  blue depending on whether COLOR is "r" or "b".  If SPOTS is
     *  0, clears the square, ignoring COLOR.  SPOTS must be less than
     *  the number of neighbors of square R, C. */
    private void setSpots(int r, int c, int spots, String color) {
        if (spots == 0) {
            _board.setWhite(r, c);
        } else {
            _board.set(r, c, spots, parseColor(color));
        }
    }

    /** Stop any current game and set the board to an empty N x N board
     *  with numMoves() == 0.  */
    private void setSize(int n) {
        _playing = false;
        _board.clear(n);
    }

    /** Save move R C in _move.  Error if R and C do not indicate an
     *  existing square on the current board. */
    private void saveMove(int r, int c) {
        if (!_board.exists(r, c)) {
            reportError("move %d %d out of bounds", r, c);
        }
        _move[0] = r;
        _move[1] = c;
    }


    /** Read and execute one command.  Leave the input at the start of
     *  a line, if there is more input. */
    private void readExecuteCommand() {
        if (promptForNext()) {
            String delim = " ";
            String[] commstring = _inp.nextLine().trim().split(delim);
            if (commstring[0].indexOf("#") == 0) {
                return;
            } else if (_playing && commstring.length == 2
                && commstring[0].matches("[0-9]")
                && commstring[1].matches("[0-9]")) {
                int r = Integer.parseInt(commstring[0]);
                int c = Integer.parseInt(commstring[1]);
                saveMove(r, c);
            } else if (commstring.length < 2
                && commstring[0].matches("[a-z]+")) {
                executeCommandThree(commstring);
            } else if (commstring.length < 3
                && commstring[0].matches("[a-z]+")) {
                executeCommandTwo(commstring);
            } else if (commstring.length < 6
                && commstring[0].matches("[a-z]+")) {
                executeCommand(commstring);
            } else if (commstring.length < 2) {
                return;
            } else {
                reportError("bad command: '%s'", commstring[0]);
            }
        } else {
            System.exit(0);
        }
    }

    /** Gather arguments and execute command CMND.  Throws GameException
     *  on errors. */
    private void executeCommand(String[] cmnd) {
        switch (cmnd[0]) {
        case "": case "\n": case "\r\n":
            return;
        case "set":
            if (cmnd.length != 5) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            } else if (!cmnd[1].matches("[0-9]+")
                || !cmnd[2].matches("[0-9]+")
                || !cmnd[3].matches("[0-9]+")
                || !cmnd[4].matches("[br]")) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            }
            int temp1 = Integer.parseInt(cmnd[1]);
            int temp2 = Integer.parseInt(cmnd[2]);
            int temp3 = Integer.parseInt(cmnd[3]);
            String temp = "WHITE";
            if (cmnd[4].equals("r")) {
                temp = "RED";
            } else if (cmnd[4].equals("b")) {
                temp = "BLUE";
            }
            setSpots(temp1, temp2, temp3, temp);
            break;
        default:
            reportError("bad command: '%s'", cmnd[0]);
        }
    }

    /** Gather arguments and execute command CMND. Required for
     *  my class to pass the style check errors due to # of lines. */
    private void executeCommandTwo(String[] cmnd) {
        if (cmnd.length != 2) {
            reportError("bad command: '%s'", cmnd[0]);
        }
        switch (cmnd[0]) {
        case "move":
            if (cmnd.length != 2 || !cmnd[1].matches("[0-9]+")) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            }
            int n = Integer.parseInt(cmnd[1]);
            setMoveNumber(n);
            _playing = false;
            break;
        case "": case "\n": case "\r\n":
            return;
        case "auto":
            if (!cmnd[1].matches("[rR][eE][dD]|[Bb][Ll][Uu][Ee]")) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            }
            Color colorA = Color.parseColor(cmnd[1]);
            setAuto(colorA);
            _playing = false;
            break;
        case "manual":
            if (!cmnd[1].matches("[rR][eE][dD]|[Bb][Ll][Uu][Ee]")) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            }
            Color colorM = Color.parseColor(cmnd[1]);
            setManual(colorM);
            _playing = false;
            break;
        case "size":
            if (!cmnd[1].matches("[0-9]+")) {
                reportError("bad command: '%s'", cmnd[0]);
                return;
            }
            int nums = Integer.parseInt(cmnd[1]);
            setSize(nums);
            _playing = false;
            break;
        default:
            reportError("bad command: '%s'", cmnd[0]);
        }
    }

    /** Gather arguments and execute command CMND. Required for
     *  my class to pass the style check errors due to # of lines. */
    private void executeCommandThree(String[] cmnd) {
        if (cmnd.length != 1) {
            reportError("bad command: '%s'", cmnd[0]);
            return;
        }
        switch (cmnd[0]) {
        case "": case "\n": case "\r\n":
            return;
        case "start":
            if (_board.getWinner() != WHITE) {
                clear();
            }
            _playing = true;
            break;
        case "clear":
            clear();
            break;
        case "dump":
            dump();
            break;
        case "quit":
            System.exit(0);
            break;
        case "help":
            help();
            break;
        default:
            reportError("bad command: '%s'", cmnd[0]);
        }
    }


    /** Print a prompt and wait for input. Returns true iff there is another
     *  token. */
    private boolean promptForNext() {
        if (_playing) {
            _prompter.print(_board.whoseMove().toString() + "> ");
            _prompter.flush();
            return _inp.hasNextLine();
        } else {
            _prompter.print("> ");
            _prompter.flush();
            return _inp.hasNextLine();
        }
    }

    /** Returns the value of _playing for whether game is in progress.*/
    boolean isPlaying() {
        return _playing;
    }

    /** Returns the board of this game.*/
    Board getBoard() {
        return _board;
    }


    /** Send an error message to the user formed from arguments FORMAT
     *  and ARGS, whose meanings are as for printf. */
    void reportError(String format, Object... args) {
        _err.print("Error: ");
        _err.printf(format, args);
        _err.println();
    }

    /** True if the play method should still run.*/
    private boolean _notFinished;

    /** Writer on which to print prompts for input. */
    private final PrintWriter _prompter;
    /** Scanner from current game input.  Initialized to return
     *  newlines as tokens. */
    private final Scanner _inp;
    /** Outlet for responses to the user. */
    private final PrintWriter _out;
    /** Outlet for error responses to the user. */
    private final PrintWriter _err;

    /** The board on which I record all moves. */
    private final Board _board;
    /** A readonly view of _board. */
    private final Board _readonlyBoard;

    /** A pseudo-random number generator used by players as needed. */
    private final Random _random = new Random();

    /** True iff a game is currently in progress. */
    private boolean _playing;

    /** The player that is modifying the game.*/
    private Player _playerRed;

    /** The player that is modifying the game.*/
    private Player _playerBlue;

    /** Human player for blue.*/
    private Player _humanBlue;

    /** Human player for red.*/
    private Player _humanRed;

    /** AI player for blue.*/
    private Player _aiBlue;

    /** AI player for red.*/
    private Player _aiRed;

   /** Used to return a move entered from the console.  Allocated
     *  here to avoid allocations. */
    private final int[] _move = new int[2];
}
