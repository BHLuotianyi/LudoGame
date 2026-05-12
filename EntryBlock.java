/** 
 * EntryBlock.java
 * Represents an entry block on the Ludo game map, which allows a player to enter their piece onto the main loop.
 */
public class EntryBlock extends MapBlock {
    private int leadsToIndex; // The index of the block in final route that this entry block leads to
    public EntryBlock(int id, String color) {
        super(id, color);
        switch(color) {
            case Game.RED:
                leadsToIndex = Game.RED_ENTRY_INDEX;
                break;
            case Game.BLUE:
                leadsToIndex = Game.BLUE_ENTRY_INDEX;
                break;
            case Game.YELLOW:
                leadsToIndex = Game.YELLOW_ENTRY_INDEX;
                break;
            case Game.GREEN:
                leadsToIndex = Game.GREEN_ENTRY_INDEX;
                break;
        }
    }

    public int getLeadsToIndex() {
        return leadsToIndex;
    }
}
