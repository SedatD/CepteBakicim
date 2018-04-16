package ceptebakicim.com;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SD on 5.03.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {
    private List<String> list;
    private List<Integer> list2;

    public RecyclerViewAdapter(List<Integer> horizontalList2, List<String> horizontalList) {
        this.list = horizontalList;
        this.list2 = horizontalList2;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.textview2.setText(list.get(position));
        holder.textview1.setText(String.valueOf(list2.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView textview1, textview2;

        public MyView(View view) {
            super(view);
            textview1 = view.findViewById(R.id.textview1);
            textview2 = view.findViewById(R.id.textview2);
        }
    }

}
