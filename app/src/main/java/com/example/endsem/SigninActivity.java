package com.example.endsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    EditText email_login, password_login;
    Button button_login;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email_login=findViewById(R.id.email_login);
        password_login=findViewById(R.id.password_login);
        button_login=findViewById(R.id.button_login);

        TextView textViewSignin= findViewById(R.id.textViewSignin);

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SigninActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()  != null){
            Intent intent=  new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
        }

        email_login.setText("@gmail.com");

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= email_login.getText().toString();
                String password = password_login.getText().toString();


                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user account created
                            Toast.makeText(SigninActivity.this, "login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SigninActivity.this, "Error occurred "+e, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}





