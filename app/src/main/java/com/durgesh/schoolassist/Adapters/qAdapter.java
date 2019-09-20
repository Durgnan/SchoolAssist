package com.durgesh.schoolassist.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durgesh.schoolassist.R;
import com.parse.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class qAdapter extends RecyclerView.Adapter<qViewHolder>  {
    List<QuestionData> list = Collections.emptyList();
    Context context;
    int i=0;
    String username;
    List<String> answers = new ArrayList<>();

    public List<String> getAnswers() {
        return answers;
    }

    public qAdapter(List<QuestionData> list, Context context, String username) {
        this.list = list;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public qViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.questions_card,viewGroup,false);
        qViewHolder viewHolder = new qViewHolder(photoView,username,this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final qViewHolder qViewHolde, final int i) {
        try {
            File file =list.get(i).getFile().getFile();
            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());


                qViewHolde.imageView.setImageBitmap(myBitmap);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        qViewHolde.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               answers.add(i,(i+1)+" : "+String.valueOf(qViewHolde.editText.getText()));
               qViewHolde.button.setEnabled(false);
               // Log.e("Message", String.valueOf(qViewHolde.editText.getText()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
