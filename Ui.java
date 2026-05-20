import java.util.Scanner;

/**
 * Ui.java
 * Handles all display and user input for the Ludo game.
 * All System.out calls in the entire project go through here.
 * Scanner lives here only - no other class uses it.
 */
public class Ui {

    /** The scanner used to read all user input from the console */
    private Scanner scanner;

    /** ANSI Color Constants for a more professional terminal look: Reset code */
    private static final String RESET = "\u001B[0m";
    /** ANSI Color Constants: Bright Red */
    private static final String RED_B = "\u001B[91m";
    /** ANSI Color Constants: Bright Blue */
    private static final String BLUE_B = "\u001B[94m";
    /** ANSI Color Constants: Bright Yellow */
    private static final String YELLOW_B = "\u001B[93m";
    /** ANSI Color Constants: Bright Green */
    private static final String GREEN_B = "\u001B[92m";
    /** ANSI Color Constants: Cyan */
    private static final String CYAN = "\u001B[36m";

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
        System.out.println(CYAN + "==========================================" + RESET);
        System.out.println(CYAN + "     WELCOME TO LUDO - PLANE EDITION     " + RESET);
        System.out.println(CYAN + "   4 Players  |  4 Planes  |  1 Winner   " + RESET);
        System.out.println(CYAN + "==========================================" + RESET);
        System.out.println();
    }

    /**
     * Displays the full game state: turn info and a visual map.
     * Called at the start of every turn.
     * @param game The current game instance
     */
    public void displayBoard(Game game) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("  Turn: " + game.getTurnCount() + "  |  Current: " + getColorCode(game.getCurrPlayer().getColor()) + game.getCurrPlayer().getColor() + RESET);
        System.out.println("============================================================");
        
        renderMap(game);
        
        System.out.println("============================================================");
        displayAllPlayerStatus(game.getPlayers(), game.getCurrPlayer());
        System.out.println("============================================================");
        System.out.println();
    }

    /**
     * Renders a 15x15 ASCII map showing all planes and path blocks.
     * @param game The current game instance
     */
    private void renderMap(Game game) {
        String[][] grid = new String[15][15];
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                grid[y][x] = "  . ";
            }
        }

        // Fill path blocks from the game map
        MapBlock[] map = game.getMap();
        for (int i = 0; i < map.length; i++) {
            int[] coords = getCoords(i);
            if (coords != null) {
                int x = coords[0];
                int y = coords[1];
                
                String openB = "[";
                String closeB = "]";
                
                if (map[i] instanceof ShortCutBlock) {
                    openB = "(";
                    closeB = ")";
                }
                
                int id = map[i].getId();
                if (id == 3 || id == 16 || id == 29 || id == 42) {
                    openB = "{";
                    closeB = "}";
                }
                
                Plane[] planesOnBlock = map[i].getLandingPlanes();
                if (planesOnBlock != null && planesOnBlock[0] != null) {
                    String name = planesOnBlock[0].getName();
                    String colorCode = getColorCode(planesOnBlock[0].getColor());
                    if (planesOnBlock[1] != null) {
                        grid[y][x] = colorCode + openB + name.charAt(0) + "+" + closeB + RESET;
                    } else {
                        grid[y][x] = colorCode + openB + name + closeB + RESET;
                    }
                } else {
                    String color = map[i].getColor();
                    String colorCode = getColorCode(color);
                    grid[y][x] = colorCode + openB + "  " + closeB + RESET;
                }
            }
        }

        for (Player p : game.getPlayers()) {
            int hX = 0, hY = 0;
            String color = p.getColor();
            if (color.equals("RED")) {
                hX = 2; hY = 2;
            } else if (color.equals("BLUE")) {
                hX = 12; hY = 2;
            } else if (color.equals("YELLOW")) {
                hX = 12; hY = 12;
            } else if (color.equals("GREEN")) {
                hX = 2; hY = 12;
            }
            
            int homeCount = 0;
            for (Plane pl : p.getHome()) {
                if (pl != null) {
                    homeCount++;
                }
            }
            grid[hY][hX] = getColorCode(p.getColor()) + " " + homeCount + "H " + RESET;
        }

        System.out.print("     "); 
        for (int x = 0; x < 15; x++) {
            System.out.printf(" %-3d", x);
        }
        System.out.println();
        System.out.print("     ");
        for (int x = 0; x < 15; x++) {
            System.out.print("---- ");
        }
        System.out.println();

        for (int y = 0; y < 15; y++) {
            System.out.printf("%2d | ", y);
            for (int x = 0; x < 15; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Maps a block index from the Game map to (x, y) coordinates on our 15x15 grid.
     * @param i The block index
     * @return An array containing {x, y} coordinates, or null if index is invalid
     */
    private int[] getCoords(int i) {
        if (i >= 0 && i <= 5) {
            return new int[]{i, 6};
        }
        if (i >= 6 && i <= 11) {
            return new int[]{6, 5 - (i - 6)};
        }
        if (i == 12) {
            return new int[]{7, 0};
        }
        if (i >= 13 && i <= 18) {
            return new int[]{8, i - 13};
        }
        if (i >= 19 && i <= 24) {
            return new int[]{9 + (i - 19), 6};
        }
        if (i == 25) {
            return new int[]{14, 7};
        }
        if (i >= 26 && i <= 31) {
            return new int[]{14 - (i - 26), 8};
        }
        if (i >= 32 && i <= 37) {
            return new int[]{8, 9 + (i - 32)};
        }
        if (i == 38) {
            return new int[]{7, 14};
        }
        if (i >= 39 && i <= 44) {
            return new int[]{6, 14 - (i - 39)};
        }
        if (i >= 45 && i <= 50) {
            return new int[]{5 - (i - 45), 8};
        }
        if (i == 51) {
            return new int[]{0, 7};
        }

        if (i >= 52 && i <= 57) {
            return new int[]{1 + (i - 52), 7}; // RED
        }
        if (i >= 58 && i <= 63) {
            return new int[]{7, 1 + (i - 58)}; // BLUE
        }
        if (i >= 64 && i <= 69) {
            return new int[]{13 - (i - 64), 7}; // YELLOW
        }
        if (i >= 70 && i <= 75) {
            return new int[]{7, 13 - (i - 70)}; // GREEN
        }
        
        return null;
    }

    /**
     * Returns the ANSI color code for a given color string.
     * @param color The color name (e.g., "RED", "BLUE")
     * @return The ANSI escape code for the color
     */
    private String getColorCode(String color) {
        if (color == null) {
            return RESET;
        }
        if (color.equals("RED") || color.equals("red") || color.equals("Red")) {
            return RED_B;
        } else if (color.equals("BLUE") || color.equals("blue") || color.equals("Blue")) {
            return BLUE_B;
        } else if (color.equals("YELLOW") || color.equals("yellow") || color.equals("Yellow")) {
            return YELLOW_B;
        } else if (color.equals("GREEN") || color.equals("green") || color.equals("Green")) {
            return GREEN_B;
        } else {
            return RESET;
        }
    }

    /**
     * Displays all four players' status rows.
     * @param players Array of all players in the game
     * @param currentPlayer The player whose turn it currently is
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
     * @param isCurrent Whether this player is the current player
     */
    public void displayPlayerStatus(Player player, boolean isCurrent) {
        String colorCode = getColorCode(player.getColor());
        if (isCurrent) {
            System.out.print(colorCode + ">> " + player.getColor() + ": " + RESET);
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
        System.out.println(getColorCode(player.getColor()) + "--- " + player.getColor() + "'s TURN  (Turn #" + turnCount + ") ---" + RESET);
        System.out.println();
    }

    /**
     * Displays the result of a dice roll, with a bonus message if it is 6.
     * @param result The value of the dice roll
     */
    public void displayDiceResult(int result) {
        System.out.println();
        System.out.println("  +---------+");
        System.out.println("  |  Dice   |");
        System.out.println("  |   [ " + result + " ]  |");
        System.out.println("  +---------+");
        if (result == 6) {
            System.out.println(YELLOW_B + "  ** Rolled a 6 - bonus turn! **" + RESET);
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
        System.out.println(getColorCode(plane.getColor()) + "  >> " + plane.getName() + " took off and is now on the board!" + RESET);
    }

    /**
     * Displays a message when a plane moves forward on the board.
     * @param plane The plane that moved
     * @param steps The number of steps moved
     * @param newPos The new position of the plane
     */
    public void displayMove(Plane plane, int steps, int newPos) {
        System.out.println("  >> " + plane.getName() + " moved " + steps + " steps -> now at position " + newPos);
    }

    /**
     * Displays a collision message when one plane knocks another back home.
     * @param attacker The plane that landed on the victim
     * @param victim The plane that was sent back home
     */
    public void displayCollision(Plane attacker, Plane victim) {
        System.out.println(RED_B + "  ** COLLISION! " + attacker.getName() + " hit " + victim.getName() + " -> sent back home! **" + RESET);
    }

    /**
     * Displays a message when a plane lands on its own color and jumps forward.
     * @param plane The plane that jumped
     * @param newPos The position after the jump
     */
    public void displaySameColorJump(Plane plane, int newPos) {
        System.out.println(getColorCode(plane.getColor()) + "  >> " + plane.getName() + " landed on same color - jumped to position " + newPos + "!" + RESET);
    }

    /**
     * Displays a message when a plane triggers a shortcut block.
     * @param plane The plane that took the shortcut
     * @param newPos The position after the shortcut jump
     */
    public void displayShortcutJump(Plane plane, int newPos) {
        System.out.println(getColorCode(plane.getColor()) + "  >> " + plane.getName() + " hit a SHORTCUT - jumped to position " + newPos + "!" + RESET);
    }

    /**
     * Displays a message when a plane enters the final route toward home.
     * @param plane The plane entering the final route
     */
    public void displayEnterFinalRoute(Plane plane) {
        System.out.println(getColorCode(plane.getColor()) + "  >> " + plane.getName() + " entered the FINAL ROUTE!" + RESET);
    }

    /**
     * Displays a message when a plane successfully reaches home (finishes).
     * @param plane The plane that reached home
     */
    public void displayPlaneLanded(Plane plane) {
        System.out.println(GREEN_B + "  ** " + plane.getName() + " reached HOME! **" + RESET);
    }

    /**
     * Displays the game-over screen showing the winning player.
     * @param winner The player who won the game
     */
    public void displayGameOver(Player winner) {
        System.out.println();
        System.out.println(YELLOW_B + "==========================================" + RESET);
        System.out.println(YELLOW_B + "              GAME OVER!                  " + RESET);
        System.out.println(YELLOW_B + "  WINNER: " + winner.getColor() + "!" + RESET);
        System.out.println(YELLOW_B + "==========================================" + RESET);
        System.out.println("        Thanks for playing Ludo!          ");
        System.out.println("==================================================");
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
     * Asks the user a yes/no question.
     * @param question The question to ask
     * @return true if the user answers yes, false if no
     */
    public boolean askYesNo(String question) {
        while (true) {
            System.out.print("  " + question + " (y/n): ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y") || input.equals("yes") || input.equals("Yes") || input.equals("YES")) {
                return true;
            } else if (input.equals("n") || input.equals("N") || input.equals("no") || input.equals("No") || input.equals("NO")) {
                return false;
            }
            displayInvalidInput();
        }
    }

    /**
     * Asks the user if they want to take a shortcut.
     * @param plane The plane that can take a shortcut
     * @return true if the user wants to take the shortcut
     */
    public boolean askShortcut(Plane plane) {
        return askYesNo("Take the shortcut for " + plane.getName() + "?");
    }

    /**
     * Asks the user if they want to jump.
     * @param plane The plane that can jump
     * @return true if the user wants to jump
     */
    public boolean askJump(Plane plane) {
        return askYesNo("Jump 4 blocks with " + plane.getName() + "?");
    }

    /**
     * Displays an error message when the user enters invalid input.
     */
    public void displayInvalidInput() {
        System.out.println(RED_B + "  [!] Invalid input - please try again." + RESET);
    }

    /**
     * Prompts the player to press ENTER to roll the dice.
     */
    public void promptRollDice() {
        System.out.print("  Press ENTER to roll the dice... ");
        scanner.nextLine();
    }

    /**
     * When a player rolls a 6, asks whether they want to take off or move.
     * @return true if the user chooses to take off, false if they choose to move
     */
    public boolean askTakeOffOrMove() {
        System.out.println(YELLOW_B + "  You rolled a 6! What do you want to do?" + RESET);
        System.out.println("    1. Take off a new plane from home");
        System.out.println("    2. Move an existing plane");
        System.out.print("  Enter choice (1 or 2): ");
        return readIntInRange(1, 2) == 1;
    }

    /**
     * Asks the player to pick an active plane to move.
     * @param player The current player
     * @return The index of the chosen plane, or -1 if no planes can move
     */
    public int getActivePlaneChoice(Player player) {
        Plane[] planes = player.getPlanes();
        int[] validIdx = new int[planes.length];
        int count = 0;

        System.out.println("  Choose a plane to move:");
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getPos() != -1 && !planes[i].getIfWin()) {
                validIdx[count] = i;
                count++;
                System.out.println("    " + count + ". " + planes[i].getName() + " (at position " + planes[i].getPos() + ")");
            }
        }

        if (count == 0) {
            return -1;
        }

        System.out.print("  Enter choice (1 to " + count + "): ");
        int choice = readIntInRange(1, count);
        return validIdx[choice - 1];
    }

    /**
     * Asks the player to pick a plane at home to take off.
     * @param player The current player
     * @return The index of the chosen plane, or -1 if no planes are at home
     */
    public int getHomePlaneChoice(Player player) {
        Plane[] planes = player.getPlanes();
        int[] validIdx = new int[planes.length];
        int count = 0;

        System.out.println("  Choose a plane to take off:");
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getIsAtHome()) {
                validIdx[count] = i;
                count++;
                System.out.println("    " + count + ". " + planes[i].getName() + " (at home)");
            }
        }

        if (count == 0) {
            return -1;
        }

        System.out.print("  Enter choice (1 to " + count + "): ");
        int choice = readIntInRange(1, count);
        return validIdx[choice - 1];
    }

    /**
     * Reads an integer from the user in the range [min, max].
     * @param min The minimum valid value
     * @param max The maximum valid value
     * @return The integer entered by the user
     */
    private int readIntInRange(int min, int max) {
        while (true) {
            int value = scanner.nextInt();
            scanner.nextLine(); 
            if (value >= min && value <= max) {
                return value;
            }
            displayInvalidInput();
            System.out.print("  Enter a number from " + min + " to " + max + ": ");
        }
    }
}
