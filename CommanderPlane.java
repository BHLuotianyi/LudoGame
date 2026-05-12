/** 
 * CommanderPlane.java
 * Represents a high-level plane in the Ludo game.
 */
public class CommanderPlane extends Plane {
    public CommanderPlane(Player owner, int id, String name) {
        super(owner, 2, id, owner.getColor(), name);
    }
}
