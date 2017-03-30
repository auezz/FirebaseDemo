package com.example.aueangkul.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    WifiManager wifi;
    WifiInfo wifiInfo;
    int SendRssi[];
    double[] AllSumRssi;
    String avgRssi[];
    String SendSsid[];
    String macAddress[];
    ListView lv;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String wifis[];
    EditText input;
    String dateFormatted;

    //Bayes
    int range100 =0;
    int range80 =0;
    int range60 =0;
    int range40 =0;
    int range20 =0;
    int count =1;


    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "messages";
    public static final String MESSAGE_CHILD2 = "SubMessage";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("การเพิ่มตำแหน่ง");


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //input position
        input = (EditText) findViewById(R.id.inputPosition);

        //AddListView
        lv = (ListView)findViewById(R.id.dataList);
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
//        rtList.setAdapter(adapter);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);



        //add position butt
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButt);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(ProfileActivity.this, AddPositionPopup.class);
                wifi.disconnect();


                if (v.getId() == R.id.addButt) {
                    wifi.startScan();
                    List<ScanResult> wifiScanList = wifi.getScanResults();
                    wifis = new String[wifiScanList.size()];
                    SendSsid = new String[wifiScanList.size()];
                    SendRssi = new int[wifiScanList.size()];
                    avgRssi = new String[wifiScanList.size()];
                    AllSumRssi = new double[wifiScanList.size()];
                    //wifiInfo = wifi.getConnectionInfo();
                    macAddress = new String[wifiScanList.size()];
                    //final String macAddress = wifiInfo.getMacAddress();

                    // Summation of SendRssi to find Rssi's average values
                    for (int k=0;k<wifiScanList.size();k++){
                    for (int j=0;j<wifiScanList.size();j++){
                        SendRssi[j] = (wifiScanList.get(j)).level;
                        AllSumRssi[j] += SendRssi[j];
                        Log.e("All sum "+j+" ", AllSumRssi[j]+"");
                    }
                    }

                    // Average Rssi's values and send data to firebase
                    for (int i=0;i<wifiScanList.size();i++){
                        //add second to db
                        Date date = new Date();
                        DateFormat formatter = new SimpleDateFormat("SSS");
                        dateFormatted = formatter.format(date);


                        SendSsid[i] = (wifiScanList.get(i)).SSID.toString();
                        avgRssi[i] = String.valueOf(AllSumRssi[i]/wifiScanList.size());
                        macAddress[i] = (wifiScanList.get(i).BSSID);

                        Log.e("AllSumRssi:  ", AllSumRssi[i]+"");
                        Log.e("avgRssi:  ", avgRssi[i]+"");

                        wifis[i] = ("Position: "+input.getText().toString()+"\nSSID: "+(wifiScanList.get(i)).SSID.toString()+ "\nRSSI: "+(wifiScanList.get(i).level)+" dB."
                                +"\nTime: "+dateFormatted);



                            SendInformation sendInformation = new SendInformation(
                                    input.getText().toString(),
                                    macAddress[i],
                                    SendSsid[i],
                                    avgRssi[i],
                                    dateFormatted);
                            mFirebaseDatabaseReference.child("PositionInfo")
                                    .child(input.getText().toString()).push().setValue(sendInformation);





                    }

                    lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, wifis));



                    Toast.makeText(getApplicationContext(), "Position: "+input.getText().toString()+" added. ", Toast.LENGTH_LONG).show();

                    //Find numbers of Rssi's values from scanning access point to apply Bayes' theorem

                    for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < wifiScanList.size(); j++) {
                                SendRssi[j] = (wifiScanList.get(j).level);
                                SendSsid[j] = (wifiScanList).get(j).SSID.toString();
                                if (SendRssi[j] >= -100 && SendRssi[j] <= -80) {
                                    range100++;
                                } else if (SendRssi[j] > -80 && SendRssi[j] <= -60) {
                                    range80++;
                                } else if (SendRssi[j] > -60 && SendRssi[j] <= -40) {
                                    range60++;
                                } else if (SendRssi[j] > -40 && SendRssi[j] <= -20) {
                                    range40++;
                                } else if (SendRssi[j] > -20 && SendRssi[j] <= 0) {
                                    range20++;
                                }
                                BayesInformation bayinfo = new BayesInformation(input.getText().toString());
                                RssiRange rssiRange = new RssiRange(input.getText().toString()
                                        ,range100, range80, range60, range40, range20, count);
                                mFirebaseDatabaseReference.child("BayesRule").child(input.getText().toString()).child(SendSsid[j]).
                                        setValue(rssiRange);


                            }
                                count++;
                                wifi.reconnect();




                }
                input.setText(null);

                //intent.putExtra("SSID", SendSsid).toString();
                //intent.putExtra("RSSI", SendRssi + " dB");

                //startActivityForResult(intent,1);




            }



        }


        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        Log.e("dwkpow","dpw111");
//        if (requestCode == 1){
//            if (requestCode==1){
//                Log.e("dwkpow","dpw");
//                String getposition=data.getStringExtra("PositionValue");
//                String getssid = data.getStringExtra("SSIDValue");
//                String getrssi = data.getStringExtra("RSSIValue");
//
////                position.setText(getposition);
////                getSsid.setText(getssid);
////                getRssi.setText(getrssi);
//
//
//
//                 String list[] = {getposition,getssid,getrssi};
//                lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list));
//                Toast.makeText(getApplicationContext(),"Saved  "+getposition,Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }





}
