package com.example.android.friendlyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    String URL;
    ArrayList<FriendlyMessage> articleArrayList;
    private Context context;

    public Adapter(ArrayList<FriendlyMessage> articleArrayList)
    {
        this.articleArrayList = articleArrayList;
    }

    @Override
    public MyViewHolder  onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final FriendlyMessage articleModel = articleArrayList.get(position);
        holder.titleText.setText(articleModel.getText());
        holder.descriptionText.setText(articleModel.getName());

    }



    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descriptionText;

        MyViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.messageTextView);
            descriptionText = view.findViewById(R.id.nameTextView);

        }
    }
}
