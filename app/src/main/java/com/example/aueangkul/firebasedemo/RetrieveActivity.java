package com.example.aueangkul.firebasedemo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RetrieveActivity extends AppCompatActivity {
    List<List<Long>> test = new ArrayList<List<Long>>();

    DatabaseReference dref;
    ListView rtList;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Object> list2 = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView listT;
    String CPosition[];

    Button chk;
    WifiManager wifi;
    List<Long> getRssi = new ArrayList<>();
    double result;

    Long rssi;
    String ssid;
    String position;
    Long getRSSI_DATA1[] = new Long[100];
    Long getRSSI_DATA2[] = new Long[100];
    Long getRSSI_DATA3[];
    Long getRSSI_DATA4[];
    Long getData[];
    List<Long> arr = new ArrayList<>();
    List<Integer> tmpCal = new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        rtList = (ListView) findViewById(R.id.RtListView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        rtList.setAdapter(adapter);

        //listT = (TextView) findViewById(R.id.lvTxt);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();

        chk = (Button) findViewById(R.id.chkButt);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.chkButt){
                    List<ScanResult> wifiScanList = wifi.getScanResults();
                    //getData = new int[wifiScanList.size()];

                    for (int i=0;i<wifiScanList.size();i++){
                        //getData[i] = wifiScanList.get(i).level;
                        getRssi.add((long) wifiScanList.get(i).level);
                        //Cal(getRssi);

                    }
                    Log.e("RssiP :",getRssi+"");

                }

            }
        });




        dref = FirebaseDatabase.getInstance().getReference();

        dref.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //Getting Data from Datasnapshot
                    SendInformation sendInformation = postSnapshot.getValue(SendInformation.class);
                    //DataSnapshot tm = postSnapshot.child(postSnapshot.getKey());


                    //Adding it to a String
                    //String string = "position"+sendInformation.getPosition()+"\nSSID "+sendInformation.getSsid()+"\nRSSI "+sendInformation.getRssi();

                    //Displaying it on TextView
//                    rtList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, string));
                    arr.clear();
                    for (DataSnapshot postSnapshots : postSnapshot.getChildren()){

                        position  = (String) postSnapshots.child("position").getValue();
                        ssid = (String) postSnapshots.child("ssid").getValue();
                        rssi = (Long) postSnapshots.child("rssi").getValue();
                        list.add(position+"  "+ssid+"  "+rssi);
                        adapter.notifyDataSetChanged();
                        //Log.e(position, rssi.toString());


                       // for(int i=0;i<arr.length;i++){
                            //getData = rssi;
                            arr.add(rssi);
                            //Log.e(position+" - "+ssid+" :",arr[i].toString());
                            //Log.e(position+" - "+ssid+" :",rssi+"");
                            //getData = rssi;
                       // }
//                        //Log.e(position+" :",getData.toString());
//                        Log.e(position+" :", Arrays.toString(arr));
//                        count++;
//                        Log.e("Count: ", count+"");

                    }
                    Log.e(position+": ", ""+arr);
                    test.add(arr);
                    Log.e(test.size()+"","test");


                }
                List<Long> tmp;
                for (int i=0;i<test.size();i++){
                    tmp = test.get(i);
                    tmpCal.add(Cal(tmp));


                }
            }

            public int Cal(List<Long> tmp){
                //สูตร
                //float getTmp = tmp;
                //Long result = Math.sqrt();

//                for (int i=0;i<test.size();i++){
//
//
//
//                }





                return 0;

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });




    }

//    public void Cal(List<Long> tmp, List<Long> getRssi){
//
//
//        return ;
//    }



}
