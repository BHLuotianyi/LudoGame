/** 
 * Player.java
 * Represents a player in the Ludo game. Each player has a color and a set of planes.
 */

public class Player {
    // Instance variables
    private int id;
    private int color;
    private int finishedCount;
    private Plane[] planes;
    private Plane[] home;

    public int getID() {
        return this.id;
    }

    public Plane[] gethome() {
        return this.home;
    }

    public void sethome(Plane[] home) {
        this.home = home;
    }

    public int getColor() {
        return this.color;
    }

    public Plane[] getPlanes() {
        return this.planes;
    }

    public void setFinishedCount(int finishedCount) {
        this.finishedCount = finishedCount;
    }

    public int getFinishedCount() {
        return this.finishedCount;
    }
}