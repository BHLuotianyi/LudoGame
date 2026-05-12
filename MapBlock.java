/**
 * Base class for a board block in the Ludo map.
 *
 * Each block has a map index, a color, and a list of planes currently
 * occupying it. Subclasses can override landing behavior to add special
 * movement effects.
 */
public abstract class MapBlock {
    /** Index of this block in the game map. */
    private int id;

    /** Color assigned to this block. */
    private String color;

    /** Planes currently occupying this block. */
    private Plane[] landingPlanes;

    /** Color of the planes currently occupying this block, or null if empty. */
    private String currLandingColor;

    /**
     * Compacts the landing-plane array so occupied slots appear before empty slots.
     */
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

    /**
     * Resolves collisions when a plane lands on this block.
     *
     * @param game the current game, used to send planes home
     * @param newcomer the plane landing on this block
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

    /**
     * Creates a board block.
     *
     * @param id the block's index in the game map
     * @param color the block color, such as "RED" or "BLUE"
     */
    public MapBlock(int id, String color) {
        this.id = id;
        this.color = color;
        this.landingPlanes = new Plane[Game.NUM_PLAYERS*4]; // To prevent out of bounds, we can set this to a large number since a block can have multiple planes landing on it
    }

    /**
     * Applies the default landing behavior for this block.
     *
     * @param game the current game
     * @param plane the plane that landed on this block
     * @param ifJumped whether the plane arrived as part of a jump
     */
    public void onLanding(Game game, Plane plane, boolean ifJumped) {
        handleTraffic(game, plane);
    }

    /**
     * Gets this block's map index.
     *
     * @return the block ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets this block's color.
     *
     * @return the block color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Gets the planes currently occupying this block.
     *
     * @return the landing-plane array
     */
    public Plane[] getLandingPlanes() {
        return this.landingPlanes;
    }

    /**
     * Removes a plane from this block if it is present.
     *
     * @param targetPlane the plane to remove
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

    /**
     * Adds a plane to the first available landing slot.
     *
     * @param plane the plane to add
     */
    private void addPlane(Plane plane) {
        for (int i = 0; i < landingPlanes.length; i++) {
            if (landingPlanes[i] == null) {
                landingPlanes[i] = plane; // Add the plane to the block by placing it in the first available position in the landingPlanes array
                break;
            }
        }
    }

}
