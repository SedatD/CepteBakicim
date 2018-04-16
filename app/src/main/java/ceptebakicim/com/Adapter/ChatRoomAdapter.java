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

import ceptebakicim.com.Activity.ChatActivity;
import ceptebakicim.com.Activity.ChatPublicActivity;
import ceptebakicim.com.Activity.ChatPublicAileActivity;
import ceptebakicim.com.Pojo.ChatRoomPojo;
import ceptebakicim.com.R;

/**
 * Created by Sedat on 1.04.2018.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.DataObjectHolder> {
    private ArrayList<ChatRoomPojo> mDataset;
    private Context mContext;

    public ChatRoomAdapter(ArrayList<ChatRoomPojo> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public ChatRoomAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_cardview_row, parent, false);
        ChatRoomAdapter.DataObjectHolder dataObjectHolder = new ChatRoomAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ChatRoomAdapter.DataObjectHolder holder, int position) {
        holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.lightRed));
        holder.textView_roomName.setText(mDataset.get(position).getRoomName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textView_roomName;
        ImageButton imageButton;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageButton = itemView.findViewById(R.id.imageButton);
            textView_roomName = itemView.findViewById(R.id.textView_roomName);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;

                    if (mDataset.get(getAdapterPosition()).getId() == -1) {
                        intent = new Intent(mContext, ChatPublicActivity.class);
                    } else if (mDataset.get(getAdapterPosition()).getId() == -2) {
                        intent = new Intent(mContext, ChatPublicAileActivity.class);
                    } else {
                        intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("chatId", mDataset.get(getAdapterPosition()).getId());
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}