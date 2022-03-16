package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class AddCompany extends AppCompatActivity {
    String URL_ADD = "https://sealed-helm.000webhostapp.com/tpo/company/create.php";
    ProgressDialog pdDialog;
    Button btn_add;
    String cname ,clocation;
    TextInputEditText name, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        pdDialog= new ProgressDialog(AddCompany.this);
        pdDialog.setMessage("Adding.....");
        pdDialog.setCancelable(false);

        name = (TextInputEditText)findViewById(R.id.cname);
        location = (TextInputEditText)findViewById(R.id.clocation);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cname = name.getText().toString().trim();
                clocation = location.getText().toString().trim();
                if(cname.isEmpty() || clocation.isEmpty()) {
                    Toast.makeText(AddCompany.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                } else {
                    addCompany();
                }
            }
        });
    }

    public void addCompany() {
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
                                Intent all_cmp_screen = new Intent(AddCompany.this, AllCompanies.class);
                                startActivity(all_cmp_screen);
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

                params.put("name", cname);
                params.put("location", clocation);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}