package com.example.shawnkim.schedulebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();
        ListView mListView = findViewById(R.id.listView);

        RealmResults<Schedule> schedules = mRealm.where(Schedule.class).findAll();
        ScheduleAdapter adapter = new ScheduleAdapter(schedules);

        mListView.setAdapter(adapter);

        Button dbTest = findViewById(R.id.db_test_button);
        dbTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RealmTestActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long[] newId = new long[1];
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number max = realm.where(Schedule.class).max("id");
                        newId[0] = 0;
                        if (max != null) {
                            newId[0] = max.longValue() + 1;
                        }

                        Schedule schedule = realm.createObject(Schedule.class, newId[0]);
                        schedule.date = new Date();
                        schedule.title = "";
                        schedule.detail = "";
                    }
                });

                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                intent.putExtra("ID", newId[0]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
