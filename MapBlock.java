/** 
 * MapBlock.java
 * Represents a block on the Ludo game map, which can be part of the main loop or a player's entry/final route.
 * Each block has an index and a color associated with it.
 */
public abstract class MapBlock {
    /** Attributes */
    private int index;
    private String color;
    private Plane[] landingPlanes;

    /** Constructor 
     * @param index The index of the block on the map
     * @param color The color of the block (e.g., "RED", "BLUE", "YELLOW", "GREEN")
    */
    public MapBlock(int index, String color) {
        this.index = index;
        this.color = color;
        this.landingPlanes = new Plane[Game.NUM_PLAYERS*4]; // To prevent out of bounds, we can set this to a large number since a block can have multiple planes landing on it
    }
}
