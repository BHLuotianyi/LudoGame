/**
 * Represents one player in the Ludo game.
 *
 * A player has a stable ID, a color, a set of four planes, a home area, and
 * a count of planes that have reached the finish.
 */

public class Player {
    /** Player's ID in the game. */
    private int id;

    /** Player's assigned color. */
    private String color;

    /** Number of this player's planes that have finished. */
    private int finishedCount;

    /** The player's four planes. */
    private Plane[] planes;

    /** The player's home area. */
    private Plane[] home;

    /** One-letter prefix used in plane display names. */
    private String shortName;

    /** The index of the block where the player's planes start. */
    private int startingBlockIndex;

    /**
     * Creates a player with an ID and color.
     *
     * @param id the player's ID
     * @param color the player's color
     * @param startingBlockIndex the index of the block where the player's planes start
     */
    public Player(int id, String color, int startingBlockIndex) {
        this.id = id;
        this.color = color; // Assuming color is represented by the player's ID (0-3)
        this.finishedCount = 0;
        this.planes = new Plane[4]; // Each player has 4 planes
        this.home = new Plane[4]; // Each player has a home base of 4 planes
        this.startingBlockIndex = startingBlockIndex;
        this.shortName = color.substring(0,1); // Short name for display purposes (e.g., "R" for Red)
    }

    /**
     * Adds a plane to the player's home area.
     *
     * @param plane the plane to add to the home area
     */
    public void addPlaneToHome(Plane plane) {
        for (int i = 0; i < home.length; i++) {
            if (home[i] == null) {
                home[i] = plane;
                break;
            }
        }
    }

    /**
     * Removes a plane from the player's home area.
     *
     * @param targetPlane the plane to remove from the home area
     */
    public void removePlaneFromHome(Plane targetPlane) {
        for (int i = 0; i < home.length; i++) {
            if (home[i] == targetPlane) {
                home[i] = null;
                break;
            }
        }
    }

    /**
     * Gets the player's ID.
     *
     * @return the player's ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the player's home area.
     *
     * @return the player's home plane array
     */
    public Plane[] getHome() {
        return this.home;
    }

    /**
     * Sets the player's home area.
     *
     * @param home the new home plane array
     */
    public void setHome(Plane[] home) {
        this.home = home;
    }

    /**
     * Gets the player's color.
     *
     * @return the player's color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Gets the player's planes.
     *
     * @return the player's planes
     */
    public Plane[] getPlanes() {
        return this.planes;
    }

    /**
     * Sets the player's planes.
     *
     * @param planes the new player plane array
     */
    public void setPlanes(Plane[] planes) {
        this.planes = planes;
    }

    /**
     * Sets the player's number of finished planes.
     *
     * @param finishedCount the new finished-plane count
     */
    public void setFinishedCount(int finishedCount) {
        this.finishedCount = finishedCount;
    }

    /**
     * Gets the player's number of finished planes.
     *
     * @return the player's finished count
     */
    public int getFinishedCount() {
        return this.finishedCount;
    }

    /**
     * Gets the player's one-letter display prefix.
     *
     * @return the player's short name
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Gets the index of the block where the player's planes start.
     *
     * @return the starting block index
     */
    public int getStartingBlockIndex() {
        return this.startingBlockIndex;
    }
}
