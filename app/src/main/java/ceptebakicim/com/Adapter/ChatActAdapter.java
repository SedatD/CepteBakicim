package ceptebakicim.com.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import ceptebakicim.com.Pojo.Mesaj;
import ceptebakicim.com.R;

/**
 * Created by SD on 13.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class ChatActAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Mesaj> mesajList;
    FirebaseUser fUser;

    public ChatActAdapter(Activity activity, ArrayList<Mesaj> mesajList, FirebaseUser fUser) {
        this.mesajList = mesajList;
        this.fUser = fUser;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mesajList.size();
    }

    @Override
    public Object getItem(int position) {
        return mesajList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satir = null;
        Mesaj mesaj = mesajList.get(position);

        if (mesaj.getGonderen().equals(fUser.getEmail())) {
            satir = layoutInflater.inflate(R.layout.custom_sag, null);

            TextView mail1 = (TextView) satir.findViewById(R.id.textViewBen);
            TextView mesaj1 = (TextView) satir.findViewById(R.id.textViewMesajim);
            TextView zaman1 = (TextView) satir.findViewById(R.id.textViewZamanim);

            mail1.setText(mesaj.getGonderen());
            mesaj1.setText(mesaj.getMesaj());
            zaman1.setText(mesaj.getZaman());
        } else {
            satir = layoutInflater.inflate(R.layout.custom_sol, null);

            TextView mail2 = (TextView) satir.findViewById(R.id.textViewGonderenKisi);
            TextView mesaj2 = (TextView) satir.findViewById(R.id.textViewMesaji);
            TextView zaman2 = (TextView) satir.findViewById(R.id.textViewZamani);

            mail2.setText(mesaj.getGonderen());
            mesaj2.setText(mesaj.getMesaj());
            zaman2.setText(mesaj.getZaman());
        }

        return satir;
    }

}
