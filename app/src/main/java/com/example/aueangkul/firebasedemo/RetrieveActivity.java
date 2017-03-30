package com.example.aueangkul.firebasedemo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RetrieveActivity extends AppCompatActivity {
    List<Test> test;
    List<Test2> test2;
    List<String> sss = new ArrayList<String>();
    DatabaseReference dref;
    DatabaseReference getSubKey;
    ListView rtList;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Button chk;
    Button NearPoint;
    WifiManager wifi;
    List<Long> getRssi = new ArrayList<>();

    double result;
    double result2 = 0;
    double resultSqrt;

    String rssi;
    String ssid;
    String position;
    String macAddress;
    String DateTime;
    List<String> TimeList = new ArrayList<>();
    List<String> arr;
    List<Double> my_num = new ArrayList<>();
    List<String> AllResultSqrt = new ArrayList<>();
    List<String> PositionList = new ArrayList<>();
    List<Integer> sum_count = new ArrayList<>();
    String KPosition;
    List<Double> convertArr = new ArrayList<>();
    int c=0;

    Button Clr;

    //BayesRule
    List<String> SortData = new ArrayList<>();
    DatabaseReference BayesList;
    String BayesPosition;
    String BayesSSID;
    int RSSIValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        getSupportActionBar().setTitle("การหาตำแหน่ง");

        test = new ArrayList<>();
        test2 = new ArrayList<>();

        rtList = (ListView) findViewById(R.id.RtListView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        rtList.setAdapter(adapter);
        arr = new ArrayList<>();
        //listT = (TextView) findViewById(R.id.lvTxt);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();



        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("PositionInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //Getting Data from Datasnapshot

                    arr.clear();
                    convertArr.clear();
                    TimeList.clear();
                    for (DataSnapshot postSnapshots : postSnapshot.getChildren()){
                        position  = (String) postSnapshots.child("position").getValue();
                        ssid = (String) postSnapshots.child("ssid").getValue();
                        rssi = (String) postSnapshots.child("avgRssi").getValue();
                        macAddress = (String) postSnapshots.child("macAddress").getValue();
                        DateTime = (String) postSnapshots.child("time").getValue();
                        list.add(position+"  "+ssid+"  "+rssi);
                        arr.add(rssi);
                        convertArr.add(Double.parseDouble(rssi));
                        TimeList.add(DateTime);
                        adapter.notifyDataSetChanged();
                    }

                    test.add(new Test(arr));
                    String str = test.get(count++).longList.toString();
                    Log.e("Position: "+position, ""+str+" count="+count);
                    sss.add(str);
                   // Log.e("sss  "+sss.size()+" ",TimeList+"");
                    PositionList.add(position);
                    Bayes(TimeList,convertArr);

                    //AcValues acValues = new AcValues(Double.parseDouble(rssi),macAddress,position,ssid,DateTime);



                }

                int count2 =1;
                for(String strs:sss) {
                    String tmp3 = strs.substring(1, strs.length() - 1);
                    String[] arr2 = tmp3.split(",");
                    double[] list_double = new double[arr2.length];
                    int i = 0;
                    for (String x : arr2) {
                        list_double[i++] = Double.parseDouble(x.trim());
                    }
                    my_num.clear();
                    for (double ii : list_double) {
                        my_num.add(ii);

                    }
                    //Log.e(null,null);
                    Log.e("my num: "+count2+"  Size: "+ my_num.size(), my_num + "");
                    //AllListData.add(my_num);
                    Calculator(my_num);
                    //RssiData(my_num);
                    count2++;



                }

//                for (String t:TimeList){
//                    String tmp = t.substring(1,t.length()-1);
//                    String[] arrT = tmp.split(",");
//                    String[] list_time = new String[arrT.length];
//                    int i=0;
//                    for (String x: arrT){
//                        list_time[i++] = x.trim();
//                    }
//                    my_time.clear();
//                    for (String ii: list_time){
//                        my_time.add(ii);
//                    }
//                    Bayes(my_time);
//
//                }



            }

            public void Calculator(List<Double> getData){

                double upperSum;
                double Rest =0;
                double sqrt;

                List<ScanResult> wifiScanList = wifi.getScanResults();
                            //getData = new int[wifiScanList.size()];
                            getRssi.clear();
                            for (int i=0;i<wifiScanList.size();i++){
                                getRssi.add((long) wifiScanList.get(i).level);
                            }

            //    Log.e("get Rssi  "+getRssi.size() ,"  "+getRssi);
                Log.e("Get DATA", getData.size()+" "+getData);

                    final double empty_data = -100;
                    for (int j=0;j<getRssi.size();j++){
                        if (getRssi.size() > getData.size()){
                            getData.add(empty_data);
                        }
                        upperSum = Math.pow((getRssi.get(j)-getData.get(j)),2);
                        Rest += upperSum;

                    }


                    sqrt = Math.sqrt(Rest);




//                    Log.e("get data offline  "+getData.size(), getData + "");
//
//                    Log.e("Rest  "+PositionList.get(c),"  "+Rest);
//
//                    Log.e("sqrt   "+PositionList.get(c),"  "+sqrt);

                   AllResultSqrt.add(PositionList.get(c)+" "+String.format("%.2f",sqrt));
                   Log.e("All result  ",AllResultSqrt+"");
                   nearby(AllResultSqrt);
                   //Bayes(AllResultSqrt);
                  // Bayes2(AllResultSqrt);
                   c++;



                chk = (Button) findViewById(R.id.chkButt);
                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RetrieveActivity.this);
                        mBuilder.setTitle("Euclidean Values");
                        View mView = getLayoutInflater().inflate(R.layout.dialog, null);
                        TextView output_Position = (TextView) mView.findViewById(R.id.Output_PositionTextView);
                        output_Position.setText(AllResultSqrt.toString());
//                        output_Position.setText("Hello");
                        Log.e("All Result click", AllResultSqrt.get(0)+"");


                        mBuilder.setView(mView);
                        mBuilder.setNegativeButton("OKAY",null);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                });

            }





            public void nearby(final List<String> EuclideanValue){
                //sorting Euclidean Values
                Collections.sort(EuclideanValue, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return extractInt(o1) - extractInt(o2);
                    }

                    int extractInt(String s){
                        String num = s.replaceAll("\\D", "");
                        //return 0 0 if no digits found
                        return num.isEmpty() ? 0 : Integer.parseInt(num);

                    }


                });


                NearPoint = (Button) findViewById(R.id.nearButt);
                NearPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RetrieveActivity.this);
                        mBuilder.setTitle("Nearby Place");
                        View mView = getLayoutInflater().inflate(R.layout.dialog,null);
                        TextView output_Position = (TextView) mView.findViewById(R.id.Output_PositionTextView);
                        output_Position.setText(EuclideanValue.get(0).toString());


                        mBuilder.setView(mView);
                        mBuilder.setNegativeButton("OKAY",null);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                    }
                });

               // Bayes(EuclideanValue);
                SortData = EuclideanValue;
             }

            public void Bayes(List<String> Time,List<Double> rssi){


                double max_val = -9999999 ;
                String time_pointer ="" ;
                int tmp=0;
                for (int i=0;i<rssi.size();i++){
                    if (rssi.get(i) > max_val){
                        //max_val = rssi.get(i);
                        time_pointer = Time.get(i);
                    }
                }
               // Log.e("TestPppp  ",time_pointer +"");
                for (int i=0;i<rssi.size();i++){
                    Log.e("TestP  ",Time.get(i) +"|||"+time_pointer);
                    if (time_pointer.equals(Time.get(i))){
                        tmp++;
                    }
                }

                Log.e("TestP  ",tmp +"TUP");





            }






            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

//
//        BayesList = FirebaseDatabase.getInstance().getReference();
//        BayesList.child("BayesRule").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                    for (DataSnapshot postSnapshots : postSnapshot.getChildren()){
//                        BayesPosition = (String) postSnapshots.child("")
//
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        Clr = (Button) findViewById(R.id.clr_Butt);
        Clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                dref.removeValue();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String P;
        List<String> Plist = new ArrayList<>();
        int id = item.getItemId();
        if (id == R.id.action_delete) {

            P = String.valueOf(dref.child("PositionInfo").child("position"));

//            dref.child("messages").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                        for (DataSnapshot postSnapshots : postSnapshot.getChildren()) {
//                        P = (String) postSnapshots.child("position").getValue();
//
//                        }
//
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(RetrieveActivity.this);
            mBuilder.setTitle("Delete Position");
            View mView = getLayoutInflater().inflate(R.layout.dialog,null);
            TextView output_Position = (TextView) mView.findViewById(R.id.Output_PositionTextView);
            output_Position.setText(P);



            mBuilder.setView(mView);
            mBuilder.setNegativeButton("Delete",null);
            AlertDialog dialog = mBuilder.create();
            dialog.show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
