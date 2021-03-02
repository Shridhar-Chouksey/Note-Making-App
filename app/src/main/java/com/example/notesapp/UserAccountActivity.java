package com.example.notesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView welcomeMessageTV;
    private Button logoutBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        welcomeMessageTV=findViewById(R.id.welcomeMessage_TV);
        logoutBtn=findViewById(R.id.logout_btn);

        firebaseAuth=firebaseAuth.getInstance();

        user=firebaseAuth.getCurrentUser();
        welcomeMessageTV.setText("Hi "+user.getEmail()+"!");
        logoutBtn.setOnClickListener(this);
        context=this;

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        switch(id){

            case R.id.logout_btn:
            //Logout here.

               showLogoutDialog();

               break;

        }

    }

    private void showLogoutDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
       builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes,logout", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
                   //logout here
               firebaseAuth.signOut();                   //end the session
               ((Activity)context).finish();             // end the current activity ,bcz now i don't want to see this screen ,after logout i again want to see login screen.

           }
       }).setNegativeButton("No!", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

              dialogInterface.dismiss();
           }
       });

       AlertDialog alertDialog=builder.create();
       alertDialog.show();


    }
}
