package sk.tuke.mygame;

import android.content.Context;

import androidx.room.Room;

import java.lang.ref.WeakReference;

public class DbTools {

    private static GameDatabase _db;
    public DbTools(WeakReference<Context> refContext) {
        getDbContext(refContext);
    }

    static GameDatabase getDbContext(WeakReference<Context> refContext) {
        if (_db != null) {
            return _db;
        }
        return Room.databaseBuilder(refContext.get(),GameDatabase.class, "game-db").build();
    }
}
