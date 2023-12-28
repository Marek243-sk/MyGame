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

public class ScoreActivity extends AppCompatActivity implements HandleClick{

    private List<GameModel> _data;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<GameHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        _data = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_game_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new DbGetData().execute();
    }

    @Override
    public void onClick(int position) {
        long gameId = _data.get(position).getId();
        Intent intent = new Intent(this, GameDetail.class);
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }

    class DbGetData extends AsyncTask<Void, Void, List<GameModel>> {

        @Override
        protected List<GameModel> doInBackground(Void... voids) {
            List<Game> games = DbTools.getDbContext(new WeakReference<>(ScoreActivity.this)).gameDao().getAll();
            List<GameModel> gameModels = new ArrayList<>();

            for (Game game : games) {
                gameModels.add(new GameModel(game.getPoints(), game.getId()));
            }

            return gameModels;
        }

        @Override
        protected void onPostExecute(List<GameModel> gameModels) {
            super.onPostExecute(gameModels);
            _data = gameModels;
            adapter = new GameAdapter(gameModels, new WeakReference<>(ScoreActivity.this), ScoreActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }

}

