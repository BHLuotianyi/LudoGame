import java.util.Scanner;

/**
 * Handles terminal display and user input for the Ludo game.
 */
public class Ui {
    /** Scanner used for terminal input. */
    private Scanner scanner;

    /**
     * Creates a terminal user interface.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the current board state.
     *
     * This method currently prints only occupied blocks as a compact summary.
     *
     * @param game the game to display
     */
    public void displayBoard(Game game) {
        // TODO: Replace this summary with a full board layout that shows the
        // main loop, each final route, and each player's home area.
        MapBlock[] map = game.getMap();
        System.out.println();
        System.out.println("Board:");
        for (int i = 0; i < map.length; i++) {
            Plane[] planes = map[i].getLandingPlanes();
            String planeNames = "";
            for (int j = 0; j < planes.length; j++) {
                if (planes[j] != null) {
                    planeNames += planes[j].getName() + " ";
                }
            }
            if (!planeNames.equals("")) {
                System.out.println(i + " " + map[i].getColor() + ": " + planeNames);
            }
        }
    }

    /**
     * Displays the current player's planes and home state.
     *
     * @param player the player to display
     */
    public void displayPlayerStatus(Player player) {
        System.out.println();
        System.out.println(player.getColor() + "'s turn");
        Plane[] planes = player.getPlanes();
        for (int i = 0; i < planes.length; i++) {
            Plane plane = planes[i];
            String status;
            if (plane.getIfWin()) {
                status = "finished";
            } else if (plane.getIsAtHome()) {
                status = "home";
            } else {
                status = "block " + plane.getPos();
            }
            System.out.println(plane.getName() + ": " + status);
        }
    }

    /**
     * Asks the player to choose one of their planes.
     *
     * This method currently rejects only finished planes.
     *
     * @param player the current player
     * @return the chosen plane
     */
    public Plane askPlaneChoice(Player player) {
        // TODO: Limit choices to planes that can legally move for the current
        // die roll once GameController or Game exposes that legal-move list.
        while (true) {
            System.out.print("Choose a plane: ");
            String input = scanner.nextLine().trim().toUpperCase();
            Plane[] planes = player.getPlanes();
            for (int i = 0; i < planes.length; i++) {
                if (planes[i].getName().equals(input) && !planes[i].getIfWin()) {
                    return planes[i];
                }
            }
            System.out.println("Invalid plane.");
        }
    }

    /**
     * Asks a yes-or-no question.
     *
     * @param question the question to ask
     * @return true for yes; false for no
     */
    public boolean askYesNo(String question) {
        while (true) {
            System.out.print(question + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }

    /**
     * Displays a message.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
