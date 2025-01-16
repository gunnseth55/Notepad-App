package com.example.notepad;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.notepad.Database.Roomdb;
import com.example.notepad.Interface.NotesClickListener;
import com.example.notepad.Model.Notepad;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {
    public RecyclerView recyclerView;
    Roomdb roomdb;
    List<Notepad> notepad = new ArrayList<>();
    public notepadAdapter adapter;
    Button button;

    public final ActivityResultLauncher<Intent> startForResult= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result->{
                if(result.getResultCode()==Activity.RESULT_OK){
                    Intent data=result.getData();
                    if(data!=null && data.hasExtra("notepad")){
                        Notepad notepad1 = (Notepad) data.getSerializableExtra("notepad");
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> {
                            roomdb.maindao().insert(notepad1);
                        });
                    }
                }
            });


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyclerView);
        roomdb = Roomdb.getInstance(this);
        button=findViewById(R.id.back);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);
        adapter=new notepadAdapter(MainActivity2.this,notepad,notesClickListener);
        recyclerView.setAdapter(adapter);


        roomdb.maindao().getAllUsers().observe(this, notepadList->{
            Log.d("Database", "Fetched notes: " + notepadList.size());
            notepad.clear();
            notepad.addAll(notepadList);
            adapter.notifyDataSetChanged();
        });
        button.setOnClickListener(v ->{
            Intent intent=new Intent(MainActivity2.this,MainActivity.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notepad notepad) {
            Log.d("onClick","on clicking from cardview");
            Intent intent = new Intent(MainActivity2.this, Notes.class);
            intent.putExtra("notepad", notepad);
            startActivity(intent);
        }

        @Override
        public void onLongPress(Notepad note, CardView cardView) {
            new AlertDialog.Builder(MainActivity2.this)
                    .setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes",(dialog, which) -> {
                        if (note == null || roomdb == null) {
                            Toast.makeText(MainActivity2.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                        Executor executor=Executors.newSingleThreadExecutor();
                        executor.execute(()->{
                                    roomdb.maindao().delete(note);
                                    runOnUiThread(()->{
                                        adapter.notifyDataSetChanged();;
                                        Toast.makeText(MainActivity2.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    });
                                });
                    })
                    .setNegativeButton("No",null)
                    .show();
        }
    };
}

