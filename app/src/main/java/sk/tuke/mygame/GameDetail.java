package sk.tuke.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameDetail extends AppCompatActivity implements HandleClick{

//    Pole na zobrazenie počtu bodov
    TextView tvPointsDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        tvPointsDet = findViewById(R.id.tv_points_details);
//        Získanie dát z predchádzajúcej aktivity cez intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long gameId = bundle.getLong("gameId");
        }
    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
}
