package com.montassar.distributeurdespliles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtMsg,edtHour;
    private Button btnAllSet;
    private Time mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtHour = (EditText) findViewById(R.id.edt_my_time_edit);
        edtMsg = (EditText)  findViewById(R.id.edt_my_msg_edit);
        btnAllSet = (Button) findViewById(R.id.btn_all_set);
        Intent i = getIntent();
        int id = i.getIntExtra("ID",0);
        String hour = i.getStringExtra("HOUR");
        String msg = i.getStringExtra("MSG");
        Time mTime =new Time(id,msg,hour);
        edtHour.setText(hour, TextView.BufferType.EDITABLE);
        edtMsg.setText(msg, TextView.BufferType.EDITABLE);

        btnAllSet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
