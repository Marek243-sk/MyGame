package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class GameOver extends AppCompatActivity {

    TextView pointsTV;
    ImageView trophy;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<GameHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;

    class DbInsertData extends AsyncTask<Game, Void, Void> {

        @Override
        protected Void doInBackground(Game... games) {
            DbTools.getDbContext(new WeakReference<Context>(GameOver.this)).gameDao().insertGame(games);
            return null;
        }
    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);
        trophy = findViewById(R.id.trophyImg);
        pointsTV = findViewById(R.id.pointsTV);

        int points = getIntent().getIntExtra("Points", 0);
        pointsTV.setText("Points: " + points);

        Game game = new Game();
        game.setPoints(points);
        new DbInsertData().execute(game);

        if (points == 320) {
            trophy.setVisibility(View.VISIBLE);
        } else {
            trophy.setVisibility(View.INVISIBLE);
        }

        recyclerView = findViewById(R.id.rv_game_view);
        layoutManager = new LinearLayoutManager(this);
    }

    public  void restart(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }

    class DbGetData extends AsyncTask<Game, Integer, List<Game>> {

        @Override
        protected List<Game> doInBackground(Game... games) {
            List<Game> data =  DbTools.getDbContext(new WeakReference<Context>(GameOver.this)).gameDao().getAll();
            if(data.size()==0) {
                DbTools.getDbContext(new WeakReference<Context>(GameOver.this)).gameDao().insertGame(games);
                return DbTools.getDbContext(new WeakReference<Context>(GameOver.this)).gameDao().getAll();
            }
            else
                return DbTools.getDbContext(new WeakReference<Context>(GameOver.this)).gameDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Game> games) {
            super.onPostExecute(games);
            Toast.makeText(GameOver.this, games.get(0).getPoints(), Toast.LENGTH_LONG).show();
            ((TextView)findViewById(R.id.tv_points)).setText(games.get(0).getPoints());
        }
    }
}
