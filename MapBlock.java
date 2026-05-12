/** 
 * MapBlock.java
 * Represents a block on the Ludo game map, which can be part of the main loop or a player's entry/final route.
 * Each block has an index and a color associated with it.
 */
public abstract class MapBlock {
    /** Attributes */
    private int id;
    private String color;
    private Plane[] landingPlanes;

    /** Constructor 
     * @param id The ID of the block in the map list
     * @param color The color of the block (e.g., "RED", "BLUE", "YELLOW", "GREEN")
    */
    public MapBlock(int id, String color) {
        this.id = id;
        this.color = color;
        this.landingPlanes = new Plane[Game.NUM_PLAYERS*4]; // To prevent out of bounds, we can set this to a large number since a block can have multiple planes landing on it
    }

    public int getId() {
        return this.id;
    }

    /**
     * @return The color of the block.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * @return The list of planes currently occupying this block.
     */
    public Plane[] getLandingPlanes() {
        return this.landingPlanes;
    }

    /** Search for a plane from the block and remove it 
     * @param targetPlane The plane to be removed from the block
    */
    public void removePlane(Plane targetPlane) {
        for (int i = 0; i < landingPlanes.length; i++) {
            if (landingPlanes[i] == targetPlane) {
                landingPlanes[i] = null; // Remove the plane from the block by setting its position in the landingPlanes array to null
                break;
            }
        }
    }

}
