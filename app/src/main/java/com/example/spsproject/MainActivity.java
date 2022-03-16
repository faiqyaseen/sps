package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String is_signed_in="";
    SharedPreferences mPreferences;
    String sharedprofFile="user_data";
    SharedPreferences.Editor preferencesEditor;
    Button admin_login, student_login, tpo_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences=getSharedPreferences(sharedprofFile, MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        is_signed_in = mPreferences.getString("issignedin","false");

        if(is_signed_in.equals("true"))
        {
            String user_role = mPreferences.getString("user_role","false");
            Intent i;
            if (user_role.equals("1")) {
                i = new Intent(MainActivity.this, AdminDashboard.class);
            } else if (user_role.equals("2")) {
                i = new Intent(MainActivity.this, TpoDashboard.class);
            } else if (user_role.equals("3")) {
                i = new Intent(MainActivity.this, TpoDashboard.class);
            } else {
                i = new Intent(MainActivity.this, TpoDashboard.class);
            }
            startActivity(i);

            finish();
        }

        admin_login = findViewById(R.id.admin_login);
//        student_login = findViewById(R.id.student_login);
        tpo_login = findViewById(R.id.tpo_login);
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(intent);
            }
        });

//        student_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, StudentLogin.class);
//                startActivity(intent);
//            }
//        });

        tpo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TpoLogin.class);
                startActivity(intent);
            }
        });
    }
}