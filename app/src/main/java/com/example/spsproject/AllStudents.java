package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllStudents extends AppCompatActivity {
    RecyclerView rv;
    List<StudentList> studentlist;
    String url = "https://sealed-helm.000webhostapp.com/admin/students/fetch.php";
    RecyclerView.Adapter adapter;
    Button add_std_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        rv = findViewById(R.id.std_recycle_list);
        add_std_btn = findViewById(R.id.add_std_btn);
        studentlist = new ArrayList<>();
        getData();
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        add_std_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_std_screen = new Intent(AllStudents.this, AddStudent.class);
                startActivity(add_std_screen);
            }
        });
    }

    public void getData() {
        StringRequest streq = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("students");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                StudentList studentList = new StudentList(
                                        jsonObject1.getString("id"),
                                        jsonObject1.getString("name"),
                                        jsonObject1.getString("email"),
                                        jsonObject1.getString("branch"),
                                        jsonObject1.getString("percentage")
                                );
                                studentlist.add(studentList);
                            }
                            adapter = new StudentCustomAdapter(AllStudents.this, studentlist);
                            rv.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue reque = Volley.newRequestQueue(AllStudents.this);
        reque.add(streq);
    }
}