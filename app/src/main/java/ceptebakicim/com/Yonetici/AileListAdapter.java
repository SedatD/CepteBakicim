package ceptebakicim.com.Yonetici;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.R;

/**
 * Created by SD
 * on 8.06.2018.
 */

public class AileListAdapter extends RecyclerView.Adapter<AileListAdapter.DataObjectHolder> {
    private ArrayList<AileListPojo> mDataset;
    private Context mContext;
    public AileListAdapterListener onClickListener;

    public AileListAdapter(ArrayList<AileListPojo> myDataset, Context context, AileListAdapterListener aileListAdapterListener) {
        mDataset = myDataset;
        mContext = context;
        this.onClickListener = aileListAdapterListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aile_list_cardview_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if (position % 2 == 0)
            holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.lightRed));
        if (mDataset.get(position).getStatus() == 0)
            holder.textView_name.setTextColor(mContext.getResources().getColor(R.color.pink500));
        holder.textView_name.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        ImageButton imageButton;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textView_name = itemView.findViewById(R.id.textView_name);
            imageButton = itemView.findViewById(R.id.imageButton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.iconTextViewOnClick(v, mDataset.get(getAdapterPosition()));
                    /*Intent intent = new Intent(mContext, TeklifDetayActivity.class);
                    Log.wtf("asd", "getServiceID : " + mDataset.get(getAdapterPosition()).getServiceID());
                    Log.wtf("asd", "getInterviewID : " + mDataset.get(getAdapterPosition()).getInterviewID());
                    intent.putExtra("pos", mDataset.get(getAdapterPosition()).getServiceID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);*/

                    /*Intent intent = new Intent(mContext, CvActivity.class);
                    intent.putExtra("id", mDataset.get(getAdapterPosition()).getCaryID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);*/

                    /*String text = "RED";
                    if (mDataset.get(getAdapterPosition()).getStatus() == 1)
                        text = "ONAY";

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getApplicationContext());
                    builder.setTitle("Uyarı");
                    builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");

                    builder.setNegativeButton("SİL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getRequest(mDataset.get(getAdapterPosition()).getId(), 2);
                        }
                    });

                    builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getRequest(mDataset.get(getAdapterPosition()).getId(), mDataset.get(getAdapterPosition()).getStatus());
                        }
                    });

                    builder.show();*/
                }
            });

        }

    }

    public interface AileListAdapterListener {

        void iconTextViewOnClick(View v, AileListPojo aileListObj);

        void iconImageViewOnClick(View v, int position);

        void iconImageUnFollowOnClick(View v, int position);
    }

}