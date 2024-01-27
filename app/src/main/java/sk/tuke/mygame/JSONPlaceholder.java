package sk.tuke.mygame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceholder {

//    Metóda na získanie zoznamu príspevkov zo servera
    @GET("posts")
    Call<List<Post>> getPost();
}
