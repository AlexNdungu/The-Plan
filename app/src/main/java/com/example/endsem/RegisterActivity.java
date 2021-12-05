package com.example.endsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText email_reg, password_reg,confpassword_reg;
    Button button_reg;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textViewLogin= findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });

        email_reg = findViewById(R.id.email_reg);
        password_reg = findViewById(R.id.password_reg);
        confpassword_reg = findViewById(R.id.confpassword_reg);
        button_reg= findViewById(R.id.button_reg);
        progressBar=findViewById(R.id.progressBar);

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = email_reg.getText().toString().trim();
                String password = password_reg.getText().toString().trim();
                String confpassword = confpassword_reg.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confpassword.equals(password) )
                {   Toast.makeText(getApplicationContext(),"passwords not matching please confirm!",Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth = FirebaseAuth.getInstance();


                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, SigninActivity.class));
                                    Toast.makeText(RegisterActivity.this, "account created successfully!" ,Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });


            }
        });
    }


}


