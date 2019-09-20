package com.durgesh.schoolassist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.durgesh.schoolassist.PracticalsActivity;
import com.durgesh.schoolassist.QuizActivity;
import com.durgesh.schoolassist.R;
import com.durgesh.schoolassist.RubricsActivity;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements   AdapterView.OnItemSelectedListener {
    TextView sname;
    Spinner spinner;

    String username;

    public RecyclerViewHolder(@NonNull View itemView, String username, Context context) {
        super(itemView);
        sname = itemView.findViewById(R.id.text_recycler);
        spinner = itemView.findViewById(R.id.spinner_recycler);
        this.username=username;
        spinner.setOnItemSelectedListener(this);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
        {
            //Do Nothing
        }
        else if(position==1)
        {
            Intent i = new Intent(view.getContext(), RubricsActivity.class).putExtra("username",username).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(i);
        }
        else if(position==2)
        {
            Intent i = new Intent(view.getContext(), QuizActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("username",username);
            view.getContext().startActivity(i);
        }
        else if(position==3)
        {
            Intent i = new Intent(view.getContext(), PracticalsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("username",username);
            view.getContext().startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
