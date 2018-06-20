package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.personaltrainer.apolka.personaltrainer.Adapters.SectionsPageAdapter;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.ProgramsFragment;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.WorkoutsExercisesFragment;

public class TabbedActivityWorkouts extends AppCompatActivity implements WorkoutsExercisesFragment.OnFragmentInteractionListener, ProgramsFragment.OnFragmentInteractionListener{


    private static final String TAG  = "TabbedActivityWorkouts";
    private SharedPreferences sharedPreferences;

    private SectionsPageAdapter mSectionsPageAdapter;

    private static final String MUSCLE_KEY = "Muscle_key";
    public static Intent getStartIntent(Context context, String muscle) {
        return new Intent(context, TabbedActivityWorkouts.class)
                .putExtra(MUSCLE_KEY, muscle);
    }


    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_workouts);


        Intent i = getIntent();
        String muscle = (String)i.getSerializableExtra(MUSCLE_KEY);
        Log.d(TAG,"..................."+muscle);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);


        sharedPreferences = this.getSharedPreferences(
                "userPreferences",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("muscle", muscle);
        editor.commit();
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void onFragmentInteraction(Uri uri){

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter((getSupportFragmentManager()));
        adapter.addFragment(new WorkoutsExercisesFragment(),"Exercises");
        adapter.addFragment(new ProgramsFragment() ,"Programs");
        viewPager.setAdapter(adapter);
    }
}
