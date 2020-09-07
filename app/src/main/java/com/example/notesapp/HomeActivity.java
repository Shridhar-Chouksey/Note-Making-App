package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeMessageTV;
    private Button createNoteBtn;
    private RecyclerView recyclerView;
    ArrayList<Note> noteArrayList = new ArrayList<>();
    NotesAdapter notesAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;

        //get the name of the user in the welcome message

        welcomeMessageTV = findViewById(R.id.welcomeMessage_TV);
        createNoteBtn = findViewById(R.id.createNote_btn);

        //get the name of the user in the firebase

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference nameReference = rootReference.child("Users").child(currentUser.getUid()).child("name");
        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {  //this will be triggered atleast once
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                welcomeMessageTV.setText("Hi, welcome " + snapshot.getValue().toString() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateNoteActivity();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //TODO: create and then set adapter after getting the data


        //get the notes from firebase
        readnotesfromFirebase();


    }

    private void readnotesfromFirebase() {
        //read the notes in firebase database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference notesReference = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Notes");

        notesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for this example , dataSnapshot will contain: datasnapshot:{ NoteID1: {noteTitle:"some title" noteContent:"someContent"}, NoteID2: {noteTitle:"some title" noteContent:"someContent"}

                noteArrayList.clear();
                Note note;
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    note = noteDataSnapshot.getValue(Note.class);
                    note.setNoteId(noteDataSnapshot.getKey());

                    Toast.makeText(HomeActivity.this, "note: Title: " + note.getNoteTitle() + " ,content: " + note.getNoteContent() + " note key/ID " + note.getNoteId(), Toast.LENGTH_SHORT).show();
                    Log.i("mynote", "note : title: " + note.getNoteTitle() + " , content: " + note.getNoteContent() + " note key/ID " + note.getNoteId());

                    //add note the arrayList of notes
                    noteArrayList.add(note);

                }


                //    printNoteList(noteArrayList);

                //Todo:setup layout

                notesAdapter = new NotesAdapter(noteArrayList, mContext);
                recyclerView.setAdapter(notesAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(HomeActivity.this, "some error occured.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void printNoteList(ArrayList<Note> noteArrayList) {
        for (Note note : noteArrayList) {

            Log.i("mynote ", "title " + note.getNoteTitle() + "content: " + note.getNoteContent());


        }


    }

    private void openCreateNoteActivity() {

        //TODO:open createNoteActivity here
        Intent createNoteIntent = new Intent(HomeActivity.this, CreateNoteActivity.class);
        startActivity(createNoteIntent);

    }

    public void deletNoteFromFirebase(String noteId) {
        //delete the note in Firebase;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference notesReference = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Notes");

        DatabaseReference particularNote = notesReference.child(noteId);

        //delete method in Firebase

     particularNote.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {

             if(task.isSuccessful()){
                 Toast.makeText(mContext, "Note is deleted, successfully!", Toast.LENGTH_SHORT).show();

             }
             else
             {
                 Toast.makeText(mContext, "Some error occurred!", Toast.LENGTH_SHORT).show();
             }




         }
     });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.useraccount:
                Intent userAccountIntent = new Intent(HomeActivity.this,UserAccountActivity.class);
                startActivity(userAccountIntent);
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                Intent loginIntent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(loginIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}