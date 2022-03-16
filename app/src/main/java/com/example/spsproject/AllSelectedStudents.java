package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class AllSelectedStudents extends AppCompatActivity {
    RecyclerView rv;
    List<SelectedStudentsList> selstdlist;
    String url = "https://sealed-helm.000webhostapp.com/tpo/notifications/fetch.php";
    RecyclerView.Adapter adapter;
    Button add_selstd_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_selected_students);

        rv = findViewById(R.id.selstd_recycle_list);
        add_selstd_btn = findViewById(R.id.add_selstd_btn);
        selstdlist = new ArrayList<>();
        getData();
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        add_selstd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_selstd_screen = new Intent(AllSelectedStudents.this, AddSelectedStudent.class);
                startActivity(add_selstd_screen);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("selected_students");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                SelectedStudentsList selStdList = new SelectedStudentsList(
                                        jsonObject1.getString("company_name"),
                                        jsonObject1.getString("company_name"),
                                        jsonObject1.getString("company_location"),
                                        jsonObject1.getString("job_description"),
                                        jsonObject1.getString("student_name"),
                                        jsonObject1.getString("student_branch"),
                                        jsonObject1.getString("student_percentage"),
                                        jsonObject1.getString("student_email")
                                );
                                selstdlist.add(selStdList);
                            }
                            adapter = new SelectedStudentCustomAdapter(AllSelectedStudents.this, selstdlist);
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

        RequestQueue reque = Volley.newRequestQueue(AllSelectedStudents.this);
        reque.add(streq);
    }
}