package sk.tuke.mygame;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//    Na zobrazenie príspevkov z JSONPlaceholder API
public class MainActivityAPI extends AppCompatActivity {

//    Adapter pre zobrazenie v RV
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_api);

//        Inicializácia RV
        RecyclerView recyclerView = findViewById(R.id.recycler_view_api);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Nastavenie adaptéra pre RV
        postAdapter = new PostAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(postAdapter);

//        Inicializácia retrofit na komunikáciu so serverom
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        Inštancia rozhrania pre API komunikáciu
        JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

//        Vytvorenie volania pre získanie príspevkov zo servera
        Call<List<Post>> call = jsonPlaceholder.getPost();

//        Volanie
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivityAPI.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
//                Získanie zoznamu príspevkov zo servera, aktualizácia RV
                List<Post> postList = response.body();
                postAdapter.setPosts(postList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivityAPI.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
