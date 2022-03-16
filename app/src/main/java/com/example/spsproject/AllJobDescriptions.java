package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.List;

public class AllJobDescriptions extends AppCompatActivity {
    RecyclerView rv;
    List<JobDescriptionList> jobescriptionist;
    String url = "https://sealed-helm.000webhostapp.com/tpo/company/fetch_job_descriptions.php";
    RecyclerView.Adapter adapter;
    Button add_jdesc_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job_descriptions);

        rv = findViewById(R.id.jdesc_recycle_list);
        add_jdesc_btn = findViewById(R.id.add_jdesc_btn);
        jobescriptionist = new ArrayList<>();
        getData();
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        add_jdesc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_jdesc_screen = new Intent(AllJobDescriptions.this, AddJobDescription.class);
                startActivity(add_jdesc_screen);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("descriptions");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JobDescriptionList jobDescriptionList = new JobDescriptionList(
                                        jsonObject1.getString("id"),
                                        jsonObject1.getString("description"),
                                        jsonObject1.getString("company_name")
                                );
                                jobescriptionist.add(jobDescriptionList);
                            }
                            adapter = new JobDescriptionCustomAdapter(AllJobDescriptions.this, jobescriptionist);
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

        RequestQueue reque = Volley.newRequestQueue(AllJobDescriptions.this);
        reque.add(streq);
    }
}