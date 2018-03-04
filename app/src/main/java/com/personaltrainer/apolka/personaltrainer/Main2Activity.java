package com.personaltrainer.apolka.personaltrainer;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity {


    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("MyPlans");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_plans:
                    toolbar.setTitle("My Plans");
                    return true;
                case R.id.navigation_workouts:
                    toolbar.setTitle("Workouts");
                    return true;
                case R.id.navigation_advices:
                    toolbar.setTitle("Advices");
                    return true;
                case R.id.navigation_results:
                    toolbar.setTitle("Results");
                    return true;
            }
            return false;
        }
    };
}
