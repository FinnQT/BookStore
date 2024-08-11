package com.example.do_an_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class donhangcuatoi_Adapter extends RecyclerView.Adapter<donhangcuatoi_Adapter.donhangcuatoiViewHolder>{

    private List<Donhang> mlistdonhang;



    public donhangcuatoi_Adapter() {
    }
    public donhangcuatoi_Adapter(List<Donhang> mlistdonhang) {
        this.mlistdonhang = mlistdonhang;
    }


    @NonNull
    @Override
    public donhangcuatoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donhang,parent,false);

        return new donhangcuatoiViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(mlistdonhang!=null)
        {
            return mlistdonhang.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull donhangcuatoiViewHolder holder, int position) {
        int a=position;
        Donhang donhang=mlistdonhang.get(position);
        if(donhang==null){
            return;
        }
        List<Book_demo1> listdonmua=donhang.getSachmua();
        String donmuahang="";
        for(int i=0;i<listdonmua.size();i++){
            donmuahang=donmuahang+listdonmua.get(i).getName()+"\n Số lượng: "+listdonmua.get(i).getAmount()+
                    "\nGiá Tiền: "+listdonmua.get(i).getPrice()+"\n";
        }

        holder.tv_tendonhang.setText(donhang.getMadonhang());
        holder.tv_tongtiendonhang.setText(String.valueOf(donhang.getTongtien()));
        holder.tv_tinhtrang.setText(donhang.getTrangthai());
        holder.tv_tenkhachhangmua.setText(donhang.getTenkhachhang());
        holder.tv_sdtmua.setText(donhang.getSdt());
        holder.tv_diachi.setText(donhang.getDiachi());
        holder.tv_donmua.setText(donmuahang);

        holder.btn_danhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xacnhannhanhang(a);
            }
        });


    }

    private void Xacnhannhanhang(int position) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("donhang");
        String a=" Đã nhận hàng";
        HashMap<String,Object> result= new HashMap<>();
        result.put("trangthai",a);
        myRef.child(user.getUid()).child(String.valueOf(position)).updateChildren(result);
    }


    public class donhangcuatoiViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tendonhang;
        private TextView tv_tongtiendonhang;
        private TextView tv_tinhtrang;
        private TextView tv_tenkhachhangmua;
        private TextView tv_sdtmua;
        private TextView tv_diachi;
        private TextView tv_donmua;
        private TextView tv_ngaydat;



        private Button btn_danhanhang;
        public donhangcuatoiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tendonhang=itemView.findViewById(R.id.tv_tendonhang);
            tv_tongtiendonhang=itemView.findViewById(R.id.tv_tongtiendonhang);
            tv_tinhtrang=itemView.findViewById(R.id.tv_tinhtrangdonhang);
            btn_danhanhang=itemView.findViewById(R.id.btn_xacnhannhanhang);
            tv_tenkhachhangmua=itemView.findViewById(R.id.tv_tenkhachhang);
            tv_sdtmua=itemView.findViewById(R.id.tv_sdtmua);
            tv_diachi=itemView.findViewById(R.id.tv_diachimua);

            tv_donmua=itemView.findViewById(R.id.tv_donmua);
            tv_ngaydat=itemView.findViewById(R.id.tv_ngaydat);
        }
    }
}
