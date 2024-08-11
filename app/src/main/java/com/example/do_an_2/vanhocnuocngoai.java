package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class vanhocnuocngoai extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter1 mbookAdapter;
    private List<Book_demo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanhocnuocngoai);


        recyclerView=findViewById(R.id.rcv_vanhocnuocngoai);
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

    }
    private void GetListBook() {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("sachdemo");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo book_demo= snapshot.getValue(Book_demo.class);
                if(book_demo!=null){
                    if(book_demo.getType().contains("vanhocnuocngoai"))
                    {
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

}