package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewholder> {

    List<Post> postList;
    Context context;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        postList = posts;
    }

    @NonNull
    @Override
    public PostViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_view_api, parent, false);
        return new PostViewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewholder holder, int position) {

        Post post = postList.get(position);
        holder.userId.setText("user id: " + post.getUserId());
        holder.id.setText("id: " + post.getId());
        holder.title.setText("title: " + post.getTitle());
        holder.body.setText("body: " + post.getBody());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPosts(List<Post> posts) {
        postList = posts;
        notifyDataSetChanged();
    }

    public class PostViewholder extends RecyclerView.ViewHolder {

        TextView userId, id, title, body;

        public PostViewholder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.user_id_tv);
            id = itemView.findViewById(R.id.id_tv);
            title = itemView.findViewById(R.id.title_tv);
            body = itemView.findViewById(R.id.body_tv);
        }
    }
}
