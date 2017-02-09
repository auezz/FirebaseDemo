package com.example.aueangkul.firebasedemo;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Aueangkul on 11/18/2016.
 */
public class AddPositionPopup extends Activity {
    EditText position;
    EditText xcoor;
    EditText ycoor;
    private DatabaseReference mFirebaseDatabaseReference;
    public static final String MESSAGES_CHILD = "messages";
    //final ListView datalist = (ListView)findViewById(R.id.dataList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] SendBackSSID = new String[1];
        final String[] SendBackRssi = new String[1];

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.add_position_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .70));

        final TextView getSSID = (TextView) findViewById(R.id.SsidTag);
        //getSSID.setText(getIntent().getExtras().getString("SSID"));
        getSSID.setText(getIntent().getStringExtra("SSID"));

        final TextView getRSSI = (TextView) findViewById(R.id.Rssi);
        //getRSSI.setText(getIntent().getExtras().getString("RSSI"));
        getRSSI.setText(getIntent().getStringExtra("RSSI"));

        Button ConfirmB = (Button) findViewById(R.id.ConfirmButt);
        Button CancelB = (Button) findViewById(R.id.CancelButt);

        ConfirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(AddPositionPopup.this,MainActivity.class);
                position = (EditText) findViewById(R.id.PostionName);


                Intent intent = new Intent();
                intent.putExtra("PositionValue", position.getText().toString());
                intent.putExtra("SSIDValue", getSSID.getText().toString());
                intent.putExtra("RSSIValue", getRSSI.getText().toString());

                setResult(1, intent);
                finish();

//                SendInformation sendInformation = new SendInformation(
//                        position.getText().toString(),
//                        getRSSI.getText().toString(),
//                        getSSID.getText().toString());
//                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(sendInformation);



//                //read database
//                mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue(String.class);
//                        lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value));
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }


        });


    }
}
