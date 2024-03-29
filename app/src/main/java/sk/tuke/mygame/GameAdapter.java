package sk.tuke.mygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

//    Adaptér pre RV na zobrazenie údajov z GameModel
public class GameAdapter extends RecyclerView.Adapter<GameHolder> {
//    Zoznam údaj na zobrazenie
    private List<GameModel> _data;
//    Referencia na kontext aplikácie
    private WeakReference<Context> _context;
//    Na zachytenie kliknutia, rozhranie
    private HandleClick _clickListener;

    public GameAdapter(List<GameModel> data, WeakReference<Context> contextWeakReference, HandleClick clickListener) {
        _context = contextWeakReference;
        _data = data;
        _clickListener = clickListener;
    }

//    Na obnovenie dát v adaptéri
    public void refreshData(List<GameModel> data) {
        _data = data;
        notifyDataSetChanged();
    }

//    Vytvorenie novej položky v RV
    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.database_activity, parent, false);
        return new GameHolder(view);
    }

//    Pridanie aktuálnych údajov k holderu
    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        GameModel currentItem = _data.get(position);
        holder.points.setText(String.valueOf(currentItem.points));

//        Na zaznamenanie dotyku po položke v RV
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (_clickListener != null) {
                    _clickListener.onClick(clickedPosition);
                }
            }
        });
    }

//    Na získanie počtu položiek v RV
    @Override
    public int getItemCount() {
        if (_data != null) {
            return _data.size();
        }
        return 0;
    }
}
