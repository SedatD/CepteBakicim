package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import ceptebakicim.com.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private EditText editText_name, editText_password, editText_address, editText_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);
        int userType = preferences.getInt("userType", -1);

        LinearLayout linearLayout_aile = findViewById(R.id.linearLayout_aile);
        LinearLayout linearLayout_bakici = findViewById(R.id.linearLayout_bakici);

        if (userType == 1)
            linearLayout_aile.setVisibility(View.VISIBLE);
        else
            linearLayout_bakici.setVisibility(View.VISIBLE);

        //ortak
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        editText_name = findViewById(R.id.editText_name);
        editText_password = findViewById(R.id.editText_password);
        editText_phone = findViewById(R.id.editText_phone);
        //aile
        editText_address = findViewById(R.id.editText_address);
        //bakici

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:

                break;
            case R.id.imageButton:

                break;
        }
    }

}
