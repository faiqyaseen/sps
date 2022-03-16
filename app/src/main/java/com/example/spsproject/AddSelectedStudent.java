package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddSelectedStudent extends AppCompatActivity {
    String URL_ADD = "https://sealed-helm.000webhostapp.com/tpo/company/create_job_descriptions.php";
    ProgressDialog pdDialog;
    Button btn_add;
    String jdesc ,jcompany;
    Spinner company_desc, student;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayAdapter<String> stringArrayAdapter2;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<String> arrayListId2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_student);

        company_desc = (Spinner) findViewById(R.id.company_desc);
        student = (Spinner) findViewById(R.id.student);
        arrayList.add("Select Company Description");
        arrayList2.add("Select Student");
        arrayListId.add("0");
        arrayListId2.add("0");
        getCompanies();
        getStudents();
        stringArrayAdapter = new ArrayAdapter<String>(AddSelectedStudent.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        stringArrayAdapter2 = new ArrayAdapter<String>(AddSelectedStudent.this, R.layout.support_simple_spinner_dropdown_item, arrayList2);
        company_desc.setAdapter(stringArrayAdapter);
        student.setAdapter(stringArrayAdapter2);

        pdDialog= new ProgressDialog(AddSelectedStudent.this);
        pdDialog.setMessage("Adding.....");
        pdDialog.setCancelable(false);

        btn_add = findViewById(R.id.btn_add_selstd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(company_desc.getSelectedItem()).equals("Select Company Description") || String.valueOf(student.getSelectedItem()).equals("Select Student") || arrayListId.get(company_desc.getSelectedItemPosition()).equals(0) || arrayListId2.get(student.getSelectedItemPosition()).equals(0)) {
                    Toast.makeText(AddSelectedStudent.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                } else {
                    String job_desc_id = arrayListId.get(company_desc.getSelectedItemPosition());
                    String std_id = arrayListId.get(student.getSelectedItemPosition());
                    Toast.makeText(AddSelectedStudent.this, std_id + " " + job_desc_id, Toast.LENGTH_LONG).show();
//                    selectStudent(job_desc_id, std_id);
                }
            }
        });

    }

    private void getCompanies() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://sealed-helm.000webhostapp.com/tpo/company/fetch_job_descriptions.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> arrayList1 = new ArrayList<>();
                ArrayList<String> arrayList3 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("descriptions");

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayList1.add(jsonObject1.getString("company_name") + " - " + jsonObject1.getString("description"));
                        arrayList3.add(jsonObject1.getString("id").toString());
                    }
                    arrayList.addAll(arrayList1);
                    arrayListId.addAll(arrayList3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(AddSelectedStudent.this);
        requestQueue.add(stringRequest);
    }

    private void getStudents() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://sealed-helm.000webhostapp.com/admin/students/fetch.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> arrayList4 = new ArrayList<>();
                ArrayList<String> arrayList5 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayList4.add(jsonObject1.getString("name") + " - " + jsonObject1.getString("percentage") + " - " + jsonObject1.getString("branch"));
                        arrayList5.add(jsonObject1.getString("id").toString());
                    }
                    arrayList2.addAll(arrayList4);
                    arrayListId2.addAll(arrayList5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(AddSelectedStudent.this);
        requestQueue.add(stringRequest);
    }
}