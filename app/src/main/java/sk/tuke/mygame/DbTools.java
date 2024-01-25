package sk.tuke.mygame;

import android.content.Context;

import androidx.room.Room;

import java.lang.ref.WeakReference;

public class DbTools {

//    Inštancia triedy
    private static GameDatabase _db;
    public DbTools(WeakReference<Context> refContext) {
//        Inicializácia db, pri vytvorení inštancie triedy
        getDbContext(refContext);
    }
//    Metóda pre získanie inštancie db
    static GameDatabase getDbContext(WeakReference<Context> refContext) {
//        Ak je db inicializovana, vráť existujúcu inštanciu
        if (_db != null) {
            return _db;
        }
//        Vytvorenie novej db/ načítanie existujúcej pomocou Room knižnice
        return Room.databaseBuilder(refContext.get(),GameDatabase.class, "game-db").build();
    }
}
