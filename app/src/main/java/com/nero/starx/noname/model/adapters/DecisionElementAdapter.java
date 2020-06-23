package com.nero.starx.noname.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nero.starx.noname.R;
import com.nero.starx.noname.Utlis.model.ResponseDecisionModel;
import com.nero.starx.noname.Utlis.model.ResponseDecisions;
import com.nero.starx.noname.model.OutletClickListener;
import java.util.ArrayList;
import java.util.List;

public class DecisionElementAdapter extends RecyclerView.Adapter<DecisionElementAdapter.ItemHolder> {

    private List<ResponseDecisionModel> strings;
    private Context context;
    private OutletClickListener listener;

    public DecisionElementAdapter(ArrayList<ResponseDecisionModel> strings, Context context, OutletClickListener listener){
        this.strings = strings ;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.decision_outlet , parent , false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.deciderName.setText(strings.get(position).getTitle());
        holder.decisionDetail.setText(strings.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        SimpleDraweeView deciderpic;
        TextView deciderName , decisionDetail;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            deciderpic = itemView.findViewById(R.id.decider_picture);
            deciderName = itemView.findViewById(R.id.decider_name);
            decisionDetail = itemView.findViewById(R.id.decision_detail);
            deciderpic.setActualImageResource(R.drawable.screen_background);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }
    }
}
