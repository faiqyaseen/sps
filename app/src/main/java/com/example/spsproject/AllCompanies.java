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

public class AllCompanies extends AppCompatActivity {
    RecyclerView rv;
    List<CompanyList> companylist;
    String url = "https://sealed-helm.000webhostapp.com/tpo/company/fetch.php";
    RecyclerView.Adapter adapter;
    Button add_cmp_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_companies);

        rv = findViewById(R.id.company_recycle_list);
        add_cmp_btn = findViewById(R.id.add_cmp_btn);
        companylist = new ArrayList<>();
        getData();
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        add_cmp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_cmp_screen = new Intent(AllCompanies.this, AddCompany.class);
                startActivity(add_cmp_screen);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("companies");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                CompanyList companyList = new CompanyList(
                                        jsonObject1.getString("id"),
                                        jsonObject1.getString("name"),
                                        jsonObject1.getString("location")
                                );
                                companylist.add(companyList);
                            }
                            adapter = new CompanyCustomAdapter(AllCompanies.this, companylist);
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

        RequestQueue reque = Volley.newRequestQueue(AllCompanies.this);
        reque.add(streq);
    }
}