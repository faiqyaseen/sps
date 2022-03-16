package com.example.spsproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TpoCustomAdapter extends RecyclerView.Adapter<TpoCustomAdapter.ViewHolder> {

    Context customAdapter;
    List<TpoList> list;
    String edid, edname, edusername, edemail;

    public TpoCustomAdapter(Context customAdapter, List<TpoList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.tpo_list_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TpoList mylist = list.get(position);
        holder.name.setText(mylist.getName());
        holder.username.setText(mylist.getUsername());
        holder.email.setText(mylist.getEmail());
//        holder.editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(customAdapter,EditUser.class);
//                intent.putExtra("id", mylist.getId());
//                intent.putExtra("name", mylist.getName());
//                intent.putExtra("username", mylist.getEmail());
//                intent.putExtra("email", mylist.getEmail());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                customAdapter.startActivity(intent);
//            }
//        });

//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                delete(mylist.getId());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, username, email;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_tpo_name);
            username = itemView.findViewById(R.id.tv_tpo_username);
            email = itemView.findViewById(R.id.tv_tpo_email);
//            editBtn = itemView.findViewById(R.id.editBtn);
//            deleteBtn = itemView.findViewById(R.id.deleteBtn);
//            editBtn.setBackgroundColor(Color.YELLOW);
//            editBtn.setTextColor(Color.BLACK);
//            deleteBtn.setBackgroundColor(Color.RED);
        }
    }
}
