package com.yasination.googleauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edLoginEmail, edLoginPassword;
    Button btnLogin;
    TextView tvGoCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edLoginEmail = findViewById(R.id.edLoginEmail);
        edLoginPassword = findViewById(R.id.edLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoCreate = findViewById(R.id.tvGoCreate);

        mAuth  = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            loginUser();
        });

        tvGoCreate.setOnClickListener(v -> {
            startActivity(new Intent(Login.this,Register.class));
        });


    }//---------------------------------------

    private void  loginUser(){
        String email = edLoginEmail.getText().toString();
        String password = edLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            edLoginEmail.setError("Enter Email");
            edLoginEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            edLoginPassword.setError("Enter Password");
            edLoginPassword.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()){
                            startActivity(new Intent(Login.this,MainActivity.class));
                            Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(Login.this,"Please Verify Account",Toast.LENGTH_LONG).show();

                    }else Toast.makeText(Login.this,"Wrong Input  ",Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    //---------------------------------------
}