package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ceptebakicim.com.Adapter.ChatPublicActAdapter;
import ceptebakicim.com.Pojo.Mesaj;
import ceptebakicim.com.R;

public class ChatPublicActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferencePublicChat;

    private EditText editText;
    private ImageButton buttonSend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_public);

        editText = findViewById(R.id.editText4);
        buttonSend = findViewById(R.id.imageButton);
        listView = findViewById(R.id.listview);
        listView.setDivider(null);
        buttonSend.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferencePublicChat = firebaseDatabase.getReference("PublicChat");

        final ArrayList<Mesaj> mesajList = new ArrayList<>();
        final ChatPublicActAdapter adapter = new ChatPublicActAdapter(this, mesajList, firebaseUser);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
        final String zaman = sdf.format(new Date());

        databaseReferencePublicChat.getRef().child("updateTime").setValue(zaman);

        databaseReferencePublicChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                    if (!ds.getKey().equals("updateTime"))
                        mesajList.add(ds.getValue(Mesaj.class));

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                String gonderen = firebaseUser.getEmail();
                final String mesaj = editText.getText().toString();
                editText.setText("");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
                final String zaman = sdf.format(new Date());
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int userId = preferences.getInt("userId", -1);
                String name = preferences.getString("userName", "");
                databaseReferencePublicChat.getRef().push().setValue(new Mesaj(gonderen, mesaj, userId+"",zaman,name));
                break;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
