/** 
 * MainMapBlock.java
 * Represents a main map block on the Ludo game map, which is part of the main loop.
 */
public class MainMapBlock extends MapBlock {
    public MainMapBlock(int index, String color) {
        super(index, color);
    }

    public void onLanding(Game game, Plane plane) {
        super.onLanding(game, plane);
        if (!plane.getIfMovedThisTurn()) {
            // TODO: Placeholder
        }
    }
}
