package com.example.notesapp;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class ValidateInput {


    Context context;

    ValidateInput(Context context){
        this.context=context;
    }


    boolean checkIfEmailIsValid(String email)
 {
  //    validate the email
  if(email.length()==0){
      Toast.makeText(context, "Please enter your email id",Toast.LENGTH_SHORT).show();
     return false;

  }

  else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
  {
   Toast.makeText(context,"Please enter a valid email id",Toast.LENGTH_SHORT).show();
   return false;

  }
else{
    return true;
  }

  }

  // Method 2: validate the password

   boolean checkIfPasswordIsValid(String password){
    {
        //    validate the email
        if(password.length()==0){
            Toast.makeText(context, "Please enter your password",Toast.LENGTH_SHORT).show();
            return false;

        }

        else if(password.length()<6)
        {
            Toast.makeText(context,"Please enter a valid password",Toast.LENGTH_SHORT).show();
            return false;

        }
        else{
            return true;
        }

    }




}




}
