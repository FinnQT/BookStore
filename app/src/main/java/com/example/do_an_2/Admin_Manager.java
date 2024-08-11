package com.example.do_an_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_Manager extends AppCompatActivity {
    Button quanlysach;
    Button quanlyreviewsach;
    Button quanlydonhang;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);
        quanlysach=findViewById(R.id.btn_quanlysach);
//        quanlyreviewsach=findViewById(R.id.btn_quanlyreviewsach);
//        quanlydonhang=findViewById(R.id.quanlydonhang);
        Logout=findViewById(R.id.btn_logout2);

        quanlysach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuanLysach();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Manager.this,login.class);
                startActivity(intent);
                finishAffinity();
            }
        });
//        quanlydonhang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Donhangquanly();
//            }
//        });
    }

    private void Donhangquanly() {
        Intent intent= new Intent(this,QuanLyDonHang.class);
        startActivity(intent);
    }

    private void QuanLysach() {
        Intent intent= new Intent(this,QuanLySanPham.class);
        startActivity(intent);
    }
}