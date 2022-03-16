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
public class StudentCustomAdapter  extends RecyclerView.Adapter<StudentCustomAdapter.ViewHolder> {
    Context customAdapter;
    List<StudentList> list;
    String edid, edname, edemail;

    public StudentCustomAdapter(Context customAdapter, List<StudentList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.student_list_data, parent, false);
        return new StudentCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentList mylist = list.get(position);
        holder.name.setText(mylist.getName());
        holder.email.setText(mylist.getEmail());
        holder.percentage.setText(mylist.getPercentage());
        holder.branch.setText(mylist.getBranch());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, percentage, branch;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_std_name);
            email = itemView.findViewById(R.id.tv_std_email);
            branch = itemView.findViewById(R.id.tv_std_branch);
            percentage = itemView.findViewById(R.id.tv_std_percentage);
        }
    }
}
