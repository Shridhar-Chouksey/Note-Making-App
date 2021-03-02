package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNoteActivity extends AppCompatActivity {

         EditText noteTitleET,noteContentET;
         Button editNoteButton;
         String noteTitle,noteContent,noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

       noteTitleET=findViewById(R.id.noteTitle_ET);
       noteContentET=findViewById(R.id.noteContent_ET);

        editNoteButton=findViewById(R.id.editNote_btn);

       if(getIntent().getExtras()!=null)
       {

           noteTitle=getIntent().getStringExtra("noteTitle");           // Taken editText in the form of string from notesAdapter
           noteContent=getIntent().getStringExtra("noteContent");
           noteId=getIntent().getStringExtra("noteId");

           noteTitleET.setText(noteTitle);                                    //gave that string to the the editText
           noteContentET.setText(noteContent);

       }



       editNoteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               editNoteInFirebase();


           }
       });

    }

    private void editNoteInFirebase() {
          //TODO: put edit note method here
          //edit the note whose ID is noteId.
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rootReference=firebaseDatabase.getReference();

        DatabaseReference notesReference= rootReference.child("Users").child(currentUser.getUid()).child("Notes");

        DatabaseReference particularNoteReference=notesReference.child(noteId);

        particularNoteReference.child("noteTitle").setValue(noteTitleET.getText().toString());
        particularNoteReference.child("noteContent").setValue(noteContentET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){



                    //noteUpdated Successfully
                    Toast.makeText(EditNoteActivity.this,"Note Updated Successfully!",Toast.LENGTH_SHORT).show();


                }else{

                          Toast.makeText(EditNoteActivity.this,"Some error occured!",Toast.LENGTH_SHORT).show();


                }
            }
        });






    }
}
