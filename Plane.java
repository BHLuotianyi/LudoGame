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

    public int getPos() {
        return this.pos;
    }

    public void setPos(int index) {
        this.pos = index;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
