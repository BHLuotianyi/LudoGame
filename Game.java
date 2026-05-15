/**
 * Coordinates the shared state and movement rules for a Ludo game.
 *
 * The game owns the board, players, and planes. It is responsible for
 * building the board layout, advancing planes one block at a time, applying
 * landing effects, and exposing the current game state to callers.
 */
public class Game {

    /** Number of blocks in the standard Ludo main loop. */
    public static final int MAIN_LOOP_SIZE = 52;

    /** Number of final-route blocks for each player. */
    public static final int FINAL_ROUTE_LENGTH = 6;

    /** First red final-route block index in the board array. */
    public static final int RED_ENTRY_INDEX = MAIN_LOOP_SIZE;

    /** First blue final-route block index in the board array. */
    public static final int BLUE_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH;

    /** First yellow final-route block index in the board array. */
    public static final int YELLOW_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*2;

    /** First green final-route block index in the board array. */
    public static final int GREEN_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*3;

    /** Red final destination block index in the board array. */
    public static final int RED_FINAL_INDEX = RED_ENTRY_INDEX + FINAL_ROUTE_LENGTH - 1;

    /** Blue final destination block index in the board array. */
    public static final int BLUE_FINAL_INDEX = BLUE_ENTRY_INDEX + FINAL_ROUTE_LENGTH - 1;

    /** Yellow final destination block index in the board array. */
    public static final int YELLOW_FINAL_INDEX = YELLOW_ENTRY_INDEX + FINAL_ROUTE_LENGTH - 1;

    /** Green final destination block index in the board array. */
    public static final int GREEN_FINAL_INDEX = GREEN_ENTRY_INDEX + FINAL_ROUTE_LENGTH - 1;

    /** Red main-loop entry block index. */
    public static final int RED_MAIN_ENTRY_INDEX = 0;

    /** Blue main-loop entry block index. */
    public static final int BLUE_MAIN_ENTRY_INDEX = 13;

    /** Yellow main-loop entry block index. */
    public static final int YELLOW_MAIN_ENTRY_INDEX = 26;

    /** Green main-loop entry block index. */
    public static final int GREEN_MAIN_ENTRY_INDEX = 39;
    
    /** Number of players in the game. */
    public static final int NUM_PLAYERS = 4;

    /** Color name for the red player and red board spaces. */
    public static final String RED = "RED";

    /** Color name for the blue player and blue board spaces. */
    public static final String BLUE = "BLUE";

    /** Color name for the yellow player and yellow board spaces. */
    public static final String YELLOW = "YELLOW";

    /** Color name for the green player and green board spaces. */
    public static final String GREEN = "GREEN";

    /** Complete board array containing the main loop and all final routes. */
    private MapBlock[] map = new MapBlock[MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*4];

    /** Number of turns that have been played. */
    private int turnCount;

    /** Players participating in the game. */
    private Player[] players;

    /** Player whose turn is currently active. */
    private Player currPlayer;

    /** Global list of every plane in the game. */
    private Plane[] planes;

    /** Whether the game has ended. */
    private boolean isGameOver = false;

