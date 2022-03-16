package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

public class AddJobDescription extends AppCompatActivity {
    String URL_ADD = "https://sealed-helm.000webhostapp.com/tpo/company/create_job_descriptions.php";
    ProgressDialog pdDialog;
    Button btn_add;
    String jdesc ,jcompany;
    TextInputEditText desc;
    Spinner company;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_description);

        company = (Spinner) findViewById(R.id.company);
        arrayList.add("Select Company");
        arrayListId.add("0");
        getCompanies();
        stringArrayAdapter = new ArrayAdapter<String>(AddJobDescription.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        company.setAdapter(stringArrayAdapter);

        pdDialog= new ProgressDialog(AddJobDescription.this);
        pdDialog.setMessage("Adding.....");
        pdDialog.setCancelable(false);

        desc = (TextInputEditText)findViewById(R.id.description);

        btn_add = findViewById(R.id.btn_add_job);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jdesc = desc.getText().toString().trim();
                if(jdesc.isEmpty() || String.valueOf(company.getSelectedItem()).equals("Select Company") || arrayListId.get(company.getSelectedItemPosition()).equals(0)) {
                    Toast.makeText(AddJobDescription.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                } else {
                    String company_id = arrayListId.get(company.getSelectedItemPosition());
                    addJobDescription(company_id, jdesc);
                }
            }
        });
    }

    public void addJobDescription(String company_id, String jdesc) {
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
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();

                                Intent all_jdesc_screen = new Intent(AddJobDescription.this, AllJobDescriptions.class);
                                startActivity(all_jdesc_screen);
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

                params.put("description", jdesc);
                params.put("company_id", company_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getCompanies() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://sealed-helm.000webhostapp.com/tpo/company/fetch.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    ArrayList<String> arrayList1 = new ArrayList<>();
                    ArrayList<String> arrayList2 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("companies");

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayList1.add(jsonObject1.getString("name"));
                        arrayList2.add(jsonObject1.getString("id").toString());
                    }
                    arrayList.addAll(arrayList1);
                    arrayListId.addAll(arrayList2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(AddJobDescription.this);
        requestQueue.add(stringRequest);
    }
}