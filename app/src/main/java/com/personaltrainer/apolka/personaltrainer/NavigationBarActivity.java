package com.personaltrainer.apolka.personaltrainer;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;


import com.google.firebase.auth.FirebaseAuth;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.AdviceFragment;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.MyPlansFragment;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.ResultsFragment;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.WorkoutFragment;



public class NavigationBarActivity extends AppCompatActivity
implements MyPlansFragment.OnFragmentInteractionListener,WorkoutFragment.OnFragmentInteractionListener,
AdviceFragment.OnFragmentInteractionListener, ResultsFragment.OnFragmentInteractionListener{


    private ActionBar toolbar;

    private FirebaseAuth mAuth;



    private static final String TAG = "NavigationBarActivity";

    @Override
    public void onFragmentInteraction(Uri uri){
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        mAuth = FirebaseAuth.getInstance();


        toolbar = getSupportActionBar();


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
                    toolbar.setTitle("Workouts");
                    fragment = new WorkoutFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_advices:
                    toolbar.setTitle("Advice");
                    fragment = new AdviceFragment();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:


                mAuth.signOut();

                startActivity(new Intent(NavigationBarActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
