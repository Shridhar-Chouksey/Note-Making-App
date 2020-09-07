package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText emailET, passwordET, passwordAgainET, nameET;
    ValidateInput validateInput;
    private String email, password, passwordAgain;
    Button signUpBtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar2;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Start signup
        emailET = findViewById(R.id.email_ET);
        passwordET = findViewById(R.id.password_ET);
        passwordAgainET = findViewById(R.id.password_Again);
        validateInput = new ValidateInput(this);

        signUpBtn = findViewById(R.id.signUp_btn);

        mAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: SIGN UP THE USER
                handleSignUpBtnClick();

            }


        });

        progressBar2 = findViewById(R.id.progressBar2);
        nameET = findViewById(R.id.name_ET);
        //End signup

    }

    private void handleSignUpBtnClick() {
        showProgressBar();

        //Fetching the string values

        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        passwordAgain = passwordAgainET.getText().toString();
        name = nameET.getText().toString();

        if (!name.isEmpty()) {         //name is not empty ,sign up the user
            if (validateInput.checkIfEmailIsValid(email) && validateInput.checkIfPasswordIsValid(password)) //validate the input
            {
                if (password.equals(passwordAgain)) {
                    //sign up user with this email and password
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //this is triggered ,when the user is created in firebase auth

                            if (task.isSuccessful()) {
                                hideProgressBar();
                                //firebase user object has email,password and other profile info inside this object
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignUpActivity.this, "Sign up is successful for  " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                savenameinFirebaseRealtimeDatabase(user);

                            } else {
                                hideProgressBar();
                                Toast.makeText(SignUpActivity.this, "Error occured " + task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else {
                    hideProgressBar();
                    Toast.makeText(this, "Passwords don't match.Please enter again", Toast.LENGTH_SHORT).show();
                }

            }

        } else{
            hideProgressBar();
            //name is Empty
            Toast.makeText(this,"Please enter a name",Toast.LENGTH_SHORT).show();
        }


    }

    private void savenameinFirebaseRealtimeDatabase(FirebaseUser user) {
     //firebase method here.

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference rootReference=firebaseDatabase.getReference();

        DatabaseReference nameReference=rootReference.child("Users").child(user.getUid()).child("name");

        nameReference.setValue(name);






    }

    private void hideProgressBar() {

        progressBar2.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {

        progressBar2.setVisibility(View.VISIBLE);
    }

}
