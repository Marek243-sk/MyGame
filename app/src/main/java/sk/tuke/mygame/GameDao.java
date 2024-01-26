package sk.tuke.mygame;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//    Rozhranie na prístup k databáze (Database Access Object)
@Dao
public interface GameDao {

//    Dopyty na získanie všetký dát, podľa id, alebo zmazanie dát v DB Game
    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Query("SELECT * FROM Game WHERE id LIKE :id")
    Game getById(int id);

    @Insert
    void insertGame(Game... games);

    @Delete
    void deleteGame(Game game);
}
