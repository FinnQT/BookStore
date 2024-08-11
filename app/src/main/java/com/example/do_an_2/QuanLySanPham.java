package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuanLySanPham extends AppCompatActivity {
    private Button btnPush;
    private Button btn_add_new;
    ProgressDialog progressDialog;
    private RecyclerView recyclerViewManagerBook;
    private ManagerBookAdapter managerBookAdapter;
    private List<Book_demo> mlistbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        progressDialog= new ProgressDialog(this);

        recyclerViewManagerBook=findViewById(R.id.rcv_Book_manager);
//        btnPush=findViewById(R.id.btn_push_realtime);
        btn_add_new=findViewById(R.id.btn_add_new_book_manager);



        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        recyclerViewManagerBook.setLayoutManager(linearLayoutManager);
        mlistbook=new ArrayList<>();
        managerBookAdapter= new ManagerBookAdapter(mlistbook,this);
        recyclerViewManagerBook.setAdapter(managerBookAdapter);
        getListBookFromDatabase();
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerViewManagerBook.addItemDecoration(dividerItemDecoration);
//        btnPush.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addListUser();
//            }
//        });
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBook();
            }
        });

    }

    private void addNewBook() {
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_newbook);
        EditText id_book=dialog.findViewById(R.id.edt_id_book);
        EditText bookname=dialog.findViewById(R.id.edt_name_book);
        EditText author=dialog.findViewById(R.id.edt_author);
        EditText type=dialog.findViewById(R.id.edt_type);
        EditText amount=dialog.findViewById(R.id.edt_amount);
        EditText price=dialog.findViewById(R.id.edt_price);
        EditText image=dialog.findViewById(R.id.edt_image);
        Button btn_cancle=dialog.findViewById(R.id.btn_cancle_add);
        Button btn_okeadd=dialog.findViewById(R.id.btn_oke_add);
        id_book.setText(String.valueOf(mlistbook.size()));

        dialog.show();







        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_okeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_add=id_book.getText().toString().trim();
                String name_add=bookname.getText().toString().trim();
                String author_add=author.getText().toString().trim();
                String type_add=type.getText().toString().trim();
                String amount_add=amount.getText().toString().trim();
                int value_amount=0;
                if(!"".equals(amount_add))
                {
                    value_amount=Integer.parseInt(amount_add);
                }
                String pirce_add= price.getText().toString().trim();
                float value_price= 0;
                if(!"".equals(pirce_add))
                {
                    value_price=Float.parseFloat(pirce_add);
                }
                String image_add= image.getText().toString().trim();

                Book_demo book_demo=new Book_demo(value_amount,author_add,id_add,image_add,name_add,value_price,type_add);
                onClickAddBook(book_demo);
                dialog.dismiss();

            }
        });




    }

    private void onClickAddBook(Book_demo book_demo) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("sachdemo");
        String pathObject= book_demo.getId();
        myRef.child(pathObject).setValue(book_demo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(QuanLySanPham.this, "success", Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void addListUser() {
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference myRef=database.getReference("sachdemo");
//        List<Book_demo> list = new ArrayList<>();
//        list.add(new Book_demo(20,"Nguyễn Quang Riệu","0","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/5/22/KEJYZJBV.jpg", "SÔNG NGÂN KHI TỎ KHI MỜ",61000,"vanhocvietnam"));
//        list.add(new Book_demo(20,"Phan Cuồng","1","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/5/9/7YYWZJ3H.jpg", "ĐẠI NAM DỊ TRUYỆN (TB 2019)",92000,"vanhocvietnam"));
//        list.add(new Book_demo(20,"Minh Thi","2","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/9/6/RJ1F7AGY.jpg", "TÔI BỎ QUÊN TÔI Ở NƯỚC ANH (TB 2019)",70000,"vanhocvietnam"));
//        list.add(new Book_demo(20,"Hoàng Nhật","3","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/5/14/NXGRTV9V.jpg", "CÁI NỒI GÌ THẾ?",72000,"vanhocvietnam"));
//        list.add(new Book_demo(20,"Đinh Hằng","4","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/4/9/2C13SJEU.jpg", "CHÂN ĐI KHÔNG MỎI ( TB 2019)",78400,"vanhocvietnam"));
//        list.add(new Book_demo(20,"Jean-Paul Dubois","5","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/7/20/J5213DVZ.jpg", "KHÔNG AI SỐNG GIỐNG AI TRONG CUỘC ĐỜI NÀY",116000,"vanhocnuocngoai"));
//        list.add(new Book_demo(20,"Guy De Maupassant","6","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/7/11/4TH9IWTX.jpg", "SÁNG TRĂNG",60000,"vanhocnuocngoai"));
//        list.add(new Book_demo(20,"Imamura Natsuko","7","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/7/11/UXYZPE2L.jpg", "CÔ GÁI MẶC VÁY TÍM",76000,"vanhocnuocngoai"));
//        list.add(new Book_demo(20,"Maz Evans","8","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/7/11/1B5GKXCF.jpg", "AI THẢ CÁC THẦN RA?",143200,"vanhocnuocngoai"));
//        list.add(new Book_demo(20,"Matt Haig","9","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/9/12/1RJP3DAX.jpg", "LÀM SAO DỪNG LẠI THỜI GIAN",152000,"vanhocnuocngoai"));
//        list.add(new Book_demo(20,"Valentin Verthé","10","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/12/14/XGLMT5IJ.jpg", "HỎI ĐÁP CÙNG EM! - BÓNG ĐÁ!",183000,"sachthieunhi"));
//        list.add(new Book_demo(20,"Phạm Ngọc Điệp minh họa","11","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/9/9/WVTGDXMZ.jpg", "TÔ MÀU PHÁT TRIỂN NÃO BỘ CHO BÉ 1-5 TUỔI TẬP 5",12000,"sachthieunhi"));
//        list.add(new Book_demo(20,"Studio Afra","12","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/4/7/GN3VCU28.jpg", "BÉ YÊU NGOAN NGOÃN GIỎI GIANG – CHĂM MẸ ỐM MỆT",30000,"sachthieunhi"));
//        list.add(new Book_demo(20,"Jinco","13","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2021/12/14/WHXVMR9L.jpg", "EHON KỸ NĂNG SỐNG - CHÀO HỎI LỊCH SỰ",47000,"sachthieunhi"));
//        list.add(new Book_demo(20,"Nhiều tác giả","14","http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/3/21/PS8PULZ6.jpg", "NEVER GET BORED - TÍ TA TÍ TOÁY",125000,"sachthieunhi"));
//
//        myRef.setValue(list, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(QuanLySanPham.this, "add success", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void getListBookFromDatabase()
    {
        progressDialog.show();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("sachdemo");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo book_demo=snapshot.getValue(Book_demo.class);
                if(book_demo!=null)
                {
                    progressDialog.dismiss();
                    mlistbook.add(book_demo);
                }
                managerBookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo book_demo=snapshot.getValue(Book_demo.class);
                if(book_demo==null||mlistbook==null||mlistbook.isEmpty()){
                    return;
                }
                for(int i=0;i<mlistbook.size();i++)
                {
                    if(book_demo.getId()==mlistbook.get(i).getId())
                    {
                        mlistbook.set(i,book_demo);
                        break;
                    }
                }
                managerBookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Book_demo book_demo=snapshot.getValue(Book_demo.class);
                if(book_demo==null||mlistbook==null||mlistbook.isEmpty()){
                    return;
                }
                for(int i=0;i<mlistbook.size();i++)
                {
                    if(book_demo.getId()==mlistbook.get(i).getId())
                    {
                        mlistbook.remove(mlistbook.get(i));
                        break;
                    }
                }
                managerBookAdapter.notifyDataSetChanged();


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