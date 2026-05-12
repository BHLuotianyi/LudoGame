/** 
 * FighterPlane.java
 * Represents a low-level plane in the Ludo game.
 */
public class FighterPlane extends Plane {
    public FighterPlane(Player owner, int id, String name) {
        super(owner, 1, id, owner.getColor(), name);
    }
}
