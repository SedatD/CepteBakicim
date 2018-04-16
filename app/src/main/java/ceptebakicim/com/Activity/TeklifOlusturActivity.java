package ceptebakicim.com.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.CreateOfferAdapter;
import ceptebakicim.com.Pojo.CreateOfferPojo;
import ceptebakicim.com.R;

public class TeklifOlusturActivity extends AppCompatActivity {
    private RecyclerView recyclerView_create_offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        recyclerView_create_offer = findViewById(R.id.recyclerView_create_offer);
        recyclerView_create_offer.setHasFixedSize(true);
        recyclerView_create_offer.setLayoutManager(new LinearLayoutManager(this));

        ArrayList results = new ArrayList<CreateOfferPojo>();
        CreateOfferPojo obj;

        obj = new CreateOfferPojo(1, "Bebek Çocuk Bakımı");
        results.add(obj);

        obj = new CreateOfferPojo(2, "Yaşlı Bakımı");
        results.add(obj);

        obj = new CreateOfferPojo(3, "Hasta Bakımı");
        results.add(obj);

        obj = new CreateOfferPojo(4, "Ev Temizliği");
        results.add(obj);

        recyclerView_create_offer.setAdapter(new CreateOfferAdapter(results, getApplicationContext()));
    }

}
