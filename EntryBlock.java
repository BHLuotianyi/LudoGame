/**
 * Main-loop block that leads a matching-color plane into its final route.
 */
public class EntryBlock extends MapBlock {
    /** Index of the first final-route block reached from this entry block. */
    private int leadsToIndex;

    /**
     * Creates an entry block and maps it to the first final-route block for its
     * color.
     *
     * @param id the block's index in the game map
     * @param color the block color
     */
    public EntryBlock(int id, String color) {
        super(id, color);
        if (color.equals(Game.RED)) {
            leadsToIndex = Game.RED_ENTRY_INDEX;
        } else if (color.equals(Game.BLUE)) {
            leadsToIndex = Game.BLUE_ENTRY_INDEX;
        } else if (color.equals(Game.YELLOW)) {
            leadsToIndex = Game.YELLOW_ENTRY_INDEX;
        } else if (color.equals(Game.GREEN)) {
            leadsToIndex = Game.GREEN_ENTRY_INDEX;
        }
    }

    /**
     * Gets the first final-route block reached from this entry block.
     *
     * @return the destination final-route index
     */
    public int getLeadsToIndex() {
        return leadsToIndex;
    }

    /**
     * Stops the plane from reversing when it passes through this block.
     *
     * @param game the current game
     * @param plane the plane that passes through this block
     */
    public void onPassing(Game game, Plane plane) {
        super.onPassing(game, plane);
        if (plane.getIsReversing()) {
            plane.setIsReversing(false);
        }
    }
}
