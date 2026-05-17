/**
 * Six-sided die used to generate movement values.
 */
public class Dice {
    /** Most recent die-roll result. */
    private int result;

    /**
     * Creates a die ready to be rolled.
     */
    public Dice() {
    }

    /**
     * Rolls the die and returns the generated value.
     *
     * @return a random value from 1 through 6
     */
    public int getResult() {
        // Generates a random number from 1 to 6
        this.result = (int) (Math.random() * 6) + 1;
        return this.result;
    }
}
