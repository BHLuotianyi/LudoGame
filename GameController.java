/**
 * Coordinates the turn flow between the game rules and the user interface.
 */
public class GameController {
    /** Game state and movement rules. */
    private Game game;

    /** Terminal user interface. */
    private Ui ui;

    /** Die used to generate turn movement. */
    private Dice dice;

    /**
     * Creates a controller with a new game, UI, and die.
     */
    public GameController() {
        game = new Game();
        ui = new Ui();
        dice = new Dice();
    }

    /**
     * Runs the game loop until the game ends.
     */
    public void play() {
        while (!game.getIfGameOver()) {
            playTurn();
        }

        ui.showMessage(game.getCurrPlayer().getColor() + " wins!");
    }

    /**
     * Runs one player's turn.
     */
    private void playTurn() {
        Player player = game.getCurrPlayer();
        ui.displayBoard(game);
        ui.displayPlayerStatus(player);

        int steps = dice.getResult();
        ui.showMessage("Rolled " + steps + ".");

        Plane plane = ui.askPlaneChoice(player);
        if (plane.getIsAtHome()) {
            game.takeoffPlane(plane);
        } else {
            game.movePlane(plane, steps, false);
        }

        handleOptionalMoves(plane, false);
        game.nextTurn();
    }

    /**
     * Handles optional shortcut and jump choices after a plane lands.
     *
     * @param plane the plane that just landed
     * @param arrivedBySpecialMove true if the landing came from a jump or shortcut
     */
    private void handleOptionalMoves(Plane plane, boolean arrivedBySpecialMove) {
        if (!plane.getIsAtHome() && !plane.getIfWin()) {
            if (game.canTakeShortcut(plane)) {
                if (ui.askYesNo("Take the shortcut for " + plane.getName() + "?")) {
                    game.movePlane(plane, 12, arrivedBySpecialMove);
                    handleOptionalMoves(plane, arrivedBySpecialMove);
                }
            } else if (game.canJump(plane, arrivedBySpecialMove)) {
                if (ui.askYesNo("Jump 4 blocks with " + plane.getName() + "?")) {
                    game.movePlane(plane, 4, true);
                    handleOptionalMoves(plane, true);
                }
            }
        }
    }
}
