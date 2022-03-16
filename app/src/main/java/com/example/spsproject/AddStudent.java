package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String URL_ADD = "https://sealed-helm.000webhostapp.com/admin/students/create.php";
    ProgressDialog pdDialog;
    Button btn_add;
    String sname ,semail, sbranch, spercentage;
    TextInputEditText name, email, percentage;
    Spinner branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        branch = (Spinner) findViewById(R.id.sbranch);
        branchSpinner();

        pdDialog= new ProgressDialog(AddStudent.this);
        pdDialog.setMessage("Adding.....");
        pdDialog.setCancelable(false);

        name = (TextInputEditText)findViewById(R.id.sname);
        email = (TextInputEditText) findViewById(R.id.semail);
        percentage = (TextInputEditText) findViewById(R.id.spercentage);
        branch = (Spinner) findViewById(R.id.sbranch);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sname = name.getText().toString().trim();
                semail = email.getText().toString().trim();
                spercentage = percentage.getText().toString().trim();
                if(sname.isEmpty() || semail.isEmpty() || spercentage.isEmpty() || sbranch.equals("Select Branch")) {
                    Toast.makeText(AddStudent.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                } else {
                    addStudent();
                }
            }
        });
    }

    public void addStudent() {
        pdDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if(success.equals("1")){

                                String getPassword = jsonObject.getString("password");

                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                                String mailSubject = "Dear " + sname + " Your Account has been created";
                                String mailMessage = "Dear " + sname + " Your Account has been created Your login email is "+ semail + " And Password is " + getPassword;
                                sendEmail(semail, mailSubject, mailMessage);
                                Intent all_std_screen = new Intent(AddStudent.this, AllStudents.class);
                                startActivity(all_std_screen);
                                finish();
                            }
                            if(success.equals("0")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }
                            if(success.equals("3")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error In Adding !1"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error In Adding !2"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("name",sname);
                params.put("email",semail);
                params.put("percentage",spercentage);
                params.put("branch",sbranch);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void branchSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branches_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(adapter);
        branch.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sbranch = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("LongLogTag")
    protected void sendEmail(String emailTo, String mailSubject, String mailMessage) {
        Log.i("Send email", "");
        String[] TO = {emailTo};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mailMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddStudent.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}