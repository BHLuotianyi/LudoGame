/**
 * Level-one plane used for normal collision strength.
 */
public class FighterPlane extends Plane {
    /**
     * Creates a fighter plane for a player.
     *
     * @param owner the player who owns this plane
     * @param id the plane's unique ID in the global plane list
     * @param name the plane's short display name
     */
    public FighterPlane(Player owner, int id, String name) {
        super(owner, 1, id, owner.getColor(), name);
    }
}
