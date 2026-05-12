/** 
 * MapBlock.java
 * Represents a block on the Ludo game map, which can be part of the main loop or a player's entry/final route.
 * Each block has an index and a color associated with it.
 */
public abstract class MapBlock {
    /** Attributes */
    private int id;
    private String color;
    private Plane[] landingPlanes; // To keep track of planes currently on this block
    private String currLandingColor;

    /** Organizes the landing planes array by removing null entries. */
    private void organizeLandingPlanes() {
        Plane[] newLandingPlanes = new Plane[landingPlanes.length];
        int index = 0;
        for (int i = 0; i < landingPlanes.length; i++) {
            if (landingPlanes[i] != null) {
                newLandingPlanes[index] = landingPlanes[i];
                index++;
            }
        }
        this.landingPlanes = newLandingPlanes;
    }

    /** Handles the traffic when a plane lands on the block, determining if any planes need to be sent home based on the colors and levels of the planes involved.
     * @param game The current game instance, used to send planes home if necessary.
     * @param newcomer The plane that is landing on the block.
     */
    private void handleTraffic(Game game, Plane newcomer) {
        if ((currLandingColor != null) && (!currLandingColor.equals(newcomer.getColor()))){
            // If there are planes of a different color already on the block
            int maxLevelInBlock = 0;
            for (int i = 0; i < landingPlanes.length; i++) { // Determine the maximum level of planes currently in the block
                if (landingPlanes[i] != null) {
                    if (landingPlanes[i].getLevel() > maxLevelInBlock) {
                        maxLevelInBlock = landingPlanes[i].getLevel();
                    }
                }
            }
            if (newcomer.getLevel() < maxLevelInBlock) {
                game.sendPlaneHome(newcomer);
            } else {
                for (int i = 0; i< landingPlanes.length; i++) {
                    if (landingPlanes[i] != null) {
                        game.sendPlaneHome(landingPlanes[i]);
                    }
                    organizeLandingPlanes();
                    addPlane(newcomer);
                    currLandingColor = newcomer.getColor(); // Update the current landing color to the newcomer's color
                }
            }
        } else {
            // If there are no planes of a different color on the block, simply add the newcomer to the block
            addPlane(newcomer);
            currLandingColor = newcomer.getColor(); // Update the current landing color to the newcomer's color
        }

    }

    /** Constructor 
     * @param id The ID of the block in the map list
     * @param color The color of the block (e.g., "RED", "BLUE", "YELLOW", "GREEN")
    */
    public MapBlock(int id, String color) {
        this.id = id;
        this.color = color;
        this.landingPlanes = new Plane[Game.NUM_PLAYERS*4]; // To prevent out of bounds, we can set this to a large number since a block can have multiple planes landing on it
    }

    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        handleTraffic(game, plane);
    }

    /** Get the ID of the block.
     * @return The ID of the block.
     */
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
        if (landingPlanes[0] == null) {
            currLandingColor = null; // If there are no planes left on the block, reset the current landing color to null
        }
    }

    private void addPlane(Plane plane) {
        for (int i = 0; i < landingPlanes.length; i++) {
            if (landingPlanes[i] == null) {
                landingPlanes[i] = plane; // Add the plane to the block by placing it in the first available position in the landingPlanes array
                break;
            }
        }
    }

}
