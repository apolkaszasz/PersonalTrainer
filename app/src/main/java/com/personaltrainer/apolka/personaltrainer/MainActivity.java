package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    SignInButton signInButton;
    Button signOutButton;
    //TextView statusTextView;
    //ImageView  imageViewProfilePicture;
    GoogleApiClient mGoogleApiClient;
    FloatingActionButton GOButton;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private GoogleSignInClient mGoogleSignInClient;

    SharedPreferences sharedpreferences;
    public static final String userPreferences = "userPreferences" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth==null){

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                        sharedpreferences.edit().remove("UserID").commit();

                        Log.d(TAG,"................."+sharedpreferences.getString("UserID",null));
                        GOButton.setVisibility(View.INVISIBLE);
                    }
                });

        }


        sharedpreferences = getSharedPreferences(userPreferences, 0);



        GOButton = (FloatingActionButton) findViewById(R.id.Exer);
        GOButton.setVisibility(View.INVISIBLE);
        GOButton.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        //statusTextView = (TextView) findViewById(R.id.status_textview);
        //imageViewProfilePicture = (ImageView)findViewById(R.id.imageViewProfilePicture);
        //imageViewProfilePicture.setImageResource(R.drawable.user_icon);
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        signOutButton = (Button)findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);


        FirebaseUser user  = mFirebaseAuth.getCurrentUser();
        if(user!=null){
            //statusTextView.setText(user.getDisplayName());
            exerActivity();
        }else{
            mFirebaseAuth.signOut();
            mGoogleSignInClient.signOut();
            //mGoogleApiClient.clearDefaultAccountAndReconnect();

            //statusTextView.setText("Signed Out");
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    exerActivity();
                }else{

                }
            }
        };
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.signOutButton:
                //signOut();
                break;
            case R.id.Exer:
                exerActivity();
                break;
        }
    }
    private void exerActivity(){
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    public void firebaseAuthWithGoogle(final GoogleSignInAccount account){

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            SharedPreferences.Editor editor = sharedpreferences.edit();



                            Log.d(TAG,".............THEIDIS:.."+account.getId());
                            editor.putString("UserID", account.getId());
                            editor.commit();



                            Log.d(TAG,"the reference value is.............."+sharedpreferences.getString("UserID",null));

                            GOButton.setVisibility(View.VISIBLE);
                            if (account.getPhotoUrl().toString() != null){
                                //Glide.with(getApplicationContext()).load(account.getPhotoUrl().toString()).into(imageViewProfilePicture);
                            }
                            //else
                                //imageViewProfilePicture.setImageResource(R.drawable.user_icon);
                            //statusTextView.setText(account.getDisplayName())

                        } else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }


                    }
                });

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.d(TAG,"onConnectionFailed " + connectionResult);
    }


    @Override
    protected void onPause(){
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}
