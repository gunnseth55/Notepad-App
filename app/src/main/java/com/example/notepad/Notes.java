package com.example.notepad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notepad.Model.Notepad;

public class Notes extends AppCompatActivity {
TextView title,content;
Notepad notepad;
Button button6,button5;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);
        title=findViewById(R.id.titleid);
        content=findViewById(R.id.contentid);
        button6=findViewById(R.id.button6);
        button5=findViewById(R.id.button5);
        notepad = (Notepad) getIntent().getSerializableExtra("notepad");
        if(notepad!=null){
            title.setText(notepad.getTitle());
            content.setText(notepad.getContent());
        }
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notes.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(Notes.this,MainActivity3.class);
            intent.putExtra("noteTitle",notepad.getTitle());
            intent.putExtra("noteContent",notepad.getContent());
            intent.putExtra("noteDate",notepad.getDate());

                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}