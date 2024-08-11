package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDonHang extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private donhangcuatoi_Adapter donhangcuatoi_adapter;
    private List<Donhang> listdonhang;
    private static List<user_donhang> listuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        recyclerView1=findViewById(R.id.rcv_quanlydonhang);
        listdonhang= new ArrayList<>();
        listuser= new ArrayList<>();
        GetlistBook();
        donhangcuatoi_adapter= new donhangcuatoi_Adapter(listdonhang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(donhangcuatoi_adapter);
        Toast.makeText(this, ""+listuser.size(), Toast.LENGTH_SHORT).show();
        for(int i=0;i<listuser.size();i++){
            for(int j=0;j<listuser.get(i).getList().size();j++){
                listdonhang.add((listuser.get(i).getList().get(j)));
            }
        }
        Toast.makeText(this, ""+listdonhang.size(), Toast.LENGTH_SHORT).show();
    }


    private void GetlistBook() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("donhang");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listuser.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    user_donhang user_donhang=snapshot.getValue(user_donhang.class);
                    if(user_donhang!=null){
                        listuser.add(user_donhang);
                    }
                    Toast.makeText(QuanLyDonHang.this, ""+listuser.size(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}