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
        if (plane.getColor().equals(this.getColor()) && !plane.getIsAtHome()) {
            super.onLanding(game, plane, true); // ifJump = true to prevent triggering a jump on this block
            
            // Then we take the shortcut. We pass the ORIGINAL ifJumped to the destination.
            // If we jumped TO this shortcut block (ifJumped=true), we won't jump at the destination.
            // If we walked TO this shortcut block (ifJumped=false), we WILL jump at the destination.
            // This ensures total movement is always Shortcut(12) + 1 Jump(4) = 16 blocks.
            // TODO: Ask the player if they want to take the shortcut
            game.movePlane(plane, 12, ifJumped);
        } else {
            // Normal landing for non-matching colors
            super.onLanding(game, plane, ifJumped);
        }
    }
}
