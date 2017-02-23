package com.example.aueangkul.firebasedemo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class RetrieveActivity extends AppCompatActivity {
    List<Test> test;
    List<String> sss = new ArrayList<String>();
    DatabaseReference dref;
    ListView rtList;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Button chk;
    WifiManager wifi;
    List<Long> getRssi = new ArrayList<>();

    double result;
    double result2 = 0;
    double resultSqrt;

    Long rssi;
    String ssid;
    String position;
    String macAddress;
    List<Long> arr;
    List<Integer> my_num = new ArrayList<>();
    List<String> AllResultSqrt = new ArrayList<>();
    List<String> PositionList = new ArrayList<>();
    String KPosition;
    List<String> myPosition = new ArrayList<>();
    int c=0;

    Button Clr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        test = new ArrayList<>();

        rtList = (ListView) findViewById(R.id.RtListView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        rtList.setAdapter(adapter);
        arr = new ArrayList<>();
        //listT = (TextView) findViewById(R.id.lvTxt);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();

        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //Getting Data from Datasnapshot

                    arr.clear();
                    for (DataSnapshot postSnapshots : postSnapshot.getChildren()){
                        position  = (String) postSnapshots.child("position").getValue();
                        ssid = (String) postSnapshots.child("ssid").getValue();
                        rssi = (Long) postSnapshots.child("rssi").getValue();
                        macAddress = (String) postSnapshots.child("macAddress").getValue();
                        list.add(position+"  "+ssid+"  "+rssi);
                        arr.add(rssi);
                        adapter.notifyDataSetChanged();
                    }

                    test.add(new Test(arr));
                    String str = test.get(count++).longList.toString();
                    Log.e("Position: "+position, ""+str+" count="+count);
                    sss.add(str);
                    PositionList.add(position);


                }

                int count2 =1;
                for(String strs:sss) {
                    String tmp3 = strs.substring(1, strs.length() - 1);
                    String[] arr2 = tmp3.split(",");
                    int[] list_int = new int[arr2.length];
                    int i = 0;
                    for (String x : arr2) {
                        list_int[i++] = Integer.parseInt(x.trim());
                    }
                    my_num.clear();
                    for (int ii : list_int) {
                        my_num.add(ii);

                    }
                    //Log.e(null,null);
                    Log.e("my num: "+count2+"  Size: "+ my_num.size(), my_num + "");
                    Calculator(my_num);
                    count2++;
                }


            }





            public void Calculator(List<Integer> getData){

                double upperSum;
                double Rest =0;
                double sqrt;

                List<ScanResult> wifiScanList = wifi.getScanResults();
                            //getData = new int[wifiScanList.size()];
                            getRssi.clear();
                            for (int i=0;i<wifiScanList.size();i++){
                                getRssi.add((long) wifiScanList.get(i).level);
                            }

                Log.e("get Rssi  "+getRssi.size() ,"  "+getRssi);

                    final int empty_data = -100;
                    for (int j=0;j<getRssi.size();j++){
                        if (getRssi.size() > getData.size()){
                            getData.add(empty_data);
                        }
                        upperSum = Math.pow((getRssi.get(j)-getData.get(j)),2);
                        Rest += upperSum;

                    }



                    sqrt = Math.sqrt(Rest);
//                    sortPosition.add(sqrt);



                    Log.e("get data offline  "+getData.size(), getData + "");

                    Log.e("Rest  "+PositionList.get(c),"  "+Rest);

                    Log.e("sqrt   "+PositionList.get(c),"  "+sqrt);

                   AllResultSqrt.add(PositionList.get(c)+" / "+String.format("%.2f",sqrt));
                    //AllResultSqrt.add(sqrt);

//                    String tmp[] = AllResultSqrt.spliterator("/");

                    Log.e("All result  ",AllResultSqrt+"");


//                    Collections.sort(AllResultSqrt);
//                    Log.e("sort",AllResultSqrt+"");

//                    myPosition.add(PositionList.get(c));
//                    Log.e("myPosition", myPosition+"");
                    c++;



                chk = (Button) findViewById(R.id.chkButt);
                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RetrieveActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog, null);
                        TextView output_Position = (TextView) mView.findViewById(R.id.Output_PositionTextView);
                        output_Position.setText(AllResultSqrt.toString());
//                        output_Position.setText("Hello");
                        Log.e("All Result click", AllResultSqrt.get(0)+"");

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                });

            }


//            public void Cal(final List<Integer> getOffline){
//
//
//                chk = (Button) findViewById(R.id.chkButt);
//                chk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getRssi.clear();
//                        //Log.e("offline", getOffline+"");
//                        if (view.getId() == R.id.chkButt){
//                            List<ScanResult> wifiScanList = wifi.getScanResults();
//                            //getData = new int[wifiScanList.size()];
//                            for (int i=0;i<wifiScanList.size();i++){
//                                getRssi.add((long) wifiScanList.get(i).level);
//                            }
//                            Log.e("RssiP :",getRssi+"");
//
//                        }
//
//
//                        Log.e("get offline jhhgfgy.", getOffline+"");
//
//
//                        for (int i=0;i<sss.size();i++){
//                            for (int j=0;j<getRssi.size();j++) {
//                                result = Math.pow((getRssi.get(j) - getOffline.get(j)), 2);
//                                result2 += result;
//                            }
//
//                            Log.e("tmp cal", getOffline+ "");
//
//                            resultSqrt = Math.sqrt(result2);
//                            AllResultSqrt.add(resultSqrt);
//                            Log.e("RssiP Size :"+getRssi.size(),getRssi+"");
//
//
//                            //Log.e("tmp"+tmp.size(),tmp+"");
//                            Log.e("result 2  "+i,result2+"");
//
//                            Log.e("Result SQRT  "+i,resultSqrt+"");
//
//
//
//
//
//
//
//
//
//
//                        }
//                        Collections.sort(AllResultSqrt);
//
//                        for (double counter: AllResultSqrt){
//
//                            Log.e("All result SQRT",counter+"");
//                        }
//
//
//
//
//
//
//                    }
//                });
//
//
//
//            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        Clr = (Button) findViewById(R.id.clr_Butt);
        Clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                dref.removeValue();

            }
        });




    }

//    public void Cal(List<Long> tmp, List<Long> getRssi){
//
//
//        return ;
//    }



}
