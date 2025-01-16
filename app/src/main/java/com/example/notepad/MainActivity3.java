package com.example.notepad;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.notepad.Database.Roomdb;
import com.example.notepad.Model.Notepad;
import androidx.appcompat.app.AppCompatDelegate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity3 extends AppCompatActivity {
 EditText text1,text2;
 Button button3;
 String originalTitle,originalContent,originalDate;
 private Roomdb db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        db= Roomdb.getInstance(getApplicationContext());
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        button3=findViewById(R.id.button3);

        Intent intent=getIntent();
        originalTitle=intent.getStringExtra("noteTitle");
        originalContent=intent.getStringExtra("noteContent");
        originalDate=intent.getStringExtra("noteDate");

        if (originalTitle != null && originalDate != null) {
            text1.setText(originalTitle);
            text2.setText(originalContent);
        }

        button3.setOnClickListener(v -> {
          insertNote();
            Intent intent1=new Intent(MainActivity3.this,MainActivity2.class);

                startActivity(intent1);
            });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
    private void insertNote(){
        String title = text1.getText().toString().trim();
        String content = text2.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and Content cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(() ->{
            if (originalTitle != null && originalDate != null) {
                db.maindao().update(title, date, content, originalTitle, originalDate);
            } else {
                Notepad newNote = new Notepad(title, content, date);
                db.maindao().insert(newNote);
            }

            runOnUiThread(() ->{
                Toast.makeText(MainActivity3.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
