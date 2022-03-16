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

public class SelectedStudentCustomAdapter extends RecyclerView.Adapter<SelectedStudentCustomAdapter.ViewHolder>{
    Context customAdapter;
    List<SelectedStudentsList> list;
    public SelectedStudentCustomAdapter(Context customAdapter, List<SelectedStudentsList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }

    @NonNull
    @Override
    public SelectedStudentCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.selected_students_list_data, parent, false);
        return new SelectedStudentCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStudentCustomAdapter.ViewHolder holder, int position) {
        SelectedStudentsList mylist = list.get(position);
        holder.company_name.setText(mylist.getCompanyName());
        holder.company_location.setText(mylist.getCompanyLocation());
        holder.job_description.setText(mylist.getJobDescription());
        holder.student_name.setText(mylist.getStudentName());
        holder.student_branch.setText(mylist.getStudentBranch());
        holder.student_percentage.setText(mylist.getStudentPercentage());
        holder.student_email.setText(mylist.getStudentEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView company_name, company_location, job_description, student_name, student_branch, student_percentage, student_email;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.tv_selstd_company);
            company_location = itemView.findViewById(R.id.tv_selstd_complocation);
            job_description = itemView.findViewById(R.id.tv_selstd_description);
            student_name = itemView.findViewById(R.id.tv_selstd_stdname);
            student_branch = itemView.findViewById(R.id.tv_selstd_stdbranch);
            student_percentage = itemView.findViewById(R.id.tv_selstd_stdpercentage);
            student_email = itemView.findViewById(R.id.tv_selstd_stdemail);
        }
    }
}
