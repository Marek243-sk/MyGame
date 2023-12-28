package sk.tuke.mygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {

    private List<GameModel> _data;
    private WeakReference<Context> _context;
    private HandleClick _clickListener;

    public GameAdapter(List<GameModel> data, WeakReference<Context> contextWeakReference, HandleClick clickListener) {
        _context = contextWeakReference;
        _data = data;
        _clickListener = clickListener;
    }

    public void refreshData(List<GameModel> data) {
        _data = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.database_activity, parent, false);
        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        //holder.points.setText(_data.get(position).points);
        GameModel currentItem = _data.get(position);
        holder.points.setText(String.valueOf(currentItem.points));

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

    @Override
    public int getItemCount() {
        if (_data != null) {
            return _data.size();
        }
        return 0;
    }
}
