package com.example.lab07b;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lab07b.db.StudentEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ObservedStudent {
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_EDIT = 1;
    public static final int REQUEST_CODE = 123;

    private RecyclerView rcv;
    private RecyclerAdapter adapter;
    private StudentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv = findViewById(R.id.rcv);
        adapter = new RecyclerAdapter(new RecyclerAdapter.StudentDiff(), this, this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        viewModel.getAllStudent().observe(this, studentEntities -> adapter.submitList(studentEntities));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            startAddActivity(null);
            return true;
        }
        return false;
    }

    @Override
    public void observedStudent(StudentEntity student, int actionId) {
        if (actionId == ACTION_DELETE) {
            viewModel.delete(student);
        }
        if (actionId == ACTION_EDIT) {
            startAddActivity(student);
        }
    }

    private void startAddActivity(StudentEntity student) {
        Intent intent = new Intent(this, AddStudentActivity.class);
        if (student != null) {
            intent.putExtra("id", student.getId());
            intent.putExtra("name", student.getName());
            intent.putExtra("email", student.getEmail());
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.insert(new StudentEntity(data.getIntExtra("id", 0),
                        data.getStringExtra("name"),
                        data.getStringExtra("email")));
            }
        }
    }
}