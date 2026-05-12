/**
 * Level-two plane with higher collision strength than a fighter plane.
 */
public class CommanderPlane extends Plane {
    /**
     * Creates a commander plane for a player.
     *
     * @param owner the player who owns this plane
     * @param id the plane's unique ID in the global plane list
     * @param name the plane's short display name
     */
    public CommanderPlane(Player owner, int id, String name) {
        super(owner, 2, id, owner.getColor(), name);
    }
}
