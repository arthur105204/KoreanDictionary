package vn.edu.fpt.koreandictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.fpt.koreandictionary.R;
import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
    
    private List<SearchHistory> historyList;
    private Context context;
    private OnHistoryItemClickListener listener;
    private SimpleDateFormat dateFormat;
    
    public interface OnHistoryItemClickListener {
        void onHistoryItemClick(SearchHistory history);
        void onHistoryItemLongClick(SearchHistory history);
    }
    
    public SearchHistoryAdapter(Context context, OnHistoryItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.historyList = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistory history = historyList.get(position);
        holder.bind(history);
    }
    
    @Override
    public int getItemCount() {
        return historyList.size();
    }
    
    public void updateHistory(List<SearchHistory> newHistory) {
        this.historyList = newHistory != null ? newHistory : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWord;
        TextView textViewTimestamp;
        TextView textViewResultCount;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewResultCount = itemView.findViewById(R.id.textViewResultCount);
            
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onHistoryItemClick(historyList.get(position));
                }
            });
            
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onHistoryItemLongClick(historyList.get(position));
                    return true;
                }
                return false;
            });
        }
        
        void bind(SearchHistory history) {
            textViewWord.setText(history.getWord());
            textViewTimestamp.setText(dateFormat.format(new Date(history.getTimestamp())));
            // Hide result count for cleaner look
            textViewResultCount.setVisibility(View.GONE);
        }
    }
} 