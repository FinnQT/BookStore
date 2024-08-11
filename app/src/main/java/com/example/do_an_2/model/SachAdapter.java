package com.example.do_an_2.model;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_2.Book;
import com.example.do_an_2.IClickItemListener;
import com.example.do_an_2.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SachAdapter extends RecyclerView.Adapter<SachAdapter.BookViewHolder> {
    private List<sach> msach;
    private Activity mActivity;
    private final IClickItemListener iClickItemListener;



    public SachAdapter(List<sach> sach, Activity activity, IClickItemListener listener) {
        this.msach = sach;
        this.mActivity=activity;
        this.iClickItemListener=listener;

    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
    final sach sachh=msach.get(position);
    if(sachh==null) {
        return;
    }


    holder.tvTitle.setText(sachh.getTensanpham());
//    holder.layoutitem.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            iClickItemListener.onClickitem();
//        }
//    });
    }


    @Override
    public int getItemCount() {
        if(msach!=null)
        {
            return msach.size();
        }
        return 0;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgBook;
        private TextView tvTitle;
        private LinearLayout layoutitem;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutitem=itemView.findViewById(R.id.layout_item);
            imgBook=itemView.findViewById(R.id.img_book);
            tvTitle=itemView.findViewById(R.id.tv_title);

        }
    }

}
