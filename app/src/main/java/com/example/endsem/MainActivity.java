package com.example.endsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //All activities model bellow

    ArrayList<allActivitiesModel> allActivitiesModels = new ArrayList<>();

    ImageButton addAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Recycler View Code
        RecyclerView recyclerView = findViewById(R.id.recActs);

       // setUpAllActivitiesModel();

        ACT_RecyclerViewAdapter adapter = new ACT_RecyclerViewAdapter(this, allActivitiesModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //To Adding Activity

        addAct = findViewById(R.id.addActivity);

        addAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, AddtasksActivity.class);
                startActivity(i);

            }
        });

        String uid = "";
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
           uid = mAuth.getCurrentUser().getUid();
        }else{
            Intent out = new Intent(MainActivity.this, Home.class);
            startActivity(out);
            finish();
        }

        //Database snapshot

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allActivitiesModels.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    allActivitiesModel allActivities = new allActivitiesModel(
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("description").getValue().toString(),
                            dataSnapshot.child("date").getValue().toString(),
                            dataSnapshot.getKey()) ;

                    allActivitiesModels.add(allActivities);
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //Menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.extramenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() != null){
                mAuth.signOut();
            }
            Intent out = new Intent(MainActivity.this, Home.class);
            startActivity(out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Recycler View Loop
    //private void setUpAllActivitiesModel(){

        //Create all the models classes
//        String [] allActivitiesNames = getResources().getStringArray(R.array.activities_texts);

        //Looping through all of the activites

       // for (String allActivitiesName : allActivitiesNames) {
            //Looping in here

          //  allActivitiesModels.add(new allActivitiesModel(allActivitiesName));

        //}

   // }
}

