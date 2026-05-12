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
    public Plane(Player owner, int level, int id) {
        this.owner = owner;
        this.level = level;
        this.id = id;
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
}
