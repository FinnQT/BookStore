package com.example.do_an_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class home_fragment extends Fragment {
    ProgressDialog progressDialog;
    private View view;
    private RecyclerView recyclerView;
    private BookAdapter mbookAdapter;
    private BookAdapter mbookAdapter1;
    private BookAdapter mbookAdapter2;
    private List<Book_demo> list;
    private List<Book_demo> list1;
    private List<Book_demo> list2;
    private ViewPager2 viewPager2Slsider;
    private CircleIndicator3 mcircleIndicator3;
    private List<photoslider> mlistphoto;
    private PhotoSliderAdapter photoSliderAdapter;
    private Handler handler= new Handler();
    private Button btn_search;
    private EditText edit_searchBook;

    private Runnable runnable= new Runnable() {
        @Override
        public void run() {
            if(viewPager2Slsider.getCurrentItem()==mlistphoto.size()-1)
            {
                viewPager2Slsider.setCurrentItem(0);
            } else{
                viewPager2Slsider.setCurrentItem(viewPager2Slsider.getCurrentItem()+1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home,container,false);
        edit_searchBook=view.findViewById(R.id.edt_serch_book);
        btn_search=view.findViewById(R.id.btn_search_book);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TruyenKey();
            }
        });
        //xu ly silder
        viewPager2Slsider=view.findViewById(R.id.viewpager_slider);
        mcircleIndicator3=view.findViewById(R.id.circle_indicator3);
        mlistphoto=getlistPhoto();
        //setting viewpager 2
        photoSliderAdapter= new PhotoSliderAdapter(mlistphoto);
        viewPager2Slsider.setAdapter(photoSliderAdapter);
        mcircleIndicator3.setViewPager(viewPager2Slsider);
        //xu ly view san pham
        progressDialog= new ProgressDialog(getContext());
        recyclerView=view.findViewById(R.id.rcv_book);
        list=new ArrayList<>();
        list1= new ArrayList<>();
        list2= new ArrayList<>();
        GetListBook();
        mbookAdapter= new BookAdapter(list, getActivity(), new IClickItemListener() {
            @Override
            public void onClickitem(Book_demo book_demo) {
                onClickitemBook(book_demo);
            }
        });


        mbookAdapter1= new BookAdapter(list1, getActivity(), new IClickItemListener() {
            @Override
            public void onClickitem(Book_demo book_demo) {
                onClickitemBook(book_demo);
            }
        });


        mbookAdapter2= new BookAdapter(list2, getActivity(), new IClickItemListener() {
            @Override
            public void onClickitem(Book_demo book_demo){
                onClickitemBook(book_demo);
            }
        });


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mbookAdapter);
        //them list danh muc san pham
        RecyclerView recyclerView1=view.findViewById(R.id.rcv_stylebook1);
        RecyclerView recyclerView2=view.findViewById(R.id.rcv_stylebook2);


        LinearLayoutManager linearLayoutManager1= new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager2= new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView1.setAdapter(mbookAdapter1);
        recyclerView2.setAdapter(mbookAdapter2);

        // xu ly autorun
        viewPager2Slsider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);

                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

        return view;

    }

    private void TruyenKey() {
        Intent intent= new Intent(getContext(),Search.class);
        String a= String.valueOf(edit_searchBook.getText());
        Bundle bundle= new Bundle();
        bundle.putSerializable("search",(String) a);
        startActivity(intent);
    }

    private void onClickitemBook(Book_demo book_demo) {

        Intent intent = new Intent(getActivity(),Detail_Book.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("object_book", (Serializable) book_demo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

// slider
    private List<photoslider> getlistPhoto() {
        List<photoslider> list = new ArrayList<>();

        list.add(new photoslider(R.drawable.sale1));
        list.add(new photoslider(R.drawable.sale2));
        list.add(new photoslider(R.drawable.sale3));
        list.add(new photoslider(R.drawable.sale4));
        return list;

    }

    private void GetListBook(){
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sachdemo");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Book_demo book= snapshot.getValue(Book_demo.class);
                if(book!=null)
                {
                    progressDialog.dismiss();
                    if(book.getType().contains("vanhocvietnam"))
                    {
                        list.add(book);
                    }
                    if(book.getType().contains("vanhocnuocngoai"))
                    {
                        list1.add(book);
                    }
                    if(book.getType().contains("sachthieunhi"))
                    {
                        list2.add(book);
                    }

                }
                mbookAdapter.notifyDataSetChanged();
                mbookAdapter1.notifyDataSetChanged();
                mbookAdapter2.notifyDataSetChanged();
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
                for(int i=0;i<list1.size();i++)
                {
                    if(book_demo.getId()==list1.get(i).getId())
                    {
                        list1.set(i,book_demo);
                    }
                }
                for(int i=0;i<list2.size();i++)
                {
                    if(book_demo.getId()==list2.get(i).getId())
                    {
                        list2.set(i,book_demo);
                    }
                }
                mbookAdapter.notifyDataSetChanged();
                mbookAdapter1.notifyDataSetChanged();
                mbookAdapter2.notifyDataSetChanged();

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

                for(int i=0;i<list1.size();i++)
                {
                    if(book_demo.getId()==list1.get(i).getId())
                    {
                        list1.remove(list1.get(i));
                        break;
                    }
                }
                for(int i=0;i<list2.size();i++)
                {
                    if(book_demo.getId()==list2.get(i).getId())
                    {
                        list2.remove(list2.get(i));
                        break;
                    }
                }
                mbookAdapter.notifyDataSetChanged();
                mbookAdapter1.notifyDataSetChanged();
                mbookAdapter2.notifyDataSetChanged();
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
