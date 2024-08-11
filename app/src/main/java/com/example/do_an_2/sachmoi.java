package com.example.do_an_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an_2.api.ApiService;
import com.example.do_an_2.model.SachAdapter;
import com.example.do_an_2.model.sach;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sachmoi extends AppCompatActivity {
    private TextView tvid;
    private TextView tvtensach;
    private TextView tvgiatien;
    private Button btncallaip;
    private RecyclerView recyclerView_loadanh;
    BookAdapter bookAdapter;

    private  List<sach> mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sachmoi);
    }

}