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

import ceptebakicim.com.Activity.CvActivity;
import ceptebakicim.com.Pojo.OfferedKeepersPojo;
import ceptebakicim.com.R;

/**
 * Created by SD on 27.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class OfferedKeepersAdapter extends RecyclerView.Adapter<OfferedKeepersAdapter.DataObjectHolder> {
    private ArrayList<OfferedKeepersPojo> mDataset;
    private Context mContext;

    public OfferedKeepersAdapter(ArrayList<OfferedKeepersPojo> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offered_keepers_cardview_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.lightRed));
        holder.textView_name.setText(mDataset.get(position).getName());
        holder.textView_durum.setText(mDataset.get(position).getDurum());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_durum;
        ImageButton imageButton;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageButton = itemView.findViewById(R.id.imageButton);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_durum = itemView.findViewById(R.id.textView_durum);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(mContext, TeklifDetayActivity.class);
                    Log.wtf("asd", "getServiceID : " + mDataset.get(getAdapterPosition()).getServiceID());
                    Log.wtf("asd", "getInterviewID : " + mDataset.get(getAdapterPosition()).getInterviewID());
                    intent.putExtra("pos", mDataset.get(getAdapterPosition()).getServiceID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);*/

                    Intent intent = new Intent(mContext, CvActivity.class);
                    intent.putExtra("id", mDataset.get(getAdapterPosition()).getCaryID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}