package com.personaltrainer.apolka.personaltrainer;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.personaltrainer.apolka.personaltrainer.Adapters.SectionsPageAdapter;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.ProgramsFragment;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.WorkoutsExercisesFragment;

//TODO Matyas: Az egesz kodra ervenyes. Probald meg kozben egy kicsit kitorolni a folosleges, nem hasznalt kodot, mert kicsit felgyult es emiatt nehezebben atlathatova valik a kod.
public class TabbedActivityWorkouts extends AppCompatActivity implements WorkoutsExercisesFragment.OnFragmentInteractionListener, ProgramsFragment.OnFragmentInteractionListener{


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
        adapter.addFragment(new ProgramsFragment(),"Programs");
        viewPager.setAdapter(adapter);
    }
}