    /**
     * Creates the main loop and each player's final route.
     */
    private void initMap() {
        for (int i = 0; i < map.length; i++) {
            String color = "";

            if ((i < MAIN_LOOP_SIZE) && (i % 4 == 0)) { // Handle the main loop blocks' colors
                color = RED;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 1)) {
                color = BLUE;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 2)) {
                color = YELLOW;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 3)) {
                color = GREEN;
            } else if ((i >= RED_ENTRY_INDEX) && (i < BLUE_ENTRY_INDEX)) { // Handle the final route blocks' colors
                color = RED;
            } else if ((i >= BLUE_ENTRY_INDEX) && (i < YELLOW_ENTRY_INDEX)) {
                color = BLUE;
            } else if ((i >= YELLOW_ENTRY_INDEX) && (i < GREEN_ENTRY_INDEX)) {
                color = YELLOW;
            } else if ((i >= GREEN_ENTRY_INDEX) && (i < GREEN_ENTRY_INDEX + FINAL_ROUTE_LENGTH)) {
                color = GREEN;
            }

            // Create the appropriate MapBlock object based on the index and color
            boolean isFinalDestination = (i == RED_FINAL_INDEX) || (i == BLUE_FINAL_INDEX) || (i == YELLOW_FINAL_INDEX) || (i == GREEN_FINAL_INDEX);

            if ((i < MAIN_LOOP_SIZE) && (i % 13 == 0)) {
                map[i] = new EntryBlock(i, color);
            } else if ((i < MAIN_LOOP_SIZE) && (i % 7 == 0)) {
                map[i] = new ShortCutBlock(i, color);
            } else if (i < MAIN_LOOP_SIZE) {
                map[i] = new MainMapBlock(i, color);
            } else if (isFinalDestination) {
                map[i] = new FinalBlock(i, color);
            } else if ((i >= MAIN_LOOP_SIZE) && !isFinalDestination) {
                map[i] = new FinalRouteBlock(i, color);
            }
        }
    }

    /**
     * Creates the four players and assigns their colors.
     */
    private void initPlayers() {
        players = new Player[4];
        players[0] = new Player(0, RED, 3);
        players[1] = new Player(1, BLUE, 16);
        players[2] = new Player(2, YELLOW, 29);
        players[3] = new Player(3, GREEN, 42);
    }

    /**
     * Creates each player's planes and stores them in both the global plane list
     * and the owning player's plane list.
     */
    private void initPlanes() {
        this.planes = new Plane[NUM_PLAYERS*4]; // Reset the planes list to ensure it is empty before adding new planes
        int newPlaneId = 0; // To assign a unique ID to each plane, which corresponds to its index in the global planes list
        for (int i = 0; i < players.length; i++) { // i is the index of the player in the players list
            Plane[] tempListOfPlanes = new Plane[4]; // Create a temporal list to storage newly created planes
            Plane[] tempHomePlanes = new Plane[4]; // Separate list for planes that are currently at home
            for (int j = 0; j < 4; j++) { // j is the index of the plane in the player's planes list
                Plane newPlane;
                if (j == 0) {
                    newPlane = new CommanderPlane(getPlayerById(i), newPlaneId, getPlayerById(i).getShortName()+j); // A plane's short name would be e.g. "R0", "B1", etc. (the player's short name + the plane's index in the player's planes list) 
                } else {
                    newPlane = new FighterPlane(getPlayerById(i), newPlaneId, getPlayerById(i).getShortName()+j);
                }
                tempListOfPlanes[j] = newPlane;
                tempHomePlanes[j] = newPlane;
                planes[newPlaneId] = newPlane;
                newPlaneId++;
            }
            players[i].setPlanes(tempListOfPlanes);
            players[i].setHome(tempHomePlanes);
        }
    }


    /**
     * Initializes a new game with a fresh board, player list, plane list, and
     * turn counter.
     */
    public Game() {
        initMap();
        initPlayers();
        initPlanes();
        turnCount = 0;
    }

    /**
     * Takes a plane off the home base and places it on the starting block.
     *
     * @param plane the plane to take off
     */
    public void takeoffPlane(Plane plane) {
        if (plane.getIsAtHome()) { // Plane can only take off if it is at home
            plane.setIsAtHome(false);
            plane.getOwner().removePlaneFromHome(plane);
            plane.setPos(plane.getOwner().getStartingBlockIndex());
            plane.setHeadingBlock((plane.getPos() + 1) % MAIN_LOOP_SIZE);
            map[plane.getPos()].onLanding(this, plane, false);
        }

    }

    /**
     * Updates the next block a plane should move to based on its current
     * position, color, and reversing state.
     *
     * @param plane the plane whose heading should be updated
     */
    private void updateHeadingBlock(Plane plane) {
        int currPos = plane.getPos();

        if ((map[currPos] instanceof EntryBlock) && map[currPos].getColor().equals(plane.getColor())) {
            plane.setHeadingBlock(((EntryBlock) map[currPos]).getLeadsToIndex());
        } else if (map[currPos] instanceof FinalBlock) {
            plane.setHeadingBlock(currPos - 1);
        } else if (map[currPos] instanceof FinalRouteBlock) {
            if (plane.getIsReversing()) {
                if (currPos == RED_ENTRY_INDEX) {
                    plane.setHeadingBlock(RED_MAIN_ENTRY_INDEX);
                } else if (currPos == BLUE_ENTRY_INDEX) {
                    plane.setHeadingBlock(BLUE_MAIN_ENTRY_INDEX);
                } else if (currPos == YELLOW_ENTRY_INDEX) {
                    plane.setHeadingBlock(YELLOW_MAIN_ENTRY_INDEX);
                } else if (currPos == GREEN_ENTRY_INDEX) {
                    plane.setHeadingBlock(GREEN_MAIN_ENTRY_INDEX);
                } else {
                    plane.setHeadingBlock(currPos - 1);
                }
            } else {
                plane.setHeadingBlock(currPos + 1);
            }
        } else {
            plane.setHeadingBlock((currPos + 1) % MAIN_LOOP_SIZE);
        }
    }

    /**
     * Moves a plane forward by the requested number of steps.
     *
     * The method advances recursively so each step can update the plane's
     * current and next block. After the final step, the destination block's
     * landing behavior is applied.
     *
     * @param plane the plane to move
     * @param steps the number of blocks to advance
     * @param ifJump whether this movement sequence was initiated by a special 
     *               effect (jump or shortcut). This flag is passed to onLanding 
     *               at the destination to prevent infinite chains of special 
     *               movements.
     */
    public void movePlane(Plane plane, int steps, boolean ifJump) {
        if (!plane.getIsMoving()) { // Adds a flag when the plane starts moving
            map[plane.getPos()].removePlane(plane); // Remove the plane from its current block
            plane.setIsMoving(true);
        }

        plane.setPos(plane.getHeadingBlock()); // Move the plane to its next expected block

        steps--;

        if (steps > 0) {
            map[plane.getPos()].onPassing(this, plane); // Trigger the onPassing event for the block that the plane is passing through
            updateHeadingBlock(plane); // This need to be after onPassing due to reversing state handling for final route blocks
            movePlane(plane, steps, ifJump); // If there are still steps left to move, recursively call movePlane until the plane has moved the required number of steps
        } else {
            updateHeadingBlock(plane);
            plane.setIsMoving(false); // Remove the moving flag
            map[plane.getPos()].onLanding(this, plane, ifJump); // Trigger the onLanding event for the block that the plane has landed on
        }
    }

    /**
     * Sends a plane back to its home area and removes it from its current block.
     *
     * @param plane the plane to send home
     */
    public void sendPlaneHome(Plane plane) {
        int currPos = plane.getPos();
        plane.setIsAtHome(true);
        map[currPos].removePlane(plane); // Remove the plane from the block it is currently on
        plane.setPos(-1);
        plane.getOwner().addPlaneToHome(plane);
    }

    /**
     * Reports whether the game has ended.
     *
     * @return true if the game is over; false otherwise
     */
    public boolean getIfGameOver() {
        return isGameOver;
    }

    /**
     * Finds a player by ID.
     *
     * @param targetId the ID of the player to find
     * @return the matching player, or null if no player has that ID
     */
    public Player getPlayerById(int targetId) {
        if (players == null) { // Defensive programming in case the players array has not been initialized
            return null;
        }
        for (int i = 0; i < players.length; i++) {
            if (players[i].getID() == targetId) {
                return players[i];
            }
        }
        return null;
    }

    /**
     * Gets the color assigned to a player.
     *
     * @param id the ID of the player
     * @return the player's color, or null if the player is not found
     */
    public String getColorById(int id) {
        if (getPlayerById(id) == null) { // Avoid null pointer exception
            return null;
        } else {
            return getPlayerById(id).getColor();
        }
    }

    /**
     * Finds a plane by display name.
     *
     * @param name the plane name to find, such as "R0"
     * @return the matching plane, or null if no plane has that name
     */
    public Plane getPlaneByName(String name) {
        if (planes == null) { // Defensive programming in case the planes array has not been initialized
            return null;
        }
        for (int i = 0; i < planes.length; i++) {
            if (planes[i].getName().equals(name)) {
                return planes[i];
            }
        }
        return null;
    }
    /**
     * Gets the complete board.
     *
     * @return the array of all main-loop and final-route blocks
     */
    public MapBlock[] getMap() {
        return map;
    }

    /**
     * Gets the number of turns played so far.
     *
     * @return the current turn count
     */
    public int getTurnCount() {
        return this.turnCount;
    }

    /**
     * Gets the player whose turn is active.
     *
     * @return the current player
     */
    public Player getCurrPlayer() {
        return this.currPlayer;
    }

    /**
     * Gets all players.
     *
     * @return the player array
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Gets all planes.
     *
     * @return the global plane array
     */
    public Plane[] getPlanes() {
        return this.planes;
    }
}
