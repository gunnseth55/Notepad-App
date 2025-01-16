package com.example.notepad.Interface;

import androidx.cardview.widget.CardView;

import com.example.notepad.Model.Notepad;

public interface NotesClickListener {
    void onClick(Notepad note);
    void onLongPress(Notepad note, CardView card);
}
