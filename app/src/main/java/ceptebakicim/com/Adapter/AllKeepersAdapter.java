package ceptebakicim.com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ceptebakicim.com.Activity.CvActivity;
import ceptebakicim.com.Pojo.AllKeepersPojo;
import ceptebakicim.com.R;

/**
 * Created by SD on 14.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class AllKeepersAdapter extends RecyclerView.Adapter<AllKeepersAdapter.MyViewHolder> {
    private Context mContext;
    private List<AllKeepersPojo> allKeepersPojoList;

    public AllKeepersAdapter(Context mContext, List<AllKeepersPojo> results) {
        this.mContext = mContext;
        this.allKeepersPojoList = results;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AllKeepersPojo allKeepersPojo = allKeepersPojoList.get(position);
        holder.textView_name.setText(allKeepersPojo.getName());
        holder.textView_nationality.setText(allKeepersPojo.getNationality());
        holder.textView_age.setText(allKeepersPojo.getAge());

        Glide.with(mContext)
                .load(allKeepersPojo.getThumbnail())
                .centerCrop()
                .into(holder.imageView_thumbnail);

        holder.imageView_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("AllKeepersAdapter", "id : " + allKeepersPojo.getId());
                Intent intent = new Intent(mContext, CvActivity.class);
                intent.putExtra("id", allKeepersPojo.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allKeepersPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_nationality,textView_age;
        ImageView imageView_thumbnail;

        public MyViewHolder(View view) {
            super(view);
            textView_name = view.findViewById(R.id.textView_name);
            textView_nationality = view.findViewById(R.id.textView_nationality);
            textView_age = view.findViewById(R.id.textView_age);
            imageView_thumbnail = view.findViewById(R.id.imageView_thumbnail);
        }
    }

}