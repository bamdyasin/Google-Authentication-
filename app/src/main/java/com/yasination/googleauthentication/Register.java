package com.yasination.googleauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText edEmail,edPassword ;
    Button btnRegister;
    TextView tvGoLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoLogin = findViewById(R.id.tvGoLogin);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {
            createUser();
        });

        tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(Register.this,Login.class));
        });


//--------------------------------------------------
    }

    private  void createUser(){
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            edEmail.setError("Enter Email");
            edEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            edPassword.setError("Enter Password");
            edPassword.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this,"Please Verify Email Before Login",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Register.this,Login.class));

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this,"Please Try Again",Toast.LENGTH_LONG).show();

                            }
                        });
                    }else
                        Toast.makeText(Register.this,"Register Error: "+task.getException().toString(),Toast.LENGTH_LONG).show();

                }
            });
        }



    }


    //--------------------------------------------
}