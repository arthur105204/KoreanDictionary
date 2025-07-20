package vn.edu.fpt.koreandictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.crizin.KoreanRomanizer;

import java.util.List;

import vn.edu.fpt.koreandictionary.R;
import vn.edu.fpt.koreandictionary.model.Example;
import vn.edu.fpt.koreandictionary.model.POSGroup;
import vn.edu.fpt.koreandictionary.model.Sense;
import vn.edu.fpt.koreandictionary.model.Translation;
import vn.edu.fpt.koreandictionary.viewmodel.DictionaryViewModel;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;

public class POSCardAdapter extends RecyclerView.Adapter<POSCardAdapter.POSCardViewHolder> {
    private final List<POSGroup> posGroups;
    private final String baseWord;
    private final Context context;
    private OnFavoriteClickListener favoriteClickListener;
    private DictionaryViewModel viewModel;
    
    public interface OnFavoriteClickListener {
        void onFavoriteClick(String word, String definition, String pos, String pronunciation);
    }

    public POSCardAdapter(Context context, List<POSGroup> posGroups, String baseWord) {
        this.context = context;
        this.posGroups = posGroups;
        this.baseWord = baseWord;
    }
    
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteClickListener = listener;
    }
    
    public void setViewModel(DictionaryViewModel viewModel) {
        this.viewModel = viewModel;
    }
    
    public void refreshFavoriteStates() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public POSCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pos_card, parent, false);
        return new POSCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull POSCardViewHolder holder, int position) {
        POSGroup posGroup = posGroups.get(position);
        holder.textViewBaseWord.setText(baseWord);
        
        // Romanize
        try {
            String romanized = KoreanRomanizer.romanize(baseWord);
            holder.textViewRomanized.setText(romanized);
        } catch (Exception e) {
            holder.textViewRomanized.setText("");
        }
        
        // Set POS
        holder.textViewPOS.setText(posGroup.getPos());
        
        // Meanings from all senses in this POS group
        StringBuilder meanings = new StringBuilder();
        int meaningCount = 0;
        
        for (Sense sense : posGroup.getSenses()) {
            List<Translation> translations = sense.getTranslations();
            if (translations != null) {
                for (Translation trans : translations) {
                    if (trans.getTransWord() != null && !trans.getTransWord().isEmpty()) {
                        meanings.append(meaningCount + 1).append(". ").append(trans.getTransWord());
                        if (trans.getTransDfn() != null && !trans.getTransDfn().isEmpty()) {
                            meanings.append(" - ").append(trans.getTransDfn());
                        }
                        meanings.append("\n");
                        meaningCount++;
                        if (meaningCount >= 10) break;
                    }
                }
            }
            if (meaningCount >= 10) break;
        }
        holder.textViewMeanings.setText(meanings.toString().trim());
        
        // Examples with inline bullet points
        holder.layoutExamples.removeAllViews();
        List<Example> examples = posGroup.getExamples();
        if (examples != null && !examples.isEmpty()) {
            // Add examples header
            TextView header = new TextView(context);
            header.setText("Example Sentences");
            header.setTextSize(16);
            header.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
            header.setTypeface(null, android.graphics.Typeface.BOLD);
            header.setPadding(0, 0, 0, 12);
            holder.layoutExamples.addView(header);
            
            int count = 0;
            for (Example ex : examples) {
                if (ex.getExample() != null && !ex.getExample().isEmpty()) {
                    TextView korean = new TextView(context);
                    korean.setText("• " + ex.getExample());
                    korean.setTextSize(14);
                    korean.setTextColor(context.getResources().getColor(android.R.color.black));
                    korean.setPadding(0, 4, 0, 4);
                    korean.setLineSpacing(4, 1.2f);
                    // Set text alignment to start to keep bullet points inline
                    korean.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    // Ensure text wraps properly with bullet points
                    korean.setBreakStrategy(android.text.Layout.BREAK_STRATEGY_SIMPLE);
                    korean.setHyphenationFrequency(android.text.Layout.HYPHENATION_FREQUENCY_NONE);
                    holder.layoutExamples.addView(korean);
                    count++;
                    if (count >= 5) break; // Limit to 5 examples for cleaner look
                }
            }
        }
        
        // Update favorite button state and color
        updateFavoriteButtonState(holder, baseWord);
        
        // Setup favorite button
        holder.buttonFavorite.setOnClickListener(v -> {
            if (favoriteClickListener != null) {
                // Check if already favorited
                if (viewModel != null && viewModel.isFavorite(baseWord)) {
                    // Remove from favorites
                    viewModel.removeFromFavorites(baseWord);
                    updateFavoriteButtonState(holder, baseWord);
                } else {
                    // Add to favorites
                    String definition = meanings.toString().trim();
                    String pronunciation = holder.textViewRomanized.getText().toString();
                    favoriteClickListener.onFavoriteClick(baseWord, definition, posGroup.getPos(), pronunciation);
                    updateFavoriteButtonState(holder, baseWord);
                }
            }
        });
    }
    
    private void updateFavoriteButtonState(POSCardViewHolder holder, String word) {
        if (viewModel != null) {
            boolean isFavorited = viewModel.isFavorite(word);
            
            if (isFavorited) {
                // Word is favorited - show filled blue star
                holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                holder.buttonFavorite.setColorFilter(context.getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                // Word is not favorited - show gray outline star
                holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                holder.buttonFavorite.setColorFilter(context.getResources().getColor(android.R.color.darker_gray));
            }
        }
    }

    @Override
    public int getItemCount() {
        return posGroups.size();
    }

    static class POSCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBaseWord, textViewRomanized, textViewPOS, textViewMeanings;
        LinearLayout layoutExamples;
        ImageButton buttonFavorite;
        POSCardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBaseWord = itemView.findViewById(R.id.textViewBaseWord);
            textViewRomanized = itemView.findViewById(R.id.textViewRomanized);
            textViewPOS = itemView.findViewById(R.id.textViewPOS);
            textViewMeanings = itemView.findViewById(R.id.textViewMeanings);
            layoutExamples = itemView.findViewById(R.id.layoutExamples);
            buttonFavorite = itemView.findViewById(R.id.buttonFavorite);
        }
    }
} 