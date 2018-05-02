package com.personaltrainer.apolka.personaltrainer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedProgram;
import com.personaltrainer.apolka.personaltrainer.Models.Program;

import java.text.DateFormat;
import java.util.Calendar;

public class NewPlannedProgram extends AppCompatActivity {

    private static final String TAG = "NewPlannedExercise";
    private static final String PROGRAM_OBJ_KEY = "Program_Obj_Key";
    private Program program;
    private PlannedProgram plannedProgram;
    private String programKey;
    private String userId;

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mMessagesDatabaseReference2;
    private ChildEventListener mChildEventListener;


    private TextView programNameTextView;
    private TextView startDateTextVew;
    private Button add;

    private DateFormat dateFormat;
    private Calendar dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_planned_program);


        Intent i = getIntent();
        program = (Program)i.getSerializableExtra(PROGRAM_OBJ_KEY);

        sharedPreferences = this.getSharedPreferences(
                "userPreferences",0
        );
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));

        userId= sharedPreferences.getString("UserID",null);

        programNameTextView = (TextView)findViewById(R.id.NewProgramNameTextView);
        programNameTextView.setText(program.getName());



        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();
        startDateTextVew = (TextView)findViewById(R.id.startDateTextViewValue);
        startDateTextVew.setText(dateFormat.format(dateTime.getTime()));
        startDateTextVew.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                onclickdatePicker();
            }
        });



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Programs");

        add = (Button)findViewById(R.id.addProgramToMyPlans);
        add.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {

                        PlannedProgram plannedProgram = new PlannedProgram(programKey,dateTime.getTime(),userId);
                        mMessagesDatabaseReference2 = mFirebaseDatabase.getReference();
                        mMessagesDatabaseReference2.child("PlannedPrograms").child(programKey+userId+Calendar.getInstance().getTime().toString()).setValue(plannedProgram);

                        Toast.makeText(NewPlannedProgram.this, "Program added to MyPlans!", Toast.LENGTH_LONG).show();
                        finish();


                    }}
        );
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR,year);
            dateTime.set(Calendar.MONTH,month);
            dateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            startDateTextVew.setText(dateFormat.format(dateTime.getTime()));
        }
    };

    private void onclickdatePicker(){
        new DatePickerDialog(this, d ,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();

    }
    @Override
    public void onPause(){
        super.onPause();

        detachDatabaseReadListener();

    }
    @Override
    public void onResume(){
        super.onResume();

        attachDatabaseReadListener();

    }
    public static Intent getStartIntent(Context context, Program program) {
        return new Intent(context, NewPlannedProgram.class)
                .putExtra(PROGRAM_OBJ_KEY, program);
    }


    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener =null;
        }
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Program programL = dataSnapshot.getValue(Program.class);
                    Log.d(TAG,"............"+programL.getName());
                    if(programL.getName()!=null){
                        if (programL.getName().equals(program.getName())){
                            programKey = dataSnapshot.getKey();
                            Log.d(TAG,"program key is ............."+programKey);
                        }
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }}



}
