/**
 * Dice class for the Ludo project.
 * Implements only the green getter as per UML specifications.
 */
public class Dice {
    private int result;

    /**
     * Generates and returns a random dice roll between 1 and 6.
     * This acts as the primary method to get a move value.
     */
    public int getResult() {
        // Generates a random number from 1 to 6
        this.result = (int) (Math.random() * 6) + 1;
        return this.result;
    }
}