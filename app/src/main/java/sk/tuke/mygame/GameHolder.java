package sk.tuke.mygame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Drží hodnoty bodov v RV
public class GameHolder extends RecyclerView.ViewHolder {
    TextView points;

    public GameHolder(@NonNull View itemView) {
//        Volá konštruktor nadtriedy, hodnota sa zobrazí v layoute podľa id
        super(itemView);
        points = itemView.findViewById(R.id.tv_points);
    }
}
