package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailET,passwordET;

    private Button loginbtn;
    private TextView signUpText;
    ValidateInput validateInput;
    private String email,password;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start login part

        validateInput=new ValidateInput(this);
        emailET=findViewById(R.id.email_ET);
      passwordET=findViewById(R.id.password_ET);
      loginbtn=findViewById(R.id.login_btn);
      signUpText=findViewById(R.id.signUp_TV);

      loginbtn.setOnClickListener(this);
      signUpText.setOnClickListener(this);

      mAuth=FirebaseAuth.getInstance();
      progressBar=findViewById(R.id.progressBar);

       //end login part

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

         switch(id){

             case R.id.login_btn: {
                 handleLoginBtnClick();
                 break;
             }
             case R.id.signUp_TV: {
                 handleSignUpClick();
                 break;
             }


         }

    }

    private void handleLoginBtnClick() {
        //  TODO: Validate email and password fields input

        showProgressBar();
      email = emailET.getText().toString();
       password=passwordET.getText().toString();

         if(validateInput.checkIfEmailIsValid(email)&&validateInput.checkIfPasswordIsValid(password)){


             mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {
                          hideProgressBar();

                         Toast.makeText(MainActivity.this,"Logged in user "+email,Toast.LENGTH_SHORT).show();
                         //TODO:show another screen on login.See how to logout.

                         Intent userAccountActivity=new Intent(MainActivity.this,HomeActivity.class);
                         startActivity(userAccountActivity);


                     }
                     else {
                         hideProgressBar();
                         Toast.makeText(MainActivity.this,"Error occured "+task.getException(),Toast.LENGTH_SHORT).show();
                     }
                 }
             });
             //  TODO: Login the user with email and password.To do this firebase , login this user

         }



    }

    private void showProgressBar() {
   progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar() {
   progressBar.setVisibility(View.INVISIBLE);
    }


    private void handleSignUpClick() {
        Toast.makeText(this,"Sign up here",Toast.LENGTH_SHORT).show();
    Intent SignUpActivity=new Intent(MainActivity.this,SignUpActivity.class);
    startActivity(SignUpActivity);

    }
}
