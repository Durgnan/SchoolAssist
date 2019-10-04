package com.durgesh.schoolassist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.durgesh.schoolassist.R;

import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    List<StudentData> list = Collections.emptyList();
    Context context;
    int i=0;
    String username;
    public RecyclerAdapter(List<StudentData> list,Context context,String username)
    {
        this.list = list;
        this.context = context;
        this.username = username;

    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.recycler_card,viewGroup,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(photoView,username,this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.sname.setText(list.get(i).sname);
        recyclerViewHolder.username = list.get(i).sid;
        String[] array = {"Select One","Rubrics","Theory Rubrics","Practicals"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recyclerViewHolder.spinner.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


