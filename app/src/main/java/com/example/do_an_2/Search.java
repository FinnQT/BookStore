package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter1 mbookAdapter;
    private List<Book_demo> list;
    private String search;
    private TextView tv_kotimthay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle bundle1= new Bundle();
        bundle1=getIntent().getExtras();
        if(bundle1==null){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            return;
        }
        search= (String) bundle1.get("search");
        recyclerView=findViewById(R.id.rcv_serach_bookk);
        tv_kotimthay=findViewById(R.id.thongbaoktimthay);
        list = new ArrayList<>();
        GetListBook();

        mbookAdapter= new BookAdapter1(list, this, new IClickItemListener() {
            @Override
            public void onClickitem(Book_demo book_demo) {
                onClickitemBook(book_demo);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mbookAdapter);
        if(list.size()==0)
        {
            Toast.makeText(this, "Không tìm thấy sách này", Toast.LENGTH_LONG).show();
        }
    }
    private void GetListBook() {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("sachdemo");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo book_demo= snapshot.getValue(Book_demo.class);
                if(book_demo!=null){
                    if(book_demo.getName().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))){
                        list.add(book_demo);
                    }
                }
                mbookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo book_demo=snapshot.getValue(Book_demo.class);
                if(book_demo==null||list==null||list.isEmpty()){
                    return;
                }
                for(int i=0;i<list.size();i++)
                {
                    if(book_demo.getId()==list.get(i).getId())
                    {
                        list.set(i,book_demo);
                    }
                }
                mbookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Book_demo book_demo=snapshot.getValue(Book_demo.class);
                if(book_demo==null||list==null||list.isEmpty()){
                    return;
                }
                for(int i=0;i<list.size();i++)
                {
                    if(book_demo.getId()==list.get(i).getId())
                    {
                        list.remove(list.get(i));
                        break;
                    }
                }
                mbookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClickitemBook(Book_demo book_demo) {
        Intent intent = new Intent(this,Detail_Book.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("object_book", (Serializable) book_demo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }
}