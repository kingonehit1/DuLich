package com.example.dulich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    List<ModeUsers> usersList;

    public AdapterUsers(Context context, List<ModeUsers> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String userImage = usersList.get(i).getImage();
        String userName = usersList.get(i).getName();
        String userEmail = usersList.get(i).getEmail();

        myHolder.mNameTv.setText(userName);
        myHolder.mEmailTV.setText(userEmail);
        try{
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_default_img_asd).into(myHolder.mAvatarIv);
        }
        catch (Exception e){

        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvatarIv;
        TextView mNameTv, mEmailTV;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //Anh xa
            mAvatarIv = itemView.findViewById(R.id.avatar_user_IV);
            mNameTv = itemView.findViewById(R.id.name_user_TV);
            mEmailTV = itemView.findViewById(R.id.email_user_TV);


        }
    }
}
