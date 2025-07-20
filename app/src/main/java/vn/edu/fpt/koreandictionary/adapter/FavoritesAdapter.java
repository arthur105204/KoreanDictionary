package vn.edu.fpt.koreandictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.fpt.koreandictionary.R;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    
    private List<Favorite> favoritesList;
    private Context context;
    private OnFavoriteItemClickListener listener;
    private SimpleDateFormat dateFormat;
    
    public interface OnFavoriteItemClickListener {
        void onFavoriteItemClick(Favorite favorite);
        void onFavoriteItemLongClick(Favorite favorite);
        void onRemoveFavorite(Favorite favorite);
    }
    
    public FavoritesAdapter(Context context, OnFavoriteItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.favoritesList = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favorite = favoritesList.get(position);
        holder.bind(favorite);
    }
    
    @Override
    public int getItemCount() {
        return favoritesList.size();
    }
    
    public void updateFavorites(List<Favorite> newFavorites) {
        this.favoritesList = newFavorites != null ? newFavorites : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWord;
        TextView textViewDefinition;
        TextView textViewPos;
        TextView textViewPronunciation;
        TextView textViewTimestamp;
        ImageButton buttonRemove;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewDefinition = itemView.findViewById(R.id.textViewDefinition);
            textViewPos = itemView.findViewById(R.id.textViewPos);
            textViewPronunciation = itemView.findViewById(R.id.textViewPronunciation);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
            
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onFavoriteItemClick(favoritesList.get(position));
                }
            });
            
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onFavoriteItemLongClick(favoritesList.get(position));
                    return true;
                }
                return false;
            });
            
            buttonRemove.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRemoveFavorite(favoritesList.get(position));
                }
            });
        }
        
        void bind(Favorite favorite) {
            textViewWord.setText(favorite.getWord());
            textViewDefinition.setText(favorite.getDefinition());
            textViewPos.setText(favorite.getPos());
            textViewPronunciation.setText(favorite.getPronunciation());
            textViewTimestamp.setText(dateFormat.format(new Date(favorite.getTimestamp())));
        }
    }
} 