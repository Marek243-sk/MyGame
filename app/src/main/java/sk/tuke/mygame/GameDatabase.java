package sk.tuke.mygame;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//    Anotácia -> na označenie triedy Game ako entity v DB
@Database(entities = {Game.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {
//    Vráti objekt na prístup a manipuláciu s údajmi v BD
    public abstract GameDao gameDao();
}
