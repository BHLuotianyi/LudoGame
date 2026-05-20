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
                        selectedPlane = null; // Prevent jump after takeoff
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
                    selectedPlane = null; // Prevent jump after takeoff
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

            // === Handle Optional Jumps and Shortcuts ===
            if (selectedPlane != null && !selectedPlane.getIfWin()) {
                // 1. Check for Shortcut first
                if (game.canTakeShortcut(selectedPlane)) {
                    if (ui.askShortcut(selectedPlane)) {
                        game.takeShortcut(selectedPlane);
                        ui.displayShortcutJump(selectedPlane, selectedPlane.getPos());
                    }
                }
                
                // 2. Check for Jump (even if a shortcut was just taken)
                // We pass 'false' because even if a shortcut was taken, it's not a 'jump'
                if (game.canJump(selectedPlane, false)) {
                    if (ui.askJump(selectedPlane)) {
                        game.movePlane(selectedPlane, 4, true);
                        ui.displaySameColorJump(selectedPlane, selectedPlane.getPos());
                    }
                }
            }
            
            // Check win condition right after the turn ends, before evaluating extra turns
            if (player.getFinishedCount() == 4) {
                // Next turn call inside loop handles game over state flip too if not doing nextTurn here, 
                // but let's just break the extra turn loop since game logic will flip it later or we can rely on loop condition
                break;
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