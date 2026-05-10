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
    private int turnCounter = 0; // To keep track of the current turn

    private Player[] players;
    private Player currPlayer; // To keep track of the current player

    private Plane[] planes; // To keep track of the planes in the game


    private boolean isGameOver = false; // To indicate if the game is over

    /** Helper methods */

    /** Initializes the game map by creating the appropriate MapBlock objects for the main loop and final routes. */
    private void initMap() {
        for (int i = 0; i < mapBlockList.length; i++) {
            String color = "";

            if ((i < MAIN_LOOP_SIZE) && (i % 4 == 0)) { // Handle the main loop blocks
                color = RED;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 1)) {
                color = BLUE;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 2)) {
                color = YELLOW;
            } else if ((i < MAIN_LOOP_SIZE) && (i % 4 == 3)) {
                color = GREEN;
            } else if ((i >= RED_ENTRY_INDEX) && (i < BLUE_ENTRY_INDEX)) {
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


    /**
     * Constructs a new Game object.
     */
    public Game() {
        initMap();
    }
}
