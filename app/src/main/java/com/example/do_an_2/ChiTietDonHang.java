package com.example.do_an_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ChiTietDonHang extends AppCompatActivity{
    private TextView madonhang;
    private TextView tenkhachhang;
    private TextView sodienthoai;
    private TextView diachi;
    private TextView email;
    private TextView donmua;
    private TextView ngaydat;
    private TextView tongtien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        madonhang.findViewById(R.id.tv_madonhang);
        tenkhachhang.findViewById(R.id.tv_tenkhachhang);
        sodienthoai.findViewById(R.id.tv_sdtmua);
        diachi.findViewById(R.id.tv_diachimua);
        email.findViewById(R.id.tv_emailmua);
        donmua.findViewById(R.id.tv_donmua);
        ngaydat.findViewById(R.id.tv_ngaydat);
        tongtien.findViewById(R.id.tv_chitietTien);
        getlistdonhang();
    }

    private void getlistdonhang() {
    }
}