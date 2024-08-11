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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DonHangDaMua extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private donhangcuatoi_Adapter donhangcuatoi_adapter;
    private List<Donhang> listdonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_da_mua);
        recyclerView1=findViewById(R.id.rcv_donhangok);
        listdonhang= new ArrayList<>();
        GetlistBook();

        donhangcuatoi_adapter= new donhangcuatoi_Adapter(listdonhang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(donhangcuatoi_adapter);


    }

    private void onClickitemBook(Donhang donhang) {
        Intent intent= new Intent(DonHangDaMua.this,ChiTietDonHang.class);;
        startActivity(intent);
    }

    private void GetlistBook() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("donhang");
        myRef.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Donhang mdonhang= snapshot.getValue(Donhang.class);
                if(mdonhang!=null){
                    listdonhang.add(mdonhang);
                }
                donhangcuatoi_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Donhang mdonhang= snapshot.getValue(Donhang.class);
                if(mdonhang==null||listdonhang==null||listdonhang.isEmpty()){
                    return;
                }

                for(int i=0;i<listdonhang.size();i++)
                {
                    if(mdonhang.getMadonhang().equals(listdonhang.get(i).getMadonhang()))
                    {
                        listdonhang.set(i,mdonhang);
                    }
                }
                donhangcuatoi_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Donhang mdonhang= snapshot.getValue(Donhang.class);
                if(mdonhang==null||listdonhang==null||listdonhang.isEmpty()){
                    return;
                }

                for(int i=0;i<listdonhang.size();i++)
                {
                    if(mdonhang.getMadonhang().equals(listdonhang.get(i).getMadonhang()))
                    {
                        listdonhang.remove(listdonhang.get(i));
                    }
                }
                donhangcuatoi_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}