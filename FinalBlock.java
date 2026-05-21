/**
 * A block that represents the final destination for a player's planes.
 */
public class FinalBlock extends FinalRouteBlock{
    /**
     * Creates a final block.
     *
     * @param index the block's index in the game map
     * @param color the owner color of this final block
     */
    public FinalBlock(int index, String color) {
        super(index, color);
    }

    /**
     * Reverse the plane when it passes through this block.
     *
     * @param game the current game
     * @param plane the plane that passes through this block
     */
    public void onPassing(Game game, Plane plane) {
        super.onPassing(game, plane);
        plane.setIsReversing(true);
    }

    /**
     * Applies the landing behavior for this final block.
     *
     * @param game the current game
     * @param plane the plane that landed on this block
     * @param ifJumped whether the plane arrived as part of a jump
     */
    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        super.onLanding(game, plane, ifJumped);
        if (!plane.getIsAtHome()) {
            this.removePlane(plane);
            plane.win();
        }
    }
}
