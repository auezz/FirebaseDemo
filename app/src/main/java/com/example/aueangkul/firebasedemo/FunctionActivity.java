package com.example.aueangkul.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView position;
    ImageView getPosition;
    Button signoutButt;
    private TextView textViewUserEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView)findViewById(R.id.EmailID);
        //displaying logged in user name
        textViewUserEmail.setText(""+user.getEmail());


        //sign out butt
        signoutButt = (Button)findViewById(R.id.SignOutButt);
        //add listener Button
        signoutButt.setOnClickListener(this);

        position = (ImageView)findViewById(R.id.PositionImage);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == position){
                    Intent intent = new Intent(FunctionActivity.this, ProfileActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });

        getPosition = (ImageView) findViewById(R.id.getPositionData);
        getPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == getPosition){
                    Intent intent = new Intent(FunctionActivity.this, RetrieveActivity.class);
                    startActivityForResult(intent,1);

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == signoutButt){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, SigninActivity.class));

        }

    }






}
