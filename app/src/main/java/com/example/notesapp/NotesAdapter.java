package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.security.acl.NotOwnerException;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

        private final ArrayList<Note> noteList;
        private final Context mContext;            //Context is used for Toast or If we want to log something,A Context is a handle to the system; it provides services like resolving resources, obtaining access to databases and preferences, and so on. An Android app has activities. Context is like a handle to the environment your application is currently running in. The activity object inherits the Context object.


    //constructor
    public NotesAdapter(ArrayList<Note> notesList, Context mContext) {

        this.noteList=notesList;
        this.mContext=mContext;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle,noteContent,editNote,deleteNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            noteTitle=itemView.findViewById(R.id.noteTitle_TV);
            noteContent=itemView.findViewById(R.id.noteContent_TV);
            editNote=itemView.findViewById(R.id.editTV);
            deleteNote=itemView.findViewById(R.id.deleteTV);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View noteView=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);
       //with this view we are inflating adapter, from parent because any changes in the future in the activity will be updated. & in .inflate(takes xml file , parent means recycler view , attach to root is false because attach it once data is added, not when there is no data)

        ViewHolder viewHolder=new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      //TODO: we will set the data here

        //position starts from zero.So initially it will be zero.

        final Note note=noteList.get(position);

        holder.noteTitle.setText(note.getNoteTitle());     //we are getting the zeroth notelist note
        holder.noteContent.setText(note.getNoteContent()); //we are getting the zeroth notelist note
        holder.editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we will open edit note Activity here
                Intent editNoteIntent=new Intent(mContext,EditNoteActivity.class);

                 editNoteIntent.putExtra("noteTitle",note.getNoteTitle());
                 editNoteIntent.putExtra("noteContent",note.getNoteContent());   //putExtra adds extended data to the intent
                 editNoteIntent.putExtra("noteId",note.getNoteId());

                mContext.startActivity(editNoteIntent);


            }

        });
        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   showDeleteNoteDialog(note.getNoteId());

            }
        });

    }

    private void showDeleteNoteDialog(final String noteId) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure you want to delete the note?");
        builder.setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //delete the note here.
                ((HomeActivity) mContext).deletNoteFromFirebase(noteId); //HomeActivity.deleteNoteFromFirebase();

            }
        });
        builder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
      //TODO: put the dataset size here
        return noteList.size();
    }

}