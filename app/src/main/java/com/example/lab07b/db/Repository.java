package com.example.lab07b.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository{
    private StudentDao studentDao;

    private LiveData<List<StudentEntity>> allStudent;

    public Repository(Application application){
        RoomDB db = RoomDB.getDataBase(application);
        studentDao = db.studentDao();
        allStudent = studentDao.getAllStudent();
    }
    public LiveData<List<StudentEntity>> getAllStudent() {
        return allStudent;
    }
    public void insert(StudentEntity student){
        RoomDB.databaseWriteExecutor.execute(() -> {
            studentDao.insert(student);
        });
    }

    public void delete(StudentEntity student){
        RoomDB.databaseWriteExecutor.execute(() -> {
            studentDao.delete(student);
        });
    }
}
