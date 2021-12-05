package com.example.endsem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;



public class ACT_RecyclerViewAdapter extends RecyclerView.Adapter<ACT_RecyclerViewAdapter.MyViewHolder> {

    //Create Variables

    Context context;
    ArrayList<allActivitiesModel> allActivitiesModels;

    //create a constructor

    public ACT_RecyclerViewAdapter(Context context , ArrayList<allActivitiesModel> allActivitiesModels){

        this.context = context;
        this.allActivitiesModels = allActivitiesModels;

    }

    @NonNull
    @Override
    public ACT_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate the layout (Giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rec_view_raw, parent, false);

        return new ACT_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ACT_RecyclerViewAdapter.MyViewHolder holder, int position) {
    //assigning values to the views we created in teh RV row layout file
    //Based on the position of the recycler view

        holder.actName.setText(allActivitiesModels.get(position).getTaskName());
        holder.dateA.setText(allActivitiesModels.get(position).getTaskDate());
        holder.addDet.setText(allActivitiesModels.get(position).getTaskDesc());


        // get task id
        String key = allActivitiesModels.get(position).getActId();
        holder.delAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = "";
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() != null){
                    uid = mAuth.getCurrentUser().getUid();
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(uid);
                databaseReference.child(key).removeValue();


            }
        });


    }

    @Override
    public int getItemCount() {

        //To know how many items you want displayed

        return allActivitiesModels.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing the views from our RC row layout
        //Like oncreate method

        TextView actName,dateA,addDet;

        CheckBox tickIt;

        ConstraintLayout constraintLayout;

        ImageButton delAct, extendbtn;





        public MyViewHolder(@NonNull View itemView ) {
            super(itemView);

            //Grabing the items from xml
            actName = itemView.findViewById(R.id.theAct);
            tickIt = itemView.findViewById(R.id.checkAct);

            delAct = itemView.findViewById(R.id.deleteAct);
            addDet = itemView.findViewById(R.id.addDetail);
            dateA = itemView.findViewById(R.id.dateAct);

            extendbtn = itemView.findViewById(R.id.extendBtn);

            constraintLayout = itemView.findViewById(R.id.contAdText);





            tickIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (tickIt.isChecked()){

                        actName.setPaintFlags(actName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    }
                    else {

                        actName.setPaintFlags(actName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                    }


                }
            });

            extendbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (constraintLayout.getVisibility() == View.VISIBLE){
                        constraintLayout.setVisibility(View.GONE);
                    }else {
                        constraintLayout.setVisibility(View.VISIBLE);
                    }

                }
            });


        }

    }

}
