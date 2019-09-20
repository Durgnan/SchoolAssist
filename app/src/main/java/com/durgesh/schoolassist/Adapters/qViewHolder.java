package com.durgesh.schoolassist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.durgesh.schoolassist.R;

public class qViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    EditText editText;
    Button button;
    String username;
    public qViewHolder(@NonNull View itemView, String username, Context context) {
        super(itemView);
        imageView = itemView.findViewById(R.id.downloaded_image);
        editText = itemView.findViewById(R.id.editText);
        button=itemView.findViewById(R.id.submit_button);
        this.username=username;

    }
}
