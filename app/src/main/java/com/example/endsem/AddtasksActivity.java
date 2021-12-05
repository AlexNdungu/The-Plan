package com.example.endsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

import android.graphics.Color;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddtasksActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText editTextName, editTextDesc;
    ImageButton backtasks;
    ImageButton buttonAddTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtasks);

        //Back To view Tasks

        backtasks = findViewById(R.id.backbtn);

        backtasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(AddtasksActivity.this, MainActivity.class);
                startActivity(in);

            }
        });


        mDisplayDate =  findViewById(R.id.textViewDate);

        editTextDesc=findViewById(R.id.editTextDesc);
        editTextName=findViewById(R.id.editTextName);
        buttonAddTasks=findViewById(R.id.buttonAddTasks);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddtasksActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        buttonAddTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=editTextName.getText().toString();
                String description=editTextDesc.getText().toString();
                String date=mDisplayDate.getText().toString();
                if (name.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddtasksActivity.this,"fill in details !",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseAuth  mAuth = FirebaseAuth.getInstance();
                    String userId=mAuth.getUid();
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("tasks").child(userId);
                    HashMap <String,String> hashMap=new HashMap<>();
                    hashMap.put("name",name);
                    hashMap.put("description",description);
                    hashMap.put("date",date);

                    ProgressDialog progressDialog= new ProgressDialog(AddtasksActivity.this);
                    progressDialog.setTitle("please wait");
                    progressDialog.setMessage("saving");
                    progressDialog.show();
                    databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            Toast.makeText(AddtasksActivity.this,"tasks saved successfully🙂",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(AddtasksActivity.this,AddtasksActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });


                }
            }
        });

    }
}


