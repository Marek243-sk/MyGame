package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

//Zobrazuje skóre hry
public class ScoreActivity extends AppCompatActivity implements HandleClick{

    private List<GameModel> _data;
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

//        Iniciallizácia zoznamu údajov
        _data = new ArrayList<>();

//        Nastavenie premenných
        recyclerView = findViewById(R.id.rv_game_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        Na zisk údajov z DB
        new DbGetData().execute();
    }

//    Rieši kliknutie na položku v RV
    @Override
    public void onClick(int position) {
//        Získa id hry kliknutej položky v RV
        long gameId = _data.get(position).getId();
//        Prechod na detail hry
        Intent intent = new Intent(this, GameDetail.class);
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }

//    Na získanie údajov z DB
    class DbGetData extends AsyncTask<Void, Void, List<GameModel>> {

        @Override
        protected List<GameModel> doInBackground(Void... voids) {
//            Získanie hier z DB
            List<Game> games = DbTools.getDbContext(new WeakReference<>(ScoreActivity.this)).gameDao().getAll();
            List<GameModel> gameModels = new ArrayList<>();

//            Zmení údaje z formátu Game na GameModel
            for (Game game : games) {
                gameModels.add(new GameModel(game.getPoints(), game.getId()));
            }

            return gameModels;
        }

//        Metóda na aktualizáciu RV po dokončení AsyncTask
        @Override
        protected void onPostExecute(List<GameModel> gameModels) {
            super.onPostExecute(gameModels);
//            Aktualizácia zoznamu
            _data = gameModels;
//            Vytvorenie nového adaptera a priradenie k RV
            RecyclerView.Adapter<GameHolder> adapter = new GameAdapter(gameModels, new WeakReference<>(ScoreActivity.this), ScoreActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }
}
