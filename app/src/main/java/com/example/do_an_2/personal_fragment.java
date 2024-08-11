package com.example.do_an_2;



import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class personal_fragment extends Fragment {
    View view;
    Button btn_Logout;
    Button btn_editinfo;
    Button btn_updatePassword;
    ImageView imageView_user;
    TextView tv_name_user1;
    TextView tv_gmail_user1;
    TextView tv_name_user2;
    TextView tv_gmail_user2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_personnal,container,false);

        imageView_user=view.findViewById(R.id.image_user);
        tv_name_user1=view.findViewById(R.id.name_user1);
        tv_gmail_user1=view.findViewById(R.id.gmail_user1);
        tv_name_user2=view.findViewById(R.id.name_user2);
        tv_gmail_user2=view.findViewById(R.id.gmail_user2);
        btn_Logout=view.findViewById(R.id.btn_logout);
        btn_editinfo=view.findViewById(R.id.btn_edit_info);
        btn_updatePassword=view.findViewById(R.id.btn_update_password);

        showUserInformation();
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btn_editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),edit_profile.class);
                startActivity(intent);
            }
        });

        btn_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),Change_password.class);
                startActivity(intent);
            }
        });


        return view;
    }

    //log out
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(getActivity(),login.class);
        startActivity(intent);
        MainActivity mainActivity= (MainActivity) getActivity();
        mainActivity.finish();
    }

    //show info trong fragmen personal

    private void showUserInformation(){

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        tv_name_user1.setText(name);
        tv_name_user2.setText(name);
        tv_gmail_user1.setText(email);
        tv_gmail_user2.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_no_image).centerCrop().into(imageView_user);
    }

}
