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
     * Displays the full game state: turn info and a visual map.
     * Called at the start of every turn.
     * @param game The current game instance
     */
    public void displayBoard(Game game) {
        System.out.println();
        System.out.println("=================================================================");
        System.out.println("  Turn: " + game.getTurnCount() + "  |  Current: " + game.getCurrPlayer().getColor());
        System.out.println("=================================================================");
        
        renderMap(game);
        
        System.out.println("=================================================================");
        displayAllPlayerStatus(game.getPlayers(), game.getCurrPlayer());
        System.out.println("=================================================================");
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
                
                String strOpenBracket = "[";
                String strCloseBracket = "]";
                
                if (map[i] instanceof ShortCutBlock) {
                    strOpenBracket = "(";
                    strCloseBracket = ")";
                }
                
                int id = map[i].getId();
                if (id == 3 || id == 16 || id == 29 || id == 42) { // The block where planes goes to after leaving home
                    strOpenBracket = "{";
                    strCloseBracket = "}";
                }
                
                Plane[] planesOnBlock = map[i].getLandingPlanes();
                if (planesOnBlock != null && planesOnBlock[0] != null) {
                    String name = planesOnBlock[0].getName();
                    if (planesOnBlock[1] != null) { // If there's more than 1 plane, show a "+" sign
                        grid[y][x] = strOpenBracket + name.substring(0, 1) + "+" + strCloseBracket;
                    } else {
                        grid[y][x] = strOpenBracket + name + strCloseBracket;
                    }
                } else {
                    grid[y][x] = strOpenBracket + map[i].getColor().substring(0,1) + " " + strCloseBracket;
                }
            }
        }

        Player[] players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            int homeX = 0; // X-coordinate for home base display
            int homeY = 0; // Y-coordinate for home base display
            String color = p.getColor();
            if (color.equals("RED")) {
                homeX = 2; homeY = 2;
            } else if (color.equals("BLUE")) {
                homeX = 12; homeY = 2;
            } else if (color.equals("YELLOW")) {
                homeX = 12; homeY = 12;
            } else if (color.equals("GREEN")) {
                homeX = 2; homeY = 12;
            }
            
            int homeCount = 0;
            Plane[] home = p.getHome();
            for (int j = 0; j < home.length; j++) {
                Plane pl = home[j];
                if (pl != null) {
                    homeCount++;
                }
            }
            grid[homeY][homeX] = " " + homeCount + "H ";
        }

        System.out.print("     "); // The paddings before the column numbers
        for (int x = 0; x < 15; x++) {
            System.out.printf(" %2d ", x);
        }
        System.out.println();
        System.out.print("     ");
        for (int x = 0; x < 15; x++) {
            System.out.print("----");
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
    private int[] getCoords(int i) { // Understand this according to the 2D map that the game shows when running
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
        if (isCurrent) {
            System.out.print(">> " + player.getColor() + ": ");
        } else {
            System.out.print("   " + player.getColor() + ": ");
        }

        Plane[] planes = player.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getIfWin()) {
                System.out.print(planes[i].getName() + "(WIN)  ");
            } else if (planes[i].getPos() == -1) {
                System.out.print(planes[i].getName() + "(HOME)  ");
            } else {
                int[] coords = getCoords(planes[i].getPos());
                System.out.print(planes[i].getName() + "(" + coords[0] + "," + coords[1] + ")  ");
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
     * @param result The value of the dice roll
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
     * Displays a message when a plane lands on its own color and jumps forward.
     * @param plane The plane that jumped
     * @param newPos The position after the jump
     */
    public void displaySameColorJump(Plane plane, int newPos) {
        int[] coords = getCoords(newPos);
        System.out.println("  >> " + plane.getName() + " landed on same color - jumped to (" + coords[0] + "," + coords[1] + ")!");
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
        System.out.println("  [!] Invalid input - please try again.");
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
        System.out.println("  You rolled a 6! What do you want to do?");
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
                int[] coords = getCoords(planes[i].getPos());
                System.out.println("    " + count + ". " + planes[i].getName() + " (at (" + coords[0] + "," + coords[1] + "))");
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
