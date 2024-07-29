package com.example.uas_moprog2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister, btnLogin;
    private ProgressBar progressBar;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                database = FirebaseDatabase.getInstance().getReference("users");

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressBar.setVisibility(View.GONE);
                            if (snapshot.child(username).exists()){
                                if (snapshot.child(username).child("password").getValue(String.class).equals(password)){
                                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                                    Intent masuk = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(masuk);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Data belum terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
