/** 
 * ShortCutBlock.java
 * Represents a shortcut block on the Ludo game map, which allows a player to take a shorter path to the finish.
 */
public class ShortCutBlock extends MainMapBlock {
    public ShortCutBlock(int index, String color) {
        super(index, color);
    }

    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        super.onLanding(game, plane, ifJumped);
        if (plane.getColor().equals(this.getColor())) {
            // TODO: Ask the player if they want to take the shortcut
            game.movePlane(plane, 12, false);
        }
    }
}
