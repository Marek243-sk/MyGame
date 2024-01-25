package sk.tuke.mygame;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//    Tabuľka 'Game'
@Entity
public class Game {
//    Definovanie PK (automaticky generovaný) a stĺpca
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "points")
    private int points;

//    Prázdny konštruktor, Room ho vyžaduje.
    public Game() {
    }
//    Konštruktor, inicializuje premennú na uchovanie bodov v hre (jednej)
    public Game(int points) {
        setPoints(points);
    }
//    Getter → získanie, Setter → nastavenie
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
