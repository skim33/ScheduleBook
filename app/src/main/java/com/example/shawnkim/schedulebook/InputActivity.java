package com.example.shawnkim.schedulebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class InputActivity extends AppCompatActivity {

    private Realm mRealm;
    private Long mId;
    private TextView mDate;
    private EditText mTitle;
    private EditText mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mRealm = Realm.getDefaultInstance();
        mDate = (TextView) findViewById(R.id.date);
        mTitle = (EditText) findViewById(R.id.title);
        mDetail = (EditText) findViewById(R.id.detail);

        if (getIntent() != null) {
            mId = getIntent().getLongExtra("ID", -1);

            Schedule schedule = mRealm.where(Schedule.class).equalTo("id", mId)
                    .findFirst();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.date);
            mDate.setText(formatDate);
            mTitle.setText(schedule.title);
            mDetail.setText(schedule.detail);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
