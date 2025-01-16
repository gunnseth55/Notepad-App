package com.example.notepad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.Interface.NotesClickListener;
import com.example.notepad.Model.Notepad;

import java.util.ArrayList;
import java.util.List;

public class notepadAdapter extends RecyclerView.Adapter<notepadAdapter.ViewHolder> {
    @NonNull
     private Context context;
     private List<Notepad> notes;
      private NotesClickListener notesClickListener;

    public notepadAdapter(@NonNull Context context, List<Notepad> notes, NotesClickListener notesClickListener) {
        this.context = context;
        this.notes = notes;
        this.notesClickListener = notesClickListener;
    }



    @Override
    public notepadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull notepadAdapter.ViewHolder holder, int position) {
        Notepad notepad=notes.get(position);
        holder.titleTextView.setText(notepad.getTitle());
        holder.contentTextView.setText(notepad.getContent());
        holder.dateTextView.setText(notepad.getDate());
        holder.dateTextView.setSelected(true);

        holder.cardview.setOnClickListener(v -> notesClickListener.onClick(notepad));
        holder.cardview.setOnLongClickListener(v ->{
            Log.d("onLongClick","on long click gunnu lets delete it");
            notesClickListener.onLongPress(notepad,holder.cardview);
            return true;
                });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView  titleTextView, contentTextView,dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview=itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleid);
            contentTextView = itemView.findViewById(R.id.contentid);
            dateTextView=itemView.findViewById(R.id.date);
        }
    }
}
