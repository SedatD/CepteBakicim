package ceptebakicim.com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.Pojo.MainKabulBekleyenCardPojo;
import ceptebakicim.com.R;
import ceptebakicim.com.Activity.TeklifDetayActivity;

/**
 * Created by SD on 9.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class MainKabulBekleyenCardAdapter extends RecyclerView.Adapter<MainKabulBekleyenCardAdapter.DataObjectHolder> {
    private ArrayList<MainKabulBekleyenCardPojo> mDataset;
    private Context mContext;

    public MainKabulBekleyenCardAdapter(ArrayList<MainKabulBekleyenCardPojo> myDataset, Context context) {
        mContext = context;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_kabul_bekleyen_cardview_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if (position % 2 == 0)
            holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.lightRed));
        holder.textView_text.setText(mDataset.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView_text;
        ImageButton imageView_kabulBekleyen;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textView_text = itemView.findViewById(R.id.textView_text);
            imageView_kabulBekleyen = itemView.findViewById(R.id.imageView_kabulBekleyen);
            imageView_kabulBekleyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TeklifDetayActivity.class);
                    Log.wtf("asd", "pos : " + mDataset.get(getAdapterPosition()).getId());
                    intent.putExtra("pos", mDataset.get(getAdapterPosition()).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}