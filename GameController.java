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
        ui.displayWelcome();

        while (!game.getIfGameOver()) {
            playTurn();
        }

        ui.displayGameOver(game.getCurrPlayer());
    }

    /**
     * Runs one full turn for the current player.
     */
    public void playTurn() {
        Player player = game.getCurrPlayer();
        boolean extraTurn = true;

        while (extraTurn && !game.getIfGameOver()) {

            ui.displayBoard(game);
            ui.displayPlayerStatus(player, true);
            ui.promptRollDice();

            int steps = dice.getResult();
            ui.displayDiceResult(steps);

            boolean hasHome   = hasPlaneAtHome(player);
            boolean hasActive = hasActivePlane(player);

            Plane selectedPlane = null; 

            if (steps == 6 && hasHome && hasActive) {
                if (ui.askTakeOffOrMove()) {
                    int idx = ui.getHomePlaneChoice(player);
                    if (idx != -1) {
                        selectedPlane = player.getPlanes()[idx];
                        game.takeoffPlane(selectedPlane);
                        ui.displayTakeOff(selectedPlane);
                    }
                } else {
                    int idx = ui.getActivePlaneChoice(player);
                    if (idx != -1) {
                        selectedPlane = player.getPlanes()[idx];
                        game.movePlane(selectedPlane, steps, false);
                    }
                }

            } else if (steps == 6 && hasHome) {
                int idx = ui.getHomePlaneChoice(player);
                if (idx != -1) {
                    selectedPlane = player.getPlanes()[idx];
                    game.takeoffPlane(selectedPlane);
                    ui.displayTakeOff(selectedPlane);
                }

            } else if (hasActive) {
                int idx = ui.getActivePlaneChoice(player);
                if (idx != -1) {
                    selectedPlane = player.getPlanes()[idx];
                    game.movePlane(selectedPlane, steps, false);
                }

            } else {
                ui.displayNoMoves(player);
            }

            // === MERGE POINT: Calling your partner's code correctly ===
            if (selectedPlane != null) {
                handleOptionalMoves(selectedPlane, false);
            }

            if (steps == 6 && !game.getIfGameOver()) {
                ui.displayMessage(player.getColor() + " rolled 6 - taking another turn!");
                extraTurn = true;
            } else {
                extraTurn = false;
            }
        }

        if (!game.getIfGameOver()) {
            game.nextTurn();
        }
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
private boolean hasPlaneAtHome(Player player) {
        Plane[] planes = player.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getIsAtHome()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasActivePlane(Player player) {
        Plane[] planes = player.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            if (!planes[i].getIsAtHome() && !planes[i].getIfWin()) {
                return true;
            }
        }
        return false;
    }
}