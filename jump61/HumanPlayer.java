package jump61;

/** A Player that gets its moves from manual input.
 *  @author Darwin Li
 */
class HumanPlayer extends Player {

    /** A new player initially playing COLOR taking manual input of
     *  moves from GAME's input source. */
    HumanPlayer(Game game, Color color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        Game game = getGame();
        Board board = getBoard();
        int[] arr = new int[2];
        if (!game.isPlaying()) {
            return;
        } else if (game.getMove(arr)) {
            game.makeMove(arr[0], arr[1]);
        } else {
            makeMove();
        }
    }

}
