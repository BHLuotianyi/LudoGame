/** 
 * Player.java
 * Represents a player in the Ludo game. Each player has a color and a set of planes.
 */

public class Player {
    // Instance variables
    private int id;
    private String color;
    private int finishedCount;
    private Plane[] planes;
    private Plane[] home;
    private String shortName;

    public Player(int id, String color) {
        this.id = id;
        this.color = color; // Assuming color is represented by the player's ID (0-3)
        this.finishedCount = 0;
        this.planes = new Plane[4]; // Each player has 4 planes
        this.home = new Plane[4]; // Each player has a home base of 4 planes
        this.shortName = color.substring(0,1); // Short name for display purposes (e.g., "R" for Red)
    }

    /**
     * Gets the player's ID.
     * @return the player's ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the player's base of four planes.
     * @return the player's home
     */
    public Plane[] getHome() {
        return this.home;
    }

    /**
     * Sets the player's home.
     * @param home the player's home
     */
    public void setHome(Plane[] home) {
        this.home = home;
    }

    /**
     * Gets the player's color in string format.
     * @return the player's color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Gets the player's planes.
     * @return the player's planes
     */
    public Plane[] getPlanes() {
        return this.planes;
    }

    /**
     * Sets the player's planes.
     * @param planes the player's planes
     */
    public void setPlanes(Plane[] planes) {
        this.planes = planes;
    }

    /**
     * Sets the player's number of finished planes.
     * @param finishedCount the player's finished count
     */
    public void setFinishedCount(int finishedCount) {
        this.finishedCount = finishedCount;
    }

    /**
     * Gets the player's number of finished planes.
     * @return the player's finished count
     */
    public int getFinishedCount() {
        return this.finishedCount;
    }

    /**
     * Gets the player's short name for display purposes.
     * @return the player's short name
     */
    public String getShortName() {
        return this.shortName;
    }
}
