package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter<GameHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        recyclerView = findViewById(R.id.rv_game_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new DbGetData().execute();
    }

    class DbGetData extends AsyncTask<Void, Void, List<GameModel>> {

        @Override
        protected List<GameModel> doInBackground(Void... voids) {
            List<Game> games = DbTools.getDbContext(new WeakReference<>(ScoreActivity.this)).gameDao().getAll();
            List<GameModel> gameModels = new ArrayList<>();

            for (Game game : games) {
                gameModels.add(new GameModel(game.getPoints()));
            }

            return gameModels;
        }

        @Override
        protected void onPostExecute(List<GameModel> gameModels) {
            super.onPostExecute(gameModels);
            adapter = new GameAdapter(gameModels, new WeakReference<>(ScoreActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }

}

