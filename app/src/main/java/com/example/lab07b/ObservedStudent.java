package com.example.lab07b;

import com.example.lab07b.db.StudentEntity;

public interface ObservedStudent {
    public void observedStudent(StudentEntity student, int actionId);
}
