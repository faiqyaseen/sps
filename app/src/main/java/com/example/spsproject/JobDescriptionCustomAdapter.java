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

public class JobDescriptionCustomAdapter extends RecyclerView.Adapter<JobDescriptionCustomAdapter.ViewHolder>{
    Context customAdapter;
    List<JobDescriptionList> list;

    public JobDescriptionCustomAdapter(Context customAdapter, List<JobDescriptionList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }

    @NonNull
    @Override
    public JobDescriptionCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.job_description_list_data, parent, false);
        return new JobDescriptionCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDescriptionCustomAdapter.ViewHolder holder, int position) {
        JobDescriptionList mylist = list.get(position);
        holder.description.setText(mylist.getDescription());
        holder.company.setText(mylist.getCompany());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, company;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_jdesc_desc);
            company = itemView.findViewById(R.id.tv_jdesc_company);
        }
    }
}
