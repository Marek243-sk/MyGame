package sk.tuke.mygame;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Query("SELECT * FROM Game WHERE id LIKE :id")
    Game getById(int id);

    @Insert
    void insertGame(Game... games);

    @Delete
    void deleteGame(Game game);
}
