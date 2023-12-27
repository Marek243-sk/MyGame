package sk.tuke.mygame;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "points")
    private int points;


    public Game() {
    }

    public Game(int points) {
        setPoints(points);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
