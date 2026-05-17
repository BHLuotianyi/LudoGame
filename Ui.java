/** 
 * Ui.java
 * Handles the interaction with the user, including displaying the game state and getting user input.
 */
import java.util.Scanner;

public class Ui {

    /** The scanner used to read all user input from the console */
    private Scanner scanner;

    /**
     * Constructor - sets up the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // ======================== DISPLAY METHODS ========================

    /**
     * Displays the welcome screen shown at the very start of the game.
     */
    public void displayWelcome() {
        System.out.println("==========================================");
        System.out.println("     WELCOME TO LUDO - PLANE EDITION     ");
        System.out.println("   4 Players  |  4 Planes  |  1 Winner   ");
        System.out.println("==========================================");
        System.out.println();
    }

    /**
     * Displays the full game state: turn info and all players' plane positions.
     * Called at the start of every turn.
     * @param game The current game instance
     */
    public void displayBoard(Game game) {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("  Turn: " + game.getTurnCount() + "  |  Current: " + game.getCurrPlayer().getColor());
        System.out.println("==========================================");
        displayAllPlayerStatus(game.getPlayers(), game.getCurrPlayer());
        System.out.println("==========================================");
        System.out.println();
    }

    /**
     * Displays all four players' status rows.
     * @param players Array of all players
     * @param currentPlayer The player whose turn it is (gets a >> arrow)
     */
    public void displayAllPlayerStatus(Player[] players, Player currentPlayer) {
        System.out.println("[PLANE POSITIONS]");
        for (int i = 0; i < players.length; i++) {
            boolean isCurrent = (players[i] == currentPlayer);
            displayPlayerStatus(players[i], isCurrent);
        }
    }

