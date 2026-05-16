/**
 * Application entry point for the Ludo game.
 */
class Main {
    /**
     * Prevents creating an entry-point object.
     */
    private Main() {
    }

    /**
     * Starts the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.play();
    }
}
