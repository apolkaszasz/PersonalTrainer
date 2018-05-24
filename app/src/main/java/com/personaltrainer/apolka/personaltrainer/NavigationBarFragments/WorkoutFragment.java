package com.personaltrainer.apolka.personaltrainer.NavigationBarFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.personaltrainer.apolka.personaltrainer.ExerciseItem;
import com.personaltrainer.apolka.personaltrainer.MainActivity;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.Square;
import com.personaltrainer.apolka.personaltrainer.R;
import com.personaltrainer.apolka.personaltrainer.TabbedActivityWorkouts;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WorkoutFragment";
    private SharedPreferences sharedPreferences;

    private String choosedMuscle;

    private List<Square> abductorsSquareList;
    private List<Square> absSquareList;
    private List<Square> bicepsSquareList;
    private List<Square> calvesSquareList;
    private List<Square> chestSquareList;
    private List<Square> feetSquareList;
    private List<Square> forearmsSquareList;
    private List<Square> glutesSquareList;
    private List<Square> hamstringsSquareList;
    private List<Square> handsSquareList;
    private List<Square> latsSquareList;
    private List<Square> neckSquareList;
    private List<Square> obliquesSquareList;
    private List<Square> quadsSquareList;
    private List<Square> shouldersSquareList;
    private List<Square> tricepsSquareList;
    private List<Square> upperbackSquareList;

    private ImageView fullBody;
    private ImageView abs;
    private ImageView abductors;
    private ImageView biceps;
    private ImageView calves;
    private ImageView chest;
    private ImageView feet;
    private ImageView forearms;
    private ImageView glutes;
    private ImageView hamstrings;
    private ImageView hands;
    private ImageView lats;
    private ImageView neck;
    private ImageView obliques;
    private ImageView quads;
    private ImageView shoulders;
    private ImageView triceps;
    private ImageView upperBack;


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

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "userPreferences",0);


        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));

        initializeMuscleSquareLists();



        FloatingActionButton mfloatingActionButton = (FloatingActionButton)view.findViewById(R.id.AllFloatingButton);
        mfloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tabbedActivityWorkouts();
            }
        });

        abductors = (ImageView)view.findViewById(R.id.imageViewAbductors);
        //abductors.setColorFilter(Color.rgb(255,64,129));
        abs = (ImageView)view.findViewById(R.id.imageViewAbs);
        biceps = (ImageView)view.findViewById(R.id.imageViewBiceps);
        calves = (ImageView)view.findViewById(R.id.imageViewCalves);
        chest = (ImageView)view.findViewById(R.id.imageViewChest);
        feet = (ImageView)view.findViewById(R.id.imageViewFeet);
        forearms = (ImageView)view.findViewById(R.id.imageViewForearms);
        glutes = (ImageView)view.findViewById(R.id.imageViewGlutes);
        hamstrings = (ImageView)view.findViewById(R.id.imageViewHamstrings);
        hands = (ImageView)view.findViewById(R.id.imageViewHands);
        lats = (ImageView)view.findViewById(R.id.imageViewLats);
        neck = (ImageView)view.findViewById(R.id.imageViewNeck);
        obliques = (ImageView)view.findViewById(R.id.imageViewObliques);
        quads = (ImageView)view.findViewById(R.id.imageViewQuads);
        shoulders = (ImageView)view.findViewById(R.id.imageViewShoulders);
        triceps = (ImageView)view.findViewById(R.id.imageViewTriceps);
        upperBack = (ImageView)view.findViewById(R.id.imageViewUpperBack);


        fullBody = (ImageView)view.findViewById(R.id.imageViewFullBody);
        fullBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d(TAG,"Touch coordinates : " +
                            String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));



                    choosedMuscle = "";
                    abductors.clearColorFilter();
                    abs.clearColorFilter();
                    biceps.clearColorFilter();
                    calves.clearColorFilter();
                    chest.clearColorFilter();
                    feet.clearColorFilter();
                    forearms.clearColorFilter();
                    glutes.clearColorFilter();
                    hamstrings.clearColorFilter();
                    hands.clearColorFilter();
                    lats.clearColorFilter();
                    neck.clearColorFilter();
                    obliques.clearColorFilter();
                    quads.clearColorFilter();
                    shoulders.clearColorFilter();
                    triceps.clearColorFilter();
                    upperBack.clearColorFilter();
                    String muscle = clickPosition_Muscle(event.getX(),event.getY());
                    Log.d(TAG, muscle);
                    switch (muscle){
                        case "Abs":
                            //abs.setColorFilter(Color.rgb(255,64,129));
                            abs.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Abs";
                            break;
                        case "Abductors":
                            abductors.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Abductors";
                            break;
                        case "Biceps":
                            biceps.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Biceps";
                            break;
                        case "Calves":
                            calves.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Calves";
                            break;
                        case "Chest":
                            chest.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Chest";
                            break;
                        case "Feet":
                            feet.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Feet";
                            break;
                        case "Forearms":
                            forearms.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Forearms";
                            break;
                        case "Glutes":
                            glutes.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Glutes";
                            break;
                        case "Hamstrings":
                            hamstrings.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Hamstrings";
                            break;
                        case "Hands":
                            hands.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Hands";
                            break;
                        case "Lats":
                            lats.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Lats";
                            break;
                        case "Neck":
                            neck.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Neck";
                            break;
                        case "Obliques":
                            obliques.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Obliques";
                            break;
                        case "Quads":
                            quads.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Quads";
                            break;
                        case "Shoulders":
                            shoulders.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Shoulders";
                            break;
                        case "Triceps":
                            triceps.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Triceps";
                            break;
                        case "Upper back":
                            upperBack.setColorFilter(Color.argb(150,255,64,129));
                            choosedMuscle = "Upper back";
                            break;
                    }


                    Log.d(TAG, fullBody.getLeft() +"    "+fullBody.getTop() + "  " + fullBody.getWidth() + "  "+ fullBody.getHeight());
                }
                return true;
            }
        });
        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(1104,920);
        fullBody.setLayoutParams(parms);
        abductors.setLayoutParams(parms);
        abs.setLayoutParams(parms);
        biceps.setLayoutParams(parms);
        calves.setLayoutParams(parms);
        chest.setLayoutParams(parms);
        feet.setLayoutParams(parms);
        forearms.setLayoutParams(parms);
        glutes.setLayoutParams(parms);
        hamstrings.setLayoutParams(parms);
        hands.setLayoutParams(parms);
        lats.setLayoutParams(parms);
        neck.setLayoutParams(parms);
        obliques.setLayoutParams(parms);
        quads.setLayoutParams(parms);
        shoulders.setLayoutParams(parms);
        triceps.setLayoutParams(parms);
        upperBack.setLayoutParams(parms);





        Log.d(TAG, abs.getLeft() +"    "+abs.getTop() + "  " + abs.getWidth() + "  "+ abs.getHeight());
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

        startActivity(TabbedActivityWorkouts.getStartIntent(getContext(), choosedMuscle));

    }

    private String clickPosition_Muscle(double x, double y){
        String muscle = "";
        for (Square sq: abductorsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Abductors";
                return muscle;
            }
        }
        for (Square sq: absSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Abs";
                return muscle;
            }
        }
        for (Square sq: bicepsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Biceps";
                return muscle;
            }
        }
        for (Square sq: calvesSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Calves";
                return muscle;
            }
        }
        for (Square sq: chestSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Chest";
                return muscle;
            }
        }
        for (Square sq: feetSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Feet";
                return muscle;
            }
        }
        for (Square sq: forearmsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Forearms";
                return muscle;
            }
        }
        for (Square sq: glutesSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Glutes";
                return muscle;
            }
        }

        for (Square sq: hamstringsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Hamstrings";
                return muscle;
            }
        }
        for (Square sq: handsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Hands";
                return muscle;
            }
        }
        for (Square sq: latsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Lats";
                return muscle;
            }
        }
        for (Square sq: neckSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Neck";
                return muscle;
            }
        }
        for (Square sq: obliquesSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Obliques";
                return muscle;
            }
        }
        for (Square sq: quadsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Quads";
                return muscle;
            }
        }
        for (Square sq: shouldersSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Shoulders";
                return muscle;
            }
        }
        for (Square sq: tricepsSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Triceps";
                return muscle;
            }
        }
        for (Square sq: upperbackSquareList){
            double proportion = 2.3;
            Square convertedSquare = new Square(sq.getX_begin()*proportion,sq.getY_begin()*proportion,sq.getX_end()*proportion,sq.getY_end()*proportion);
            if(x>convertedSquare.getX_begin() && y>convertedSquare.getY_begin() && x<convertedSquare.getX_end() && y<convertedSquare.getY_end()){
                muscle = "Upper back";
                return muscle;
            }
        }
        return muscle;
    }
    private void initializeMuscleSquareLists(){
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));
        abductorsSquareList = new ArrayList<Square>();
        absSquareList  = new ArrayList<Square>();
         bicepsSquareList  = new ArrayList<Square>();
        calvesSquareList  = new ArrayList<Square>();
       chestSquareList  = new ArrayList<Square>();
         feetSquareList  = new ArrayList<Square>();
        forearmsSquareList  = new ArrayList<Square>();
         glutesSquareList  = new ArrayList<Square>();
        hamstringsSquareList  = new ArrayList<Square>();
       handsSquareList = new ArrayList<Square>();
       latsSquareList = new ArrayList<Square>();
        neckSquareList = new ArrayList<Square>();
        obliquesSquareList = new ArrayList<Square>();
        quadsSquareList = new ArrayList<Square>();
        shouldersSquareList = new ArrayList<Square>();
       tricepsSquareList = new ArrayList<Square>();
        upperbackSquareList = new ArrayList<Square>();
        //abductors
        abductorsSquareList.add(new Square(322,150,331,179));
        abductorsSquareList.add(new Square(321,180,327,186));
        abductorsSquareList.add(new Square(331,168,340,176));
        abductorsSquareList.add(new Square(377,149,385,179));
        abductorsSquareList.add(new Square(380,179,386,185));
        abductorsSquareList.add(new Square(370,166,377,176));
        abductorsSquareList.add(new Square(368,170,371,174));
        //abs
        absSquareList.add(new Square(116,118,151,165));
        absSquareList.add(new Square(118,165,149,184));
        absSquareList.add(new Square(121,184,146,189));
        absSquareList.add(new Square(124,189,144,193));
        //biceps
        bicepsSquareList.add(new Square(85,108,99,133));
        bicepsSquareList.add(new Square(82,133,91,140));
        bicepsSquareList.add(new Square(167,107,182,122));
        bicepsSquareList.add(new Square(169,122,185,136));
        bicepsSquareList.add(new Square(177,136,185,140));
        //calves
        calvesSquareList.add(new Square(107,294,131,365));
        calvesSquareList.add(new Square(136,295,159,365));
        calvesSquareList.add(new Square(322,276,336,331));
        calvesSquareList.add(new Square(336,284,348,370));
        calvesSquareList.add(new Square(329,331,336,374));
        calvesSquareList.add(new Square(358,284,382,374));
        calvesSquareList.add(new Square(369,279,382,284));
        //chest
        chestSquareList.add(new Square(108,85,158,114));
        chestSquareList.add(new Square(103,95,108,111));
        chestSquareList.add(new Square(98,102,103,108));
        chestSquareList.add(new Square(158,92,162,112));
        chestSquareList.add(new Square(162,100,166,109));
        chestSquareList.add(new Square(166,103,170,107));
        //feet
        feetSquareList.add(new Square(113,365,154,390));
        feetSquareList.add(new Square(327,377,380,390));
        //forearms
        forearmsSquareList.add(new Square(67,140,95,164));
        forearmsSquareList.add(new Square(63,164,87,173));
        forearmsSquareList.add(new Square(59,173,81,180));
        forearmsSquareList.add(new Square(56,180,75,188));
        forearmsSquareList.add(new Square(172,140,198,152));
        forearmsSquareList.add(new Square(174,152,202,165));
        forearmsSquareList.add(new Square(179,165,207,177));
        forearmsSquareList.add(new Square(189,177,211,188));
        forearmsSquareList.add(new Square(285,144,312,166));
        forearmsSquareList.add(new Square(281,166,305,181));
        forearmsSquareList.add(new Square(274,181,294,191));
        forearmsSquareList.add(new Square(393,144,422,160));
        forearmsSquareList.add(new Square(398,160,427,174));
        forearmsSquareList.add(new Square(407,174,434,189));
        //glutes
        glutesSquareList.add(new Square(375,212,383,218));
        glutesSquareList.add(new Square(317,187,390,202));
        glutesSquareList.add(new Square(327,181,379,187));
        glutesSquareList.add(new Square(332,178,344,181));
        glutesSquareList.add(new Square(363,177,376,181));
        glutesSquareList.add(new Square(318,202,342,210));
        glutesSquareList.add(new Square(323,210,332,218));
        glutesSquareList.add(new Square(342,202,350,206));
        glutesSquareList.add(new Square(358,202,389,206));
        glutesSquareList.add(new Square(367,207,388,211));
        //hamstrings
        hamstringsSquareList.add(new Square(317,218,350,251));
        hamstringsSquareList.add(new Square(320,251,349,270));
        hamstringsSquareList.add(new Square(336,270,347,277));
        hamstringsSquareList.add(new Square(339,277,346,283));
        hamstringsSquareList.add(new Square(339,270,347,277));
        hamstringsSquareList.add(new Square(333,211,351,211));
        hamstringsSquareList.add(new Square(343,208,351,211));
        hamstringsSquareList.add(new Square(357,207,367,244));
        hamstringsSquareList.add(new Square(367,212,375,270));
        hamstringsSquareList.add(new Square(358,244,367,283));
        hamstringsSquareList.add(new Square(375,218,388,268));
        hamstringsSquareList.add(new Square(367,270,372,277));
        hamstringsSquareList.add(new Square(379,269,384,277));
        //hands
        handsSquareList.add(new Square(38,188,67,219));
        handsSquareList.add(new Square(196,190,231,219));
        handsSquareList.add(new Square(258,191,289,223));
        handsSquareList.add(new Square(417,192,451,222));
        //lats
        latsSquareList.add(new Square(315,107,339,120));
        latsSquareList.add(new Square(367,107,391,120));
        latsSquareList.add(new Square(339,115,367,167));
        latsSquareList.add(new Square(319,120,339,137));
        latsSquareList.add(new Square(367,120,389,135));
        latsSquareList.add(new Square(325,137,339,150));
        latsSquareList.add(new Square(367,135,383,148));
        latsSquareList.add(new Square(331,150,339,158));
        latsSquareList.add(new Square(367,149,376,155));
        latsSquareList.add(new Square(334,158,339,167));
        latsSquareList.add(new Square(367,156,374,165));
        latsSquareList.add(new Square(341,167,366,172));
        latsSquareList.add(new Square(345,173,361,179));
        //neck
        neckSquareList.add(new Square(119,60,149,71));
        neckSquareList.add(new Square(123,71,143,85));
        //obliques
        obliquesSquareList.add(new Square(99,112,108,140));
        obliquesSquareList.add(new Square(108,114,116,182));
        obliquesSquareList.add(new Square(102,140,108,175));
        obliquesSquareList.add(new Square(152,114,164,175));
        obliquesSquareList.add(new Square(150,175,159,183));
        obliquesSquareList.add(new Square(163,112,167,183));
        //quads
        quadsSquareList.add(new Square(101,178,108,244));
        quadsSquareList.add(new Square(108,184,121,275));
        quadsSquareList.add(new Square(121,193,131,276));
        quadsSquareList.add(new Square(135,193,159,244));
        quadsSquareList.add(new Square(147,186,167,193));
        quadsSquareList.add(new Square(160,177,167,186));
        quadsSquareList.add(new Square(159,194,167,254));
        //shoulders
        shouldersSquareList.add(new Square(80,81,98,106));
        shouldersSquareList.add(new Square(98,81,103,100));
        shouldersSquareList.add(new Square(103,81,107,93));
        shouldersSquareList.add(new Square(158,83,181,89));
        shouldersSquareList.add(new Square(162,89,186,97));
        shouldersSquareList.add(new Square(166,98,185,102));
        shouldersSquareList.add(new Square(171,102,186,107));
        shouldersSquareList.add(new Square(301,90,308,106));
        shouldersSquareList.add(new Square(308,80,314,101));
        shouldersSquareList.add(new Square(314,77,319,92));
        shouldersSquareList.add(new Square(319,82,324,88));
        shouldersSquareList.add(new Square(400,86,407,107));
        shouldersSquareList.add(new Square(395,79,400,102));
        shouldersSquareList.add(new Square(391,81,395,97));
        shouldersSquareList.add(new Square(387,83,391,92));
        //tricpes
        tricepsSquareList.add(new Square(76,111,85,133));
        tricepsSquareList.add(new Square(76,133,82,150));
        tricepsSquareList.add(new Square(183,111,194,121));
        tricepsSquareList.add(new Square(185,122,192,140));
        tricepsSquareList.add(new Square(297,107,315,143));
        tricepsSquareList.add(new Square(392,107,410,142));
        //upperback
        upperbackSquareList.add(new Square(315,95,391,106));
        upperbackSquareList.add(new Square(340,106,367,114));
        upperbackSquareList.add(new Square(329,76,380,95));
        upperbackSquareList.add(new Square(330,70,377,76));
        upperbackSquareList.add(new Square(337,62,371,70));
        upperbackSquareList.add(new Square(320,90,329,95));
        upperbackSquareList.add(new Square(380,89,386,94));
        upperbackSquareList.add(new Square(324,77,329,82));
        upperbackSquareList.add(new Square(380,77,388,82));
    }
}
