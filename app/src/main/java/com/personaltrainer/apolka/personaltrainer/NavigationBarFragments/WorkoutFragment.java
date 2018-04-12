package com.personaltrainer.apolka.personaltrainer.NavigationBarFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.personaltrainer.apolka.personaltrainer.R;
import com.personaltrainer.apolka.personaltrainer.TabbedActivityWorkouts;


public class WorkoutFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WorkoutFragment";

    private Button go_button;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WorkoutFragment() {
    }


    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        FloatingActionButton mfloatingActionButton = (FloatingActionButton)view.findViewById(R.id.AllFloatingButton);
        mfloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tabbedActivityWorkouts();
            }
        });

        ImageView abs = (ImageView)view.findViewById(R.id.imageViewAbs);
        abs.setColorFilter(Color.rgb(255,64,129));

        ImageView abductors = (ImageView)view.findViewById(R.id.imageViewAbductors);
        abductors.setColorFilter(Color.rgb(255,64,129));

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void tabbedActivityWorkouts(){
        Intent i = new Intent(getActivity(), TabbedActivityWorkouts.class);
        startActivity(i);
    }
}