    /**
     * Displays one player's row showing all four planes and their positions.
     * @param player The player to display
     * @param isCurrent True if this player is the active player this turn
     */
    public void displayPlayerStatus(Player player, boolean isCurrent) {
        if (isCurrent) {
            System.out.print(">> " + player.getColor() + ": ");
        } else {
            System.out.print("   " + player.getColor() + ": ");
        }

        Plane[] planes = player.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getPos() == -1) {
                System.out.print(planes[i].getName() + "(HOME)  ");
            } else {
                System.out.print(planes[i].getName() + "(pos:" + planes[i].getPos() + ")  ");
            }
        }
        System.out.println();
    }

    /**
     * Displays the turn header for the current player.
     * @param player The current player
     * @param turnCount The current turn number
     */
    public void displayTurnStart(Player player, int turnCount) {
        System.out.println("--- " + player.getColor() + "'s TURN  (Turn #" + turnCount + ") ---");
        System.out.println();
    }

    /**
     * Displays the result of a dice roll, with a bonus message if it is 6.
     * @param result The dice roll result (1 to 6)
     */
    public void displayDiceResult(int result) {
        System.out.println();
        System.out.println("  +---------+");
        System.out.println("  |  Dice   |");
        System.out.println("  |   [ " + result + " ]  |");
        System.out.println("  +---------+");
        if (result == 6) {
            System.out.println("  ** Rolled a 6 - bonus turn! **");
        }
        System.out.println();
    }

    /**
     * Displays a message when a player has no valid moves this turn.
     * @param player The player who has no moves
     */
    public void displayNoMoves(Player player) {
        System.out.println("  >> " + player.getColor() + " has no valid moves. Skipping turn...");
    }

    /**
     * Displays a message when a plane takes off from home onto the board.
     * @param plane The plane that took off
     */
    public void displayTakeOff(Plane plane) {
        System.out.println("  >> " + plane.getName() + " took off and is now on the board!");
    }

    /**
     * Displays a message when a plane moves forward on the board.
     * @param plane The plane that moved
     * @param steps How many steps it moved
     * @param newPos Its new position on the board
     */
    public void displayMove(Plane plane, int steps, int newPos) {
        System.out.println("  >> " + plane.getName() + " moved " + steps + " steps -> now at position " + newPos);
    }

    /**
     * Displays a collision message when one plane knocks another back home.
     * @param attacker The plane that caused the hit
     * @param victim The plane that was sent back home
     */
    public void displayCollision(Plane attacker, Plane victim) {
        System.out.println("  ** COLLISION! " + attacker.getName() + " hit " + victim.getName() + " -> sent back home! **");
    }

    /**
     * Displays a message when a plane lands on its own color and jumps forward.
     * @param plane The plane that jumped
     * @param newPos The position it jumped to
     */
    public void displaySameColorJump(Plane plane, int newPos) {
        System.out.println("  >> " + plane.getName() + " landed on same color - jumped to position " + newPos + "!");
    }

    /**
     * Displays a message when a plane triggers a shortcut block.
     * @param plane The plane that used the shortcut
     * @param newPos The position it jumped to
     */
    public void displayShortcutJump(Plane plane, int newPos) {
        System.out.println("  >> " + plane.getName() + " hit a SHORTCUT - jumped to position " + newPos + "!");
    }

    /**
     * Displays a message when a plane enters the final route toward home.
     * @param plane The plane that entered the final route
     */
    public void displayEnterFinalRoute(Plane plane) {
        System.out.println("  >> " + plane.getName() + " entered the FINAL ROUTE!");
    }

    /**
     * Displays a message when a plane successfully reaches home (finishes).
     * @param plane The plane that reached home
     */
    public void displayPlaneLanded(Plane plane) {
        System.out.println("  ** " + plane.getName() + " reached HOME! **");
    }

    /**
     * Displays the game-over screen showing the winning player.
     * @param winner The player who won the game
     */
    public void displayGameOver(Player winner) {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("              GAME OVER!                  ");
        System.out.println("  WINNER: " + winner.getColor() + "!");
        System.out.println("==========================================");
        System.out.println("        Thanks for playing Ludo!          ");
        System.out.println("==========================================");
        System.out.println();
    }

    /**
     * Displays a general info or status message.
     * @param message The message to display
     */
    public void displayMessage(String message) {
        System.out.println("  >> " + message);
    }

    /**
     * Displays an error message when the user enters invalid input.
     */
    public void displayInvalidInput() {
        System.out.println("  [!] Invalid input - please try again.");
    }

    // ======================== INPUT METHODS ========================

    /**
     * Prompts the player to press ENTER to roll the dice, then waits.
     */
    public void promptRollDice() {
        System.out.print("  Press ENTER to roll the dice... ");
        scanner.nextLine();
    }

    /**
     * When a player rolls a 6 and has both home planes and active planes,
     * asks whether they want to take off a new plane or move an existing one.
     * @return true if they choose to take off, false if they choose to move
     */
    public boolean askTakeOffOrMove() {
        System.out.println("  You rolled a 6! What do you want to do?");
        System.out.println("    1. Take off a new plane from home");
        System.out.println("    2. Move an existing plane");
        System.out.print("  Enter choice (1 or 2): ");
        return readIntInRange(1, 2) == 1;
    }

    /**
     * Shows the current player's planes that are on the board and asks them to pick one to move.
     * @param player The current player
     * @return The index (0 to 3) of the chosen plane in the player's planes array,
     *         or -1 if no active planes are available
     */
    public int getActivePlaneChoice(Player player) {
        Plane[] planes = player.getPlanes();
        int[] validIdx = new int[planes.length];
        int count = 0;

        System.out.println("  Choose a plane to move:");
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getPos() != -1) {
                validIdx[count] = i;
                count++;
                System.out.println("    " + count + ". " + planes[i].getName() + " (at position " + planes[i].getPos() + ")");
            }
        }

        if (count == 0) {
            System.out.println("  No active planes available.");
            return -1;
        }

        System.out.print("  Enter choice (1 to " + count + "): ");
        int choice = readIntInRange(1, count);
        return validIdx[choice - 1];
    }

    /**
     * Shows the current player's planes that are still at home and asks them to pick one to take off.
     * @param player The current player
     * @return The index (0 to 3) of the chosen plane in the player's planes array,
     *         or -1 if no planes are at home
     */
    public int getHomePlaneChoice(Player player) {
        Plane[] planes = player.getPlanes();
        int[] validIdx = new int[planes.length];
        int count = 0;

        System.out.println("  Choose a plane to take off:");
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getPos() == -1) {
                validIdx[count] = i;
                count++;
                System.out.println("    " + count + ". " + planes[i].getName() + " (at home)");
            }
        }

        if (count == 0) {
            System.out.println("  No planes at home.");
            return -1;
        }

        System.out.print("  Enter choice (1 to " + count + "): ");
        int choice = readIntInRange(1, count);
        return validIdx[choice - 1];
    }

    // ======================== PRIVATE HELPERS ========================

    /**
     * Reads an integer from the user in the range [min, max].
     * Keeps asking until the user enters a valid number.
     * @param min Minimum valid value (inclusive)
     * @param max Maximum valid value (inclusive)
     * @return A valid integer within [min, max]
     */
    private int readIntInRange(int min, int max) {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // clear the leftover newline after the number
                if (value >= min && value <= max) {
                    return value;
                }
                displayInvalidInput();
                System.out.print("  Enter a number from " + min + " to " + max + ": ");
            } catch (Exception e) {
                scanner.nextLine(); // clear bad input so we don't loop forever
                displayInvalidInput();
                System.out.print("  Enter a number from " + min + " to " + max + ": ");
            }
        }
    }
}