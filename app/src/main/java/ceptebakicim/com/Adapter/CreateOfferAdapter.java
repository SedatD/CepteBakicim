package ceptebakicim.com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.Activity.TeklifGonderActivity;
import ceptebakicim.com.Pojo.CreateOfferPojo;
import ceptebakicim.com.R;

/**
 * Created by SD on 28.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class CreateOfferAdapter extends RecyclerView.Adapter<CreateOfferAdapter.DataObjectHolder> {
    private ArrayList<CreateOfferPojo> mDataset;
    private Context mContext;

    public CreateOfferAdapter(ArrayList<CreateOfferPojo> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_offer_cardview_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.textView.setText(mDataset.get(position).getTitle());
        switch (mDataset.get(position).getId()) {
            case 1:
                holder.imageView.setImageResource(R.drawable.bebek_cocuk_bakim);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.yasli_bakim);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.hasta_bakim);
                break;
            case 4:
                holder.imageView.setImageResource(R.drawable.ev_temizligi);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;
        ImageView imageView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.wtf("asd", "getId : " + mDataset.get(getAdapterPosition()).getId());
                    Intent intent = new Intent(mContext, TeklifGonderActivity.class);
                    intent.putExtra("id", mDataset.get(getAdapterPosition()).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    //((Activity) mContext).finish();
                }
            });
        }
    }
}