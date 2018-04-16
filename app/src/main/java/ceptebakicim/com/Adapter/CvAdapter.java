package ceptebakicim.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.Pojo.CvPojo;
import ceptebakicim.com.R;

/**
 * Created by SD on 6.03.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class CvAdapter extends ArrayAdapter<CvPojo> {
    private Context mContext;
    private ArrayList<CvPojo> dataSet;

    public CvAdapter(ArrayList<CvPojo> data, Context context) {
        super(context, R.layout.cv_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CvPojo dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.cv_row, parent, false);
            viewHolder.linearLayout = convertView.findViewById(R.id.linearLayout);
            viewHolder.textView_title = convertView.findViewById(R.id.textView_title);
            viewHolder.textView_content = convertView.findViewById(R.id.textView_content);
            convertView.setTag(viewHolder);
            if (dataModel != null) {
                viewHolder.textView_title.setText(dataModel.getTitle());
                viewHolder.textView_content.setText(dataModel.getContent());
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (dataModel != null) {
            viewHolder.textView_title.setText(dataModel.getTitle());
            viewHolder.textView_content.setText(dataModel.getContent());
        }
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout linearLayout;
        TextView textView_title, textView_content;
    }
}