/**
 * Base class for all movable planes in the Ludo game.
 *
 * A plane tracks its owner, strength level, board position, next heading
 * block, and movement state. Concrete subclasses define the level used when
 * resolving collisions.
 */
public abstract class Plane {
    /** Whether this plane has reached the finish. */
    private boolean ifWin;

    /** Current map index, or -1 when the plane is at home. */
    private int pos;

    /** Color of the plane and its owner. */
    private String color;

    /** Collision strength level for this plane. */
    private int level;

    /** Whether this plane is currently in its home area. */
    private boolean isAtHome;

    /** Player who owns this plane. */
    private Player owner;

    /** Short display name of the plane. */
    private String name;

    /** Unique ID in the global plane list. */
    private int id;

    /** Whether this plane is currently in a movement sequence. */
    private boolean isMoving;

    /** Map index of the next block this plane will enter. */
    private int headingBlockIndex;

    /** Whether this plane is currently moving in reverse. */
    private boolean isReversing;

    /**
     * Creates a plane owned by a player.
     *
     * @param owner the player who owns this plane
     * @param level the plane's collision strength level
     * @param id the plane's unique ID in the global plane list
     * @param color the plane's player color
     * @param name the short display name of the plane
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
     * Marks this plane as finished and increments its owner's finished-plane count.
     * Has no effect if the plane has already finished.
     */
    public void win() {
        if (this.ifWin) {
            return; // Prevent double-counting
        }
        this.ifWin = true;
        this.owner.setFinishedCount(this.owner.getFinishedCount() + 1);
    }

    /**
     * Reports whether the plane has reached the finish.
     *
     * @return true if the plane has finished; false otherwise
     */
    public boolean getIfWin() {
        return this.ifWin;
    }

    /**
     * Gets the plane's current board position.
     *
     * @return the map index, or -1 when the plane is at home
     */
    public int getPos() {
        return this.pos;
    }

    /**
     * Sets the plane's current board position.
     *
     * @param index the new map index, or -1 when the plane is at home
     */
    public void setPos(int index) {
        this.pos = index;
    }

    /**
     * Gets the player who owns the plane.
     *
     * @return the plane's owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Gets the plane's unique ID in the global plane list.
     *
     * @return the plane ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the plane's short display name.
     *
     * @return the plane name, such as "G1" or "R2"
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets whether the plane is in its home area.
     *
     * @param isAtHome true if the plane is at home; false otherwise
     */
    public void setIsAtHome(boolean isAtHome) {
        this.isAtHome = isAtHome;
    }

    /**
     * Reports whether the plane is in its home area.
     *
     * @return true if the plane is at home; false otherwise
     */
    public boolean getIsAtHome() {
        return this.isAtHome;
    }

    /**
     * Gets the plane's collision strength level.
     *
     * @return the plane level
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * Gets the plane's color.
     *
     * @return the plane color, such as "RED" or "BLUE"
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Reports whether the plane is currently in the middle of a movement sequence.
     *
     * @return true if the plane is moving; false otherwise
     */
    public boolean getIsMoving() {
        return this.isMoving;
    }

    /**
     * Sets whether the plane is currently in the middle of a movement sequence.
     *
     * @param isMoving true if the plane is moving; false otherwise
     */
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * Gets the next block the plane will enter.
     *
     * @return the map index of the next block
     */
    public int getHeadingBlockIndex() {
        return this.headingBlockIndex;
    }

    /**
     * Sets the next block the plane will enter.
     *
     * @param index the map index of the next block
     */
    public void setHeadingBlockIndex(int index) {
        this.headingBlockIndex = index;
    }

    /**
     * Gets whether the plane is currently moving in reverse.
     *
     * @return true if the plane is moving in reverse; false otherwise
     */
    public boolean getIsReversing() {
        return this.isReversing;
    }

    /**
     * Sets whether the plane is currently moving in reverse.
     *
     * @param isReversing true if the plane is moving in reverse; false otherwise
     */
    public void setIsReversing(boolean isReversing) {
        this.isReversing = isReversing;
    }
}
