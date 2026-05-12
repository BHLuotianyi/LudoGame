/** 
 * MainMapBlock.java
 * Represents a main map block on the Ludo game map, which is part of the main loop.
 */
public class MainMapBlock extends MapBlock {
    public MainMapBlock(int index, String color) {
        super(index, color);
    }

    /**
     * Handles the event when a plane lands on this block.
     * @param game The game instance.
     * @param plane The plane that landed on this block.
     * @param ifJumped Whether the plane jumped to this block.
     */
    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        super.onLanding(game, plane, ifJumped);
        if (!ifJumped && plane.getColor().equals(this.getColor())) {
            // TODO: Ask the player if they want to jump
            game.movePlane(plane, 4, true);
        }
    }
}
