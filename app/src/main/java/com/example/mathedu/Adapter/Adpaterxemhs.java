package com.example.mathedu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathedu.Model.Students;
import com.example.mathedu.R;

import java.util.List;

public class Adpaterxemhs extends RecyclerView.Adapter<Adpaterxemhs.ViewHolder> {
    private List<Students> list;

    public Adpaterxemhs(List<Students> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public Adpaterxemhs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hs, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adpaterxemhs.ViewHolder holder, int position) {
        Students s = list.get(position);

        if (s != null) {
            holder.txtName.setText(s.hoten);
            holder.txtInfo.setText(s.lop);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtInfo = itemView.findViewById(R.id.txtInfo);
        }
    }
}
