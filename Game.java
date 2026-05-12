/** 
 * Game.java
 * The main class to run the Ludo game
 */
public class Game {

    /** Global variables */
    public static final int MAIN_LOOP_SIZE = 52; // Standard Ludo board has 52 blocks in the main loop
    public static final int FINAL_ROUTE_LENGTH = 6; // This does not include the entry block, which is the first block of the final route
    public static final int RED_ENTRY_INDEX = MAIN_LOOP_SIZE; // The index of the blue entry block in the mapBlockList
    public static final int BLUE_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH; // The index of the red entry block in the mapBlockList
    public static final int YELLOW_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*2; // The index of the green entry block in the mapBlockList
    public static final int GREEN_ENTRY_INDEX = MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*3; // The index of the yellow entry block in the mapBlockList
    
    public static final int NUM_PLAYERS = 4;

    public static final String RED = "RED";
    public static final String BLUE = "BLUE";
    public static final String YELLOW = "YELLOW";
    public static final String GREEN = "GREEN";

    /** Private variables */
    private MapBlock[] mapBlockList = new MapBlock[MAIN_LOOP_SIZE + FINAL_ROUTE_LENGTH*4]; // The list to keep track of all the blocks in the game, including the main loop and the final routes for each player
    private int turnCount; // To keep track of the current turn

    private Player[] players;
    private Player currPlayer; // To keep track of the current player

    private Plane[] planes; // To keep track of the planes in the game


    private boolean isGameOver = false; // To indicate if the game is over

    /** Helper methods */

    /** Initializes the game map by creating the appropriate MapBlock objects for the main loop and final routes. */
    private void initMap() {
        for (int i = 0; i < mapBlockList.length; i++) {
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
            if ((i < MAIN_LOOP_SIZE) && (i % 13 == 0)) {
                mapBlockList[i] = new EntryBlock(i, color);
            } else if ((i < MAIN_LOOP_SIZE) && (i % 7 == 0)) {
                mapBlockList[i] = new ShortCutBlock(i, color);
            } else if (i < MAIN_LOOP_SIZE) {
                mapBlockList[i] = new MainMapBlock(i, color);
            } else if (i >= MAIN_LOOP_SIZE) {
                mapBlockList[i] = new FinalRouteBlock(i, color);
            }
        }
    }

    /** Initializes the players by creating Player objects for each player and assigning them their respective colors. */
    private void initPlayers() {
        players = new Player[4];
        players[0] = new Player(0, RED);
        players[1] = new Player(1, BLUE);
        players[2] = new Player(2, YELLOW);
        players[3] = new Player(3, GREEN);
    }

    /** Initializes the planes for each player by creating Plane objects and assigning them to the respective players. */
    private void initPlanes() {
        int newPlaneId = 0; // To assign a unique ID to each plane, which corresponds to its index in the global planes list
        for (int i = 0; i < players.length; i++) { // i is the index of the player in the players list
            Plane[] tempListOfPlanes = new Plane[4]; // Create a temporal list to storage newly created planes
            for (int j = 0; j < 4; j++) { // j is the index of the plane in the player's planes list
                if (j == 0) {
                    tempListOfPlanes[j] = new CommanderPlane(getPlayerById(i), newPlaneId, getPlayerById(i).getShortName()+j); // A plane's short name would be e.g. "R0", "B1", etc. (the player's short name + the plane's index in the player's planes list) 
                    planes[newPlaneId] = tempListOfPlanes[j];
                } else {
                    tempListOfPlanes[j] = new FighterPlane(getPlayerById(i), newPlaneId, getPlayerById(i).getShortName()+j);
                    planes[newPlaneId] = tempListOfPlanes[j];
                }
                newPlaneId++;
            }
            players[i].setPlanes(tempListOfPlanes);
        }
    }


    /**
     * CONSTRUCTOR
     * Initializes the game by setting up the map and players, and resetting the turn count.
     */
    public Game() {
        initMap();
        planes = new Plane[NUM_PLAYERS*4]; // Each player has 4 planes
        initPlayers();
        initPlanes();
        turnCount = 0;
    }

    /** Public methods */

    /**
     * Checks if the game is over by determining if any player has all their planes in the final route.
     * @return true if the game is over, false otherwise
     */
    public boolean getIfGameOver() {
        return isGameOver;
    }

    /**
     * Searches for a player by their ID in the players array.
     * @param targetId the ID of the player to search for
     * @return the player with the specified ID, or null if not found
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
     * Gets the color of a player by their ID.
     * @param id the ID of the player
     * @return the color of the player, or null if the player is not found
     */
    public String getColorById(int id) {
        String result = getPlayerById(id).getColor();
        if (result == null) { // Avoid null pointer exception
            return null;
        }
        return result;
    }

    /**
     * Gets a plane by its name.
     * @param name the name of the plane to search for
     * @return the plane with the specified name, or null if not found
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
}
