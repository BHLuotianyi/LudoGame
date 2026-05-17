/**
 * Main.java
 * The entry point of the Ludo game.
 * This class only starts the game - all game logic is in Game,
 * and all display/input is in Ui.
 */
class Main {

    /**
     * Creates the game, UI, and dice, then runs the game loop.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        Ui ui = new Ui();
        Dice dice = new Dice();

        ui.displayWelcome();

        // Game loop - runs until a player wins
        while (!game.getIfGameOver()) {
            ui.displayBoard(game);
            ui.displayTurnStart(game.getCurrPlayer(), game.getTurnCount());
            ui.promptRollDice();
            int roll = dice.getResult();
            ui.displayDiceResult(roll);

            // Game logic for this turn - to be added by partner in Game.java
            // game.playTurn(roll, ui);
        }

        // Show the winner - to be added by partner (game.getWinner())
        // ui.displayGameOver(game.getWinner());
    }
}