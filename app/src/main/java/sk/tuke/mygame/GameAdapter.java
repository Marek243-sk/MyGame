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

    public GameAdapter(List<GameModel> data, WeakReference<Context> contextWeakReference) {
        _context = contextWeakReference;
        _data = data;
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
    }

    @Override
    public int getItemCount() {
        if (_data != null) {
            return _data.size();
        }
        return 0;
    }
}
