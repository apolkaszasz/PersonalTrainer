package com.personaltrainer.apolka.personaltrainer;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
//import com.personaltrainer.apolka.personaltrainer.BottomNavigationBehavior;


public class Main2Activity extends AppCompatActivity
implements MyPlansFragment.OnFragmentInteractionListener,WorkoutFragment.OnFragmentInteractionListener,
AdvicesFragment.OnFragmentInteractionListener, ResultsFragment.OnFragmentInteractionListener{


    private ActionBar toolbar;

    private static final String TAG = "Main2Activity";

    @Override
    public void onFragmentInteraction(Uri uri){
        
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = getSupportActionBar();

        //load the MypPlans fragment by default

        toolbar.setTitle("MyPlans");
        loadFragment(new MyPlansFragment());


        BottomNavigationView mBottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_plans:
                    toolbar.setTitle("MyPlans");
                    fragment = new MyPlansFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_workouts:
                    Log.d(TAG,"here...............");
                    toolbar.setTitle("Workouts");
                    fragment = new WorkoutFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_advices:
                    toolbar.setTitle("Advices");
                    fragment = new AdvicesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_results:
                    toolbar.setTitle("Results");
                    fragment = new ResultsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
