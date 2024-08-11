package com.example.do_an_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class sachbanchay extends AppCompatActivity {
    private RecyclerView rcv_loaisach;
    private BookAdapter bookAdapter_loaisach;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sachbanchay);
        rcv_loaisach=findViewById(R.id.rcv_loaisach);
//        bookAdapter_loaisach= new BookAdapter(getlistbook(), this, new IClickItemListener() {
//            @Override
//            public void onClickitem() {
//                Toast.makeText(sachbanchay.this, "ko co j", Toast.LENGTH_SHORT).show();
//            }
//        });
//        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,3);
//        rcv_loaisach.setAdapter(bookAdapter_loaisach);
//        rcv_loaisach.setLayoutManager(gridLayoutManager);

    }
    private List<Book_demo> getlistbook() {
        List<Book_demo> list= new ArrayList<>();

        return list;
    }



}