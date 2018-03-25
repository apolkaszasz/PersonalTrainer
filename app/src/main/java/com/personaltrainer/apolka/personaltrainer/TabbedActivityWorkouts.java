package com.personaltrainer.apolka.personaltrainer;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class TabbedActivityWorkouts extends AppCompatActivity implements WorkoutsExercisesFragment.OnFragmentInteractionListener, ProgrammesFragment.OnFragmentInteractionListener{


    private static final String TAG  = "TabbedActivityWorkouts";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_workouts);


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void onFragmentInteraction(Uri uri){

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter((getSupportFragmentManager()));
        adapter.addFragment(new WorkoutsExercisesFragment(),"Exercises");
        adapter.addFragment(new ProgrammesFragment(),"Programmes");
        viewPager.setAdapter(adapter);
    }
}
