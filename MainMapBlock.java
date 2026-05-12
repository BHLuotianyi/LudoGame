/**
 * Standard block on the main loop of the Ludo board.
 */
public class MainMapBlock extends MapBlock {
    /**
     * Creates a main-loop block.
     *
     * @param index the block's index in the game map
     * @param color the block color
     */
    public MainMapBlock(int index, String color) {
        super(index, color);
    }

    /**
     * Handles a landing on a main-loop block.
     *
     * After default collision handling, a matching-color landing can trigger
     * a four-block jump if the plane did not arrive from another jump.
     *
     * @param game the current game
     * @param plane the plane that landed on this block
     * @param ifJumped whether the plane arrived as part of a jump
     */
    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        super.onLanding(game, plane, ifJumped);
        if (!ifJumped && plane.getColor().equals(this.getColor()) && !plane.getIsAtHome()) { // Note: adding the "not at home" condition to prevent planes sent home from triggering the jump logic
            // TODO: Ask the player if they want to jump
            game.movePlane(plane, 4, true);
        }
    }
}
