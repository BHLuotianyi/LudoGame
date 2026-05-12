/** 
 * Plane.java
 * An abstract class representing a plane in the Ludo game. This class will be extended by specific types of planes such as FighterPlane and CommanderPlane.
 */
/**
 * Abstract Plane class for the Ludo CCT project.
 * Contains the green-colored Constructor, Getters, and Setters.
 */
public abstract class Plane {
    // Instance variables
    private int pos;
    private String color;
    private int level;
    private boolean isAtHome;
    private Player owner;
    private String name;
    private int id;

    // --- Constructor & Accessors/Mutators

    /**
     * Constructor for the Plane object.
     */
    public Plane(Player owner, int level, int id, String color, String name) {
        this.owner = owner;
        this.level = level;
        this.id = id; // The index of the plane in the global planes list
        this.color = color;
        this.name = name;
        this.isAtHome = true; // Planes start at home by default
        this.pos = -1; // Position is set to -1 when the plane is at home, and will be updated to the index of the map block when it moves out of home
    }

    /**
     * Gets the plane's position (index in the map array).
     * @return the plane's position
     */
    public int getPos() {
        return this.pos;
    }

    /**
     * Sets the plane's position (index in the map array).
     * @param index the new position of the plane
     */
    public void setPos(int index) {
        this.pos = index;
    }

    /**
     * Gets the plane's owner player.
     * @return the plane's owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Gets the plane's ID.
     * @return the plane's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the plane's name.
     * @return the plane's name in string format (e.g. "G1", "R2", etc.)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets if the plane is at home or not.
     * @param isAtHome boolean value indicating if the plane is at home (true) or not (false)
     */
    public void setIsAtHome(boolean isAtHome) {
        this.isAtHome = isAtHome;
    }

    /**
     * Gets whether the plane is at home or not.
     * @return true if the plane is at home, false otherwise
     */
    public boolean getIsAtHome() {
        return this.isAtHome;
    }

    /**
     * Gets the plane's level.
     * @return the plane's level
     */
    public int getLevel(){
        return this.level;
    }
}
