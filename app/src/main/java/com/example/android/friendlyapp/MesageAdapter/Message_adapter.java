package com.example.android.friendlyapp.MesageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.friendlyapp.Message;
import com.example.android.friendlyapp.R;
import com.example.android.friendlyapp.databinding.ItemReceiveBinding;
import com.example.android.friendlyapp.databinding.ItemSendBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Message_adapter extends  RecyclerView.Adapter{
    Context context;
    ArrayList<Message> messages;


    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    public Message_adapter(Context context, ArrayList<Message> messages)
    {
        this.context = context;
        this.messages = messages;
    }

    //------------------------------------------------------------//
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
            return new SendViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }

    }
    //SET DATA IN VIEW_TYPE
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if(holder.getClass()==SendViewHolder.class)
        {
            ((SendViewHolder) holder).binding.message.setText(message.getMessage());
        }
        else
        {
            ((ReceiverViewHolder)holder).binding2.message.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SendViewHolder extends RecyclerView.ViewHolder
    {
        ItemSendBinding binding;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemSendBinding.bind(itemView);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder
    {
        ItemReceiveBinding binding2;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding2=ItemReceiveBinding.bind(itemView);
        }
    }
}
