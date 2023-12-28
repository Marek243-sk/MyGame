package sk.tuke.mygame;

public class GameModel {
    public int points;
    private long id;

    public GameModel(int points, long id) {
        this.points = points;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
