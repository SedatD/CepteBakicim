package ceptebakicim.com.Activity;

import android.os.Bundle;
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

import ceptebakicim.com.Adapter.ChatActAdapter;
import ceptebakicim.com.Pojo.Mesaj;
import ceptebakicim.com.R;

public class ChatPublicAileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferencePublicAileChat;

    private EditText editText;
    private ImageButton buttonSend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_public_aile);

        editText = findViewById(R.id.editText4);
        buttonSend = findViewById(R.id.imageButton);
        listView = findViewById(R.id.listview);
        listView.setDivider(null);
        buttonSend.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferencePublicAileChat = firebaseDatabase.getReference("PublicAileChat");

        final ArrayList<Mesaj> mesajList = new ArrayList<>();
        final ChatActAdapter adapter = new ChatActAdapter(this, mesajList, firebaseUser);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
        final String zaman = sdf.format(new Date());

        databaseReferencePublicAileChat.getRef().child("updateTime").setValue(zaman);

        databaseReferencePublicAileChat.addValueEventListener(new ValueEventListener() {
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
                databaseReferencePublicAileChat.getRef().push().setValue(new Mesaj(gonderen, mesaj, zaman));
                break;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
