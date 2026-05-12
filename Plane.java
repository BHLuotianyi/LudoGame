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
    private boolean ifWin; // Used to track if the plane has reached the end
    private int pos;
    private String color;
    private int level;
    private boolean isAtHome;
    private Player owner;
    private String name;
    private int id;
    private boolean isMoving;
    private int headingBlock; // The index of the block that the plane is heading to (going to move to in next step)
    private boolean ifMovedThisTurn; // Used to track if the plane has already moved in the current turn, to prevent multiple moves in one turn

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
        this.isMoving = false;
        this.ifWin = false;
        this.pos = -1; // Position is set to -1 when the plane is at home, and will be updated to the index of the map block when it moves out of home
    }

    /**
     * Marks the plane as having won the game.
     */
    public void win() {
        this.ifWin = true;
        this.owner.setFinishedCount(this.owner.getFinishedCount() + 1);
    }

    /** Gets whether the plane has reached the end.
     * @return true if the plane has won, false otherwise
     */
    public boolean getIfWin() {
        return this.ifWin;
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

    /**
     * Gets the plane's color.
     * @return the plane's color in string format (e.g. "RED", "BLUE", etc.)
     */
    public String getColor() {
        return this.color;
    }

    /** Gets whether the plane has moved in the current turn.
     * @return true if the plane has moved this turn, false otherwise
     */
    public boolean getIfMovedThisTurn() {
        return this.ifMovedThisTurn;
    }

    /**
     * Sets whether the plane has moved in the current turn.
     * @param whetherMovedThisTurn true if the plane has moved this turn, false otherwise
     */
    public void setIfMovedThisTurn(boolean whetherMovedThisTurn) {
        this.ifMovedThisTurn = whetherMovedThisTurn;
    }

    /** Gets whether the plane is currently moving.
     * @return true if the plane is moving, false otherwise
     */
    public boolean getIsMoving() {
        return this.isMoving;
    }

    /** Sets whether the plane is currently moving.
     * @param isMoving true if the plane is moving, false otherwise
     */
    public void setIsMoving(boolean isMoving) {
            this.isMoving = isMoving;
    }

    /**
     * Gets the index of the block that the plane is heading to.
     * @return the index of the next block
     */
    public int getHeadingBlock() {
        return this.headingBlock;
    }

    /**
     * Sets the index of the block that the plane is heading to.
     * @param headingBlock the index of the next block
     */
    public void setHeadingBlock(int headingBlock) {
        this.headingBlock = headingBlock;
    }
}
