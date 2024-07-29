package com.example.uas_moprog2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    private DatabaseReference database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://uas-moprog2-default-rtdb.firebaseio.com/");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    database = FirebaseDatabase.getInstance().getReference("users");
                    database.child(username).child("username").setValue(username);
                    database.child(username).child("email").setValue(email);
                    database.child(username).child("password").setValue(password);

                    Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Intent register = new Intent(getApplicationContext(), Login.class);
                    startActivity(register);
                }
            }
        });
    }
}
