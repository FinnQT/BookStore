package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Detail_Book extends AppCompatActivity {


    private TextView tv_price_getted;
    private ImageView imageView_getted;
    private TextView tv_nameGetted;
    private TextView tv_authorGettted;
    private TextView tv_Review_getted;
    private Book_demo book_getted;
    Button button_down;
    Button button_up;
    TextView textView_count;
    Button button_addToCart;
    Button button_BuyBook;
    EditText editText_vote;
    Button button_comment_vote;
    RecyclerView recyclerViewComment;
    private Book_demo1 Book_to_get_amount;
    static int soluong=1;
    int soluong2;
    int m;
    List<Commment> listcomment= new ArrayList<>();
    List<Donhang> listdonhang= new ArrayList<>();
    AdapterComment adapterComment;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        // get du lieu detail
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            return;
        }

        book_getted= (Book_demo) bundle.get("object_book");

        imageView_getted=findViewById(R.id.detail_image);
        tv_price_getted = findViewById(R.id.tv_price_detail);
        tv_nameGetted = findViewById(R.id.tv_nameBook_getted);
        tv_authorGettted = findViewById(R.id.tv_author_gettted);
        tv_Review_getted=findViewById(R.id.tv_reviewTextGetted);
        button_down=findViewById(R.id.btn_down);
        button_up=findViewById(R.id.btn_up);
        textView_count=findViewById(R.id.tv_count);
        button_addToCart=findViewById(R.id.btn_addToCart);
        button_BuyBook=findViewById(R.id.btn_Buy);
        editText_vote=findViewById(R.id.edt_vote);
        button_comment_vote=findViewById(R.id.btn_comment_vote);
        recyclerViewComment=findViewById(R.id.rcv_comment_vote);
        Glide.with(this).load(book_getted.getImage()).error(R.drawable.ic_no_image).into(imageView_getted);
        tv_price_getted.setText(String.valueOf(book_getted.getPrice()));
        tv_nameGetted.setText(book_getted.getName());
        tv_authorGettted.setText(book_getted.getAuthor());
        textView_count.setText(String.valueOf(soluong));
        get();
        GetSoluongMua();
        getlistComment();

        button_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addtocart();
            }
        });
        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownBookButton();

            }
        });
        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddBookButton();

            }
        });
        button_BuyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyBookButton();
            }
        });

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(linearLayoutManager);
        adapterComment= new AdapterComment(listcomment);
        recyclerViewComment.setAdapter(adapterComment);
        button_comment_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickComment();
            }
        });

        GetReview();

    }

    private void OnClickComment() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("comment");

        Commment cmt=new Commment(editText_vote.getText().toString(),user.getDisplayName().toString());
        myRef.child(book_getted.getId()).child(String.valueOf(listcomment.size())).setValue(cmt, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Detail_Book.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getlistComment() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("comment");
        myRef.child(book_getted.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Commment cmt=snapshot.getValue(Commment.class);
                if(cmt!=null){
                    listcomment.add(cmt);
                }
                adapterComment.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Commment cmt=snapshot.getValue(Commment.class);
                for(int i=0;i<listcomment.size();i++)
                {
                    if(cmt.getUid().equals(listcomment.get(i).getUid()))
                    {
                        listcomment.set(i,cmt);
                    }

                }
                adapterComment.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Commment cmt=snapshot.getValue(Commment.class);
                for(int i=0;i<listcomment.size();i++)
                {
                    if(cmt.getUid().equals(listcomment.get(i).getUid()))
                    {
                        listcomment.remove(listcomment.get(i));
                        break;
                    }

                }
                adapterComment.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void BuyBookButton() {
        View viewDialog=getLayoutInflater().inflate(R.layout.layout_address,null);
        BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        TextView tongtien= bottomSheetDialog.findViewById(R.id.tv_tongtienmua);
        EditText diachi=bottomSheetDialog.findViewById(R.id.edt_diachi);
        EditText dienthoai=bottomSheetDialog.findViewById(R.id.edt_dienthoai);
        Button btn_muahang=bottomSheetDialog.findViewById(R.id.btn_buyDialog);

        int a= (int) (soluong*book_getted.getPrice());
        tongtien.setText(String.valueOf(a));
        bottomSheetDialog.show();
        btn_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diachimua=diachi.getText().toString();
                String dienthoaimua=dienthoai.getText().toString();
                if(diachimua.equals("")||dienthoaimua.equals("")){
                    Toast.makeText(Detail_Book.this, "Vui Lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }if(book_getted.getAmount()-soluong<1){
                    Toast.makeText(Detail_Book.this, "chọn vượt quá sách trong kho", Toast.LENGTH_SHORT).show();
                }if(book_getted.getAmount()<1){
                    Toast.makeText(Detail_Book.this, "đã bán hết", Toast.LENGTH_SHORT).show();
                }else{
                MuaHang(diachimua,dienthoaimua,a,bottomSheetDialog);}
            }
        });

    }

    private  void GetSoluongMua() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("donhang");
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Donhang a=dataSnapshot1.getValue(Donhang.class);
                    listdonhang.add(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void MuaHang(String diachimua, String dienthoaimua, int a, BottomSheetDialog bottomSheetDialog) {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Book_demo1 book_demo1= new Book_demo1(a,book_getted.author,false,book_getted.id,book_getted.image,
                book_getted.name,book_getted.price,book_getted.type);
        List<Book_demo1> list= new ArrayList<>();
        list.add(book_demo1);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("donhang");
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        Donhang donhang= new Donhang(currentDate,diachimua,"BSMDH"+listdonhang.size(),list,dienthoaimua,user.getDisplayName(),
                a,"Đang chuẩn bị hàng");
        myRef.child(user.getUid()).child(String.valueOf(listdonhang.size())).setValue(donhang, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Detail_Book.this, "success", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                GiamSoLuong(a);
            }
        });
    }

    private void GiamSoLuong(int a) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("sachdemo");
        HashMap<String,Object> result= new HashMap<>();
        result.put("amount",book_getted.getAmount()-soluong);
        myRef.child(book_getted.getId()).updateChildren(result);
    }



    public void get() {
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference("cart");
    myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                Book_demo1 a=dataSnapshot1.getValue(Book_demo1.class);
                if(a.getId().equals(book_getted.getId()))
                {
                    soluong2=a.getAmount();
                }
            }}
        @Override
        public void onCancelled(DatabaseError error) {
            Toast.makeText(Detail_Book.this, "loi", Toast.LENGTH_SHORT).show();
        }
    });

}



    private void Addtocart() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        Book_demo1 bookaddtocart= new Book_demo1(soluong+soluong2
                ,book_getted.author,false,book_getted.id,book_getted.image,
                book_getted.name,book_getted.price,book_getted.type);
        myRef.child(user.getUid()).child(book_getted.getId()).setValue(bookaddtocart, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Detail_Book.this, "success", Toast.LENGTH_SHORT).show();
                soluong=1;
                textView_count.setText(String.valueOf(soluong));
            }
        });
    }

    private void DownBookButton() {
        if(soluong>1)
        {
            soluong=soluong-1;
            textView_count.setText(String.valueOf(soluong));
        }else{
            Toast.makeText(this, "Số lượng không bé hơn 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddBookButton() {

        if(soluong<book_getted.getAmount()){
            soluong=soluong+1;
            textView_count.setText(String.valueOf(soluong));
        }else{
            Toast.makeText(this, "Vượt quá số lượng sách trong kho", Toast.LENGTH_SHORT).show();
        }

    }





    private void GetReview() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("review");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ReviewBook reviewBook =snapshot.getValue(ReviewBook.class);
                if(reviewBook!=null)
                {
                    if(book_getted.getId().equals(reviewBook.getId()))
                    {
                        tv_Review_getted.setText(reviewBook.getText());
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ReviewBook reviewBook =snapshot.getValue(ReviewBook.class);
                if(reviewBook!=null)
                {
                    if(book_getted.getId().equals(reviewBook.getId()))
                    {
                        tv_Review_getted.setText(reviewBook.getText());
                    }

                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ReviewBook reviewBook =snapshot.getValue(ReviewBook.class);
                if(reviewBook!=null)
                {
                    if(book_getted.getId().equals(reviewBook.getId()))
                    {
                        tv_Review_getted.setText("");
                    }
                }

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