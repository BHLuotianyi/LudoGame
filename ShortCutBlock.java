/**
 * Main-loop block that can move a matching-color plane through a shortcut.
 */
public class ShortCutBlock extends MainMapBlock {
    /**
     * Creates a shortcut block.
     *
     * @param index the block's index in the game map
     * @param color the block color
     */
    public ShortCutBlock(int index, String color) {
        super(index, color);
    }

    /**
     * Handles a landing on a shortcut block.
     *
     * After normal main-loop landing behavior, a matching-color plane takes
     * the shortcut movement.
     *
     * @param game the current game
     * @param plane the plane that landed on this block
     * @param ifJumped whether the plane arrived as part of a jump
     */
    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        super.onLanding(game, plane, ifJumped);
    }
}
