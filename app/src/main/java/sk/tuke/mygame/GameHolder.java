package sk.tuke.mygame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameHolder extends RecyclerView.ViewHolder {
    TextView points;

    public GameHolder(@NonNull View itemView) {
        super(itemView);
        points = itemView.findViewById(R.id.tv_points);
    }
}
