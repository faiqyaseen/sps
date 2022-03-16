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

public class AllTpos extends AppCompatActivity {
    RecyclerView rv;
    List<TpoList> tpolist;
    String url = "https://sealed-helm.000webhostapp.com/admin/tpo/fetch.php";
    RecyclerView.Adapter adapter;
    Button add_tpo_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tpos);

        rv = findViewById(R.id.tpo_recycle_list);
        add_tpo_btn = findViewById(R.id.add_tpo_btn);
        tpolist = new ArrayList<>();
        getData();
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        add_tpo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_tpo_screen = new Intent(AllTpos.this, AddTpo.class);
                startActivity(add_tpo_screen);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("tpos");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                TpoList tpoList = new TpoList(
                                        jsonObject1.getString("id"),
                                        jsonObject1.getString("name"),
                                        jsonObject1.getString("username"),
                                        jsonObject1.getString("email")
                                );
                                tpolist.add(tpoList);
                            }
                            adapter = new TpoCustomAdapter(AllTpos.this, tpolist);
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

        RequestQueue reque = Volley.newRequestQueue(AllTpos.this);
        reque.add(streq);
    }
}
