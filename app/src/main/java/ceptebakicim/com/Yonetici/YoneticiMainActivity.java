package ceptebakicim.com.Yonetici;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ceptebakicim.com.Activity.LoginActivity;
import ceptebakicim.com.R;

public class YoneticiMainActivity extends AppCompatActivity implements View.OnClickListener {
    private int wpID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetici_main);

        TextView textMerhaba = findViewById(R.id.textMerhaba);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            wpID = bundle.getInt("wpID", -1);
            textMerhaba.setText("Yönetici girişi");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(YoneticiMainActivity.this, AileIslemleriActivity.class));
                break;
            case R.id.btn2:
                Intent intent = new Intent(YoneticiMainActivity.this, BakiciIslemleriActivity.class);
                intent.putExtra("wpID",wpID);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(YoneticiMainActivity.this);
                builder.setTitle("Uyarı");
                builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("wpID");
                        editor.apply();
                        startActivity(new Intent(YoneticiMainActivity.this, LoginActivity.class));
                        finish();
                    }
                });

                builder.show();
                break;
        }
    }

}
