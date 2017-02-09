package com.example.aueangkul.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignup;
    private EditText edittextEmail;
    private EditText edittextPass;
    private TextView textviewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), FunctionActivity.class));

        }

        progressDialog = new ProgressDialog(this);


        buttonSignup = (Button) findViewById(R.id.SignUpButt);
        edittextEmail = (EditText) findViewById(R.id.EmailF);
        edittextPass = (EditText) findViewById(R.id.PassF);
        textviewSignin = (TextView) findViewById(R.id.SignInText);

        buttonSignup.setOnClickListener(this);
        textviewSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = edittextEmail.getText().toString().trim();
        String pass = edittextPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }
        if(TextUtils.isEmpty(pass)){
            //pass is empty
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validation are ok
        //we will show progressbar

        progressDialog.setMessage("Signing Up User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start profile activity here
                            //right now lets display a toast only
                            finish();
                            startActivity(new Intent(getApplicationContext(), FunctionActivity.class));
                            Toast.makeText(MainActivity.this, "Sign Up is Successfully", Toast.LENGTH_SHORT);

                        }else{
                            Toast.makeText(MainActivity.this, "Could not Sign Up, Please try again.", Toast.LENGTH_SHORT);

                        }

                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();
        }
        if(view == textviewSignin){
            //will open login page
            startActivity(new Intent(this, SigninActivity.class));

        }


    }
}
