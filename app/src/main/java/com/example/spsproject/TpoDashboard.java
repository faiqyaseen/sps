package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
public class TpoDashboard extends AppCompatActivity {
    SharedPreferences mPreferences;
    String sharedprofFile="user_data";
    SharedPreferences.Editor preferencesEditor;
    String id, name, email;
    String is_signed_in="";
    String user_role;
    TextView Signedinusername;
    ProgressDialog pdDialog;
    CardView companies, past_papers, logout, select_students, job_descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_dashboard);
        pdDialog= new ProgressDialog(TpoDashboard.this);
        pdDialog.setMessage("Logging out...");
        pdDialog.setCancelable(false);

        mPreferences = getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        is_signed_in = mPreferences.getString("issignedin","false");
        user_role = mPreferences.getString("user_role","false");

        if(!is_signed_in.equals("true") || !user_role.equals("2"))
        {
            Intent i = new Intent(TpoDashboard.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            finish();
        }

        logout = findViewById(R.id.logout);
        companies = findViewById(R.id.companies);
//        past_papers = findViewById(R.id.pastpapers);
        job_descriptions = findViewById(R.id.job_descriptions);
        select_students = findViewById(R.id.select_std);
        Signedinusername = (TextView)findViewById(R.id.signinusername);

        id = mPreferences.getString("SignedInUserID","null");
        name = mPreferences.getString("SignedInName","null");
        email = mPreferences.getString("SignedInemail","null");
        email = mPreferences.getString("SignedInUsername","null");

        Signedinusername.setText("Hello, " + name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialog.show();
                preferencesEditor.putString("issignedin","false");
                preferencesEditor.apply();
                TastyToast.makeText(getApplicationContext(), "Logged Out Successfully", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
                Intent loginscreen = new Intent(TpoDashboard.this, MainActivity.class);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pdDialog.dismiss();
                startActivity(loginscreen);
            }
        });

        companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent company_screen = new Intent(TpoDashboard.this, AllCompanies.class);
                startActivity(company_screen);
            }
        });

        job_descriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jdesc_screen = new Intent(TpoDashboard.this, AllJobDescriptions.class);
                startActivity(jdesc_screen);
            }
        });

//        past_papers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tpo_screen = new Intent(TpoDashboard.this, AllTpos.class);
//                startActivity(tpo_screen);
//            }
//        });

        select_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selstd_screen = new Intent(TpoDashboard.this, AllSelectedStudents.class);
                startActivity(selstd_screen);
            }
        });
    }
}