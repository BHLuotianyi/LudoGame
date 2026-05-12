/** 
 * Dice.java
 * To deal with the random number formatting.
 */
public class Dice {
    private int result;

    //  CONSTRUCTOR 
    public Dice() {
        // Initialize the dice result to a default value (e.g., 0 or 1)
        this.result = 1;
    }

    /**
     * Gets the last rolled dice result.
     * @return The current value of the dice (1-6).
     */
    public int getResult() {
        return this.result;
    }
}