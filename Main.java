/**
 * Application entry point for the Ludo game.
 */
class Main {
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
