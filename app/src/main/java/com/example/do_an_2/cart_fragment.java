package com.example.do_an_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class cart_fragment extends Fragment {

    private View view;
    private List<Book_demo1> list;
    private RecyclerView recyclerView;
    private cart_Adapter cart_adapter;
    private Button btn_Mua;
    private Button btn_xemdonhang;
    private TextView tv_tongtien;
    private double tongtien=0;
    private List<Book_demo1> listbookchecked= new ArrayList<>();
    List<Donhang> listdonhang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_cart,container,false);

        recyclerView=view.findViewById(R.id.rcv_cartBook);
        btn_Mua=view.findViewById(R.id.btn_buyInCart);
        btn_xemdonhang=view.findViewById(R.id.btn_xemdonhang);
        tv_tongtien=view.findViewById(R.id.tv_tongtienInCart);
        list= new ArrayList<>();
        GetlistBook();
        tv_tongtien.setText(String.valueOf(tongtien));
        listdonhang= new ArrayList<>();


        cart_adapter= new cart_Adapter(list,getActivity());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cart_adapter);

        btn_Mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listdonhang.clear();
                GetSoluongMua();
                OnClickBuy();

            }
        });
        btn_xemdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DonHangDaMua.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void OnClickBuy() {
        View viewDialog=getLayoutInflater().inflate(R.layout.layout_address,null);
        BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(viewDialog);
        TextView mtongtien= bottomSheetDialog.findViewById(R.id.tv_tongtienmua);
        EditText diachi=bottomSheetDialog.findViewById(R.id.edt_diachi);
        EditText dienthoai=bottomSheetDialog.findViewById(R.id.edt_dienthoai);
        Button btn_muahang=bottomSheetDialog.findViewById(R.id.btn_buyDialog);
        mtongtien.setText(String.valueOf(tongtien));
        bottomSheetDialog.show();
        btn_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diachimua=diachi.getText().toString();
                String dienthoaimua=dienthoai.getText().toString();
                if(diachimua.equals("")||dienthoaimua.equals("")){
                    Toast.makeText(getContext(), "Vui Lòng Điền Đầy Đủ Thông tin", Toast.LENGTH_SHORT).show();
                }else if((int)tongtien==0){
                    Toast.makeText(getContext(), "Vui Lòng Chọn Sách để mua", Toast.LENGTH_SHORT).show();
                }else{
                MuaHang(diachimua,dienthoaimua,tongtien,bottomSheetDialog);}
            }
        });

    }
    private void MuaHang(String diachimua, String dienthoaimua, double tongtien, BottomSheetDialog bottomSheetDialog) {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("donhang");
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        Donhang donhang= new Donhang(currentDate,diachimua,"BSMDH"+listdonhang.size(),listbookchecked,dienthoaimua,user.getDisplayName(),
                tongtien,"Đang chuẩn bị hàng");
        myRef.child(user.getUid()).child(String.valueOf(listdonhang.size())).setValue(donhang, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                DeleteDonHangDaMua();
                bottomSheetDialog.dismiss();
                Giamsoluong();
            }
        });
    }

    private void Giamsoluong() {

    }

    private void DeleteDonHangDaMua() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        for(int i=0;i<listbookchecked.size();i++) {

            myRef.child(user.getUid()).child(listbookchecked.get(i).getId()).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                }
            });
        }
        tv_tongtien.setText("0");
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


    private void GetlistBook() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cart");
        myRef.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo1 book= snapshot.getValue(Book_demo1.class);
                if(book!=null){
                    list.add(book);
                }
                cart_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book_demo1 book=snapshot.getValue(Book_demo1.class);
                if(book==null||list==null||list.isEmpty()){
                    return;
                }


                for(int i=0;i<list.size();i++)
                {
                    if(book.getId().equals(list.get(i).getId()))
                    {
                        list.set(i,book);
                    }
                }
                cart_adapter.notifyDataSetChanged();
                RefeshTongTien();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                Book_demo1 book=snapshot.getValue(Book_demo1.class);
                if(book==null||list==null||list.isEmpty()){
                    return;
                }
                for(int i=0;i<list.size();i++)
                {
                    if(book.getId().equals(list.get(i).getId()))
                    {
                        list.remove(list.get(i));
                        break;
                    }
                }
                cart_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RefeshTongTien() {
        tongtien=0;
        listbookchecked.clear();
        for(int i=0;i<list.size();i++){
            if(list.get(i).check==true){
                tongtien=tongtien+list.get(i).getAmount()*list.get(i).getPrice();
                listbookchecked.add(list.get(i));
            }
        }
        tv_tongtien.setText(String.valueOf(tongtien));
    }
}
