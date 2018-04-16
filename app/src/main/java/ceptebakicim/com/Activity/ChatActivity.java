package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ceptebakicim.com.Adapter.ChatActAdapter;
import ceptebakicim.com.Pojo.Mesaj;
import ceptebakicim.com.Pojo.User;
import ceptebakicim.com.R;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceChats, databaseReferenceUsers;

    private TextView textView;
    private EditText editText;
    private ImageButton buttonSend;
    private ListView listView;

    private int userId, userType, chatId;
    private String roomName, oneSignalId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textView = findViewById(R.id.textView2);
        editText = findViewById(R.id.editText4);
        buttonSend = findViewById(R.id.imageButton);
        listView = findViewById(R.id.listview);
        listView.setDivider(null);
        buttonSend.setOnClickListener(this);

        chatId = getIntent().getIntExtra("chatId", -1);

        if (chatId == -1) {
            Toast.makeText(this, "Bir hata oluştu. Tekrar deneyiniz.", Toast.LENGTH_LONG).show();
            finish();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        userType = preferences.getInt("userType", -1);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceChats = firebaseDatabase.getReference("Chats");
        databaseReferenceUsers = firebaseDatabase.getReference("Users");

        final ArrayList<Mesaj> mesajList = new ArrayList<>();
        final ChatActAdapter adapter = new ChatActAdapter(this, mesajList, firebaseUser);

        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
        final String zaman = sdf.format(new Date());

        if (chatId < userId)
            roomName = chatId + "-" + userId;
        else
            roomName = userId + "-" + chatId;

        databaseReferenceChats.getRef().child(roomName).child("updateTime").setValue(zaman);
        databaseReferenceUsers.child(userId + "").child("updateTime").setValue(zaman);

        databaseReferenceChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                    if (ds.getKey().equals(roomName))
                        for (DataSnapshot ds2 : ds.getChildren())
                            if (!ds2.getKey().equals("updateTime"))
                                mesajList.add(ds2.getValue(Mesaj.class));

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                    if (Integer.parseInt(ds.getKey()) == chatId) {
                        User user = ds.getValue(User.class);
                        textView.setText(user.getName());
                        oneSignalId = user.getOneSignalId();
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*databaseReferenceChats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                String gonderen = firebaseUser.getEmail();
                final String mesaj = editText.getText().toString();
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:dd");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
                final String zaman = sdf.format(new Date());

                databaseReferenceChats.getRef().child(roomName).push().setValue(new Mesaj(gonderen, mesaj, zaman));
                editText.setText("");

                // 'small_icon':'"+R.drawable.ic_stat_onesignal_default+"',
                // ResourcesCompat.getDrawable(getResources(), R.drawable.your_drawable, null)
                if (oneSignalId != null)
                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':" + mesaj + "}, 'include_player_ids': ['" + oneSignalId + "'],'data':{'chatId':" + userId + "}}"), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
