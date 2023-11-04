package com.example.lab07b;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lab07b.db.Repository;
import com.example.lab07b.db.StudentEntity;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private Repository repository;
    private final LiveData<List<StudentEntity>> allStudent;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allStudent = repository.getAllStudent();
    }

    public LiveData<List<StudentEntity>> getAllStudent() {
        return allStudent;
    }

    public void insert(StudentEntity student) {
        repository.insert(student);
    }

    public void delete(StudentEntity student) {
        repository.delete(student);
    }
}
