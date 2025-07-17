package vn.edu.fpt.koreandictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class POSCardAdapter extends RecyclerView.Adapter<POSCardAdapter.POSCardViewHolder> {
    private final List<POSGroup> posGroups;
    private final String baseWord;
    private final Context context;

    public POSCardAdapter(Context context, List<POSGroup> posGroups, String baseWord) {
        this.context = context;
        this.posGroups = posGroups;
        this.baseWord = baseWord;
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
        
        // Examples
        holder.layoutExamples.removeAllViews();
        List<Example> examples = posGroup.getExamples();
        if (examples != null) {
            int count = 0;
            for (Example ex : examples) {
                if (ex.getExample() != null && !ex.getExample().isEmpty()) {
                    TextView korean = new TextView(context);
                    korean.setText(ex.getExample());
                    korean.setTextAppearance(android.R.style.TextAppearance_Material_Body1);
                    holder.layoutExamples.addView(korean);
                    count++;
                    if (count >= 10) break;
                }
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
        POSCardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBaseWord = itemView.findViewById(R.id.textViewBaseWord);
            textViewRomanized = itemView.findViewById(R.id.textViewRomanized);
            textViewPOS = itemView.findViewById(R.id.textViewPOS);
            textViewMeanings = itemView.findViewById(R.id.textViewMeanings);
            layoutExamples = itemView.findViewById(R.id.layoutExamples);
        }
    }
} 