package com.personaltrainer.apolka.personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.TextView;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;

/**
 * TODO Matyas: ha megnezed, azokban a kigeneralt Fragmensekben van egy newInstance metodus. A lenyege, hogy az adott osztalyban
 * egy statikus metodus hozza letre a peldanyt. Ennek az az ertelme, hogy egy helyen tudod tartani a peldany letrehozasat es az
 * intentben szereplo key is csak 1 helyen szerepel. Pl. itt egy konstansba kitudod menteni ezt az "EcerciseObject"-et es azt tudod hasznalni
 * a letrehozaskor es a kiolvasaskor is, ezzel elkerulve azt, hogy egyik helyen ha elirod, akkor nem fog mukodni.
 *
 * private static final KEY = "key"
 * public static Intent newInstance() {
 *     ...
 *     intent.putExtra(KEY, valami)
 * }
 *
 * s az onCreatben pedig intent.getExtra(KEY) as ...
 */
public class ExerciseItem extends AppCompatActivity {

    private static final String TAG = "ExerciseItem";
    private TextView NameTextView;
    private TextView MuscleTextView;
    private TextView RecRepTextViewNumber;
    private TextView RecSetsTextViewNumber;
    private TextView DescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_item);

        Intent i = getIntent();
        Exercise exercise = (Exercise) i.getSerializableExtra("EcerciseObject");

        NameTextView = (TextView)findViewById(R.id.ExerciseNameTextView);
        NameTextView.setText(exercise.getName());

        MuscleTextView =(TextView)findViewById(R.id.ExerceMuscleTextView);
        MuscleTextView.setText(exercise.getMuscle());

        RecRepTextViewNumber=(TextView)findViewById(R.id.ExerciseRecRepNumber);
        RecRepTextViewNumber.setText(exercise.getRecommendedRepetitions()+"");

        RecSetsTextViewNumber = (TextView)findViewById(R.id.ExerciseRecSetsNumber);
        RecSetsTextViewNumber.setText(exercise.getRecommendedSets()+"");

        DescriptionTextView = (TextView)findViewById(R.id.DescriptionValue);
        DescriptionTextView.setText(exercise.getDescription());

    }
}
