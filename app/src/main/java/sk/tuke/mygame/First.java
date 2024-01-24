package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_activity);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(First.this, MainActivity.class);
            startActivity(intent);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button scoreButton = findViewById(R.id.scoreButton);

        scoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(First.this, ScoreActivity.class);
            startActivity(intent);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button API_button = findViewById(R.id.API_button);

        API_button.setOnClickListener(v -> {
            Intent intent = new Intent(First.this, MainActivityAPI.class);
            startActivity(intent);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button storage_button = findViewById(R.id.storage_button);

        storage_button.setOnClickListener(v -> {
            Intent intent = new Intent(First.this, Upload.class);
            startActivity(intent);
        });
    }
}
