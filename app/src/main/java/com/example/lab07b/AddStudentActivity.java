package com.example.lab07b;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddStudentActivity extends AppCompatActivity {

    private TextInputEditText edtName, edtEmail;
    private Button btnSave;
    private Intent startIntent, returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        btnSave = findViewById(R.id.btn_save);
        Intent startIntent = getIntent();
        returnIntent = new Intent();

        int id = startIntent.getIntExtra("id", 0);
        String name = startIntent.getStringExtra("name");
        String email = startIntent.getStringExtra("email");
        if (id != 0 || name != null || email != null) {
            returnIntent.putExtra("id", id);
            edtName.setText(startIntent.getStringExtra("name"));
            edtEmail.setText(startIntent.getStringExtra("email"));
        }
    }

    public void onClick(View view) {
        returnIntent.putExtra("name", edtName.getText().toString())
                .putExtra("email", edtEmail.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}