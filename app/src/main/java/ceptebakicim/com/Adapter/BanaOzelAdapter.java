package ceptebakicim.com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.Activity.TeklifDetayActivity;
import ceptebakicim.com.Pojo.BanaOzelPojo;
import ceptebakicim.com.R;

/**
 * Created by SD on 22.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class BanaOzelAdapter extends RecyclerView.Adapter<BanaOzelAdapter.DataObjectHolder> {
    private ArrayList<BanaOzelPojo> mDataset;
    private Context mContext;

    public BanaOzelAdapter(ArrayList<BanaOzelPojo> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public BanaOzelAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bana_ozel_cardview_row, parent, false);
        BanaOzelAdapter.DataObjectHolder dataObjectHolder = new BanaOzelAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindViewHolder(BanaOzelAdapter.DataObjectHolder holder, int position) {
        if (position % 2 == 0)
            holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.lightRed));
        holder.textView_name.setText(mDataset.get(position).getName());
        holder.textView_typeTitle.setText(mDataset.get(position).getTypeTitle());
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_typeTitle;
        ImageButton imageButton;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageButton = itemView.findViewById(R.id.imageButton);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_typeTitle = itemView.findViewById(R.id.textView_typeTitle);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TeklifDetayActivity.class);
                    intent.putExtra("jsonObj", mDataset.get(getAdapterPosition()).getJsonObject() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}