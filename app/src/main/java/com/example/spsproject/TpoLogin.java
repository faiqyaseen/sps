package com.example.spsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TpoLogin extends AppCompatActivity {

    ProgressDialog pdDialog;
    String URL_LOGIN = "https://sealed-helm.000webhostapp.com/tpo/login.php";
    String lemail,lpass;
    TextInputEditText email,password;
    Button loginButton;
    String is_signed_in="";
    SharedPreferences mPreferences;
    String sharedprofFile="user_data";
    SharedPreferences.Editor preferencesEditor;
    TextInputLayout lpassword_edit;
    TextInputLayout lemail_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_login);

        mPreferences=getSharedPreferences(sharedprofFile, MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        is_signed_in = mPreferences.getString("issignedin","false");

        if(is_signed_in.equals("true"))
        {
            String user_role = mPreferences.getString("user_role","false");
            Toast.makeText(TpoLogin.this, user_role, Toast.LENGTH_SHORT).show();
            Intent i;
            if (user_role.equals("1")) {
                i = new Intent(TpoLogin.this, AdminDashboard.class);
            } else if (user_role.equals("2")) {
                i = new Intent(TpoLogin.this, TpoDashboard.class);
            } else if (user_role.equals("3")) {
                i = new Intent(TpoLogin.this, TpoDashboard.class);
            } else {
                i = new Intent(TpoLogin.this, TpoDashboard.class);
            }
            startActivity(i);

            finish();
        }

        pdDialog= new ProgressDialog(TpoLogin.this);
        pdDialog.setMessage("Logging in please wait...");
        pdDialog.setCancelable(false);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        email = (TextInputEditText) findViewById(R.id.lemail);
        password = (TextInputEditText)findViewById(R.id.lpassword);
        lpassword_edit = (TextInputLayout) findViewById(R.id.lpassword_edit);
        lemail_edit = (TextInputLayout) findViewById(R.id.lemail_edit);
        loginButton=(Button) findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lemail = email.getText().toString().trim();
                lpass = password.getText().toString().trim();

                if (lpass.isEmpty()) {
                    lpassword_edit.setError("Password field should not be empty.");
                    lpassword_edit.setErrorEnabled(true);
                } else {
                    lpassword_edit.setErrorEnabled(false);
                }

                if (lemail.isEmpty()) {
                    lemail_edit.setError("Email field should not be empty.");
                    lemail_edit.setErrorEnabled(true);
                } else {
                    lemail_edit.setErrorEnabled(false);
                }

                if(!lemail.isEmpty() && !lpass.isEmpty()) {
                    Login();
                }
            }
        });
    }

    private void Login()
    {
        pdDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if(success.equals("1")){

                                String id= jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String username = jsonObject.getString("username");
                                String email = jsonObject.getString("email");

                                TastyToast.makeText(getApplicationContext(), "You Are Logged In Successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                pdDialog.dismiss();

                                preferencesEditor.putString("issignedin","true");
                                preferencesEditor.putString("SignedInUserID",id);
                                preferencesEditor.putString("SignedInName",name);
                                preferencesEditor.putString("SignedInUsername",username);
                                preferencesEditor.putString("SignedInemail",email);
                                preferencesEditor.putString("user_role","2");
                                preferencesEditor.apply();

                                Intent i = new Intent(TpoLogin.this, TpoDashboard.class);
                                startActivity(i);
                                finish();

                            }
                            if(success.equals("0")){
                                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                pdDialog.dismiss();
                            }
                            if(success.equals("3")){
                                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                pdDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            TastyToast.makeText(getApplicationContext(), "Something went wrong....", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Please check your interent connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Please check your interent connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Please check your interent connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_SHORT, TastyToast.INFO);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("email",lemail);
                params.put("password",lpass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
