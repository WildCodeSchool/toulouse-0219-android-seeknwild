package fr.wildcodeschool.seeknwild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_accueil);

        RecyclerView rvStudents = findViewById(R.id.rvStudent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvStudents.setLayoutManager(layoutManager);

        final ArrayList<StudentModel> students = new ArrayList<>();
        final AdapterStudentList adapter = new AdapterStudentList(students);
        rvStudents.setAdapter(adapter);


    }
}
