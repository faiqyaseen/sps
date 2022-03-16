package com.example.spsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompanyCustomAdapter  extends RecyclerView.Adapter<CompanyCustomAdapter.ViewHolder>{
    Context customAdapter;
    List<CompanyList> list;

    public CompanyCustomAdapter(Context customAdapter, List<CompanyList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }

    @NonNull
    @Override
    public CompanyCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.company_list_data, parent, false);
        return new CompanyCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyCustomAdapter.ViewHolder holder, int position) {
        CompanyList mylist = list.get(position);
        holder.name.setText(mylist.getName());
        holder.location.setText(mylist.getLocation());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, location;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_cmp_name);
            location = itemView.findViewById(R.id.tv_cmp_location);
        }
    }
}
