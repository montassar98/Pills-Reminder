package com.montassar.distributeurdespliles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.montassar.distributeurdespliles.Fragments.HomeFragment;
import com.montassar.distributeurdespliles.Fragments.TimePickerFragment;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtMsg;
    public  static TextView edtHour;
    private Button btnAllSet;
    public FirebaseDatabase database;
    public DatabaseReference myRefTime;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mAuth = FirebaseAuth.getInstance();
        edtHour = (TextView) findViewById(R.id.edt_my_time);
        edtMsg = (EditText)  findViewById(R.id.edt_my_msg);

        btnAllSet = (Button) findViewById(R.id.btn_all_set);
        database = FirebaseDatabase.getInstance();
        myRefTime = database.getReference("MyTimes/"+mAuth.getCurrentUser().getUid());

        btnAllSet.setOnClickListener(this);
        edtHour.setOnClickListener(this);
        //myRefTime.addValueEventListener(timeListener);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_all_set :
                addTime();
                Toast.makeText(this, "allSet clicked.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edt_my_time:
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

                break;
        }
    }
    public void addTime() {
        String hour = edtHour.getText().toString();
        String msg = edtMsg.getText().toString();
        //HomeFragment.arrayList.add(new Time(hour,msg));
        Time mTime = new Time(msg,hour);
        myRefTime.push().setValue(mTime);
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
